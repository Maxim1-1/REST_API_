package com.Maxim.controller;


import com.Maxim.model.User;
import com.Maxim.model.User;
import com.Maxim.service.UserService;
import com.Maxim.service.UserService;
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
import java.util.List;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> events = userService.getAllUsers();

        resp.setContentType("application/json");
        UserView eventView = new UserView();
        eventView.getUsers(events,resp);
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
        User event = new ObjectMapper().readerFor( User.class).readValue(json);

        userService.saveUser(event);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String event_id = req.getParameter("event_id");
        if(event_id!=null) {
            userService.deleteUserById(Integer.valueOf(event_id));
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
        User event = new ObjectMapper().readerFor(User.class).readValue(json);

        if (userService.getUserById(event.getId())!=null) {
            userService.updateUserById(event);
        } else {
            resp.sendError(400,"File not exist");
        }
    }

}
