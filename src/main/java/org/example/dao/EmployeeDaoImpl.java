package org.example.dao;

import org.example.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private EmployeeDaoImpl() {
    }

    public static EmployeeDao getInstance() {
        if (INSTANCE == null) {
            synchronized (TaskDaoImpl.class) {
                if (INSTANCE == null)
                    return INSTANCE = new EmployeeDaoImpl();
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Employee> create(Employee employee) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPatronymic());
            statement.setString(4, employee.getPosition());
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? Optional.of(new Employee(
                    result.getInt(ID_FIELD),
                    employee.getName(),
                    employee.getSurname(),
                    employee.getPatronymic(),
                    employee.getPosition()))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> read(Integer id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? Optional.of(new Employee(
                    result.getInt(ID_FIELD),
                    result.getString(NAME_FIELD),
                    result.getString(SURNAME_FIELD),
                    result.getString(PATRONYMIC_FIELD),
                    result.getString(POSITION_FIELD)))
                    : Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Employee employee) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPatronymic());
            statement.setString(4, employee.getPosition());
            statement.setInt(5, employee.getId());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public List<Employee> findAll() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL);
            ResultSet result = statement.executeQuery();
            List<Employee> projects = new ArrayList<>();
            while (result.next()) {
                projects.add(new Employee(result.getInt(ID_FIELD),
                        result.getString(NAME_FIELD),
                        result.getString(SURNAME_FIELD),
                        result.getString(PATRONYMIC_FIELD),
                        result.getString(POSITION_FIELD)));
            }
            return projects;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
    private static volatile EmployeeDao INSTANCE;
    private final static String INSERT = "INSERT INTO employee (name, surname, patronymic, position) VALUES (?, ?, ?, ?) RETURNING id";
    private final static String READ = "SELECT * FROM employee WHERE id = ?";
    private final static String UPDATE = "UPDATE employee SET name = ?, surname = ?, patronymic = ?, position = ? WHERE id = ?";
    private final static String DELETE = "DELETE FROM employee WHERE id = ?";
    private final static String READ_ALL = "SELECT * FROM employee ORDER BY id DESC";
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";
    private final static String SURNAME_FIELD = "surname";
    private final static String PATRONYMIC_FIELD = "patronymic";
    private final static String POSITION_FIELD = "position";
}
