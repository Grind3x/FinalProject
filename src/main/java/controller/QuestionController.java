package controller;

import domain.model.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/question")
public class QuestionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String questionId = req.getParameter("q");
        int id = Integer.valueOf(questionId);

        HttpSession session = req.getSession();
        Test test = (Test) session.getAttribute("test");

        req.setAttribute("question", test.getQuestions().get(id - 1));
        req.setAttribute("question_number", questionId);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/test_page.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
