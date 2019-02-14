package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallengeService {

    private final ChallengeRepository repository;

    private final Logger LOG = LoggerFactory.getLogger(ChallengeService.class);

    public ChallengeService(@Autowired ChallengeRepository repository) {
        this.repository = repository;
    }

    public Challenge saveChallenge(Challenge challenge) {

        LocalDate launchDate = challenge.getLaunchDate();
        LocalDate endDate = challenge.getEndDate();

        if (doOverlappingChallengesExist(launchDate, endDate)) {
            LOG.warn("Trying to create overlapping challenge by name " + challenge.getName());
            throw new RuntimeException();
        }

        return repository.save(challenge);
    }

    private boolean doOverlappingChallengesExist(LocalDate start, LocalDate end) {
        List<Challenge> challengesThatEndBeforeCurrentStart = repository.findByEndDateBefore(start);
        List<Challenge> challengesThatStartAfterCurrentEnd = repository.findByLaunchAfter(end);

        List<Challenge> allChallenges = repository.findAll();

        return challengesThatEndBeforeCurrentStart.size() + challengesThatStartAfterCurrentEnd.size() != allChallenges.size();
    }

    public Challenge getOngoingChallenge() {
        LocalDate today = LocalDate.now();
        List<Challenge> ongoing = repository.findByStartBeforeAndEndAfter(today);

        return ongoing.get(0);
    }
}
