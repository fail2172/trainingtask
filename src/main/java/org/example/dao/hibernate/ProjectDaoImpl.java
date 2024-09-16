package org.example.dao.hibernate;

import org.example.dao.ProjectDao;
import org.example.model.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;
import java.util.Optional;

public class ProjectDaoImpl implements ProjectDao {
    public static org.example.dao.ProjectDao getInstance() {
        if (INSTANCE == null) {
            synchronized (HibernateConnectionFactory.class) {
                if (INSTANCE == null)
                    return INSTANCE = new ProjectDaoImpl();
            }
        }
        return INSTANCE;
    }

    private ProjectDaoImpl() {
        connectionFactory = HibernateConnectionFactory.getInstance();
    }

    @Override
    public Optional<Project> create(Project project) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
            return Optional.of(project);
        }
    }

    @Override
    public Optional<Project> read(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            return Optional.ofNullable(session.get(Project.class, id));
        }
    }

    @Override
    public boolean update(Project project) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Project project = session.get(Project.class, id);
            session.delete(project);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public List<Project> findAll() {
        try (Session session = connectionFactory.openSession()) {
            return session.createQuery("FROM project").list();
        }
    }

    private static volatile org.example.dao.ProjectDao INSTANCE;
    private final HibernateConnectionFactory connectionFactory;
}
