package org.example.dao;

import org.example.model.Task;
import org.example.model.TaskStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDaoImpl implements TaskDao {

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
    public Optional<Task> create(Task task) {
/*        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, task.getName());
            statement.setString(2, task.getStatus().toString());
            statement.setDate(3, Date.valueOf(task.getStart()));
            statement.setDate(4, Date.valueOf(task.getStart()));
            statement.setInt(5, task.getEstimate());
            statement.setInt(6, task.getProjectId());
            statement.setInt(7, task.getEmployeeId());
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? Optional.of(new Task(
                    result.getInt(ID_FIELD),
                    task.getName(),
                    task.getStatus(),
                    task.getStart(),
                    task.getFinish(),
                    task.getEstimate(),
                    task.getProjectId(),
                    task.getEmployeeId()))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        return null;
    }

    @Override
    public Optional<Task> read(Integer id) {
//        try (Connection connection = connectionFactory.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(READ);
//            statement.setInt(1, id);
//            ResultSet result = statement.executeQuery();
//            return result.next()
//                    ? Optional.of(new Task(
//                    result.getInt(ID_FIELD),
//                    result.getString(NAME_FIELD),
//                    TaskStatus.valueOf(result.getString(STATUS_FIELD)),
//                    result.getDate(START_FIELD).toLocalDate(),
//                    result.getDate(FINISH_FIELD).toLocalDate(),
//                    result.getInt(ESTIMATE_FIELD),
//                    result.getInt(PROJECT_ID_FIELD),
//                    result.getInt(EMPLOYEE_ID_FIELD)))
//                    : Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    @Override
    public boolean update(Task task) {
//        try (Connection connection = connectionFactory.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(UPDATE);
//            statement.setString(1, task.getName());
//            statement.setString(2, task.getStatus().toString());
//            statement.setDate(3, Date.valueOf(task.getStart()));
//            statement.setDate(4, Date.valueOf(task.getFinish()));
//            statement.setInt(5, task.getEstimate());
//            statement.setInt(6, task.getProjectId());
//            statement.setInt(7, task.getEmployeeId());
//            statement.setInt(8, task.getId());
//
//            return statement.executeUpdate() != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
//        try (Connection connection = connectionFactory.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(READ_ALL);
//            ResultSet result = statement.executeQuery();
//            List<Task> projects = new ArrayList<>();
//            while (result.next()) {
//                projects.add(new Task(result.getInt(ID_FIELD),
//                        result.getString(NAME_FIELD),
//                        TaskStatus.valueOf(result.getString(STATUS_FIELD)),
//                        result.getDate(START_FIELD).toLocalDate(),
//                        result.getDate(FINISH_FIELD).toLocalDate(),
//                        result.getInt(ESTIMATE_FIELD),
//                        result.getInt(PROJECT_ID_FIELD),
//                        result.getInt(EMPLOYEE_ID_FIELD)));
//            }
//            return projects;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        return new ArrayList<>();
    }

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private static volatile TaskDao INSTANCE;
    private final static String INSERT = "INSERT INTO task (name, status, start, finish, estimate, project_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private final static String READ = "SELECT * FROM task WHERE id = ?";
    private final static String UPDATE = "UPDATE task SET name = ?, status = ?, start = ?, finish = ?, estimate = ?, project_id = ?, employee_id = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM task WHERE id = ?";
    private final static String READ_ALL = "SELECT * FROM task ORDER BY id DESC";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String STATUS_FIELD = "status";
    private final static String START_FIELD = "start";
    private final static String FINISH_FIELD = "finish";
    private final static String ESTIMATE_FIELD = "estimate";
    private final static String PROJECT_ID_FIELD = "project_id";
    private final static String EMPLOYEE_ID_FIELD = "employee_id";
}
