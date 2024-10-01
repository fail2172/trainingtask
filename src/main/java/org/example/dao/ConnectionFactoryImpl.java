package org.example.dao;

import org.example.model.Employee;
import org.example.model.Project;
import org.example.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private final SessionFactory sessionFactory;

    public ConnectionFactoryImpl() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Project.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Task.class);
        var builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        this.sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }
}
