package org.example.model.dto;

import org.example.model.TaskStatus;

import java.time.LocalDate;

public class TaskDto {
    private Integer id;
    private String name;
    private TaskStatus status;
    private LocalDate start;
    private LocalDate finish;
    private Integer estimate;
    private Integer employeeId;
    private Integer projectId;

    public TaskDto(Integer id, String name, TaskStatus status, LocalDate start, LocalDate finish, Integer estimate, Integer projectId, Integer employeeId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.finish = finish;
        this.estimate = estimate;
        this.projectId = projectId;
        this.employeeId = employeeId;
    }

    public TaskDto() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
