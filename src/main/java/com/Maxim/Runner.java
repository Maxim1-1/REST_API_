package com.Maxim;

import com.Maxim.model.Event;
import com.Maxim.model.User;
import com.Maxim.service.EventService;
import com.Maxim.service.UserService;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        EventService eventService = new EventService();
        List<Event> events = eventService.getAllEvents();

        System.out.println(events.get(2).getUser().getName());

//        for (Event s:events) {
//            if (s.getUser().getName()!=null){
//                System.out.println(s.getUser().getName());
//            }
//
//
//        }
    }
}
