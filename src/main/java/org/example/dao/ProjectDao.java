package org.example.dao;

import org.example.model.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectDao {
    Project create(Project project) throws SQLException;

    Optional<Project> read(Integer id) throws SQLException;

    boolean update(Project project) throws SQLException;

    boolean delete(Integer id) throws SQLException;

    List<Project> findAll() throws SQLException;
}
