package com.chimyrys.universityefficiencychecker.utils;

import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UtilityClass
public class DateUtils {
    public List<Date> getDateRangeForYear(int since, int to) {
        Date sinceDate = null;
        Date toDate = null;
        try {
            sinceDate = new SimpleDateFormat("yyyy-MM-dd").parse(since + "-01-01");
            toDate = new SimpleDateFormat("yyyy-MM-dd").parse(to + "-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Date> dates = new ArrayList<>();
        dates.add(sinceDate);
        dates.add(toDate);
        return dates;
    }
    public Date getDateFromString(String value) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(value);
    }
}
