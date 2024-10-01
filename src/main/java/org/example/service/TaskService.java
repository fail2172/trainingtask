package org.example.service;

import org.example.exception.BadRequestException;
import org.example.model.Task;
import org.example.model.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task create(Task task);

    Task create(TaskDto taskDto) throws BadRequestException;

    Optional<Task> read(Integer id);

    boolean update(Task task);

    boolean delete(Integer id);

    List<Task> findAll();
}
