package domain.service.impl;

import domain.dao.CategoryDAO;
import domain.dao.OptionDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.CategoryDAOImpl;
import domain.dao.impl.OptionDAOImpl;
import domain.model.Category;
import domain.model.Option;
import domain.service.CategoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> category = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            category = categoryDAO.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            result = categoryDAO.findAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Category> findAllLimit(int first, int max) {
        List<Category> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            result = categoryDAO.findAllLimit(first, max);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Optional<Category> findById(Long id) {
        Optional<Category> category = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            category = categoryDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void insert(Category category) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
                categoryDAO.insert(category);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
                categoryDAO.update(category);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Category category) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
                categoryDAO.remove(category);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int count() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            CategoryDAO categoryDAO = new CategoryDAOImpl(connection);
            return categoryDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
