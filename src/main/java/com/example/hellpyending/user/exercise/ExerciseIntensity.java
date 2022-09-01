package com.example.hellpyending.user.exercise;

public enum ExerciseIntensity {
    HIGH("HIGH"),
    MIDDLE("MIDDLE"),
    ROW("ROW");
    private String exerciseIntensity;
    private ExerciseIntensity(String exerciseIntensity) {
        this.exerciseIntensity = exerciseIntensity;
    }
}
