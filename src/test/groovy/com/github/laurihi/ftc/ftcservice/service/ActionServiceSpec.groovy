package com.github.laurihi.ftc.ftcservice.service

import com.github.laurihi.ftc.ftcservice.model.DailyAction
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.ftcservice.persistence.repository.ActionRepository
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository
import com.github.laurihi.ftc.ftcservice.persistence.repository.ParticipantRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class ActionServiceSpec extends Specification  {

    ChallengeRepository challengeRepository = Mock(ChallengeRepository)
    ParticipantRepository participantRepository = Mock(ParticipantRepository)

    ActionRepository actionRepository = Mock(ActionRepository)
    ChallengeService challengeService = Mock(ChallengeService, constructorArgs: [challengeRepository, participantRepository])


    @Subject
    def actionService = new ActionService(actionRepository, challengeService)

    void setup() {

    }


    def "When adding actions, the ongoing challenge is queried for"() {

        given:
        def actions = []
        def dailyAction = new DailyAction()
        dailyAction.exercise = exercise
        dailyAction.amountOf = amountOf
        actions << dailyAction

        Challenge challenge = new Challenge()
        challenge.setId(45)
        challenge.setExercises([])

        when: "Daily actions are added"
        actionService.addDailyActions(userHandle, actions, date)

        then: "challengeservices get ongoint must be called at least once"
        1 * challengeService.getOngoingChallenge() >> challenge

        where:
        exercise   | amountOf | userHandle | date
        "exercise" | 2        | "donaldt"  | LocalDate.now()
    }

}
