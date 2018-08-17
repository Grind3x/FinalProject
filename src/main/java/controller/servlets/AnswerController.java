package controller.servlets;

import domain.dao.EmptyResultException;
import domain.model.Option;
import domain.model.Question;
import domain.service.OptionService;
import domain.service.QuestionService;
import domain.service.impl.OptionServiceImpl;
import domain.service.impl.QuestionServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings(value = "unchecked")
@WebServlet("/answer")
public class AnswerController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Question, List<Option>> answers = getAnswerFromSession(req);
        List<Option> options = getOptionsFromRequest(req);

        if (req.getAttribute("redirect") != null && options.isEmpty()) {
            String redirect = req.getAttribute("redirect").toString();
            resp.sendRedirect(redirect);
            return;
        }

        Question question = getQuestionFromRequest(req);
        answers.put(question, options);

        HttpSession session = req.getSession();
        session.setAttribute("answers", answers);

        String tId = req.getParameter("test");
        String qId = req.getParameter("question");

        if (req.getAttribute("redirect") == null) {
            if (Integer.valueOf(qId) == session.getAttribute("max_questions")) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/result");
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/test?t=" + tId + "&q=" + (Integer.valueOf(qId) + 1));
            }
        } else {
            String redirect = req.getAttribute("redirect").toString();
            resp.sendRedirect(redirect);
        }
    }

    private Question getQuestionFromRequest(HttpServletRequest req) {
        Question result = null;
        String qId = req.getParameter("question");
        if (qId != null) {
            result = ((List<Question>) req.getSession().getAttribute("questions")).get(Integer.valueOf(qId) - 1);
        }
        return result;
    }

    private List<Option> getOptionsFromRequest(HttpServletRequest req) {
        List<Option> result = new ArrayList<>();
        OptionService optionService = new OptionServiceImpl();
        String[] userOptions = req.getParameterValues("option");
        if (userOptions != null) {
            for (String s : userOptions) {
                Option option = null;
                try {
                    option = optionService.findById(Long.valueOf(s)).orElseThrow(EmptyResultException::new);
                } catch (EmptyResultException e) {
                    e.printStackTrace();
                }
                result.add(option);
            }
        }
        return result;
    }

    private Map<Question, List<Option>> getAnswerFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Map<Question, List<Option>> result;
        if (session.getAttribute("answers") != null) {
            if (session.getAttribute("answers") instanceof HashMap) {
                result = (Map<Question, List<Option>>) session.getAttribute("answers");
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            result = new HashMap<>();
        }
        return result;
    }

}
