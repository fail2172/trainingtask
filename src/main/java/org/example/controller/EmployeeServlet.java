package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    private final static EmployeeService service = EmployeeServiceImpl.getInstance();
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
        Employee employee = service.create(objectMapper.readValue(body, Employee.class));

        if (employee.getId() == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        writeJsonToResponse(resp, objectMapper.writeValueAsString(employee));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if (!service.update(objectMapper.readValue(body, Employee.class)))
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