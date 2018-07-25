package domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Role {
    private Long id;
    private String name;
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public void add(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        this.users.add(user);

        if (user.getRole() == null) {
            user.setRole(this);
        }
    }

    public void remove(User user) {
        this.users.remove(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
