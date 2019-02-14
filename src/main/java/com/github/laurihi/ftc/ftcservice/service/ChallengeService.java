package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.persistence.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallengeService {


    private final ChallengeRepository repository;

    public ChallengeService(@Autowired ChallengeRepository repository){
        this.repository = repository;
    }

    public Challenge saveChallenge(Challenge challenge){
        return repository.save(challenge);
    }
    public Challenge getOngoingChallenge(){
        LocalDate today = LocalDate.now();
        List<Challenge> ongoing = repository.findByStartBeforeAndEndAfter(today);

        return ongoing.get(0);
    }
}
