package org.example.service;

import org.example.dao.TaskDao;
import org.example.exception.BadRequestException;
import org.example.model.Task;
import org.example.model.dto.TaskDto;
import org.example.model.dto.TaskMapper;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class TaskServiceImpl implements TaskService {
    private final static Logger LOGGER = Logger.getLogger(TaskServiceImpl.class.getName());
    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final TaskDao dao;

    public TaskServiceImpl(EmployeeService employeeService, ProjectService projectService, TaskDao dao) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.dao = dao;
    }

    @Override
    public Task create(Task task) {
        return dao.create(task);
    }

    @Override
    public Task create(TaskDto taskDto) throws BadRequestException {
        Task task = TaskMapper.toModel(taskDto);
        task.setEmployee(employeeService.read(taskDto.getEmployeeId()).orElseThrow(BadRequestException::new));
        task.setProject(projectService.read(taskDto.getProjectId()).orElseThrow(BadRequestException::new));
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
}