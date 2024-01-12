package com.Maxim.controller;


import com.Maxim.model.User;
import com.Maxim.service.UserService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = new UserService();
        List<User> users = userService.getAllUsers();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


            Gson gson = new Gson();

            UserService userService = new UserService();
            List<User> users2 = userService.getAllUsers();

            resp.setContentType("application/json");

            PrintWriter messageWriter = resp.getWriter();
            messageWriter.print(gson.toJson(users2));


    }


}
