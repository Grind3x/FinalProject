package domain.service;

import domain.model.Question;
import domain.model.Test;

import java.util.List;

public interface QuestionService extends GenericService<Question> {
    Question findByText(String text);

    List<Question> findByTest(Test test);
}
