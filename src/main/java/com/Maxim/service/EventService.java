package com.Maxim.service;

import com.Maxim.model.Event;
import com.Maxim.repository.EventRepository;
import com.Maxim.repository.hibernate.HibernateEventRepositoryImpl;


import java.util.List;

public class EventService {

    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();

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
