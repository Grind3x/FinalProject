package domain.service;

import domain.model.Role;

public interface RoleService extends GenericService<Role> {
    Role findByName(String name);
}
