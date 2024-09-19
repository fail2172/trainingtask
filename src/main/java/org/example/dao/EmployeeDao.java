package org.example.dao;

import org.example.model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    Employee create(Employee employee) throws SQLException;

    Optional<Employee> read(Integer id) throws SQLException;

    boolean update(Employee employee) throws SQLException;

    boolean delete(Integer id) throws SQLException;

    List<Employee> findAll() throws SQLException;
}
