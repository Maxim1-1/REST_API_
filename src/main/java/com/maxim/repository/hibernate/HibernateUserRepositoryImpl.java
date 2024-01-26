package com.maxim.repository.hibernate;


import com.maxim.model.User;
import com.maxim.repository.UserRepository;
import com.maxim.utils.hibernate_utils.HibernateConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();

    @Override
    public User getById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            String jpqlQuery = "select u from User u left join fetch u.events e left join fetch e.file f where u.id = :id";
            return session.createQuery(jpqlQuery, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            return session.createQuery("from User u left join fetch u.events e left join fetch e.file").getResultList();
        }
    }

    @Override
    public User save(User writer) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(writer);
            transaction.commit();
            return writer;
        }
    }

    @Override
    public User update(User user) {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return user;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            User writer = session.get(User.class, id);
            session.remove(writer);
            transaction.commit();
        }
    }
}
