package com.maxim.controller;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.maxim.dto.EventDTO;
import com.maxim.dto.FileDTO;
import com.maxim.dto.UserDTO;
import com.maxim.model.Event;
import com.maxim.model.Status;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/api/v1/users/*")
public class UsersRestControllerV1 extends HttpServlet {

    private UserService userService = new UserService();
    private ModelMapper mapper = new ModelMapper();
    ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        UserMapper userMapper = new UserMapper();
        if (path.equals("")) {
            resp.setContentType("application/json");
            List<User> users = userService.getAllUsers();
            List<UserDTO> usersDTO = new ArrayList<>();
//            for (User user : users) {
//                UserDTO userDTO = mapper.map(user, UserDTO.class);
//
//                if (user.getEvents() != null) {
//                    List<EventDTO> eventDTOS = mapper.map(user.getEvents(), new TypeToken<List<EventDTO>>() {}.getType());
//                    for (EventDTO eventDTO : eventDTOS) {
//                        eventDTO.setUserDTO(userDTO);
//                    }
//                    List<Event> events = user.getEvents();
//
//                    for (int i = 0; i < events.size(); i++) {
//                        Event event = events.get(i);
//                        EventDTO eventDTO = eventDTOS.get(i);
//                        if (event.getFile()!=null) {
//                            FileDTO fileDTO = mapper.map(event.getFile(), FileDTO.class);
//                            eventDTO.setFileDTO(fileDTO);
//                        } else userDTO.setEventsDTO(eventDTOS);
//                    }
//
//                } else userDTO.setEventsDTO(null);
//
//
//                usersDTO.add(userDTO);
//            }

            for (User user : users) {
                UserDTO userDTO = mapper.map(user, UserDTO.class);

                if (user.getEvents() != null) {
                    List<EventDTO> eventDTOS = mapper.map(user.getEvents(), new TypeToken<List<EventDTO>>() {}.getType());

                    for (EventDTO eventDTO : eventDTOS) {
                        eventDTO.setUserDTO(userDTO);
                    }

                    for (Event event : user.getEvents()) {
                        EventDTO eventDTO = eventDTOS.stream()
                                .filter(dto -> dto.getId() == event.getId())
                                .findFirst()
                                .orElse(null);

                        if (event.getFile()!=null) {
                            FileDTO fileDTO = mapper.map(event.getFile(), FileDTO.class);
                            eventDTO.setFileDTO(fileDTO);
                        }
                    }

                    userDTO.setEventsDTO(eventDTOS);
                }

                usersDTO.add(userDTO);
            }
            userMapper.getUsers(usersDTO, resp);

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
        UserDTO userDTO = new ObjectMapper().readerFor(UserDTO.class).readValue(json);
        User user = mapper.map(userDTO, User.class);

        userService.saveUser(user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            String user_id = path.substring(1);
            User user = userService.getUserById(Integer.parseInt(user_id));
            if (user != null) {
                user.setStatus(String.valueOf(Status.DELETED));
                userService.updateUserById(user);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not exist");
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
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not exist");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
