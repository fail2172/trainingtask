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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        if (id == null) {
            List<Employee> employees = service.findAll();
            json = objectMapper.writeValueAsString(employees);
        } else {
            Optional<Employee> employee = service.read(Integer.valueOf(id));
            if (employee.isPresent())
                json = objectMapper.writeValueAsString(employee.get());
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

        Employee employee = objectMapper.readValue(body, Employee.class);
        Optional<Employee> employeeInBase = service.create(employee);
        String json = employeeInBase.isPresent() ? objectMapper.writeValueAsString(employeeInBase.get()) : "{}";

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

        Employee project = objectMapper.readValue(body, Employee.class);
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

    private final EmployeeService service = EmployeeServiceImpl.getInstance();
}
