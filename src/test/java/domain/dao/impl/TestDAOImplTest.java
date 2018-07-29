package domain.dao.impl;

import domain.dao.BaseTest;
import domain.dao.*;
import domain.model.*;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.*;

public class TestDAOImplTest extends BaseTest {
    private Connection connection;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private TestDAO testDAO;
    private QuestionDAO questionDAO;
    private OptionDAO optionDAO;
    private CategoryDAO categoryDAO;

    public TestDAOImplTest() throws SQLException {
        connection = getH2Connection();
        userDAO = new UserDAOImpl(connection);
        roleDAO = new RoleDAOImpl(connection);
        testDAO = new TestDAOImpl(connection);
        questionDAO = new QuestionDAOImpl(connection);
        optionDAO = new OptionDAOImpl(connection);
        categoryDAO = new CategoryDAOImpl(connection);
    }

    @Before
    public void setUp() throws Exception {
        RunScript.execute(connection, new FileReader("query.sql"));
    }

    @Test
    public void findByName() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        assertEquals(Optional.of(test), testDAO.findByName(test.getName()));
    }

    @Test
    public void findByUser() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        User user = new User("Test name", "test@test.ua", "testpassword", role);
        userDAO.insert(user);
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        user.addAssessment(test, 95);
        userDAO.update(user);

        assertTrue(testDAO.findByUser(user).contains(test));
    }

    @Test
    public void getAnswers() throws SQLException {
        domain.model.Test test = insertTestWithTwoQuestions();
        User user = insertOneUser();

        Map<Question, List<Option>> map = new HashMap<>();
        List<Option> options = new ArrayList<>();
        options.add(test.getQuestions().get(0).getOptions().get(1));
        options.add(test.getQuestions().get(0).getOptions().get(2));

        map.put(test.getQuestions().get(0), options);
        options = new ArrayList<>();

        options.add(test.getQuestions().get(1).getOptions().get(2));
        map.put(test.getQuestions().get(1), options);

        testDAO.setAnswers(map, user);

        assertEquals(map.toString().trim(), testDAO.getAnswers(test, user).toString().trim());
    }

    private User insertOneUser() throws SQLException {
        Role role = new Role("admin");
        roleDAO.insert(role);
        User user = new User("Test user", "test@gmail.com", "qwerty", role);
        userDAO.insert(user);
        roleDAO.update(role);

        return user;
    }

    private domain.model.Test insertTestWithTwoQuestions() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        Question question1 = new Question("Test question1", test);
        Question question2 = new Question("Test question2", test);
        questionDAO.insert(question1);
        questionDAO.insert(question2);

        Option option1 = new Option("Test option1", question1, false);
        Option option2 = new Option("Test option2", question1, true);
        Option option3 = new Option("Test option3", question1, true);
        Option option4 = new Option("Test option4", question1, true);

        optionDAO.insert(option1);
        optionDAO.insert(option2);
        optionDAO.insert(option3);
        optionDAO.insert(option4);

        questionDAO.update(question1);

        Option option5 = new Option("Test option5", question2, true);
        Option option6 = new Option("Test option6", question2, false);
        Option option7 = new Option("Test option7", question2, false);
        Option option8 = new Option("Test option8", question2, false);

        optionDAO.insert(option5);
        optionDAO.insert(option6);
        optionDAO.insert(option7);
        optionDAO.insert(option8);

        questionDAO.update(question2);
        testDAO.update(test);

        return test;
    }

    @Test
    public void findAll() throws SQLException {
        insertFiveTests();
        assertEquals(5, testDAO.findAll().size());
    }

    private void insertFiveTests() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);

        domain.model.Test test1 = new InteractiveTest("Test1", "test descr", category);
        domain.model.Test test2 = new InteractiveTest("Test2", "test descr", category);
        domain.model.Test test3 = new InteractiveTest("Test3", "test descr", category);
        domain.model.Test test4 = new InteractiveTest("Test4", "test descr", category);
        domain.model.Test test5 = new InteractiveTest("Test5", "test descr", category);

        testDAO.insert(test1);
        testDAO.insert(test2);
        testDAO.insert(test3);
        testDAO.insert(test4);
        testDAO.insert(test5);
    }

    @Test
    public void findAllLimit() throws SQLException {
        insertFiveTests();
        assertEquals(3, testDAO.findAllLimit(1, 3).size());
    }

    @Test
    public void findById() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        assertEquals(Optional.of(test), testDAO.findById(test.getId()));
    }

    @Test
    public void update() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        test.setName("Test2");
        test.setDescription("Descr2");
        testDAO.update(test);
        assertFalse(testDAO.findByName("Test").isPresent());
        assertEquals(Optional.of(test), testDAO.findByName("Test2"));
    }

    @Test
    public void remove() throws SQLException {
        Category category = new Category("test category");
        categoryDAO.insert(category);
        domain.model.Test test = new InteractiveTest("Test test", "test descr", category);
        testDAO.insert(test);
        assertNotNull(testDAO.findByName("Test"));
        testDAO.remove(test);
        assertFalse(testDAO.findByName("Test").isPresent());
    }

    @Test
    public void count() throws SQLException {
        insertFiveTests();
        assertEquals(5, testDAO.count());
    }

    @Test
    public void setAnswers() {
    }
}