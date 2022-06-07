package com.chimyrys.universityefficiencychecker.model;

import lombok.Getter;

@Getter
public enum TypeOfWork {
    DRUK(0, "Стаття"),
    ELEC(1, "Тези");
    private int id;
    private String type;

    TypeOfWork(int id, String type) {
        this.id = id;
        this.type = type;
    }
}