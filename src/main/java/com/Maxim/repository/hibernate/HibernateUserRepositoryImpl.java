package com.Maxim.repository.hibernate;


import com.Maxim.Hibernate_utils.HibernateConnector;
import com.Maxim.model.User;
import com.Maxim.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();
    @Override
    public User getById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.get(User.class,id);
    }

    @Override
    public List<User> getAll() {

        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.createQuery("from User").getResultList();
    }

    @Override
    public User save(User writer)  {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(writer);
        transaction.commit();
        return writer;
    }

    @Override
    public User update(User user) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
        return user;

    }

    @Override
    public void deleteById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User writer = session.get(User.class,id);
        session.remove(writer);
        transaction.commit();

    }
}
