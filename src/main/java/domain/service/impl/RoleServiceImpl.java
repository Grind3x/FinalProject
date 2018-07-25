package domain.service.impl;

import domain.dao.RoleDAO;
import domain.dao.factory.ConnectionFactory;
import domain.dao.impl.RoleDAOImpl;
import domain.model.Role;
import domain.service.RoleService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.Collections.emptyList;

public class RoleServiceImpl implements RoleService {

    @Override
    public Role findByName(String name) {
        Role role = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(connection);
            role = roleDAO.findByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(connection);
            result = roleDAO.findAll();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public List<Role> findAllLimit(int first, int max) {
        List<Role> result;
        try (Connection connection = ConnectionFactory.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(connection);
            result = roleDAO.findAllLimit(first, max);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emptyList();
    }

    @Override
    public Role findById(Long id) {
        Role role = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            RoleDAO roleDAO = new RoleDAOImpl(connection);
            role = roleDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public void insert(Role role) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                RoleDAO roleDAO = new RoleDAOImpl(connection);
                roleDAO.insert(role);
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
    public void update(Role role) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                RoleDAO roleDAO = new RoleDAOImpl(connection);
                roleDAO.update(role);
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
    public void remove(Role role) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                RoleDAO roleDAO = new RoleDAOImpl(connection);
                roleDAO.remove(role);
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
            RoleDAO roleDAO = new RoleDAOImpl(connection);
            return roleDAO.count();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
