package com.github.laurihi.ftc.ftcservice.controller;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final static Logger LOG = LoggerFactory.getLogger(ExerciseController.class);

    @RequestMapping
    public String doGet(){
        LOG.info("Do get called in exercises.");
        return "Yey!";

    }
}
