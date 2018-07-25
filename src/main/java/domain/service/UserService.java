package domain.service;

import domain.model.Role;
import domain.model.Test;
import domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericService<User> {
    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String fullName);

    List<User> findByTest(Test test);

    List<User> findByRole(Role role);

    boolean isValid(String email, String password);
}
