package com.github.laurihi.ftc.ftcservice.service

import com.github.laurihi.ftc.specification.FtcSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext

import java.time.LocalDate

@SpringBootTest
@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChallengeServiceSpec extends FtcSpecification {



    @Autowired
    ChallengeService challengeService


    void setup() {
    }


    def "Challenge builder"() {

        when:
        def challenge = challengeBuilder.name("Challenge")
        .launchDate(LocalDate.now().minusDays(1))
        .endDate(LocalDate.now().plusDays(5)).build()
        challengeService.saveChallenge(challenge)
        then:
        noExceptionThrown()
    }
    def "Creating a challenge does not throw an exception"() {

        when:
        def challenge = createChallenge("Challenge")
        challengeService.saveChallenge(challenge)
        then:
        noExceptionThrown()
    }

    def "No challenges, ongoing challenge throws exception"() {

        when:
        challengeService.ongoingChallenge()
        then:
        thrown Exception
    }



}
