package controller.servlets;

import domain.dao.EmptyResultException;
import domain.model.User;
import domain.service.UserService;
import domain.service.impl.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService service = new UserServiceImpl();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (service.isValid(email, password)) {
            User user = service.findByEmail(email).orElse(null);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            System.out.println(user);

            if (user != null && user.getRole().getName().equals("admin")) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin");
                dispatcher.forward(req, resp);
            } else if (user != null && user.getRole().getName().equals("student")) {
                resp.sendRedirect("/tests");
            }
        } else {
            req.setAttribute("error", true);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
