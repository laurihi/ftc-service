package com.github.laurihi.ftc.ftcservice.model.challenge;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateChallenge {


    private String name;

    @ApiModelProperty(value = "The id", position = 1)
    private LocalDate startDate;
    @ApiModelProperty(value = "The id", position = 2)
    private LocalDate endDate;
    @ApiModelProperty(value = "The id", position = 3)
    private List<CreateRatedExercise> exercises = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public List<CreateRatedExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<CreateRatedExercise> exercises) {
        this.exercises = exercises;
    }
}
