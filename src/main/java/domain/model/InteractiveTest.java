package domain.model;

import java.util.*;

public class InteractiveTest extends Test {
    private Long id;
    private String name;
    private String description;
    private List<Question> questions = new ArrayList<>();

    public InteractiveTest() {
    }

    public InteractiveTest(String name) {
        this.name = name;
    }

    public InteractiveTest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public InteractiveTest(Long id, String name, String description, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public InteractiveTest(Long id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException();
        }
        questions.add(question);
        if (question.getTest() == null) {
            question.setTest(this);
        }
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractiveTest that = (InteractiveTest) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(questions, that.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, questions);
    }

    @Override
    public String toString() {
        return "InteractiveTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", questions=" + questions +
                '}';
    }
}
