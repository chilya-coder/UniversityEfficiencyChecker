package com.chimyrys.universityefficiencychecker.utils;

import com.chimyrys.universityefficiencychecker.model.condition.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConditionUtils {
    public Condition getConditionFromString(String condition) {
        if (!condition.contains("(")) {
            return getSimpleConditionFromString(condition);
        } else if (amountOfGroup(condition) == 1) {
            return getSimpleConditionFromString(condition.substring(1, condition.length() - 1));
        }
        else {
            ComplexCondition.ComplexConditionBuilder builder = ComplexCondition.builder();
            String firstCondition = condition.substring(condition.indexOf("(") + 1, condition.indexOf(")"));
            String methodName = condition.substring(condition.indexOf(")", 1) + 2, condition.indexOf("(", 1) - 1);
            String anotherCondition = condition.substring(condition.indexOf("(", 1));
            builder.condition1(getSimpleConditionFromString(firstCondition));
            builder.condition2(getConditionFromString(anotherCondition));
            for (ComplexMethod method : ComplexMethod.values()) {
                if (methodName.equals(method.getValue())) {
                    builder.method(method);
                }
            }
            return builder.build();
        }
    }
    private Condition getSimpleConditionFromString(String condition) {
        SimpleCondition.SimpleConditionBuilder builder = SimpleCondition.builder();
        SimpleMethod trueMethod = null;
        for (SimpleMethod method : SimpleMethod.values()) {
            if (condition.contains(method.getValue())) {
                trueMethod = method;
                builder.method(method);
            }
        }
        String[] parts = condition.split(" " + trueMethod.getValue() + " ");
        builder.paramName(ParameterName.getByValue(parts[0]));
        builder.value(parts[1]);
        return builder.build();
    }
    private int amountOfGroup(String condition) {
        int amount = 0;
        while (condition.indexOf("(") != -1) {
            amount++;
            condition = condition.substring(condition.indexOf("(") + 1);
        }
        return amount;
    }
}
