package com.Maxim.view;

import com.Maxim.model.Event;
import com.Maxim.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventView {

    public void getEvents(List<Event> events, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(events));
    }

    public void getEventById(Event event, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(event));
    }
}
