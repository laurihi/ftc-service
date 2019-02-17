package com.github.laurihi.ftc.ftcservice.model.challenge;

import io.swagger.annotations.ApiModelProperty;

public class CreateRatedExercise {

    private String exerciseKey;

    @ApiModelProperty(value = "The id", position = 1)
    private String unit;

    @ApiModelProperty(value = "The id", position = 2)
    private Integer pointsPerUnit;

    public String getExerciseKey() {
        return exerciseKey;
    }

    public void setExerciseKey(String exerciseKey) {
        this.exerciseKey = exerciseKey;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPointsPerUnit() {
        return pointsPerUnit;
    }

    public void setPointsPerUnit(Integer pointsPerUnit) {
        this.pointsPerUnit = pointsPerUnit;
    }
}
