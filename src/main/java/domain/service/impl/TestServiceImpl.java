package domain.service.impl;

import domain.dao.TestDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.TestDAOImpl;
import domain.model.Option;
import domain.model.Question;
import domain.model.Test;
import domain.model.User;
import domain.service.TestService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

public class TestServiceImpl implements TestService {
    @Override
    public Test findByName(String name) {
        Test test = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            test = testDAO.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    @Override
    public List<Test> findByUser(User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            return testDAO.findByUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Map<Question, List<Option>> getAnswers(Test test, User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            return testDAO.getAnswers(test, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyMap();
    }

    @Override
    public void setAnswers(Map<Question, List<Option>> answers, User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                TestDAO testDAO = new TestDAOImpl(connection);
                testDAO.setAnswers(answers, user);
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
    public List<Test> findAll() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            return testDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Test> findAllLimit(int first, int max) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            return testDAO.findAllLimit(first, max);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Test findById(Long id) {
        Test test = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            TestDAO testDAO = new TestDAOImpl(connection);
            test = testDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    @Override
    public void insert(Test test) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                TestDAO testDAO = new TestDAOImpl(connection);
                testDAO.insert(test);
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
    public void update(Test test) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                TestDAO testDAO = new TestDAOImpl(connection);
                testDAO.update(test);
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
    public void remove(Test test) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                TestDAO testDAO = new TestDAOImpl(connection);
                testDAO.remove(test);
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
            TestDAO testDAO = new TestDAOImpl(connection);
            return testDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
