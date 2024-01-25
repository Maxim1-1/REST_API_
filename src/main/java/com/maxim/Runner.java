package com.maxim;

import com.maxim.model.Event;
import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;
import com.maxim.service.EventService;
import com.maxim.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        UserService userService = new UserService();

        User user = new User();
        Event event = new Event();
        File file = new File();
        file.setName("tes");
        file.setCreateAt("dsdds");
        file.setStatus(String.valueOf(Status.ACTIVE));

        event.setStatus(String.valueOf(Status.ACTIVE));
        event.setFile(file);
        event.setUser(user);


        List<Event> events = new ArrayList<>();
        events.add(event);

        user.setName("sdds");
        user.setStatus(String.valueOf(Status.ACTIVE));
        user.setEvents(events);


        userService.saveUser(user);
    }
}
