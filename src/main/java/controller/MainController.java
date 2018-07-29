package controller;

import domain.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/")
public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
        } else if (user.getRole().getName().equals("admin")) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin.jsp");
            requestDispatcher.forward(req, resp);
        } else if (user.getRole().getName().equals("student")) {
            resp.sendRedirect("/welcome");
        }
    }
}
