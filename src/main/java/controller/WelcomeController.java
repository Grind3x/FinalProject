package controller;

import domain.model.Category;
import domain.service.CategoryService;
import domain.service.impl.CategoryServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/welcome")
public class WelcomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService service = new CategoryServiceImpl();
        List<Category> allCategories = service.findAll();
        req.setAttribute("categories", allCategories);

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/all_categories.jsp");
        requestDispatcher.forward(req, resp);
    }
}
