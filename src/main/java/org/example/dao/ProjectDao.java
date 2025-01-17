package org.example.dao;

import org.example.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectDao {
    Optional<Project> create(Project project);

    Optional<Project> read(Integer id);

    boolean update(Project project);

    boolean delete(Integer id);

    List<Project> findAll();
}
