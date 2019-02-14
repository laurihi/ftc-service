package com.github.laurihi.ftc.specification

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise
import groovy.transform.builder.Builder
import groovy.transform.builder.ExternalStrategy
import spock.lang.Specification

import java.time.LocalDate

class FtcSpecification extends Specification {

    @Builder(builderStrategy = ExternalStrategy, forClass = Challenge)
    class ChallengeBuilder {}

    @Builder(builderStrategy = ExternalStrategy, forClass = RatedExercise)
    class RatedExerciseBuilder {}

    def challengeBuilder = new ChallengeBuilder()
    def ratedExerciseBuilder = new RatedExerciseBuilder()


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
        challenge.setLaunchDate(start)
        challenge.setEndDate(end)
        return challenge
    }

    def createRatedExercise(String name) {
        RatedExercise exercise = new RatedExercise()
        exercise.exerciseKey = name
        return exercise
    }
}
