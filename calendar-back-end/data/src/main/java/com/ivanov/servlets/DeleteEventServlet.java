package com.ivanov.servlets;

import com.google.gson.Gson;
import com.ivanov.dao.EventDao;
import com.ivanov.dao.EventDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/events/delete/*")
public class DeleteEventServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String param = pathInfo.substring(1);
        EventDao eventDao = new EventDaoImpl();
        try {
            eventDao.deleteEvent(Long.parseLong(param));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
