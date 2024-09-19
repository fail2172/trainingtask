package org.example.dao;

import org.example.model.Task;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Task create(Task task) throws SQLException;

    Optional<Task> read(Integer id) throws SQLException;

    boolean update(Task task) throws SQLException;

    boolean delete(Integer id) throws SQLException;

    List<Task> findAll() throws SQLException;

    List<Task> findByEmployee(Integer employeeId) throws SQLException;

    List<Task> findByProject(Integer projectId) throws SQLException;
}
