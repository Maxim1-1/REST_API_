package com.Maxim.repository.hibernate;


import com.Maxim.Hibernate_utils.HibernateConnector;
import com.Maxim.model.Event;
import com.Maxim.repository.EventRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class HibernateEventRepositoryImpl implements EventRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();
    @Override
    public Event getById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.get(Event.class,id);
    }

    @Override
    public List<Event> getAll() {
//        TODO
        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.createQuery("from Event").getResultList();
    }

    @Override
    public Event save(Event event)  {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(event);
        transaction.commit();
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(event);
        transaction.commit();
        return event;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Event label = session.get(Event.class,id);
        session.remove(label);
        transaction.commit();
    }
}
