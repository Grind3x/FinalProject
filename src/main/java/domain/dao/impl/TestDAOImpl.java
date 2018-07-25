package domain.dao.impl;

import domain.dao.OptionDAO;
import domain.dao.QuestionDAO;
import domain.dao.TestDAO;
import domain.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import static java.util.Collections.emptyMap;

public class TestDAOImpl implements TestDAO {
    private Connection connection;
    private Properties prop;

    public TestDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Test findByName(String name) throws SQLException {
        Test test = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.find-by-name"))) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    test = extractTestFromResultSet(resultSet);
                }
            }
        }
        return test;
    }

    @Override
    public List<Test> findByUser(User user) throws SQLException {
        List<Test> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.find-by-user"))) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Test test = extractTestFromResultSet(resultSet);
                    result.add(test);
                }
            }
        }
        return result;
    }

    @Override
    public Map<Question, List<Option>> getAnswers(Test test, User user) throws SQLException {
        OptionDAO optionDAO = new OptionDAOImpl(connection);
        List<Option> allOptions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.getAnswers"))) {
            statement.setLong(1, user.getId());
            statement.setLong(2, test.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Option option = optionDAO.findById(resultSet.getLong("option_id"));
                    allOptions.add(option);
                }
                return getQuestionsWithOptions(allOptions);
            }
        }
    }

    @Override
    public void setAnswers(Map<Question, List<Option>> answers, User user) throws SQLException {
        for (Question question : answers.keySet()) {
            for (Option option : answers.get(question)) {
                try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.setAnswer"))) {
                    statement.setLong(1, question.getId());
                    statement.setLong(2, user.getId());
                    statement.setLong(3, option.getId());
                    statement.executeUpdate();
                }
            }
        }
    }

    private Map<Question, List<Option>> getQuestionsWithOptions(List<Option> allOptions) {
        Map<Question, List<Option>> result = new HashMap<>();
        Question question;
        if (allOptions.size() > 0) {
            question = allOptions.get(0).getQuestion();

            List<Option> tmp = new ArrayList<>();
            for (int i = 0; i < allOptions.size(); i++) {
                if (allOptions.get(i).getQuestion().equals(question)) {
                    tmp.add(allOptions.get(i));
                }
                if (i == allOptions.size() - 1 || !allOptions.get(i + 1).getQuestion().equals(question)) {
                    result.put(question, tmp);
                    tmp = new ArrayList<>();
                    if (i != allOptions.size() - 1) {
                        question = allOptions.get(i + 1).getQuestion();
                    }
                }
            }
            return result;
        }
        return emptyMap();
    }

    @Override
    public List<Test> findAll() throws SQLException {
        List<Test> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Test test = extractTestFromResultSet(resultSet);
                    result.add(test);
                }
            }
        }
        return result;
    }

    @Override
    public List<Test> findAllLimit(int first, int max) throws SQLException {
        List<Test> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Test test = extractTestFromResultSet(resultSet);
                    result.add(test);
                }
            }
        }
        return result;
    }

    @Override
    public Test findById(Long id) throws SQLException {
        Test test = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    test = extractTestFromResultSet(resultSet);
                }
            }
        }
        return test;
    }

    @Override
    public void insert(Test test) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, test.getName());
            statement.executeUpdate();
            if (test.getQuestions().size() > 0) {
                updateQuestions(test.getQuestions());
            }

            test.setId(getGeneratedId(statement));
        }
    }

    @Override
    public void update(Test test) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.update"))) {
            statement.setString(1, test.getName());
            statement.setLong(2, test.getId());
            if (test.getQuestions().size() > 0) {
                updateQuestions(test.getQuestions());
            }
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Test test) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.remove"))) {
            statement.setLong(1, test.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("test.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private Test extractTestFromResultSet(ResultSet resultSet) throws SQLException {
        Test test = null;
        QuestionDAO questionDAO = new QuestionDAOImpl(connection);
        Long id = (resultSet.getLong("id"));
        String name = resultSet.getString("name");
        Test tmp = new InteractiveTest(id, name);
        List<Question> questions = questionDAO.findByTest(tmp);
        test = new InteractiveTest(id, name, questions);
        return test;
    }

    private void updateQuestions(List<Question> questions) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAOImpl(connection);
        for (Question question : questions) {
            questionDAO.update(question);
        }
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