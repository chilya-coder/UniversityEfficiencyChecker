package com.chimyrys.universityefficiencychecker.model.condition;

import lombok.Getter;

@Getter
public enum ComplexMethod {
    OR("||"),
    AND("&&");
    private String value;
    ComplexMethod(String value) {
        this.value = value;
    }
}
