package com.github.laurihi.ftc.ftcservice.controller;

import com.github.laurihi.ftc.ftcservice.model.actions.AvailableExercise;
import com.github.laurihi.ftc.ftcservice.model.actions.AvailableExercises;
import com.github.laurihi.ftc.ftcservice.service.ChallengeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@CrossOrigin
@EnableSwagger2
@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final static Logger LOG = LoggerFactory.getLogger(ExerciseController.class);
    private final ChallengeService challengeService;

    public ExerciseController(@Autowired ChallengeService challengeService){
        this.challengeService = challengeService;
    }

    @ApiOperation("Get exercises available for actions")
    @GetMapping("/available")
    public AvailableExercises getAvailableExercises(){
        List<AvailableExercise> availableExercises = challengeService.availableExercises();
        AvailableExercises result = new AvailableExercises();
        result.addAvailableExercises(availableExercises);
        return result;
    }
}
