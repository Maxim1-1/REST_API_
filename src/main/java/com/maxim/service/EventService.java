package com.maxim.service;

import com.maxim.model.Event;
import com.maxim.repository.EventRepository;
import com.maxim.repository.hibernate.HibernateEventRepositoryImpl;


import java.util.List;

public class EventService {

    private  EventRepository eventRepository = new HibernateEventRepositoryImpl();

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event getEventById(Integer eventId) {
        return eventRepository.getById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAll();
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public void deleteEventById(Integer id) {
        eventRepository.deleteById(id);
    }
}
