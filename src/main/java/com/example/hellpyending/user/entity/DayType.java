package com.example.hellpyending.user.entity;

public enum DayType {
    Monday("월요일"),
    Tuesday("화요일"),
    Wednesday("수요일"),
    Thursday("목요일"),
    Friday("금요일"),
    Saturday("토요일"),
    Sunday("일요일");
    private String dayType;
    private DayType(String dayType) {
        this.dayType = dayType;
    }
}
