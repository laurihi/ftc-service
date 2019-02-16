package com.github.laurihi.ftc.ftcservice.persistence.repository

import com.github.laurihi.ftc.ftcservice.persistence.data.actions.Action
import com.github.laurihi.ftc.specification.FtcSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Unroll

import javax.transaction.Transactional

@SpringBootTest
@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ActionRepositorySpec extends FtcSpecification {


    @Autowired
    ActionRepository actionRepository

    @Unroll
    @Transactional
    def "Action is persisted"() {

        when: "challenge by #name initialised and saved"
        def challenge = persistChallengeWithExerciseAndParticipant(name, "exercise", "donaldt")

        def action = actionBuilder.participant(challenge.participants[0])
                .challenge(challenge).exercise(challenge.exercises[0])
                .amountDone(10).pointsGained(100).build()

        then: "Challenge saved and fetched from the repo by id"
        def savedAction = actionRepository.save(action)
        expect: "challenge fetched from repo to equal saved"
        savedAction != null
        savedAction.challenge == challenge
        savedAction.participant == challenge.participants[0]

        where:
        name             | _
        "Challenge name" | _
    }
}
