package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Project;
import org.example.service.ProjectService;
import org.example.service.ProjectServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        if (id == null) {
            List<Project> projects = service.findAll();
            json = objectMapper.writeValueAsString(projects);
        } else {
            Optional<Project> project = service.read(Integer.valueOf(id));
            if (project.isPresent())
                json = objectMapper.writeValueAsString(project.get());
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
        ObjectMapper objectMapper = new ObjectMapper();
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Project project = objectMapper.readValue(body, Project.class);
        Optional<Project> projectInBase = service.create(project);
        String json = projectInBase.isPresent() ? objectMapper.writeValueAsString(projectInBase.get()) : "{}";

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

        Project project = objectMapper.readValue(body, Project.class);
        if (service.update(project))
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

    private final ProjectService service = ProjectServiceImpl.getInstance();
}
