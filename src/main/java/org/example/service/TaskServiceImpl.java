package org.example.service;

import org.example.dao.TaskDao;
import org.example.dao.TaskDaoImpl;
import org.example.model.Task;

import java.util.List;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

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
    public Optional<Task> create(Task task) {
        return dao.create(task);
    }

    @Override
    public Optional<Task> read(Integer id) {
        return dao.read(id);
    }

    @Override
    public boolean update(Task task) {
        return dao.update(task);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return dao.findAll();
    }

    private final static TaskDao dao = TaskDaoImpl.getInstance();
    private static volatile TaskService INSTANCE;
}
