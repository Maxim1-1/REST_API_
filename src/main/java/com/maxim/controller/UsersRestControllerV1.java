package com.maxim.controller;


import com.maxim.dto.UserDTO;
import com.maxim.model.User;
import com.maxim.service.UserService;
import com.maxim.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.utils.dto_ustils.MappingDTOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/api/v1/users/*")
public class UsersRestControllerV1 extends HttpServlet {

    private UserService userService = new UserService();
    private MappingDTOUtils mappingDTOUtils = new MappingDTOUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        UserMapper userMapper = new UserMapper();
        if (path.equals("")) {
            List<User> users = userService.getAllUsers();
            resp.setContentType("application/json");

//            List<UserDTO> userDTOS = users.stream().map(user -> mappingDTOUtils.convertUserToUserDTO(user)).toList();
            userMapper.getUsers(users,resp);

        } else if (path.matches("/\\d+")) {
            String id = path.substring(1);
            User user = userService.getUserById(Integer.parseInt(id));
            if (user != null) {
                resp.setContentType("application/json");
                userMapper.getUserById(user, resp);
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
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();

        UserDTO userDTO = new ObjectMapper().readerFor( UserDTO.class).readValue(json);
        User user = mappingDTOUtils.convertUserDTOToUser(userDTO);
        userService.saveUser(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            String user_id = path.substring(1);
            User user = userService.getUserById(Integer.parseInt(user_id));
            if (user != null) {
                userService.deleteUserById(Integer.valueOf(user_id));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not exist");
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
        User user = new ObjectMapper().readerFor(User.class).readValue(json);
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            if (userService.getUserById(user.getId()) != null) {
                userService.updateUserById(user);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"User not exist");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
