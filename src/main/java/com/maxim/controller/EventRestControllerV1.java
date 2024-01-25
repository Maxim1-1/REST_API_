package com.maxim.controller;


import com.maxim.dto.EventDTO;
import com.maxim.model.Event;
import com.maxim.service.EventService;
import com.maxim.mapper.EventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.utils.dto_ustils.MappingDTOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {

    private EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        EventMapper eventMapper = new EventMapper();
        if (path.equals("")) {
            List<Event> events = eventService.getAllEvents();
            resp.setContentType("application/json");
            eventMapper.getEvents(events, resp);
        } else if (path.matches("/\\d+")) {
            String id = path.substring(1);
            Event event = eventService.getEventById(Integer.parseInt(id));
            if (event != null) {
                resp.setContentType("application/json");
                eventMapper.getEventById(event, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        MappingDTOUtils mappingDTOUtils = new MappingDTOUtils();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();
        EventDTO eventDTO = new ObjectMapper().readerFor(EventDTO.class).readValue(json);
        Event event = mappingDTOUtils.convertEventDTOToEvent(eventDTO);
//        Event event =new ObjectMapper().readerFor(EventDTO.class).readValue(json);
        eventService.saveEvent(event);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";

        if (path.matches("/\\d+")) {
            String event_id = path.substring(1);
            Event event = eventService.getEventById(Integer.parseInt(event_id));

            if (event != null) {
                eventService.deleteEventById(Integer.valueOf(event_id));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Event not exist");
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            if (eventService.getEventById(event.getId()) != null) {
                eventService.updateEvent(event);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not exist");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}