package org.example.controller;

import org.example.exception.NotFoundEntityException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Employee getEmployee(@PathVariable("id") Integer id) throws NotFoundEntityException {
        return service.read(id).orElseThrow(NotFoundEntityException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Employee> getEmployees() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.create(employee);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean updateEmployee(@RequestBody Employee employee) {
        return service.update(employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean deleteEmployee(@PathVariable("id") Integer id) {
        return service.delete(id);
    }
}