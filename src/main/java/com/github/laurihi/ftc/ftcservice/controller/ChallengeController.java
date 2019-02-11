package com.github.laurihi.ftc.ftcservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenges")
public class ChallengeController {


    private final static Logger LOG = LoggerFactory.getLogger(ChallengeController.class);

    @RequestMapping
    public String doGet(){
        LOG.info("Do get called in challenges.");
        return "Yey!";
    }
}
