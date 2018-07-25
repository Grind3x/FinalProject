package domain.dao.impl;

import domain.model.Question;
import domain.model.Test;
import domain.dao.OptionDAO;
import domain.dao.QuestionDAO;
import domain.dao.TestDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuestionDAOImpl implements QuestionDAO {
    private Connection connection;
    private Properties prop;

    public QuestionDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Question findByText(String text) throws SQLException {
        Question question = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.find-by-text"))) {
            statement.setString(1, text);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    question = extractQuestionFromResultSet(resultSet);
                }
            }
        }
        return question;
    }

    @Override
    public List<Question> findByTest(Test test) throws SQLException {
        List<Question> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.find-by-test"))) {
            statement.setLong(1, test.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Question question = extractQuestionFromResultSet(resultSet, test);
                    result.add(question);
                }
            }
        }
        return result;
    }

    @Override
    public List<Question> findAll() throws SQLException {
        List<Question> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Question question = extractQuestionFromResultSet(resultSet);
                    result.add(question);
                }
            }
        }
        return result;
    }

    @Override
    public List<Question> findAllLimit(int first, int max) throws SQLException {
        List<Question> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Question question = extractQuestionFromResultSet(resultSet);
                    result.add(question);
                }
            }
        }
        return result;
    }

    @Override
    public Question findById(Long id) throws SQLException {
        Question question = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    question = extractQuestionFromResultSet(resultSet);
                }
            }
        }
        return question;
    }

    @Override
    public void insert(Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, question.getText());
            statement.setLong(2, question.getTest().getId());
            statement.setString(3, question.getText());
            statement.setLong(4, question.getTest().getId());
            statement.executeUpdate();

            question.setId(getGeneratedId(statement));
        }
    }

    @Override
    public void update(Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.update"))) {
            statement.setString(1, question.getText());
            statement.setLong(2, question.getTest().getId());
            statement.setLong(3, question.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Question question) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.remove"))) {
            statement.setLong(1, question.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("question.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private Question extractQuestionFromResultSet(ResultSet resultSet, Test... tests) throws SQLException {
        OptionDAO optionDAO = new OptionDAOImpl(connection);
        TestDAO testDAO = new TestDAOImpl(connection);
        Question question = new Question();
        question.setId(resultSet.getLong("id"));
        question.setText(resultSet.getString("text"));
        question.setOptions(optionDAO.findByQuestion(question));
        if (tests.length > 0) {
            question.setTest(tests[0]);
        } else {
            question.setTest(testDAO.findById(resultSet.getLong("test_id")));
        }
        return question;
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
