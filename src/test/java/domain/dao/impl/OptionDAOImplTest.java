package domain.dao.impl;

import domain.dao.BaseTest;
import domain.dao.OptionDAO;
import domain.dao.QuestionDAO;
import domain.dao.TestDAO;
import domain.model.InteractiveTest;
import domain.model.Option;
import domain.model.Question;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class OptionDAOImplTest extends BaseTest {
    private Connection connection;
    private TestDAO testDAO;
    private QuestionDAO questionDAO;
    private OptionDAO optionDAO;

    public OptionDAOImplTest() throws SQLException {
        connection = getH2Connection();
        testDAO = new TestDAOImpl(connection);
        questionDAO = new QuestionDAOImpl(connection);
        optionDAO = new OptionDAOImpl(connection);
    }

    @Before
    public void setUp() throws Exception {
        RunScript.execute(connection, new FileReader("query.sql"));
    }

    @Test
    public void findByQuestion() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test question", test);
        questionDAO.insert(question);
        Option option = new Option("Test option", question);
        optionDAO.insert(option);

        assertTrue(optionDAO.findByQuestion(question).contains(option));
    }

    @Test
    public void findAll() throws SQLException {
        insertFiveOptions();
        assertEquals(5, optionDAO.findAll().size());
    }

    @Test
    public void findAllLimit() throws SQLException {
        insertFiveOptions();
        assertEquals(3, optionDAO.findAllLimit(1, 3).size());
    }

    @Test
    public void findById() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test question", test);
        questionDAO.insert(question);
        Option option = new Option("Test option", question);
        optionDAO.insert(option);

        assertEquals(option, optionDAO.findById(1L));
    }

    @Test
    public void update() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test question", test);
        questionDAO.insert(question);
        Option option = new Option("Test option", question);
        optionDAO.insert(option);
        option.setOptionText("New option text");
        optionDAO.update(option);
        assertEquals(option, optionDAO.findById(option.getId()));
    }

    @Test
    public void remove() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test question", test);
        questionDAO.insert(question);
        Option option = new Option("Test option", question);
        optionDAO.insert(option);
        assertNotNull(optionDAO.findById(option.getId()));
        optionDAO.remove(option);
        assertNull(optionDAO.findById(option.getId()));
    }

    @Test
    public void count() throws SQLException {
        insertFiveOptions();
        assertEquals(5, optionDAO.count());
    }

    private void insertFiveOptions() throws SQLException {
        domain.model.Test test = new InteractiveTest("Test test");
        testDAO.insert(test);
        Question question = new Question("Test question", test);
        questionDAO.insert(question);

        Option option1 = new Option("Test option1", question, true);
        Option option2 = new Option("Test option2", question);
        Option option3 = new Option("Test option3", question);
        Option option4 = new Option("Test option4", question);
        Option option5 = new Option("Test option5", question);

        optionDAO.insert(option1);
        optionDAO.insert(option2);
        optionDAO.insert(option3);
        optionDAO.insert(option4);
        optionDAO.insert(option5);
    }
}