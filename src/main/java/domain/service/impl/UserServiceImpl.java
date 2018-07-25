package domain.service.impl;

import domain.dao.UserDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.UserDAOImpl;
import domain.model.Role;
import domain.model.Test;
import domain.model.User;
import domain.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            user = userDAO.findByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Optional<User> findByFullName(String fullName) {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            user = userDAO.findByFullName(fullName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findByTest(Test test) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            return userDAO.findByTest(test);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<User> findByRole(Role role) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            return userDAO.findByRole(role);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public boolean isValid(String email, String password) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            User user = userDAO.findByEmail(email).orElse(null);
            if (user == null) {
                return false;
            } else {
                if (user.getPassword().equals(sha256Hex(password))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            return userDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<User> findAllLimit(int first, int max) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            return userDAO.findAllLimit(first, max);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = ConnectionFactory.getConnection()) {
            UserDAO userDAO = new UserDAOImpl(connection);
            user = userDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insert(User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                UserDAO userDAO = new UserDAOImpl(connection);
                userDAO.insert(user);
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
    public void update(User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                UserDAO userDAO = new UserDAOImpl(connection);
                userDAO.update(user);
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
    public void remove(User user) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                UserDAO userDAO = new UserDAOImpl(connection);
                userDAO.remove(user);
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
            UserDAO userDAO = new UserDAOImpl(connection);
            return userDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
