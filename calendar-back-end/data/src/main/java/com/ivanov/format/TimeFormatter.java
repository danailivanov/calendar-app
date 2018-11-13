package com.ivanov.format;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeFormatter {
    private static final String TIME_FORMAT = "HH:mm";

    public Time formattedTime(String time) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
        Time timeF = null;

        try {
            return timeF = new Time(simpleDateFormat.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new ParseException("Unparseable date:" +
                time + ". Supported formats:" + TIME_FORMAT, 1);
    }
}

