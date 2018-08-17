package controller.servlets.admin;

import domain.dao.EmptyResultException;
import domain.model.Category;
import domain.model.InteractiveTest;
import domain.model.Test;
import domain.service.CategoryService;
import domain.service.TestService;
import domain.service.impl.CategoryServiceImpl;
import domain.service.impl.TestServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/add_test")
public class AddTestController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TestService testService = new TestServiceImpl();
        CategoryService categoryService = new CategoryServiceImpl();
        HttpSession session = req.getSession();

        String testName = req.getParameter("test_name");
        String description = req.getParameter("description");
        Long categoryId = Long.valueOf(req.getParameter("category"));

        if (testService.findByName(testName).isPresent()) {
            req.setAttribute("test_exist", true);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin/add_test.jsp");
            requestDispatcher.forward(req, resp);
            return;
        }

        try {
            Category category = categoryService.findById(categoryId).orElseThrow(EmptyResultException::new);
            Test test = new InteractiveTest(testName, replaceLineBreaks(description), category);
            testService.insert(test);
        } catch (EmptyResultException e) {
            e.printStackTrace();
        }

        List<Test> tests = testService.findAll();
        session.setAttribute("all_tests", tests);
        req.setAttribute("succsess", true);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin/add_test.jsp");
        requestDispatcher.forward(req, resp);
    }

    private String replaceLineBreaks(String description) {
        return description.replaceAll("\n", "<br>");
    }
}
