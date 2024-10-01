package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundEntityException;
import org.example.model.Task;
import org.example.model.dto.TaskDto;
import org.example.model.dto.TaskMapper;
import org.example.service.EmployeeService;
import org.example.service.ProjectService;
import org.example.service.TaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController extends HttpServlet {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Task getTask(@PathVariable("id") Integer id) throws NotFoundEntityException {
        return taskService.read(id).orElseThrow(NotFoundEntityException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Task> getTasks() {
        return taskService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskDto taskDto) throws BadRequestException {
        return taskService.create(taskDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateTask(@RequestBody Task task) {
        return taskService.update(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteTask(@PathVariable("id") Integer id) {
        return taskService.delete(id);
    }
}