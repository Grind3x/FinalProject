package domain.service.impl;

import domain.dao.OptionDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.OptionDAOImpl;
import domain.model.Option;
import domain.model.Question;
import domain.service.OptionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class OptionServiceImpl implements OptionService {
    @Override
    public List<Option> findByQuestion(Question question) {
        List<Option> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            OptionDAO optionDAO = new OptionDAOImpl(connection);
            result = optionDAO.findByQuestion(question);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Option> findAll() {
        List<Option> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            OptionDAO optionDAO = new OptionDAOImpl(connection);
            result = optionDAO.findAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Option> findAllLimit(int first, int max) {
        List<Option> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            OptionDAO optionDAO = new OptionDAOImpl(connection);
            result = optionDAO.findAllLimit(first, max);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Optional<Option> findById(Long id) {
        Optional<Option> option = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            OptionDAO optionDAO = new OptionDAOImpl(connection);
            option = optionDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return option;
    }

    @Override
    public void insert(Option option) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                OptionDAO optionDAO = new OptionDAOImpl(connection);
                optionDAO.insert(option);
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
    public void update(Option option) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                OptionDAO optionDAO = new OptionDAOImpl(connection);
                optionDAO.update(option);
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
    public void remove(Option option) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                OptionDAO optionDAO = new OptionDAOImpl(connection);
                optionDAO.remove(option);
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
            OptionDAO optionDAO = new OptionDAOImpl(connection);
            return optionDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
