package com.maxim.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.dto.EventDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventMapper {

    public void getEvents(List<EventDTO> events, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(events));
    }

    public void getEventById(EventDTO event, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(event));
    }
}
