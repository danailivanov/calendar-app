package com.ivanov.dao;


import com.ivanov.models.Event;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface EventDao extends AutoCloseable{

    void createEvent(Event event) throws SQLException;
    void editEvent(Event event) throws SQLException;
    void deleteEvent(long id) throws SQLException;
    Event getEventById(long id) throws SQLException, ParseException;
    List<Event> getAllEvents() throws SQLException, ParseException;
    List<Event> getDayEvents(String date) throws SQLException, ParseException;
    List<Event> getWeekEvents(String week) throws SQLException, ParseException;
    List<Event> getMonthEvents(String month) throws SQLException, ParseException;
    List<Event> getEventsByPeriod(List<String> timeInterval) throws SQLException, ParseException;


}
