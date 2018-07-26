package domain.dao.impl;

import domain.dao.UserDAO;
import domain.model.Role;
import domain.dao.RoleDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RoleDAOImpl implements RoleDAO {
    private Connection connection;
    private Properties prop;

    public RoleDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Role> findByName(String name) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        Role role = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.find-by-name"))) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    role = extractRoleFromResultSet(resultSet);
                }
            }
        }
        return Optional.ofNullable(role);
    }

    @Override
    public List<Role> findAll() throws SQLException {
        List<Role> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Role role = extractRoleFromResultSet(resultSet);
                    result.add(role);
                }
            }
        }
        return result;
    }

    @Override
    public List<Role> findAllLimit(int first, int max) throws SQLException {
        List<Role> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Role role = extractRoleFromResultSet(resultSet);
                    result.add(role);
                }
            }
        }
        return result;
    }

    @Override
    public Optional<Role> findById(Long id) throws SQLException {
        Role role = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role = extractRoleFromResultSet(resultSet);
                }
            }
        }
        return Optional.ofNullable(role);
    }

    @Override
    public void insert(Role role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, role.getName());
            statement.executeUpdate();

            role.setId(getGeneratedId(statement));
        }
    }

    @Override
    public void update(Role role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.update"))) {
            statement.setString(1, role.getName());
            statement.setLong(2, role.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Role role) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.remove"))) {
            statement.setLong(1, role.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("role.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private Role extractRoleFromResultSet(ResultSet resultSet) throws SQLException {
        UserDAO userDAO = new UserDAOImpl(connection);
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));
        role.setUsers(userDAO.findByRole(role));

        return role;
    }

    private Long getGeneratedId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        long generatedKey = 0L;
        if (resultSet.next()) {
            generatedKey = resultSet.getLong(1);
        }
        return generatedKey;
    }
}
