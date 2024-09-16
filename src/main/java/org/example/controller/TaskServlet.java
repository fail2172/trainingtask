package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Task;
import org.example.service.TaskService;
import org.example.service.TaskServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(df);
        String json;
        if (id == null) {
            List<Task> projects = service.findAll();
            json = objectMapper.writeValueAsString(projects);
        } else {
            Optional<Task> task = service.read(Integer.valueOf(id));
            if (task.isPresent())
                json = objectMapper.writeValueAsString(task.get());
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(df);
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Task task = objectMapper.readValue(body, Task.class);
        Optional<Task> taskInBase = service.create(task);
        String json = taskInBase.isPresent() ? objectMapper.writeValueAsString(taskInBase.get()) : "{}";

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Task task = objectMapper.readValue(body, Task.class);
        if (service.update(task))
            resp.setStatus(HttpServletResponse.SC_OK);
        else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (service.delete(Integer.valueOf(id)))
            resp.setStatus(HttpServletResponse.SC_OK);
        else
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    private final TaskService service = TaskServiceImpl.getInstance();
}
