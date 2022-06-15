package com.chimyrys.universityefficiencychecker.model.condition;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCondition implements Condition {
    private ParameterName paramName;
    private SimpleMethod method;
    private String value;
    @Override
    public boolean isValid(ScienceWork scienceWork) {
        switch (method) {
            case CONTAINS:
                return paramName.getParameter(scienceWork).toLowerCase().contains(value.toLowerCase());
            case EQUALS:
                return paramName.getParameter(scienceWork).toLowerCase().equals(value.toLowerCase());
        }
        return false;
    }
}
