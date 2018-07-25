package domain.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

public class User {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private Map<Test, Integer> assessments = new HashMap<>();
    private Role role;

    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = sha256Hex(password);
    }

    public User(String fullName, String email, String password, Role role) {
        this.fullName = fullName;
        this.email = email;
        this.password = sha256Hex(password);
        this.role = role;
        if (!role.getUsers().contains(this)) {
            role.add(this);
        }
    }

    public User(Long id, String fullName, String email, String password, Map<Test, Integer> assessments, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = sha256Hex(password);
        this.assessments = assessments;
        this.role = role;
    }

    public void addAssessment(Test test, Integer mark) {
        if (test == null || mark == null) {
            throw new IllegalArgumentException();
        }
        this.assessments.put(test, mark);
    }

    public Integer getMark(Test test) {
        if (test == null) {
            throw new IllegalArgumentException();
        }
        return assessments.get(test);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Test, Integer> getAssessments() {
        return assessments;
    }

    public void setAssessments(Map<Test, Integer> assessments) {
        this.assessments = assessments;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(fullName, user.fullName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(assessments, user.assessments) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fullName, email, password, assessments, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", assessments=" + assessments +
                ", role=" + role.getName() +
                '}';
    }
}
