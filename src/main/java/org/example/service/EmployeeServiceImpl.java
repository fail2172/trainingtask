package org.example.service;

import org.example.dao.EmployeeDao;
import org.example.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class EmployeeServiceImpl implements EmployeeService {
    private final static Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());
    private final EmployeeDao dao;

    public EmployeeServiceImpl(EmployeeDao dao) {
        this.dao = dao;
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