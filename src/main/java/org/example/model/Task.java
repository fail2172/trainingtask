package org.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private TaskStatus status;
    private LocalDate start;
    private LocalDate finish;
    private Integer estimate;
    private Employee employee;
    private Project project;

    public Task(Integer id, String name, TaskStatus status, LocalDate start, LocalDate finish, Integer estimate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.finish = finish;
        this.estimate = estimate;
        this.employee = null;
        this.project = null;
    }

    public Task(String name, TaskStatus status, LocalDate start, LocalDate finish, Integer estimate) {
        this.id = null;
        this.name = name;
        this.status = status;
        this.start = start;
        this.finish = finish;
        this.estimate = estimate;
        this.employee = null;
        this.project = null;
    }

    public Task() {

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

    public Project getProject() {
        return project;
    }

    public Employee getEmployee() {
        return employee;
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

    public void setProject(Project project) {
        this.project = project;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && status == task.status && Objects.equals(start, task.start) && Objects.equals(finish, task.finish) && Objects.equals(estimate, task.estimate) && Objects.equals(project, task.project) && Objects.equals(employee, task.employee);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(start);
        result = 31 * result + Objects.hashCode(finish);
        result = 31 * result + Objects.hashCode(estimate);
        result = 31 * result + Objects.hashCode(project);
        result = 31 * result + Objects.hashCode(employee);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", start=" + start +
                ", finish=" + finish +
                ", estimate=" + estimate +
                ", project=" + project +
                ", employee=" + employee +
                '}';
    }
}
