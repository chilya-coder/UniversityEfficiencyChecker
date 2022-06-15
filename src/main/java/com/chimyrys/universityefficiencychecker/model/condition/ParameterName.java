package com.chimyrys.universityefficiencychecker.model.condition;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import lombok.Getter;

@Getter
public enum ParameterName {
    NAME("назва"),
    CHAR_OF_WORK("характер роботи"),
    DATE_OF_PUBLICATION("дата публікації"),
    TYPE_OF_WORK("тип роботи"),
    SIZE("обсяг"),
    OUTPUT_DATA("вихідні дані");
    private final String value;
    ParameterName(String value) {
        this.value = value;
    }
    public static ParameterName getByValue(String value) {
        for (ParameterName name : values()) {
            if (name.getValue().equals(value)) {
                return name;
            }
        }
        return null;
    }
    public String getParameter(ScienceWork scienceWork) {
        switch (this) {
            case NAME:
                return scienceWork.getName();
            case CHAR_OF_WORK:
                return scienceWork.getCharOfWork().getCharacteristic();
            case DATE_OF_PUBLICATION:
                return DateUtils.getStringFromDate(scienceWork.getDateOfPublication());
            case TYPE_OF_WORK:
                return scienceWork.getTypeOfWork().getType();
            case SIZE:
                return String.valueOf(scienceWork.getSize());
            case OUTPUT_DATA:
                return scienceWork.getOutputData();
        }
        return "";
    }
}
