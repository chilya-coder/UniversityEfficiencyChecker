package com.chimyrys.universityefficiencychecker.model.condition;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexCondition implements Condition {
    private Condition condition1;
    private ComplexMethod method;
    private Condition condition2;

    @Override
    public boolean isValid(ScienceWork scienceWork) {
        switch (method) {
            case OR:
                return condition1.isValid(scienceWork) || condition2.isValid(scienceWork);
            case AND:
                return condition1.isValid(scienceWork) && condition2.isValid(scienceWork);
        }
        return false;
    }
}
