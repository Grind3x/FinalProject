package domain.dao.impl;

import domain.dao.*;
import domain.model.Category;
import domain.model.InteractiveTest;
import domain.model.Role;
import domain.model.User;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserDAOImplTest extends BaseTest {
    private Connection connection;
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private TestDAO testDAO;
    private CategoryDAO categoryDAO;


    public UserDAOImplTest() throws SQLException {
        connection = getH2Connection();
        userDAO = new UserDAOImpl(connection);
        roleDAO = new RoleDAOImpl(connection);
        testDAO = new TestDAOImpl(connection);
        categoryDAO = new CategoryDAOImpl(connection);
    }

    @Before
    public void setUp() throws Exception {
        RunScript.execute(connection, new FileReader("query.sql"));
    }

    @Test
    public void findByEmail() throws SQLException {
        Role role = new Role("admin");
        roleDAO.insert(role);
        User user = new User("Test User", "test@test.ua", "testpass", role);
        userDAO.insert(user);
        assertEquals(Optional.of(user), userDAO.findByEmail(user.getEmail()));
    }

    @Test
    public void findByFullName() throws SQLException {
        Role role = new Role("test");
        roleDAO.insert(role);
        User user = new User("Test User", "test@test.ua", "testpass", role);
        userDAO.insert(user);
        assertEquals(Optional.of(user), userDAO.findByFullName(user.getFullName()));
    }

    @Test
    public void findByTest() throws SQLException {
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

        assertEquals(1, userDAO.findByTest(test).size());
        assertTrue(userDAO.findByTest(test).contains(user));
    }

    @Test
    public void findByRole() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        User user = new User("Test name", "test@test.ua", "testpassword", role);
        userDAO.insert(user);

        assertTrue(userDAO.findByRole(role).contains(user));
    }

    @Test
    public void findAll() throws SQLException {
        insertFiveUsers();
        assertEquals(5, userDAO.findAll().size());
    }

    @Test
    public void findAllLimit() throws SQLException {
        insertFiveUsers();
        assertEquals(3, userDAO.findAllLimit(1, 3).size());
    }

    @Test
    public void findById() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        User user = new User("Test name", "test@test.ua", "testpassword", role);
        userDAO.insert(user);

        assertEquals(Optional.of(user), userDAO.findById(user.getId()));
    }

    @Test
    public void remove() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        User user = new User("Test name", "test@test.ua", "testpassword", role);
        userDAO.insert(user);
        assertEquals(Optional.of(user), userDAO.findById(user.getId()));
        userDAO.remove(user);
        assertFalse(userDAO.findById(user.getId()).isPresent());
    }

    @Test
    public void count() throws SQLException {
        insertFiveUsers();
        assertEquals(5, userDAO.count());
    }

    private void insertFiveUsers() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);

        User user1 = new User("Test name1", "test1@test.ua", "testpassword", role);
        User user2 = new User("Test name2", "test2@test.ua", "testpassword", role);
        User user3 = new User("Test name3", "test3@test.ua", "testpassword", role);
        User user4 = new User("Test name4", "test4@test.ua", "testpassword", role);
        User user5 = new User("Test name5", "test5@test.ua", "testpassword", role);

        userDAO.insert(user1);
        userDAO.insert(user2);
        userDAO.insert(user3);
        userDAO.insert(user4);
        userDAO.insert(user5);
    }
}