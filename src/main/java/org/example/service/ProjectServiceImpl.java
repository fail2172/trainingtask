package org.example.service;

import org.example.dao.ProjectDao;
import org.example.dao.ProjectDaoImpl;
import org.example.dao.TaskDaoImpl;
import org.example.model.Project;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    private final static ProjectDao dao = ProjectDaoImpl.getInstance();
    private static volatile ProjectService INSTANCE;

    private ProjectServiceImpl() {
    }

    public static ProjectService getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new ProjectServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Project create(Project project) {
        return dao.create(project);
    }

    @Override
    public Optional<Project> read(Integer id) {
        return dao.read(id);
    }

    @Override
    public boolean update(Project project) {
        return dao.update(project);
    }

    @Override
    public boolean delete(Integer id) {
        return dao.delete(id);
    }

    @Override
    public List<Project> findAll() {
        return dao.findAll();
    }
}
