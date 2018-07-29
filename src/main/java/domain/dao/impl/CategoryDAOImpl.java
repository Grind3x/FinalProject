package domain.dao.impl;

import domain.dao.CategoryDAO;
import domain.dao.TestDAO;
import domain.model.Category;
import domain.model.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class CategoryDAOImpl implements CategoryDAO {
    private Connection connection;
    private Properties prop;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Category> findByName(String name) throws SQLException {
        Category category = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.find-by-name"))) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = extractCategoryFromResultSet(resultSet);
                }
            }
        }
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Category category = extractCategoryFromResultSet(resultSet);
                    result.add(category);
                }
            }
        }
        return result;
    }

    @Override
    public List<Category> findAllLimit(int first, int max) throws SQLException {
        List<Category> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Category category = extractCategoryFromResultSet(resultSet);
                    result.add(category);
                }
            }
        }
        return result;
    }

    @Override
    public Optional<Category> findById(Long id) throws SQLException {
        Category category = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = extractCategoryFromResultSet(resultSet);
                }
            }
        }
        return Optional.ofNullable(category);
    }

    @Override
    public void insert(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            statement.executeUpdate();
            if (category.getTests().size() > 0) {
                updateTests(category.getTests());
            }
            category.setId(getGeneratedId(statement));
        }
    }

    @Override
    public void update(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.update"))) {
            statement.setString(1, category.getName());
            statement.setLong(1, category.getId());
            statement.executeUpdate();
            if (category.getTests().size() > 0) {
                updateTests(category.getTests());
            }
        }
    }

    @Override
    public void remove(Category category) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.remove"))) {
            statement.setLong(1, category.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("category.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private Long getGeneratedId(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        long generatedKey = 0L;
        if (resultSet.next()) {
            generatedKey = resultSet.getLong(1);
        }
        return generatedKey;
    }

    private void updateTests(List<Test> tests) throws SQLException {
        TestDAO testDAO = new TestDAOImpl(connection);
        for (Test test : tests) {
            testDAO.update(test);
        }
    }

    private Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        TestDAO testDAO = new TestDAOImpl(connection);
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        category.setTests(testDAO.findByCategory(category));

        return category;
    }
}
