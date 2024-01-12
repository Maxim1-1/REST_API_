package com.Maxim.controller;


import com.Maxim.model.Event;
import com.Maxim.service.EventService;
import com.Maxim.view.EventView;
import com.Maxim.view.UserView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/events/id")
public class EventByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String event_id = req.getParameter("event_id");


        if(event_id !=null) {

            EventService eventService = new EventService();
            Event event = eventService.getEventById(Integer.valueOf(event_id));

            resp.setContentType("application/json");
            EventView eventView = new EventView();
            eventView.getEventById(event,resp);
        } else {
            resp.sendError(400);
        }

    }
}


