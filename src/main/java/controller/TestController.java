package controller;

import domain.dao.EmptyResultException;
import domain.model.Test;
import domain.service.TestService;
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

@WebServlet("/test")
public class TestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TestService service = new TestServiceImpl();
        String testId = req.getParameter("t");
        String questionId = req.getParameter("q");

        if (testId == null && questionId == null) {
            List<Test> allTests = service.findAll();
            req.setAttribute("tests", allTests);

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/welcome.jsp");
            requestDispatcher.forward(req, resp);
        } else if (testId != null && questionId == null) {
            long tId = Long.parseLong(testId);
            HttpSession session = req.getSession();
            Test test;
            if (tId > 0) {
                try {
                    if (session.getAttribute("test") == null) {
                        test = service.findById(tId).orElseThrow(EmptyResultException::new);
                        session.setAttribute("test", test);
                    } else {
                        test = (Test) session.getAttribute("test");
                        if (!(test.getId() == tId)) {
                            test = service.findById(tId).orElseThrow(EmptyResultException::new);
                            session.setAttribute("test", test);
                        }
                    }
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/test.jsp");
                    requestDispatcher.forward(req, resp);

                } catch (EmptyResultException e) {
                    e.printStackTrace();
                    req.setAttribute("error", "Something is wrong. There is no such test or question!");

                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/welcome.jsp");
                    requestDispatcher.forward(req, resp);
                }
            }
        } else if (testId != null && questionId != null) {
            System.out.println("q= " +questionId);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/question");
            requestDispatcher.forward(req ,resp);
        }
    }
}
