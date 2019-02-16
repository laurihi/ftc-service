package com.github.laurihi.ftc.ftcservice.controller;

import com.github.laurihi.ftc.ftcservice.model.CreateChallengeModel;
import com.github.laurihi.ftc.ftcservice.persistence.data.Challenge;
import com.github.laurihi.ftc.ftcservice.service.ChallengeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(@Autowired ChallengeService challengeService){
        this.challengeService = challengeService;
    }

    private final static Logger LOG = LoggerFactory.getLogger(ChallengeController.class);

    @ApiOperation("Create new challenge")
    @PostMapping
    public Challenge createChallenge(@RequestBody CreateChallengeModel challenge){

        return challengeService.create(challenge);
    }
}
