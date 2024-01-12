package com.Maxim.Hibernate_utils;

import com.Maxim.model.File;
import com.Maxim.model.Event;

import com.Maxim.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {

    private final Configuration configuration = new Configuration();


    public Configuration getConfiguration() {
        configuration.configure();
        configuration.addAnnotatedClass(Event.class);
        configuration.addAnnotatedClass(File.class);
        configuration.addAnnotatedClass(User.class);
        return configuration;
    }


    public SessionFactory getSessionFactory() {
       return getConfiguration().buildSessionFactory();
    }
}
