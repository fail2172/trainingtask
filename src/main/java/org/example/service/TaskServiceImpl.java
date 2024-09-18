package org.example.service;

import org.example.dao.*;
import org.example.model.Task;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {
    private final static TaskDao taskDao = TaskDaoImpl.getInstance();
    private static volatile TaskService INSTANCE;

    private TaskServiceImpl() {
    }

    public static TaskService getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new TaskServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Task create(Task task) {
        return taskDao.create(task);
    }

    @Override
    public Optional<Task> read(Integer id) {
        return taskDao.read(id);
    }

    @Override
    public boolean update(Task task) {
        return taskDao.update(task);
    }

    @Override
    public boolean delete(Integer id) {
        return taskDao.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }
}
