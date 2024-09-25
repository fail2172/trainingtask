package org.example.dao;

import org.example.model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {
    private static volatile TaskDao INSTANCE;
    private final static HibernateConnectionFactory connectionFactory = HibernateConnectionFactory.getInstance();

    private TaskDaoImpl() {
    }

    public static TaskDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new TaskDaoImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Task create(Task task) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            return task;
        }
    }

    @Override
    public Optional<Task> read(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            return Optional.ofNullable(session.get(Task.class, id));
        }
    }

    @Override
    public boolean update(Task task) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(Task.class, id));
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public List<Task> findAll() {
        try (Session session = connectionFactory.openSession()) {
            return session.createQuery("from Task", Task.class).list();
        }
    }
}
