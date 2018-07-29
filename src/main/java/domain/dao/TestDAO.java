package domain.dao;

import domain.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TestDAO extends GenericDAO<Test> {
    Optional<Test> findByName(String name) throws SQLException;

    List<Test> findByUser(User user) throws SQLException;

    Map<Question, List<Option>> getAnswers(Test test, User user) throws SQLException;

    void setAnswers(Map<Question, List<Option>> answers, User user) throws SQLException;

    List<Test> findByCategory(Category category) throws SQLException;
}
