package controller.servlets.admin;

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
import java.io.IOException;

@WebServlet("/remove_question")
public class RemoveQuestionController extends HttpServlet {
    private TestService testService = new TestServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long testId = Long.valueOf(req.getParameter("test"));
        try {
            Test test = testService.findById(testId).orElseThrow(EmptyResultException::new);
            req.setAttribute("test", test);
        } catch (EmptyResultException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin/remove_question.jsp");
        requestDispatcher.forward(req, resp);
    }
}
