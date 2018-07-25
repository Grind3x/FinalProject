package domain.dao.impl;

import domain.dao.BaseTest;
import domain.dao.RoleDAO;
import domain.model.Role;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RoleDAOImplTest extends BaseTest {
    private Connection connection;
    private RoleDAO roleDAO;

    public RoleDAOImplTest() throws SQLException {
        connection = getH2Connection();
        roleDAO = new RoleDAOImpl(connection);
    }

    @Before
    public void setUp() throws Exception {
        RunScript.execute(connection, new FileReader("query.sql"));
    }



    @Test
    public void findByName() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        assertEquals(role, roleDAO.findByName(role.getName()));
    }

    @Test
    public void findAll() throws SQLException {
        insertFiveRoles();
        assertEquals(5, roleDAO.findAll().size());
    }

    @Test
    public void findAllLimit() throws SQLException {
        insertFiveRoles();
        assertEquals(3, roleDAO.findAllLimit(1, 3).size());
    }

    @Test
    public void findById() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        assertEquals(role, roleDAO.findById(role.getId()));
    }

    @Test
    public void update() throws SQLException {
        Role role = new Role("Test");
        connection.setAutoCommit(false);
        roleDAO.insert(role);
        role.setName("Test2");
        roleDAO.update(role);
        connection.commit();
        assertEquals(role, roleDAO.findById(role.getId()));
    }

    @Test
    public void remove() throws SQLException {
        Role role = new Role("Test");
        roleDAO.insert(role);
        assertNotNull(roleDAO.findById(role.getId()));
        roleDAO.remove(role);
        assertNull(roleDAO.findById(role.getId()));
    }

    @Test
    public void count() throws SQLException {
        insertFiveRoles();
        assertEquals(5, roleDAO.count());
    }

    @Test
    public void emptyCount() throws SQLException {
        assertEquals(0, roleDAO.count());
    }

    private void insertFiveRoles() throws SQLException {
        Role role1 = new Role("1");
        Role role2 = new Role("2");
        Role role3 = new Role("3");
        Role role4 = new Role("4");
        Role role5 = new Role("5");

        roleDAO.insert(role1);
        roleDAO.insert(role2);
        roleDAO.insert(role3);
        roleDAO.insert(role4);
        roleDAO.insert(role5);
    }
}