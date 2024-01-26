package com.maxim.repository.hibernate;


import com.maxim.model.Event;
import com.maxim.model.Status;
import com.maxim.repository.EventRepository;
import com.maxim.utils.hibernate_utils.HibernateConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();

    @Override
    public Event getById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            return session.get(Event.class, id);
        }

    }

    @Override
    public List<Event> getAll() {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            return session.createQuery("from Event").getResultList();
        }
    }

    @Override
    public Event save(Event event) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(event);
            transaction.commit();
            return event;
        }
    }

    @Override
    public Event update(Event event) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(event);
            transaction.commit();
            return event;
        }

    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Event event = session.get(Event.class, id);
            event.setStatus(String.valueOf(Status.DELETED));
            session.merge(event);
            transaction.commit();
        }

    }
}
