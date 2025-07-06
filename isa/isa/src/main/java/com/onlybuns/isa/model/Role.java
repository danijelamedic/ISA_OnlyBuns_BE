package com.onlybuns.isa.model;

public enum Role {
    ADMIN(0),
    REGISTERED_USER(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}