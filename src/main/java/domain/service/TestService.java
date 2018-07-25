package domain.service;

import domain.model.Option;
import domain.model.Question;
import domain.model.Test;
import domain.model.User;

import java.util.List;
import java.util.Map;

public interface TestService extends GenericService<Test> {
    Test findByName(String name);

    List<Test> findByUser(User user);

    Map<Question, List<Option>> getAnswers(Test test, User user);

    void setAnswers(Map<Question, List<Option>> answers, User user);

}
