package com.ivanov.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateDeserializer implements JsonDeserializer<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd";


    @Override
    public Date deserialize(JsonElement jsonElement, Type typeOF,
                            JsonDeserializationContext context) throws JsonParseException {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return new Date(simpleDateFormat.parse(jsonElement.getAsString()).getTime());
        } catch (ParseException e) {
        }

        throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                + "\". Supported formats: " + DATE_FORMAT);
    }
}