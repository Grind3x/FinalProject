package domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {
    private Long id;
    private String text;
    private List<Option> options = new ArrayList<>();
    private Test test;

    public Question() {
    }

    public Question(String text) {
        this.text = text;
    }

    public Question(String text, Test test) {
        this.text = text;
        this.test = test;
        if (!test.getQuestions().contains(this)) {
            test.addQuestion(this);
        }
    }

    public Question(Long id, String text, List<Option> options, Test test) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.test = test;
        if (!test.getQuestions().contains(this)) {
            test.addQuestion(this);
        }
    }

    public void addOption(Option option) {
        if (option == null) {
            throw new IllegalArgumentException();
        }
        this.options.add(option);
        if (option.getQuestion() == null) {
            option.setQuestion(this);
        }
    }

    public boolean isMultivariate() {
        int count = 0;
        for (Option option : options) {
            if (option.isCorrect()) {
                count++;
            }
        }
        return count > 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(text, question.text);
    }

    @Override
    public int hashCode() {

        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", test=" + test.getName() +
                '}';
    }
}
