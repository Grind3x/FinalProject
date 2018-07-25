package domain.service.impl;

import domain.model.Role;
import domain.model.User;
import domain.service.RoleService;
import domain.service.UserService;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    private UserService userService = new UserServiceImpl();
    private RoleService roleService = new RoleServiceImpl();

    @Test
    public void isValid() {
        Role role = new Role("test");
        roleService.insert(role);
        User user = new User("Test User", "test_user@gmail.com", "password", role);
        userService.insert(user);
        assertTrue(userService.isValid("test_user@gmail.com", "password"));
        userService.remove(user);
        roleService.remove(role);
    }
}