package domain.model;

import java.util.List;

public abstract class Test {
    public abstract void setId(Long id);

    public abstract Long getId();

    public abstract void setName(String name);

    public abstract String getName();

    public abstract List<Question> getQuestions();

    public abstract void setQuestions(List<Question> questions);

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public abstract void addQuestion(Question question);

    public abstract Category getCategory();

    public abstract void setCategory(Category category);

    public abstract List<Question> getRandomQuestions(int maxQuestions);

}
