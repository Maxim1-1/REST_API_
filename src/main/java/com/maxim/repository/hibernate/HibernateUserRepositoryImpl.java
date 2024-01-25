package com.maxim.repository.hibernate;


import com.maxim.utils.hibernate_utils.HibernateConnector;
import com.maxim.model.User;
import com.maxim.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateUserRepositoryImpl implements UserRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();

    @Override
    public User getById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> getAll() {

     Session session = hibernateConnector.getSessionFactory().openSession();
            return session.createQuery("from User").getResultList();


//        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
//            return session.createQuery("from User").getResultList();
//            String jpqlQuery = "select u, e, f\n" +
//                    "from user u \n" +
//                    "left join u.events e \n" +
//                    "left join e.file f";
//            return session.createQuery(jpqlQuery).getResultList();
//        }
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
