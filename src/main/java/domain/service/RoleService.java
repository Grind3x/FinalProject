package domain.service;

import domain.model.Role;

import java.util.Optional;

public interface RoleService extends GenericService<Role> {
    Optional<Role> findByName(String name);
}
