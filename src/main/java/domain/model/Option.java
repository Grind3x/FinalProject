package domain.model;

import java.util.Objects;

public class Option {
    private Long id;
    private String optionText;
    private Question question;
    private boolean correct;

    public Option() {
    }

    public Option(String optionText) {
        this.optionText = optionText;
    }

    public Option(String optionText, Question question) {
        this.optionText = optionText;
        this.question = question;
        if (!question.getOptions().contains(this)) {
            question.addOption(this);
        }
    }

    public Option(String optionText, Question question, boolean correct) {
        this.optionText = optionText;
        this.question = question;
        this.correct = correct;
        if (!question.getOptions().contains(this)) {
            question.addOption(this);
        }
    }

    public Option(Long id, String optionText, Question question, boolean correct) {
        this.id = id;
        this.optionText = optionText;
        this.question = question;
        this.correct = correct;
        if (!question.getOptions().contains(this)) {
            question.addOption(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return correct == option.correct &&
                Objects.equals(optionText, option.optionText) &&
                Objects.equals(question, option.question);
    }

    @Override
    public int hashCode() {

        return Objects.hash(optionText, question, correct);
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionText='" + optionText + '\'' +
                ", question=" + question.getText() +
                ", correct=" + correct +
                '}';
    }
}
