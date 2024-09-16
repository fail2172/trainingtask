package org.example.dao.hibernate;

import org.example.dao.TaskDao;
import org.example.model.Employee;
import org.example.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateConnectionFactory {
    public static HibernateConnectionFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (HibernateConnectionFactory.class) {
                if (INSTANCE == null)
                    return INSTANCE = new HibernateConnectionFactory();
            }
        }
        return INSTANCE;
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    private HibernateConnectionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Project.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(TaskDao.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        this.sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    private static volatile HibernateConnectionFactory INSTANCE;
    private final SessionFactory sessionFactory;
}
