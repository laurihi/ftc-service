package com.github.laurihi.ftc.ftcservice.service;

import com.github.laurihi.ftc.ftcservice.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeService {


    private final ChallengeRepository repository;

    public ChallengeService(@Autowired ChallengeRepository repository){
        this.repository = repository;
    }
}
