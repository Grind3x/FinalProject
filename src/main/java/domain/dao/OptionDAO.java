package domain.dao;

import domain.model.Option;
import domain.model.Question;

import java.sql.SQLException;
import java.util.List;

public interface OptionDAO extends GenericDAO<Option> {
    List<Option> findByQuestion(Question question) throws SQLException;
}
