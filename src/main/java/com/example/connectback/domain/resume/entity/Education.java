package com.example.connectback.domain.resume.entity;

public enum Education {
    중졸("중졸"),
    고졸("고졸"),
    초대졸("초대졸"),
    대졸("대졸");

    private final String displayName;

    Education(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

