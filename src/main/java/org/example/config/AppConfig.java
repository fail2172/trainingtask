package org.example.config;

import org.example.dao.*;
import org.example.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl();
    }

    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDaoImpl(getConnectionFactory());
    }

    @Bean
    public ProjectDao projectDao() {
        return new ProjectDaoImpl(getConnectionFactory());
    }

    @Bean
    public TaskDao taskDao() {
        return new TaskDaoImpl(getConnectionFactory());
    }

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeeDao());
    }

    @Bean
    public ProjectService projectService() {
        return new ProjectServiceImpl(projectDao());
    }

    @Bean
    public TaskServiceImpl taskService() {
        return new TaskServiceImpl(employeeService(), projectService(), taskDao());
    }
}
