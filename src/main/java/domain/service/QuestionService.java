package domain.service;

import domain.model.Question;
import domain.model.Test;

import java.util.List;
import java.util.Optional;

public interface QuestionService extends GenericService<Question> {
    Optional<Question> findByText(String text);

    List<Question> findByTest(Test test);
}
