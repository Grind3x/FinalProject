package domain.service.impl;

import domain.dao.QuestionDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.QuestionDAOImpl;
import domain.model.Question;
import domain.model.Role;
import domain.model.Test;
import domain.service.QuestionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;

public class QuestionServiceImpl implements QuestionService {
    @Override
    public Question findByText(String text) {
        Question question = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            question = questionDAO.findByText(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public List<Question> findByTest(Test test) {
        List<Question> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            result = questionDAO.findByTest(test);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Question> findAll() {
        List<Question> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            result = questionDAO.findAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Question> findAllLimit(int first, int max) {
        List<Question> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            result = questionDAO.findAllLimit(first, max);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Question findById(Long id) {
        Question question = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            question = questionDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public void insert(Question question) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                QuestionDAO questionDAO = new QuestionDAOImpl(connection);
                questionDAO.insert(question);
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
    public void update(Question question) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                QuestionDAO questionDAO = new QuestionDAOImpl(connection);
                questionDAO.update(question);
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
    public void remove(Question question) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                QuestionDAO questionDAO = new QuestionDAOImpl(connection);
                questionDAO.remove(question);
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
            QuestionDAO questionDAO = new QuestionDAOImpl(connection);
            return questionDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
