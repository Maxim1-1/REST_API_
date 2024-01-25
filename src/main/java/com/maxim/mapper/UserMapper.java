package com.maxim.mapper;

import com.maxim.dto.UserDTO;
import com.maxim.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserMapper {

    public void getUsers(List<UserDTO> users, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(users));
    }

    public void getUserById(User user, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(user));
    }
}
