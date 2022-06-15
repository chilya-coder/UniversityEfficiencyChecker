package com.chimyrys.universityefficiencychecker.model.condition;

import lombok.Getter;

@Getter
public enum SimpleMethod {
    CONTAINS("contains"),
    EQUALS("equals");
    private String value;
    SimpleMethod(String value) {
        this.value = value;
    }
}
