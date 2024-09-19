package org.example.service;

import org.example.dao.TaskDao;
import org.example.dao.TaskDaoImpl;
import org.example.model.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TaskServiceImpl implements TaskService {
    private final static Logger LOGGER = Logger.getLogger(TaskServiceImpl.class.getName());
    private final static TaskDao taskDao = TaskDaoImpl.getInstance();
    private static volatile TaskService INSTANCE;

    private TaskServiceImpl() {
    }

    public static TaskService getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskServiceImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new TaskServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Task create(Task task) {
        try {
            return taskDao.create(task);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return new Task();
        }
    }

    @Override
    public Optional<Task> read(Integer id) {
        try {
            return taskDao.read(id);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Task task) {
        try {
            return taskDao.update(task);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return taskDao.delete(id);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            return taskDao.findAll();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return new ArrayList<>();
        }
    }
}
