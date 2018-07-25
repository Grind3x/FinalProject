package domain.dao;

import domain.model.Role;
import domain.model.Test;
import domain.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends GenericDAO<User> {
    Optional<User> findByEmail(String email) throws SQLException;

    Optional<User> findByFullName(String fullName) throws SQLException;

    List<User> findByTest(Test test) throws SQLException;

    List<User> findByRole(Role role) throws SQLException;
}
