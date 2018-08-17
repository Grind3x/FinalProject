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
        Set<Role> roles = SecurityConfig.getAllAppRoles();
        for (Role role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);

            if (urlPattern != null && urlPatterns.contains(urlPattern)) {
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
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(userRole);
            return urlPattern != null && urlPatterns.contains(urlPattern);
        }
        return false;
    }

    private static String getUrlPattern(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String urlPattern;

        urlPattern = request.getServletPath();

        if (pathInfo != null) {
            urlPattern += "/*";
            return urlPattern;
        }

        return urlPattern;
    }
}
