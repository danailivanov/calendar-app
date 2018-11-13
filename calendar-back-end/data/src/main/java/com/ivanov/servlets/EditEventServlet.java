package com.ivanov.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


@WebServlet("/events/edit")
public class EditEventServlet extends HttpServlet {


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        Event event = (Event) getServletContext().getAttribute("event");
//        System.out.println(event.getTitle());

        GetBody getBody = new GetBody();
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Time.class, new TimeDeserializer());
        gb.registerTypeAdapter(Date.class, new DateDeserializer());
        Gson gson = gb.create();

        String payloadRequest = getBody.getBody(req);
        Event event = gson.fromJson(payloadRequest, Event.class);
        EventDao eventDao = new EventDaoImpl();

        try {
            eventDao.editEvent(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(event);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
//
//        try {
//            eventDao.editEvent(event);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }
}
