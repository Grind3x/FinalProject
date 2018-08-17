package controller.servlets;

import domain.model.Option;
import domain.model.Question;
import domain.model.User;
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
import java.util.Map;

@SuppressWarnings("unchecked")
@WebServlet("/result")
public class ResultController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Map<Question, List<Option>> answers = (Map<Question, List<Option>>) session.getAttribute("answers");
        User user = (User) session.getAttribute("user");
        TestService testService = new TestServiceImpl();
        testService.setAnswers(answers, user);

        int correct = 0;

        for (Question question : questions) {
            if (answers.get(question) != null && question.getCorrect().size() == answers.get(question).size() &&
                    question.getCorrect().containsAll(answers.get(question))) {
                correct++;
            }
        }

        req.setAttribute("correct", correct);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/result.jsp");
        requestDispatcher.forward(req, resp);

    }
}
