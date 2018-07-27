package controller.security.config;

import domain.dao.EmptyResultException;
import domain.model.Role;
import domain.service.RoleService;
import domain.service.impl.RoleServiceImpl;

import java.util.*;

import static java.util.Collections.emptyList;

public class SecurityConfig {

    private static final Map<Role, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        Role role = null;
        RoleService service = new RoleServiceImpl();
        List<String> urlPatterns = new ArrayList<>();

        urlPatterns.add("/test.jsp");
        urlPatterns.add("/test");
        urlPatterns.add("/all_test");

        try {
            role = service.findByName("student").orElseThrow(EmptyResultException::new);
        } catch (EmptyResultException e) {
            e.printStackTrace();
        }
        mapConfig.put(role, urlPatterns);

        urlPatterns = new ArrayList<>();
        urlPatterns.add("/test.jsp");
        urlPatterns.add("/admin.jsp");

        try {
            role = service.findByName("admin").orElseThrow(EmptyResultException::new);
        } catch (EmptyResultException e) {
            e.printStackTrace();
        }
        mapConfig.put(role, urlPatterns);
    }

    public static Set<Role> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(Role role) {
        List<String> result;
        if (role == null) {
            throw new IllegalArgumentException();
        }
        if ((result = mapConfig.get(role)) != null) {
            return result;
        }

        return emptyList();
    }
}
