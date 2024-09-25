package org.example.service;

import org.example.dao.ProjectDao;
import org.example.dao.ProjectDaoImpl;
import org.example.model.Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProjectServiceImpl implements ProjectService {
    private final static Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class.getName());
    private final static ProjectDao dao = ProjectDaoImpl.getInstance();
    private static volatile ProjectService INSTANCE;

    private ProjectServiceImpl() {
    }

    public static ProjectService getInstance() {
        if (INSTANCE == null) {
            synchronized (ProjectServiceImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new ProjectServiceImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Project create(Project project) {
        try {
            return dao.create(project);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return new Project();
        }
    }

    @Override
    public Optional<Project> read(Integer id) {
        try {
            return dao.read(id);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Project project) {
        try {
            return dao.update(project);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            return dao.delete(id);
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Project> findAll() {
        try {
            return dao.findAll();
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            return new ArrayList<>();
        }
    }
}
