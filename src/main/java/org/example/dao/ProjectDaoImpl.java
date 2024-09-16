package org.example.dao;

import org.example.model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDaoImpl implements ProjectDao {

    private ProjectDaoImpl() {
    }

    public static ProjectDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new ProjectDaoImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Project> create(Project project) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? Optional.of(new Project(
                    result.getInt(ID_FIELD),
                    project.getName(),
                    project.getDescription()))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> read(Integer id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? Optional.of(new Project(
                    result.getInt(ID_FIELD),
                    result.getString(NAME_FIELD),
                    result.getString(DESCRIPTION_FIELD)))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Project project) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setInt(3, project.getId());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL);
            ResultSet result = statement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (result.next()) {
                projects.add(new Project(result.getInt(ID_FIELD),
                        result.getString(NAME_FIELD),
                        result.getString(DESCRIPTION_FIELD)));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ConnectionFactory connectionService = ConnectionFactory.getInstance();
    private static volatile ProjectDao INSTANCE;
    private final static String INSERT = "INSERT INTO project (name, description) VALUES (?, ?) RETURNING id";
    private final static String READ = "SELECT * FROM project WHERE id = ?";
    private final static String UPDATE = "UPDATE project SET name = ?, description = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM project WHERE id = ?";
    private final static String READ_ALL = "SELECT * FROM project ORDER BY id DESC";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String DESCRIPTION_FIELD = "description";
}
