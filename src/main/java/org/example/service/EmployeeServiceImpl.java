package org.example.service;

import org.example.dao.EmployeeDao;
import org.example.dao.EmployeeDaoImpl;
import org.example.dao.TaskDaoImpl;
import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final static EmployeeDao dao = EmployeeDaoImpl.getInstance();
    private static volatile EmployeeService INSTANCE;

    private EmployeeServiceImpl() {
    }

    public static EmployeeService getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new EmployeeServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Employee create(Employee employee) {
        return dao.create(employee);
    }

    @Override
    public Optional<Employee> read(Integer id) {
        return dao.read(id);
    }

    @Override
    public boolean update(Employee employee) {
        return dao.update(employee);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public List<Employee> findAll() {
        return dao.findAll();
    }
}
