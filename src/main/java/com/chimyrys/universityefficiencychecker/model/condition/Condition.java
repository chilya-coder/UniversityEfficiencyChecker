package com.chimyrys.universityefficiencychecker.model.condition;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;

public interface Condition {
    public boolean isValid(ScienceWork scienceWork);
}
