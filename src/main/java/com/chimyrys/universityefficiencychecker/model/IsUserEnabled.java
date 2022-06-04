package com.chimyrys.universityefficiencychecker.model;

public enum IsUserEnabled {
    ENABLED(0),
    DISABLED(1);
    private final int id;

    IsUserEnabled(int id) {
        this.id = id;
    }
}
