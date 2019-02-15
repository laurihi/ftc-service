package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.data.Participant;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private ParticipantRepository participantRepository;

    private final Logger LOG = LoggerFactory.getLogger(ChallengeService.class);

    public ChallengeService(@Autowired ChallengeRepository challengeRepository,
                            @Autowired ParticipantRepository participantRepository) {
        this.challengeRepository = challengeRepository;
        this.participantRepository = participantRepository;
    }

    public Challenge saveChallenge(Challenge challenge) {

        LocalDate launchDate = challenge.getLaunchDate();
        LocalDate endDate = challenge.getEndDate();

        if (doOverlappingChallengesExist(launchDate, endDate)) {
            LOG.warn("Trying to create overlapping challenge by name " + challenge.getName());
            throw new RuntimeException();
        }

        return challengeRepository.save(challenge);
    }

    private boolean doOverlappingChallengesExist(LocalDate start, LocalDate end) {
        List<Challenge> challengesThatEndBeforeCurrentStart = challengeRepository.findByEndDateBefore(start);
        List<Challenge> challengesThatStartAfterCurrentEnd = challengeRepository.findByLaunchAfter(end);

        List<Challenge> allChallenges = challengeRepository.findAll();

        return challengesThatEndBeforeCurrentStart.size() + challengesThatStartAfterCurrentEnd.size() != allChallenges.size();
    }

    public Challenge getOngoingChallenge() {
        LocalDate today = LocalDate.now();
        List<Challenge> ongoing = challengeRepository.findByStartBeforeAndEndAfter(today);

        return ongoing.size() == 0 ? null : ongoing.get(0);
    }

    public void joinOngoing(String userHandle) {

        Challenge ongoingChallenge = getOngoingChallenge();
        if (ongoingChallenge == null){
            throw new IllegalStateException("No ongoing challenges to join.");
        }
        Participant participant = participantRepository.findById(userHandle).orElse(addParticipant(userHandle));

        ongoingChallenge.addParticipant(participant);
        challengeRepository.save(ongoingChallenge);

    }

    private Participant addParticipant(String userHandle) {
        Participant participant = new Participant();
        participant.setUserHandle(userHandle);
        return participantRepository.save(participant);
    }
}
