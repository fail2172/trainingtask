package org.example.dao;

import org.example.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Task create(Task task);

    Optional<Task> read(Integer id);

    boolean update(Task task);

    boolean delete(Integer id);

    List<Task> findAll();
}
