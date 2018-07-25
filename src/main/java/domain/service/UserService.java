package domain.service;

import domain.model.Role;
import domain.model.Test;
import domain.model.User;

import java.util.List;

public interface UserService extends GenericService<User> {
    User findByEmail(String email);

    User findByFullName(String fullName);

    List<User> findByTest(Test test);

    List<User> findByRole(Role role);

    boolean isValid(String email, String password);
}
