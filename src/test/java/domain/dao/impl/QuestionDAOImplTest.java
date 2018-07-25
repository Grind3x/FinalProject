package domain.dao.impl;

import domain.dao.BaseTest;
import domain.dao.*;
import domain.model.InteractiveTest;
import domain.model.Question;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class QuestionDAOImplTest extends BaseTest {
    private Connection connection;
    private TestDAO testDAO;
    private QuestionDAO questionDAO;

    public QuestionDAOImplTest() throws SQLException {
        connection = getH2Connection();
        testDAO = new TestDAOImpl(connection);
        questionDAO = new QuestionDAOImpl(connection);
    }

    @Before
    public void setUp() throws Exception {
        RunScript.execute(connection, new FileReader("query.sql"));
    }

    @Test
    public void findByText() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test", test);
        questionDAO.insert(question);
        assertEquals(Optional.of(question), questionDAO.findByText(question.getText()));
    }

    @Test
    public void findByTest() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test", test);
        questionDAO.insert(question);

        assertTrue(questionDAO.findByTest(test).contains(question));
    }

    @Test
    public void findAll() throws SQLException {
        insertFiveQuestions();
        assertEquals(5, questionDAO.findAll().size());
    }

    @Test
    public void findAllLimit() throws SQLException {
        insertFiveQuestions();
        assertEquals(3, questionDAO.findAllLimit(1, 3).size());
    }

    @Test
    public void findById() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test", test);
        questionDAO.insert(question);

        assertEquals(Optional.of(question), questionDAO.findById(question.getId()));
    }

    @Test
    public void update() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test", test);
        questionDAO.insert(question);
        question.setText("New Test");
        questionDAO.update(question);
        assertFalse(questionDAO.findByText("Test").isPresent());
        assertEquals(Optional.of(question), questionDAO.findByText(question.getText()));
    }

    @Test
    public void remove() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test", test);
        questionDAO.insert(question);
        assertNotNull(questionDAO.findByText(question.getText()));
        questionDAO.remove(question);
        assertFalse(questionDAO.findByText(question.getText()).isPresent());
    }

    @Test
    public void count() throws SQLException {
        insertFiveQuestions();
        assertEquals(5, questionDAO.count());
    }

    private void insertFiveQuestions() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question1 = new Question("Question1", test);
        Question question2 = new Question("Question2", test);
        Question question3 = new Question("Question3", test);
        Question question4 = new Question("Question4", test);
        Question question5 = new Question("Question5", test);

        questionDAO.insert(question1);
        questionDAO.insert(question2);
        questionDAO.insert(question3);
        questionDAO.insert(question4);
        questionDAO.insert(question5);
    }
}