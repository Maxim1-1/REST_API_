package com.maxim.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.dto.UserDTO;
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

    public void getUserById(UserDTO userDTO, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(userDTO));
    }
}
