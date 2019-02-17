package com.github.laurihi.ftc.ftcservice.model.actions;

import java.time.LocalDate;

public class DailyAction {

    private String exercise;
    private Long units;


    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Long getUnits() {
        return units;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

}
