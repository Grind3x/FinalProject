package domain.dao;

import domain.model.Role;

import java.sql.SQLException;
import java.util.Optional;

public interface RoleDAO extends GenericDAO<Role> {
    Optional<Role> findByName(String name) throws SQLException;
}
