package org.example.config;

import org.example.controller.EmployeeController;
import org.example.controller.ProjectController;
import org.example.controller.TaskController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class WebConfig {
    @Bean
    public EmployeeController employeeController() {
        AppConfig appConfig = new AppConfig();
        return new EmployeeController(appConfig.employeeService());
    }

    @Bean
    public ProjectController projectController() {
        AppConfig appConfig = new AppConfig();
        return new ProjectController(appConfig.projectService());
    }

    @Bean
    public TaskController taskController() {
        AppConfig appConfig = new AppConfig();
        return new TaskController(appConfig.taskService());
    }
}
