package com.example.connectback.domain.resume.entity;

public enum WorkType {
    상용직("상용직"),
    시간제("시간제"),
    계약직("계약직");

    private final String displayName;

    WorkType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}