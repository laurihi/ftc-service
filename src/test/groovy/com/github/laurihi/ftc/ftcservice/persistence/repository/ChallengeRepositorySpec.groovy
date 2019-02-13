package com.github.laurihi.ftc.ftcservice.persistence.repository

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification
import spock.lang.Unroll

import javax.transaction.Transactional
import java.time.LocalDate

@SpringBootTest
@Profile("test")
class ChallengeRepositorySpec extends Specification {


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
        1                   | _
        4                   | _
        7                   | _
        21                  | _
    }

    def createChallenge(String name) {
        def start = LocalDate.now().plusDays(1)
        def end = LocalDate.now().plusDays(4)

        Challenge challenge = new Challenge()
        challenge.setName(name)
        challenge.setLaunchDate(start)
        challenge.setEndDate(end)
        return challenge
    }
}
