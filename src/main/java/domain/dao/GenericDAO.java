package domain.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {

    List<T> findAll() throws SQLException;

    List<T> findAllLimit(int first, int max) throws SQLException;

    T findById(Long id) throws SQLException;

    void insert(T t) throws SQLException;

    void update(T t) throws SQLException;

    void remove(T t) throws SQLException;

    int count() throws SQLException;
}
