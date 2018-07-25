package domain.dao;

import domain.model.Role;

import java.sql.SQLException;

public interface RoleDAO extends GenericDAO<Role> {
    Role findByName(String name) throws SQLException;
}
