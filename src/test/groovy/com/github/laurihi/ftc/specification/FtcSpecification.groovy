package com.github.laurihi.ftc.specification

import com.github.laurihi.ftc.ftcservice.model.challenge.CreateChallenge
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.ftcservice.persistence.data.ExerciseCategory
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise
import com.github.laurihi.ftc.ftcservice.persistence.data.actions.Action
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDate

class FtcSpecification extends Specification {

    @Builder(builderStrategy = ExternalStrategy, forClass = Participant)
    class ParticipantBuilder {}

    @Builder(builderStrategy = ExternalStrategy, forClass = Challenge)
    class ChallengeBuilder {}

    @Builder(builderStrategy = ExternalStrategy, forClass = CreateChallenge)
    class CreateChallengeBuilder {}

    @Builder(builderStrategy = ExternalStrategy, forClass = RatedExercise)
    class RatedExerciseBuilder {}

    @Builder(builderStrategy = ExternalStrategy, forClass = Action)
    class ActionBuilder {}

    @Autowired(required = false)
    ChallengeRepository challengeRepository

    def challengeBuilder = new ChallengeBuilder()
    def ratedExerciseBuilder = new RatedExerciseBuilder()
    def participantBuilder = new ParticipantBuilder()
    def actionBuilder = new ActionBuilder()
    def createChallengeBuilder = new CreateChallengeBuilder()


    def persistChallengeWithExerciseAndParticipant(String challengeName, String exerciseName, String userHandle){
        def challenge = createChallengeWithRatedExercise(challengeName, exerciseName)
        def participant = createParticipant(userHandle)
        challenge.addParticipant(participant)
        return challengeRepository.save(challenge)
    }

    def createParticipant(String userHandle){
        return participantBuilder.userHandle(userHandle).build()
    }

    def createChallengeWithRatedExercise(String challengeName, String exerciseName){

        def challenge = createChallenge(challengeName)
        def exercise = createRatedExercise(exerciseName)
        challenge.exercises << exercise
        return challenge
    }

    def createChallenge(String name) {
        def start = LocalDate.now().minusDays(1)
        def end = LocalDate.now().plusDays(4)

        Challenge challenge = new Challenge()
        challenge.setName(name)
        challenge.setStartDate(start)
        challenge.setEndDate(end)
        return challenge
    }

    def createRatedExercise(String name) {
        RatedExercise exercise = new RatedExercise()
        exercise.exerciseKey = name
        exercise.category = ExerciseCategory.COMMUTING
        return exercise
    }
}
