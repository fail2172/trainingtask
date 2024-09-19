package org.example.dao;

import org.example.model.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDaoImpl implements ProjectDao {
    private static volatile ProjectDao INSTANCE;
    private final static String INSERT = "INSERT INTO project (name, description) VALUES (?, ?) RETURNING id";
    private final static String READ = "SELECT * FROM project WHERE id = ?";
    private final static String UPDATE = "UPDATE project SET name = ?, description = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM project WHERE id = ?";
    private final static String READ_ALL = "SELECT * FROM project ORDER BY id DESC";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String DESCRIPTION_FIELD = "description";
    ConnectionFactory connectionService = ConnectionFactory.getInstance();

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
    public Project create(Project project) throws SQLException {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            ResultSet result = statement.executeQuery();

            if (!result.next())
                throw new RuntimeException("Project could not be created");

            return new Project(
                    result.getInt(ID_FIELD),
                    project.getName(),
                    project.getDescription());
        }
    }

    @Override
    public Optional<Project> read(Integer id) throws SQLException {
        if (id == null) {
            return Optional.empty();
        }

        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            return result.next()
                    ? Optional.of(extractProjectFromResultSet(result))
                    : Optional.empty();
        }
    }

    @Override
    public boolean update(Project project) throws SQLException {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setInt(3, project.getId());

            return statement.executeUpdate() != 0;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        if (id == null) {
            return false;
        }

        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);

            return statement.executeUpdate() != 0;
        }
    }

    @Override
    public List<Project> findAll() throws SQLException {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL);
            ResultSet result = statement.executeQuery();
            List<Project> projects = new ArrayList<>();

            while (result.next())
                projects.add(extractProjectFromResultSet(result));

            return projects;
        }
    }

    private Project extractProjectFromResultSet(ResultSet set) throws SQLException {
        return new Project(
                    set.getInt(ID_FIELD),
                    set.getString(NAME_FIELD),
                    set.getString(DESCRIPTION_FIELD));
    }
}
