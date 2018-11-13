package com.ivanov.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeDeserializer implements JsonDeserializer<Time> {
    private static final String TIME_FORMAT = "HH:mm";

    @Override
    public Time deserialize(JsonElement jsonElement, Type typeOF,
                            JsonDeserializationContext context) throws JsonParseException {
        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
            return new Time(simpleDateFormat.parse(jsonElement.getAsString()).getTime());
        } catch (ParseException e) {
        }
        throw new JsonParseException("Unparseable time: \"" + jsonElement.getAsString()
                + "\". Supported formats: " + TIME_FORMAT);
    }
}
