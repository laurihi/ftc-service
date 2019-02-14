package com.github.laurihi.ftc.ftcservice

import com.github.laurihi.ftc.ftcservice.controller.ChallengeController
import com.github.laurihi.ftc.ftcservice.controller.ExerciseController
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification

import javax.transaction.Transactional
import java.time.LocalDate

@SpringBootTest
@Profile("test")
class ContextLoadsSpec extends Specification {

    @Autowired (required = false)
    private ChallengeController challengeController

    @Autowired (required = false)
    private ExerciseController exerciseController


    def "when context is loaded then all expected beans are created"() {

        expect: "the controllers are instantiated"
        challengeController != null
        exerciseController != null
    }


}