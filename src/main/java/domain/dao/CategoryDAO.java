package domain.dao;

import domain.model.Category;

import java.sql.SQLException;
import java.util.Optional;

public interface CategoryDAO extends GenericDAO<Category> {

    Optional<Category> findByName(String name) throws SQLException;

}
