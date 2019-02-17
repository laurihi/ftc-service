package com.github.laurihi.ftc.ftcservice.controller;

import com.github.laurihi.ftc.ftcservice.model.actions.AvailableExercise;
import com.github.laurihi.ftc.ftcservice.model.actions.AvailableExercises;
import com.github.laurihi.ftc.ftcservice.model.actions.DailyAction;
import com.github.laurihi.ftc.ftcservice.model.actions.DailyActionsWrapper;
import com.github.laurihi.ftc.ftcservice.service.ActionService;
import com.github.laurihi.ftc.ftcservice.service.ChallengeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    private final ActionService actionService;

    public ExerciseController(@Autowired ChallengeService challengeService, @Autowired ActionService actionService){
        this.challengeService = challengeService;
        this.actionService = actionService;
    }

    @ApiOperation("Get exercises available for actions")
    @GetMapping("/available")
    public AvailableExercises getAvailableExercises(){
        List<AvailableExercise> availableExercises = challengeService.availableExercises();
        AvailableExercises result = new AvailableExercises();
        result.addAvailableExercises(availableExercises);
        return result;
    }

    @CrossOrigin
    @ApiOperation("Get exercises available for actions")
    @PostMapping("/save")
    public void doExercise(@RequestBody DailyActionsWrapper dailyActions){

        actionService.addDailyActions(dailyActions);
    }
}
