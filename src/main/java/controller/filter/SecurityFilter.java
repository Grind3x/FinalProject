package controller.filter;

import controller.security.utils.SecurityUtils;
import domain.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        User user = (User) request.getSession().getAttribute("user");

        if (servletPath.equals("/login.jsp") || servletPath.equals("/login") || servletPath.equals("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityUtils.isSecurityPage(request)) {
            if (user == null) {
                response.sendRedirect("/login.jsp");
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(request);
            if (!hasPermission) {
                RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/accessDenied.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
