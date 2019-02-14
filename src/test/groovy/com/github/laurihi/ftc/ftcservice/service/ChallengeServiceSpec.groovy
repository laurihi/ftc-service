package com.github.laurihi.ftc.ftcservice.service

import com.github.laurihi.ftc.specification.FtcSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Unroll

import java.time.LocalDate

@SpringBootTest
@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ChallengeServiceSpec extends FtcSpecification {


    @Autowired
    ChallengeService challengeService
    def challenge

    void setup() {
        challenge = challengeBuilder.name("Challenge")
                .launchDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(5)).build()
    }


    def "Creating ongoing challenge can be found"() {

        given: "Challenge with today between launchdate and enddate saved"

        def challengeId = challengeService.saveChallenge(challenge).id
        when:
        def ongoingChallenge = challengeService.getOngoingChallenge()
        then:
        ongoingChallenge.id == challengeId

    }


    def "Ongoing challenge gets the ongoing, not the ones before or after"() {

        given: "Challenges saved as in the past, ongoing and future"
        def today = LocalDate.now()
        def pastChallenge = challengeBuilder.launchDate(today.minusMonths(5))
                .endDate(today.minusMonths(2))
                .name("Past challenge").build()

        def futureChallenge = challengeBuilder.launchDate(today.plusMonths(5))
                .endDate(today.plusMonths(8))
                .name("Past challenge").build()

        def ongoingChallenge = challenge

        challengeService.saveChallenge(pastChallenge)
        challengeService.saveChallenge(futureChallenge)
        ongoingChallenge = challengeService.saveChallenge(ongoingChallenge)
        when:
        def ongoingFromDb = challengeService.getOngoingChallenge()
        then:
        ongoingChallenge.id == ongoingFromDb.id
    }


    @Unroll
    def "Unable to save with overlapping dates, offsets: #launchOffset - #endOffset"() {

        given: "Challenges saved as in the past, ongoing and future"
        def today = LocalDate.now()
        def challenge = challengeBuilder.launchDate(today.plusMonths(2))
                .endDate(today.plusMonths(5))
                .name("name").build()
        challengeService.saveChallenge(challenge)

        def overLappingChallenge = challengeBuilder.launchDate(today.plusMonths(launchOffset))
                .endDate(today.plusMonths(endOffset))
                .name("Overlapper").build()

        when:
        challengeService.saveChallenge(overLappingChallenge)
        then:
        thrown Exception
        where:
        launchOffset | endOffset
        1            | 3
        3            | 5
        1            | 7
        3            | 7
    }

    def "No challenges, ongoing challenge throws exception"() {

        when:
        challengeService.getOngoingChallenge()
        then:
        thrown Exception
    }


}
