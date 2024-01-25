package com.maxim.mapper;

import com.maxim.dto.UserDTO;
import com.maxim.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserMapper {
    private ObjectMapper mapper = new ObjectMapper();
    public void getUsers(List<UserDTO> users, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(users));
    }

    public void getUserById(User user, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(user));
    }
}
