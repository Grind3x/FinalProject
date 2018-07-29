package controller;

import domain.dao.EmptyResultException;
import domain.model.Question;
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
    private static final int MAX_QUESTIONS = 12;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("redirect", "/test?t=" + req.getParameter("t") + "&q=" + req.getParameter("q"));
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/answer");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String testId = req.getParameter("t");
        String questionNumber = req.getParameter("q");
        if (testId != null && questionNumber == null) {
            showTestInfo(testId, req, resp);
        } else if (testId != null && questionNumber != null) {
            showQuestion(req, resp);
        }
    }

    private void showTestInfo(String testId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TestService service = new TestServiceImpl();
        long tId = Long.parseLong(testId);
        HttpSession session = req.getSession();
        Test test;
        if (tId > 0) {
            try {
                if (session.getAttribute("test") == null) {
                    test = service.findById(tId).orElseThrow(EmptyResultException::new);
                } else {
                    test = (Test) session.getAttribute("test");
                    if ((test.getId() != tId)) {
                        test = service.findById(tId).orElseThrow(EmptyResultException::new);
                    }
                }
                session.setAttribute("test", test);
                session.setAttribute("questions", test.getRandomQuestions(MAX_QUESTIONS));
                session.setAttribute("max_questions", MAX_QUESTIONS);

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/test_info.jsp");
                requestDispatcher.forward(req, resp);

            } catch (EmptyResultException e) {
                e.printStackTrace();
                req.setAttribute("error", "Something is wrong. There is no such test or question!");

                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/all_categories.jsp");
                requestDispatcher.forward(req, resp);
            }
        }
    }

    private void showQuestion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("q");
        int id = Integer.valueOf(questionId);

        HttpSession session = req.getSession();
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        req.setAttribute("question", questions.get(id - 1));
        req.setAttribute("question_number", questionId);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/test_page.jsp");
        requestDispatcher.forward(req, resp);
    }

}
