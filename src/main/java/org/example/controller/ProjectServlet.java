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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {
    private final static ProjectService service = ProjectServiceImpl.getInstance();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = req.getParameter("id") == null
                    ? objectMapper.writeValueAsString(service.findAll())
                    : objectMapper.writeValueAsString(service.read(Integer.valueOf(req.getParameter("id")))
                    .orElseThrow());
            writeJsonToResponse(resp, json);
        } catch (NoSuchElementException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Project project = service.create(objectMapper.readValue(body, Project.class));

        if (project.getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        writeJsonToResponse(resp, objectMapper.writeValueAsString(project));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if (!service.update(objectMapper.readValue(body, Project.class)))
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!service.delete(Integer.valueOf(req.getParameter("id"))))
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void writeJsonToResponse(HttpServletResponse resp, String json) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}