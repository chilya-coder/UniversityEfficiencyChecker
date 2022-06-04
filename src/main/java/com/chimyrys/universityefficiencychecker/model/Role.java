package com.chimyrys.universityefficiencychecker.model;

public enum Role {
    TEACHER(0),
    ADMIN(1);
    private int id;

    Role(int id) {
        this.id = id;
    }
}
