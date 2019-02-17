package com.github.laurihi.ftc.ftcservice.model.actions;

import java.util.*;

public class AvailableExercises {

    private Map<String, List<AvailableExercise>> exercisesByCategory = new HashMap<>();

    public void addAvailableExercises(Collection<AvailableExercise> exercises){

        exercises.stream().filter(exercise -> exercise.getCategory() != null)
                .forEach(exercise -> {
                    String category = exercise.getCategory();
                    List<AvailableExercise> availableExercises = exercisesByCategory.get(category);
                    if(availableExercises == null){
                        availableExercises = new ArrayList<>();
                        exercisesByCategory.put(category, availableExercises);
                    }
                    availableExercises.add(exercise);
                });
    }

    public Map<String, List<AvailableExercise>> getExercisesByCategory() {
        return exercisesByCategory;
    }
}
