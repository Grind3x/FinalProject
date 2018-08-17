package controller.servlets;

import domain.model.Role;
import domain.model.User;
import domain.service.RoleService;
import domain.service.UserService;
import domain.service.impl.RoleServiceImpl;
import domain.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl();

        String fullName = req.getParameter("full_name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm_password");

        if (!validate(email) || !password.equals(confirmPassword) || fullName.equals("") || password.equals("")) {
            req.setAttribute("error", "You entered incorrect data");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            Role role = roleService.findByName("student").orElse(new Role("student"));
            User user = new User(fullName, email, password, role);

            userService.insert(user);
            System.out.println(user);

            req.setAttribute("msg", "You have successfully registered");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
