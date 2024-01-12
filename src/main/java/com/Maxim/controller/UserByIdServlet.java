package com.Maxim.controller;


import com.Maxim.model.User;
import com.Maxim.service.UserService;
import com.Maxim.view.UserView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


@WebServlet("/users/id")
public class UserByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_id = req.getParameter("user_id");
        if(user_id!=null) {

            UserService userService = new UserService();
            User user = userService.getUserById(Integer.valueOf(user_id));

            resp.setContentType("application/json");
            UserView userView = new UserView();
            userView.getUserById(user,resp);
        } else {
            resp.sendError(400);
        }



    }
}


