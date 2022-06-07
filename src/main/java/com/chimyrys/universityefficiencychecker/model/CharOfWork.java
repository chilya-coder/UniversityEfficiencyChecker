package com.chimyrys.universityefficiencychecker.model;

import lombok.Getter;

@Getter
public enum CharOfWork {
    DRUK(0, "Друк"),
    ELEC(1, "Електронне видання"),
    MONO(2, "Монографія"),
    AUTH(3, "Авторське свідоцтво"),
    ELEC_RES(4,"Електронний ресурс");
    private int id;
    private String characteristic;

    CharOfWork(int id, String characteristic) {
        this.id = id;
        this.characteristic = characteristic;
    }
}
