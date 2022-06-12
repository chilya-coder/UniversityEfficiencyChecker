package com.chimyrys.universityefficiencychecker.model;

import lombok.Getter;

@Getter
public enum TypeOfWork {
    ARTICLE(0, "Стаття"),
    SCIENCE_NOTES(1, "Тези");
    private int id;
    private String type;

    TypeOfWork(int id, String type) {
        this.id = id;
        this.type = type;
    }
}