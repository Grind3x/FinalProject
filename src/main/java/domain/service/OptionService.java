package domain.service;

import domain.model.Option;
import domain.model.Question;

import java.util.List;

public interface OptionService extends GenericService<Option> {
    List<Option> findByQuestion(Question question);
}
