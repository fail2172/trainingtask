package org.example.service;

import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee create(Employee employee);

    Optional<Employee> read(Integer id);

    boolean update(Employee employee);

    boolean delete(Integer id);

    List<Employee> findAll();
}
