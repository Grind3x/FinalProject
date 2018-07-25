package domain.dao;

import domain.model.Question;
import domain.model.Test;

import java.sql.SQLException;
import java.util.List;

public interface QuestionDAO extends GenericDAO<Question> {
    Question findByText(String text) throws SQLException;

    List<Question> findByTest(Test test) throws SQLException;
}
