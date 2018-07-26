package controller.security.utils;

import domain.model.Role;
import controller.security.config.SecurityConfig;
import domain.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public class SecurityUtils {

    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = getUrlPattern(request);
        System.out.println(urlPattern);
        Set<Role> roles = SecurityConfig.getAllAppRoles();
        System.out.println("all roles: " + roles);
        for (Role role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);

            System.out.println("url patterns for role: " + urlPatterns);
            if (urlPattern != null && urlPatterns.contains(urlPattern)) {
                System.out.println("contains. Page is security");
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = getUrlPattern(request);

        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Role userRole = user.getRole();
            System.out.println("role: " + userRole);

            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(userRole);
            return urlPattern != null && urlPatterns.contains(urlPattern);
        }
        return false;
    }

    private static String getUrlPattern(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String urlPattern;

        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
            return urlPattern;
        }

        urlPattern = servletPath;
        return urlPattern;
    }
}