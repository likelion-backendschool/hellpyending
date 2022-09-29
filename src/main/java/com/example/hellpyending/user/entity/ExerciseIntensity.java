package com.example.hellpyending.user.entity;

public enum ExerciseIntensity {
    HIGH("HIGH"),
    MIDDLE("MIDDLE"),
    ROW("ROW");
    private String exerciseIntensity;
    private ExerciseIntensity(String exerciseIntensity) {
        this.exerciseIntensity = exerciseIntensity;
    }
}
