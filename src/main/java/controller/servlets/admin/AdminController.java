package controller.servlets.admin;

import domain.model.Category;
import domain.model.Test;
import domain.service.CategoryService;
import domain.service.TestService;
import domain.service.impl.CategoryServiceImpl;
import domain.service.impl.TestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        TestService testService = new TestServiceImpl();
        List<Category> categories = categoryService.findAll();
        List<Test> tests = testService.findAll();

        HttpSession session = req.getSession();
        session.setAttribute("all_categories", categories);
        session.setAttribute("all_tests", tests);

        resp.sendRedirect("/admin/index.jsp");
    }
}
