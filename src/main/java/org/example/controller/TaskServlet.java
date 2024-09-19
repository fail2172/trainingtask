package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Employee;
import org.example.model.Project;
import org.example.model.Task;
import org.example.model.dto.TaskDto;
import org.example.model.dto.TaskMapper;
import org.example.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    private final static EmployeeService employeeService = EmployeeServiceImpl.getInstance();
    private final static ProjectService projectService = ProjectServiceImpl.getInstance();
    private final static TaskService taskService = TaskServiceImpl.getInstance();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(df);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = req.getParameter("id") == null
                    ? objectMapper.writeValueAsString(taskService.findAll())
                    : objectMapper.writeValueAsString(taskService.read(Integer.valueOf(req.getParameter("id")))
                    .orElseThrow());
            writeJsonToResponse(resp, json);
        } catch (NoSuchElementException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Task task = taskFromJson(body);
            String json = objectMapper.writeValueAsString(TaskMapper.toDto(taskService.create(task)));

            writeJsonToResponse(resp, json);
        } catch (NoSuchElementException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Task task = taskFromJson(body);

        if (!taskService.update(task))
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!taskService.delete(Integer.valueOf(req.getParameter("id"))))
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private Task taskFromJson(String json) throws JsonProcessingException {
        TaskDto taskDto = objectMapper.readValue(json, TaskDto.class);
        Task task = TaskMapper.toModel(taskDto);
        Optional<Employee> employee = Optional.of(employeeService.read(taskDto.getEmployeeId()).orElseThrow());
        Optional<Project> project = Optional.of(projectService.read(taskDto.getProjectId()).orElseThrow());
        employee.ifPresent(task::setEmployee);
        project.ifPresent(task::setProject);

        return task;
    }

    private void writeJsonToResponse(HttpServletResponse resp, String json) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
