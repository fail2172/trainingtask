package org.example.dao;

import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Optional<Employee> create(Employee employee);

    Optional<Employee> read(Integer id);

    boolean update(Employee employee);

    boolean delete(Integer id);

    List<Employee> findAll();
}
