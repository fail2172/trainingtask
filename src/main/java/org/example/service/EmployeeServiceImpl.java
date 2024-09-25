package org.example.service;

import org.example.dao.EmployeeDao;
import org.example.dao.EmployeeDaoImpl;
import org.example.model.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeServiceImpl implements EmployeeService {
    private final static Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());
    private final static EmployeeDao dao = EmployeeDaoImpl.getInstance();
    private static volatile EmployeeService INSTANCE;

    private EmployeeServiceImpl() {
    }

    public static EmployeeService getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeServiceImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new EmployeeServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Employee create(Employee employee) {
        try {
            return dao.create(employee);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new Employee();
        }
    }

    @Override
    public Optional<Employee> read(Integer id) {
        try {
            return dao.read(id);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Employee employee) {
        try {
            return dao.update(employee);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return dao.delete(id);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Employee> findAll() {
        try {
            return dao.findAll();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ArrayList<>();
        }
    }
}