package org.example.dao;

import org.example.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {
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
    ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

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
    public Employee create(Employee employee) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPatronymic());
            statement.setString(4, employee.getPosition());
            ResultSet result = statement.executeQuery();

            if (!result.next())
                throw new RuntimeException("Employee could not be created");

            return new Employee(
                    result.getInt(ID_FIELD),
                    employee.getName(),
                    employee.getSurname(),
                    employee.getPatronymic(),
                    employee.getPosition());
        }
    }

    @Override
    public Optional<Employee> read(Integer id) throws SQLException {
        if (id == null) {
            return Optional.empty();
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            return result.next()
                    ? Optional.of(extractEmployeeFromResultSet(result))
                    : Optional.empty();
        }
    }

    @Override
    public boolean update(Employee employee) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getSurname());
            statement.setString(3, employee.getPatronymic());
            statement.setString(4, employee.getPosition());
            statement.setInt(5, employee.getId());

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
    public List<Employee> findAll() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(READ_ALL);
            ResultSet result = statement.executeQuery();
            List<Employee> employees = new ArrayList<>();

            while (result.next())
                employees.add(extractEmployeeFromResultSet(result));

            return employees;
        }
    }

    private Employee extractEmployeeFromResultSet(ResultSet set) throws SQLException {
        return new Employee(
                set.getInt(ID_FIELD),
                set.getString(NAME_FIELD),
                set.getString(SURNAME_FIELD),
                set.getString(PATRONYMIC_FIELD),
                set.getString(POSITION_FIELD));
    }
}
