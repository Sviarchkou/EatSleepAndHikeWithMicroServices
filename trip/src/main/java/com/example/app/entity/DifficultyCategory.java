package com.example.app.entity;

public enum DifficultyCategory {
    BEYOND_CATEGORICAL(0),
    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6);

    private final int level;

    DifficultyCategory(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
