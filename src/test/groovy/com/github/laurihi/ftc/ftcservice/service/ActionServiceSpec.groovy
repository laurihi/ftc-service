package com.github.laurihi.ftc.ftcservice.service

import com.github.laurihi.ftc.ftcservice.model.DailyAction
import com.github.laurihi.ftc.ftcservice.model.DailyActionsWrapper
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.ftcservice.persistence.data.RatedExercise
import com.github.laurihi.ftc.ftcservice.persistence.repository.ActionRepository
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository
import com.github.laurihi.ftc.ftcservice.persistence.repository.ParticipantRepository
import com.github.laurihi.ftc.specification.FtcSpecification
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class ActionServiceSpec extends FtcSpecification {

    ChallengeRepository challengeRepository = Mock(ChallengeRepository)
    ParticipantRepository participantRepository = Mock(ParticipantRepository)

    ActionRepository actionRepository = Mock(ActionRepository)
    ChallengeService challengeService = Mock(ChallengeService, constructorArgs: [challengeRepository, participantRepository])


    @Subject
    def actionService = new ActionService(actionRepository, challengeService)

    void setup() {

    }


    def "When adding actions, you call "() {

        given:


        def actions = []
        def dailyAction = new DailyAction()
        dailyAction.exercise = exercise
        dailyAction.amountOf = amountOf
        actions << dailyAction

        def dailyActionsWrapper = new DailyActionsWrapper(
                userHandle, date, actions
        )

        Challenge challenge = new Challenge()
        challenge.setId(45)
        challenge.setExercises([])

        when: "Daily actions are added"
        actionService.addDailyActions(dailyActionsWrapper)

        then: "challengeservices get ongoing must be called at least once"
        1 * challengeService.getOngoingChallenge() >> challenge
        1 * actionRepository.saveAll(_)

        where:
        exercise   | amountOf | userHandle | date
        "exercise" | 2        | "donaldt"  | LocalDate.now()
    }

    /* Add maybe as an integration test.

    def "If adding exercise that's not mapped to challenge, it won't be included"() {

        given:

        def actions = []
        def dailyAction = new DailyAction()
        dailyAction.exercise = exercise
        dailyAction.amountOf = amountOf
        actions << dailyAction

        def dailyActionsWrapper = new DailyActionsWrapper(
                userHandle, date, actions
        )

        def exercises = []
        exercises << ratedExerciseBuilder.exerciseKey(mappedToChallenge).build()
        Challenge challenge = new Challenge()
        challenge.setId(45)
        challenge.setExercises(exercises)

        when: "Daily actions are added"
        actionService.addDailyActions(dailyActionsWrapper)

        then: "challengeservices get ongoing must be called at least once"
        1 * challengeService.getOngoingChallenge() >> challenge

        where:
        mappedToChallenge | includeable   | unincludeable
        "stair-climb"     | "stair-climb" | "something-else"

    }
    */
}
