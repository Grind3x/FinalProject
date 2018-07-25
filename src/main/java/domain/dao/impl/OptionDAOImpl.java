package domain.dao.impl;

import domain.model.Option;
import domain.model.Question;
import domain.dao.OptionDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OptionDAOImpl implements OptionDAO {
    private Connection connection;
    private Properties prop;

    public OptionDAOImpl(Connection connection) {
        this.connection = connection;
        this.prop = new Properties();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Option> findByQuestion(Question question) throws SQLException {
        List<Option> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.find-by-question"))) {
            statement.setLong(1, question.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Option option = extractOptionFromResultSet(resultSet, question);
                    result.add(option);
                }
            }
        }
        return result;
    }

    @Override
    public List<Option> findAll() throws SQLException {
        List<Option> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.find-all"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Option option = extractOptionFromResultSet(resultSet);
                    result.add(option);
                }
            }
        }
        return result;
    }

    @Override
    public List<Option> findAllLimit(int first, int max) throws SQLException {
        List<Option> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.find-all-limit"))) {
            statement.setInt(1, first);
            statement.setInt(2, max);
            try (ResultSet resultSet = statement.executeQuery()) {
                for (; resultSet.next(); ) {
                    Option option = extractOptionFromResultSet(resultSet);
                    result.add(option);
                }
            }
        }
        return result;
    }

    @Override
    public Option findById(Long id) throws SQLException {
        Option option = null;
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.find-by-id"))) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    option = extractOptionFromResultSet(resultSet);
                }
            }
        }
        return option;
    }

    @Override
    public void insert(Option option) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.insert"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, option.getOptionText());
            statement.setLong(2, option.getQuestion().getId());
            statement.setBoolean(3, option.isCorrect());
            statement.executeUpdate();

            option.setId(getGeneratedId(statement));
        }
    }

    @Override
    public void update(Option option) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.update"))) {
            statement.setString(1, option.getOptionText());
            statement.setLong(2, option.getQuestion().getId());
            statement.setBoolean(3, option.isCorrect());
            statement.setLong(4, option.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Option option) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.remove"))) {
            statement.setLong(1, option.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(prop.getProperty("option.count"))) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }

    private Option extractOptionFromResultSet(ResultSet resultSet, Question... questions) throws SQLException {
        Option option = new Option();
            option.setId(resultSet.getLong("id"));
            option.setOptionText(resultSet.getString("option_text"));
            if (questions.length > 0) {
                option.setQuestion(questions[0]);
            } else {
                option.setQuestion(new QuestionDAOImpl(connection).findById(resultSet.getLong("question_id")));
            }
            option.setCorrect(resultSet.getBoolean("correct"));

        return option;
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
