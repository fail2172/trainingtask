package org.example.dao;

import org.example.model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {
    private static volatile EmployeeDao INSTANCE;
    private final static HibernateConnectionFactory connectionFactory = HibernateConnectionFactory.getInstance();

    private EmployeeDaoImpl() {
    }

    public static EmployeeDao getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new EmployeeDaoImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Employee create(Employee employee) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            return employee;
        }
    }

    @Override
    public Optional<Employee> read(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            return Optional.ofNullable(session.get(Employee.class, id));
        }
    }

    @Override
    public boolean update(Employee employee) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = connectionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(Employee.class, id));
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        }
    }

    @Override
    public List<Employee> findAll() {
        try (Session session = connectionFactory.openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        }
    }
}
