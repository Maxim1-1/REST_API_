package com.Maxim.controller;


import com.Maxim.model.Event;
import com.Maxim.model.File;
import com.Maxim.model.User;
import com.Maxim.service.EventService;
import com.Maxim.service.FileService;
import com.Maxim.service.UserService;
import com.Maxim.view.EventView;
import com.Maxim.view.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/events")
public class EventsServlet extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events = eventService.getAllEvents();

        resp.setContentType("application/json");
        EventView eventView = new EventView();
        eventView.getEvents(events,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();

        System.out.println(json);
        Event event = new ObjectMapper().readerFor( Event.class).readValue(json);

        eventService.saveEvent(event);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String event_id = req.getParameter("event_id");
        if(event_id!=null) {
            eventService.deleteEventById(Integer.valueOf(event_id));
        } else {
            resp.sendError(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();
        Event event = new ObjectMapper().readerFor(Event.class).readValue(json);

        if (eventService.getEventById(event.getId())!=null) {
            eventService.updateEvent(event);
        } else {
            resp.sendError(400,"File not exist");
        }
    }
}
