package com.maxim.repository.hibernate;


import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.repository.FileRepository;
import com.maxim.utils.hibernate_utils.HibernateConnector;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HibernateFileRepositoryImpl implements FileRepository {

    private HibernateConnector hibernateConnector = new HibernateConnector();

    @Override
    public File getById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            return session.get(File.class, id);
        }

    }

    @Override
    public List<File> getAll() {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            return session.createQuery("from File").getResultList();
        }
    }

    @Override
    public File save(File file) {
        try (Session session = hibernateConnector.getSessionFactory().openSession();) {
            Transaction transaction = session.beginTransaction();
            session.persist(file);
            transaction.commit();
            return file;
        }
    }

    @Override
    public File update(File file) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(file);
            transaction.commit();
            return file;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Session session = hibernateConnector.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            File fileFromDB = session.get(File.class, id);
            fileFromDB.setStatus(String.valueOf(Status.DELETED));
            session.merge(fileFromDB);
            transaction.commit();
        }
    }
}
