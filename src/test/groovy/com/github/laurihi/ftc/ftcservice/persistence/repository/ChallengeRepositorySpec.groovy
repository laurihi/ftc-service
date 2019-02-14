package com.github.laurihi.ftc.ftcservice.persistence.repository

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.specification.FtcSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Unroll

import javax.transaction.Transactional
import java.time.LocalDate

@SpringBootTest
@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChallengeRepositorySpec extends FtcSpecification {


    @Autowired
    private ChallengeRepository challengeRepository

    @Unroll
    @Transactional
    def "Repository saves #name and can get it"() {

        when: "challenge by #name initialised and saved"
        Challenge challenge = new Challenge()
        challenge.setName(name)
        def start = LocalDate.now().plusDays(1)
        def end = LocalDate.now().plusDays(4)

        challenge.setLaunchDate(start)
        challenge.setEndDate(end)

        then: "Challenge saved and fetched from the repo by id"
        def saved = challengeRepository.save(challenge)
        def challengeId = saved.id;
        def fromRepo = challengeRepository.getOne(challengeId)

        expect: "challenge fetched from repo to equal saved"
        saved.getId() == fromRepo.getId()
        saved.getName() == name

        where:
        name                        | _
        "Ultimate challenge"        | _
        "More ultimater challenge"  | _
        "Most ultimatest challenge" | _
    }

    @Unroll
    @Transactional
    def "#amountOfChallenges challenges can be saved"() {

        when: "Initialise #amountOfChallenges challenges"
        def challenges = []
        amountOfChallenges.times {
            challenges << createChallenge("Challenge " + it)
        }
        then: "All challenges saved"
        challenges.each {
            challengeRepository.save(it)
        }

        expect: "Find all should find #amountOfChallenges challenges"
        challengeRepository.findAll().size() == amountOfChallenges

        where:
        amountOfChallenges | _
        1                  | _
        4                  | _
        7                  | _
        21                 | _
    }

    @Unroll
    @Transactional
    def "Rated exercises #exerciseName is saved and fetched as a part of challenge"() {

        when: "challenge initialised and exercises added"
        def challenge = createChallenge(challengeName)
        def exercise = createRatedExercise(exerciseName)
        challenge.exercises << exercise

        then: "Challenge saved and fetched from the repo by id"
        def saved = challengeRepository.save(challenge)
        def challengeId = saved.id
        def fromRepo = challengeRepository.getOne(challengeId)

        expect: "challenge fetched from repo to contain the exercise"
        fromRepo.exercises.size() == 1
        def ratedExercise = fromRepo.exercises.get(0)
        ratedExercise.exerciseKey == exerciseName

        where:
        challengeName               | exerciseName
        "Ultimate challenge"        | "Exercise name"
        "More ultimater challenge"  | "Another exercise"
        "Most ultimatest challenge" | "Exercise name"
    }


    @Unroll
    @Transactional
    def "Multiple rated exercises (#amountOfExercises) can be saved"() {

        when: "challenge initialised and exercises added"
        def challenge = createChallenge(challengeName)
        amountOfExercises.times {
            challenge.exercises << createRatedExercise("Exercise " + it)
        }

        then: "Challenge saved and fetched from the repo by id"
        def saved = challengeRepository.save(challenge)
        def challengeId = saved.id
        def fromRepo = challengeRepository.getOne(challengeId)

        expect: "challenge fetched from repo to contain the exercise"
        fromRepo.exercises.size() == amountOfExercises

        where:
        challengeName               | amountOfExercises
        "Ultimate challenge"        | 1
        "More ultimater challenge"  | 7
        "Most ultimatest challenge" | 21
    }


    @Unroll
    @Transactional
    def "Rated exercises fields are persisted correctly"() {

        when: "challenge initialised and exercises added"
        def challenge = createChallenge(challengeName)
        def ratedExercise = createRatedExercise("exercise")
        ratedExercise.pointsPerUnit = 5
        ratedExercise.unit = "km"
        challenge.exercises << ratedExercise

        then: "Challenge saved and fetched from the repo by id"
        def saved = challengeRepository.save(challenge)
        def challengeId = saved.id
        def fromRepo = challengeRepository.getOne(challengeId)

        expect: "Rated exercise contains the correct details"

        fromRepo.exercises[0].unit == 'km'
        fromRepo.exercises[0].pointsPerUnit == 5

        where:
        challengeName               | _
        "Ultimate challenge"        | _
    }


}
