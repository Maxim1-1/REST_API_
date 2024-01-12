package com.Maxim.repository.hibernate;


import com.Maxim.Hibernate_utils.HibernateConnector;
import com.Maxim.model.File;
import com.Maxim.repository.FileRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {

    HibernateConnector hibernateConnector = new HibernateConnector();

    @Override
    public File getById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.get(File.class,id);
    }

    @Override
    public List<File> getAll() {
        Session session = hibernateConnector.getSessionFactory().openSession();
        return session.createQuery("from File").getResultList();
    }

    @Override
    public File save(File file) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(file);
        transaction.commit();
        return file;
    }

    @Override
    public File update(File file) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(file);
        transaction.commit();
        return file;
    }

    @Override
    public void deleteById(Integer id) {
        Session session = hibernateConnector.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        File fileFromDB = session.get(File.class,id);
        session.remove(fileFromDB);
        transaction.commit();
    }
}
