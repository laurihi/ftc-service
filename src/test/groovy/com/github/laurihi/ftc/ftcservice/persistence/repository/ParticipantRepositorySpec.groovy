package com.github.laurihi.ftc.ftcservice.persistence.repository

import com.github.laurihi.ftc.ftcservice.persistence.data.Participant
import com.github.laurihi.ftc.specification.FtcSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Subject
import spock.lang.Unroll

import javax.transaction.Transactional

@SpringBootTest
@Profile("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParticipantRepositorySpec extends FtcSpecification {

    @Subject
    @Autowired
    private ParticipantRepository participantRepository

    @Autowired
    private ChallengeRepository challengeRepository

    @Unroll
    @Transactional
    def "Repository saves participant by handle and can fetch it"() {

        when: "participant by #name initialised and saved"
        def participant = participantBuilder.userHandle(userhandle).build()

        then: "participant saved and fetched from the repo by user handle"
        participantRepository.save(participant)
        def fromRepo = participantRepository.getOne(userhandle)

        expect: "challenge fetched from repo to equal saved"
        fromRepo != null
        fromRepo.userHandle == userhandle

        where:
        userhandle  | _
        "donaldt"   | _
        "billc"     | _
        "vladimirp" | _
    }


    @Unroll
    @Transactional
    def "Participant #userhandle to challenge mapping is persisted"() {

        when: "Challenge and participant created and saved"
        def participant = participantBuilder.userHandle(userhandle).build()
        def challenge = challengeBuilder.name("Challenge").build()
        participantRepository.save(participant)
        challengeRepository.save(challenge)

        then: "Challenge is added for participant and participant saved"
        participant.addChallenge(challenge)
        participantRepository.save(participant)

        expect: "The user has challenge added"
        def fromRepo = participantRepository.getOne(userhandle)
        fromRepo.challenges.size() == 1
        where:
        userhandle  | _
        "donaldt"   | _
        "billc"     | _
        "vladimirp" | _
    }



    @Unroll
    @Transactional
    def "Save #userhandle and #anotherUserhandle, amount of users #expectedRepoSize"() {

        when: "participants initialised"
        def participant = participantBuilder.userHandle(userhandle).build()
        def anotherParticipant = participantBuilder.userHandle(anotherUserhandle).build()

        then: "participants saved "
        participantRepository.save(participant)
        participantRepository.save(anotherParticipant)


        expect: "Find all returns expected repo size"
        participantRepository.findAll().size() == expectedRepoSize

        where:
        userhandle  | anotherUserhandle | expectedRepoSize
        "donaldt"   | "donaldt"         | 1
        "billc"     | "donaldt"         | 2
        "vladimirp" | "vladimirp"       | 1
    }



}
