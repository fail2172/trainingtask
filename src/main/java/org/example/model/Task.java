package org.example.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @Column(name = "start")
    private LocalDate start;
    @Column(name = "finish")
    private LocalDate finish;
    @Column(name = "estimate")
    private Integer estimate;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Task(Integer id, String name, TaskStatus status, LocalDate start, LocalDate finish, Integer estimate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.start = start;
        this.finish = finish;
        this.estimate = estimate;
    }

    public Task(String name, TaskStatus status, LocalDate start, LocalDate finish, Integer estimate) {
        this.name = name;
        this.status = status;
        this.start = start;
        this.finish = finish;
        this.estimate = estimate;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public Integer getEstimate() {
        return estimate;
    }

    public void setEstimate(Integer estimate) {
        this.estimate = estimate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && status == task.status && Objects.equals(start, task.start) && Objects.equals(finish, task.finish) && Objects.equals(estimate, task.estimate) && Objects.equals(employee, task.employee) && Objects.equals(project, task.project);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(start);
        result = 31 * result + Objects.hashCode(finish);
        result = 31 * result + Objects.hashCode(estimate);
        result = 31 * result + Objects.hashCode(employee);
        result = 31 * result + Objects.hashCode(project);
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
                ", employee=" + employee +
                ", project=" + project +
                '}';
    }
}