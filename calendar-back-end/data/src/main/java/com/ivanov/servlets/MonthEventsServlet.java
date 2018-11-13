package com.ivanov.servlets;

import com.google.gson.Gson;
import com.ivanov.dao.EventDao;
import com.ivanov.dao.EventDaoImpl;
import com.ivanov.models.Event;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@WebServlet("/events/month/*")
public class MonthEventsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String param = pathInfo.substring(1);
        EventDao eventDao = new EventDaoImpl();

        try {
            List<Event> events = eventDao.getMonthEvents(param);
            String json = new Gson().toJson(events);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
