package com.github.laurihi.ftc.ftcservice.persistence.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RatedExercise {


    @Id
    @GeneratedValue
    private Long id;

    private String exerciseKey;

    private String unit;

    private Integer pointsPerUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
