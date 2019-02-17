package com.github.laurihi.ftc.ftcservice.service

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
class ChallengeServiceSpec extends FtcSpecification {


    @Autowired
    ChallengeService challengeService
    def challenge

    void setup() {
        challenge = createChallengeBuilder.name("Challenge")
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .exercises([]).build()
    }


    def "Creating ongoing challenge can be found"() {

        given: "Challenge with today between launchdate and enddate saved"

        def challengeId = challengeService.create(challenge).id
        when:
        def ongoingChallenge = challengeService.getOngoingChallengeEntity()
        then:
        ongoingChallenge.id == challengeId

    }


    def "Ongoing challenge gets the ongoing, not the ones before or after"() {

        given: "Challenges saved as in the past, ongoing and future"
        def today = LocalDate.now()
        def pastChallenge = createChallengeBuilder.startDate(today.minusMonths(5))
                .endDate(today.minusMonths(2))
                .name("Past challenge").build()

        def futureChallenge = createChallengeBuilder.startDate(today.plusMonths(5))
                .endDate(today.plusMonths(8))
                .name("Past challenge").build()

        def ongoingChallenge = challenge

        challengeService.create(pastChallenge)
        challengeService.create(futureChallenge)
        ongoingChallenge = challengeService.create(ongoingChallenge)
        when:
        def ongoingFromDb = challengeService.getOngoingChallengeEntity()
        then:
        ongoingChallenge.id == ongoingFromDb.id
    }

    @Unroll
    @Transactional
    def "Participant can join an ongoing challenge"() {

        given: "One ongoing challenge exists"
        def ongoingChallenge = challenge
        challengeService.create(ongoingChallenge)
        when:
        challengeService.joinOngoing("usr-handle")
        then:
        challengeService.getOngoingChallengeEntity().participants.size() == 1
    }

    @Unroll
    @Transactional
    def "Participant can not join if no ongoing challenge is available"() {

        given: "No ongoing challenges"

        when:
        challengeService.joinOngoing("usr-handle")
        then:
        thrown IllegalStateException
    }


    @Unroll
    def "Unable to save with overlapping dates, offsets: #launchOffset - #endOffset"() {

        given: "Challenges saved as in the past, ongoing and future"
        def today = LocalDate.now()
        def challenge = createChallengeBuilder.startDate(today.plusMonths(2))
                .endDate(today.plusMonths(5))
                .name("name").build()
        challengeService.create(challenge)

        def overLappingChallenge = createChallengeBuilder.startDate(today.plusMonths(launchOffset))
                .endDate(today.plusMonths(endOffset))
                .name("Overlapper").build()

        when:
        challengeService.create(overLappingChallenge)
        then:
        thrown Exception
        where:
        launchOffset | endOffset
        1            | 3
        3            | 5
        1            | 7
        3            | 7
    }

    def "No challenges, returns null"() {

        when:
        def result = challengeService.getOngoingChallengeEntity()
        then:
        result == null
    }


}
