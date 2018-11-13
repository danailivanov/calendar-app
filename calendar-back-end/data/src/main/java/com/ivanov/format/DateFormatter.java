package com.ivanov.format;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public Date formattedDate(String date) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date dateF = null;

        try {
            return dateF = new Date (simpleDateFormat.parse(date).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new ParseException("Unparseable date:" +
                date + ". Supported formats:" + DATE_FORMAT,1);
    }
}
