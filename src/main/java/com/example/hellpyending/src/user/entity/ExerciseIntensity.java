package com.example.hellpyending.src.user.entity;

public enum ExerciseIntensity {
    HIGH("HIGH"),
    MIDDLE("MIDDLE"),
    ROW("ROW");
    private String exerciseIntensity;
    private ExerciseIntensity(String exerciseIntensity) {
        this.exerciseIntensity = exerciseIntensity;
    }
}
