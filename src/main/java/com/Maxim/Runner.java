package com.Maxim;

import com.Maxim.model.User;
import com.Maxim.service.UserService;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        UserService userService = new UserService();
        List<User> users2 = userService.getAllUsers();

        for (User s:users2) {
            System.out.println(s.getName());
            System.out.println(s.getEvents().get(1).getId());

        }
    }
}
