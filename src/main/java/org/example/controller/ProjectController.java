package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import org.example.exception.NotFoundEntityException;
import org.example.model.Project;
import org.example.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController extends HttpServlet {
    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Project getProject(@PathVariable("id") Integer id) throws NotFoundEntityException {
        return service.read(id).orElseThrow(NotFoundEntityException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Project> getProjects() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody Project project) {
        return service.create(project);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateProject(@RequestBody Project project) {
        return service.update(project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteProject(@PathVariable("id") Integer id) {
        return service.delete(id);
    }
}