package com.ivanov.servlets;

import com.google.gson.*;
import com.ivanov.dao.EventDao;
import com.ivanov.dao.EventDaoImpl;
import com.ivanov.deserializer.DateDeserializer;
import com.ivanov.deserializer.TimeDeserializer;
import com.ivanov.models.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


@WebServlet("/events/create")
public class CreateEventsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GetBody getBody = new GetBody();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Time.class, new TimeDeserializer());
        gb.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gb.create();

        String payloadRequest = getBody.getBody(req);
        Event event = gson.fromJson(payloadRequest, Event.class);
        EventDao eventDao = new EventDaoImpl();
        try {
            eventDao.createEvent(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

