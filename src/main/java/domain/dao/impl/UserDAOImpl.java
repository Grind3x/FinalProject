package domain.dao.impl;

import domain.model.Role;
import domain.model.Test;
import domain.model.User;
import domain.dao.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class UserDAOImpl implements UserDAO {
    private Connection connection;
    private Properties prop;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-by-email"))) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                }
            }
        }
        return user;
    }

    @Override
    public User findByFullName(String fullName) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-by-full-name"))) {
            statement.setString(1, fullName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                }
            }
        }
        return user;
    }

    @Override
    public List<User> findByTest(Test test) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-by-test"))) {
            statement.setLong(1, test.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    User user = extractUserFromResultSet(resultSet);
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public List<User> findByRole(Role role) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-by-role"))) {
            statement.setLong(1, role.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    User user = extractUserFromResultSet(resultSet, role);
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public List<User> findAll() throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    User user = extractUserFromResultSet(resultSet);
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public List<User> findAllLimit(int first, int max) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    User user = extractUserFromResultSet(resultSet);
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public User findById(Long id) throws SQLException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = extractUserFromResultSet(resultSet);
                }
            }
        }
        return user;
    }

    @Override
    public void insert(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getRole().getId());
            statement.executeUpdate();

            user.setId(getGeneratedId(statement));
            if (user.getAssessments().size() > 0) {
                setAssessments(user);
            }
        }
    }

    private void setAssessments(User user) throws SQLException {
        Map<Test, Integer> assessments = user.getAssessments();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.insert-assessment"))) {
            for (Test test : assessments.keySet()) {
                statement.setLong(1, user.getId());
                statement.setLong(2, test.getId());
                statement.setInt(3, assessments.get(test));
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.update"))) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getRole().getId());
            statement.setLong(5, user.getId());

            if (user.getAssessments().size() > 0) {
                setAssessments(user);
            }

            statement.executeUpdate();
        }
    }

    @Override
    public void remove(User user) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.remove"))) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private User extractUserFromResultSet(ResultSet resultSet, Role... roles) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFullName(resultSet.getString("full_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setAssessments(getAssessments(user));
        if (roles.length > 0) {
            user.setRole(roles[0]);
        } else {
            user.setRole(new RoleDAOImpl(connection).findById(resultSet.getLong("role_id")));
        }
        return user;
    }

    private Map<Test, Integer> getAssessments(User user) throws SQLException {
        Map<Test, Integer> result = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("user.get-assessments"))) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Test test = new TestDAOImpl(connection).findById(resultSet.getLong("test_id"));
                    Integer mark = resultSet.getInt("mark");
                    result.put(test, mark);
                }
            }
        }
        return result;
    }

    private Long getGeneratedId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        Long generatedKey = 0L;
        if (resultSet.next()) {
            generatedKey = resultSet.getLong(1);
        }
        return generatedKey;
    }
}
