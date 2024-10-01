package org.example.service;

import org.example.dao.ProjectDao;
import org.example.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProjectServiceImpl implements ProjectService {
    private final static Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class.getName());
    private final ProjectDao dao;

    public ProjectServiceImpl(ProjectDao dao) {
        this.dao = dao;
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