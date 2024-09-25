package org.example.dao;

import org.example.model.Employee;
import org.example.model.Project;
import org.example.model.Task;
import org.example.model.TaskStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {
    private static volatile TaskDao INSTANCE;
    private final static String INSERT = "INSERT INTO task (name, status, start, finish, estimate, project_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private final static String READ = "SELECT * FROM task WHERE id = ?";
    private final static String UPDATE = "UPDATE task SET name = ?, status = ?, start = ?, finish = ?, estimate = ?, project_id = ?, employee_id = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM task WHERE id = ?";
    private final static String READ_ALL = "SELECT * FROM task ORDER BY id DESC";
    private final static String READ_ALL_BY_PROJECT = "SELECT * FROM task WHERE project_id = ?";
    private final static String READ_ALL_BY_EMPLOYEE = "SELECT * FROM task WHERE employee_id = ?";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String STATUS_FIELD = "status";
    private final static String START_FIELD = "start";
    private final static String FINISH_FIELD = "finish";
    private final static String ESTIMATE_FIELD = "estimate";
    private final static String PROJECT_ID_FIELD = "project_id";
    private final static String EMPLOYEE_ID_FIELD = "employee_id";
    private final static ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private final static EmployeeDao employeeDao = EmployeeDaoImpl.getInstance();
    private final static ProjectDao projectDao = ProjectDaoImpl.getInstance();

    private TaskDaoImpl() {
    }

    public static TaskDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new TaskDaoImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Task create(Task task) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, task.getName());
            statement.setString(2, task.getStatus().toString());
            statement.setDate(3, Date.valueOf(task.getStart()));
            statement.setDate(4, Date.valueOf(task.getFinish()));
            statement.setInt(5, task.getEstimate());
            statement.setInt(6, task.getProject().getId());
            statement.setInt(7, task.getEmployee().getId());
            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                throw new RuntimeException("Task could not be created");
            }

            Task taskInBase = new Task(
                    result.getInt(ID_FIELD),
                    task.getName(),
                    task.getStatus(),
                    task.getStart(),
                    task.getFinish(),
                    task.getEstimate());

            taskInBase.setEmployee(task.getEmployee());
            taskInBase.setProject(task.getProject());

            return taskInBase;
        }
    }

    @Override
    public Optional<Task> read(Integer id) throws SQLException {
        if (id == null) {
            return Optional.empty();
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            return result.next()
                    ? Optional.of(extractTaskFromResultSet(result))
                    : Optional.empty();
        }
    }

    @Override
    public boolean update(Task task) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getName());
            statement.setString(2, task.getStatus().toString());
            statement.setDate(3, Date.valueOf(task.getStart()));
            statement.setDate(4, Date.valueOf(task.getFinish()));
            statement.setInt(5, task.getEstimate());
            statement.setInt(6, task.getProject().getId());
            statement.setInt(7, task.getEmployee().getId());
            statement.setInt(8, task.getId());

            return statement.executeUpdate() != 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        if (id == null) {
            return false;
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);

            return statement.executeUpdate() != 0;
        }
    }

    @Override
    public List<Task> findAll() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL);
            ResultSet result = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();

            while (result.next())
                tasks.add(extractTaskFromResultSet(result));

            return tasks;
        }
    }

    @Override
    public List<Task> findByEmployee(Integer employeeId) throws SQLException {
        if (employeeId == null) {
            return new ArrayList<>();
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_EMPLOYEE);
            statement.setInt(1, employeeId);
            ResultSet result = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();

            while (result.next())
                tasks.add(extractTaskFromResultSet(result));

            return tasks;
        }
    }

    @Override
    public List<Task> findByProject(Integer projectId) throws SQLException {
        if (projectId == null) {
            return new ArrayList<>();
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_PROJECT);
            statement.setInt(1, projectId);
            ResultSet result = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();

            while (result.next())
                tasks.add(extractTaskFromResultSet(result));

            return tasks;
        }
    }

    private Task extractTaskFromResultSet(ResultSet set) throws SQLException {
            Optional<Employee> employee = employeeDao.read(set.getInt(EMPLOYEE_ID_FIELD));
            Optional<Project> project = projectDao.read(set.getInt(PROJECT_ID_FIELD));
            Task task = new Task(
                    set.getInt(ID_FIELD),
                    set.getString(NAME_FIELD),
                    TaskStatus.valueOf(set.getString(STATUS_FIELD)),
                    set.getDate(START_FIELD).toLocalDate(),
                    set.getDate(FINISH_FIELD).toLocalDate(),
                    set.getInt(ESTIMATE_FIELD));
            employee.ifPresent(task::setEmployee);
            project.ifPresent(task::setProject);

            return task;
    }
}
