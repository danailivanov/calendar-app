package com.ivanov.dao;

import com.ivanov.connection.DataBaseConnection;

import com.ivanov.format.DateFormatter;
import com.ivanov.format.TimeFormatter;
import com.ivanov.models.Event;


import java.sql.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EventDaoImpl implements EventDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private DateFormatter dateFormatter;
    private TimeFormatter timeFormatter;

    private static final String SQL_SELECT = "SELECT * FROM events";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM events " +
            "WHERE id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO events(title, date, start_time, end_time, description) " +
                    "VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE events SET title=?, " +
            "description = ?, date=?, start_time =?, end_time = ? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM events " +
            "WHERE id = ?";
    private static final String SQL_BETWEEN = "SELECT * FROM events\n" +
            "WHERE date BETWEEN ? AND ? ";


    public EventDaoImpl() {
        this.dateFormatter = new DateFormatter();
        this.timeFormatter = new TimeFormatter();
    }

    @Override
    public void createEvent(Event event) throws SQLException {
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_INSERT);

        preparedStatement.setString(1,event.getTitle());
        preparedStatement.setDate(2, event.getDate());
        preparedStatement.setTime(3, event.getStartTime());
        preparedStatement.setTime(4, event.getEndTime());
        preparedStatement.setString(5,event.getDescription());
        preparedStatement.execute();
    }

    @Override
    public void editEvent(Event event) throws SQLException {
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_UPDATE);
        preparedStatement.setString(1,event.getTitle());
        preparedStatement.setString(2,event.getDescription());
        preparedStatement.setDate(3, event.getDate());
        preparedStatement.setTime(4, event.getStartTime());
        preparedStatement.setTime(5, event.getEndTime());

        preparedStatement.setLong(6,event.getId());
        preparedStatement.execute();
    }

    @Override
    public void deleteEvent(long id) throws SQLException {
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_DELETE);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

    }

    @Override
    public Event getEventById(long id) throws SQLException, ParseException {
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);
        preparedStatement.setLong(1, id);
        resultSet = preparedStatement.executeQuery();
        Event event = new Event();

        while(resultSet.next()) {
            event.setId(resultSet.getLong("id"));
            event.setTitle(resultSet.getString("title"));
            event.setDate(dateFormatter.formattedDate(resultSet.getString("date")));
            event.setStartTime(timeFormatter.formattedTime(resultSet.getString("start_time")));
            event.setEndTime(timeFormatter.formattedTime(resultSet.getString("end_time")));
            event.setDescription(resultSet.getString("description"));
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() throws SQLException, ParseException {
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_SELECT);
        resultSet = preparedStatement.executeQuery();
        List<Event> events = new ArrayList<>();

        while(resultSet.next()){
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setTitle(resultSet.getString("title"));
            event.setDate(dateFormatter.formattedDate(resultSet.getString("date")));
            event.setStartTime(timeFormatter.formattedTime(resultSet.getString("start_time")));
            event.setEndTime(timeFormatter.formattedTime(resultSet.getString("end_time")));
            event.setDescription(resultSet.getString("description"));
            events.add(event);
        }
        return events.stream()
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getDayEvents(String stDate) throws SQLException, ParseException {
        Date date = dateFormatter.formattedDate(stDate);

        return  getAllEvents().stream()
                .filter(x->x.getDate().equals(date))
                .sorted(Comparator.comparing(Event::getDate)
                .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getWeekEvents(String week) throws SQLException, ParseException {
        Calendar cal = Calendar.getInstance();
        List<Event> events = getAllEvents();
        List<Event> weekEvents = new ArrayList<>();
        for (Event event : events) {
            cal.setTime(event.getDate());
            int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
            if(weekOfYear == Integer.parseInt(week.substring(6))){
                weekEvents.add(event);
            }
        }
        return weekEvents.stream()
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getMonthEvents(String month) throws SQLException, ParseException {
       Calendar cal = Calendar.getInstance();
        List<Event> events = getAllEvents();
        List<Event> monthEvents = new ArrayList<>();
        for (Event event : events) {
            cal.setTime(event.getDate());
            int monthOfYear = cal.get(Calendar.MONTH) + 1;
            if(monthOfYear == Integer.parseInt(month.substring(5))){
                monthEvents.add(event);
            }
        }
        return monthEvents.stream()
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());

    }

    @Override
    public List<Event> getEventsByPeriod(List<String> timeInterval) throws SQLException, ParseException {
        Date startDate = dateFormatter.formattedDate(timeInterval.get(0));
        Date endDate = dateFormatter.formattedDate(timeInterval.get(1));
        connection = DataBaseConnection.getConnection();
        preparedStatement = connection.prepareStatement(SQL_BETWEEN);
        preparedStatement.setDate(1,startDate);
        preparedStatement.setDate(2,endDate);
        resultSet = preparedStatement.executeQuery();
        List<Event> events = new ArrayList<>();

        while (resultSet.next()){
            Event event = new Event();
            event.setId(resultSet.getLong("id"));
            event.setTitle(resultSet.getString("title"));
            event.setDate(dateFormatter.formattedDate(resultSet.getString("date")));
            event.setStartTime(timeFormatter.formattedTime(resultSet.getString("start_time")));
            event.setEndTime(timeFormatter.formattedTime(resultSet.getString("end_time")));
            event.setDescription(resultSet.getString("description"));

            events.add(event);

        }
        return events.stream()
                .sorted(Comparator.comparing(Event::getDate)
                        .thenComparing(Event::getStartTime))
                .collect(Collectors.toList());
    }

    @Override
    public void close() throws Exception {
        if (this.resultSet != null){
            this.resultSet.close();
        }
        if (this.preparedStatement != null){
            this.preparedStatement.close();
        }
        if(this.connection != null) {
            this.connection.close();
        }
    }



}
