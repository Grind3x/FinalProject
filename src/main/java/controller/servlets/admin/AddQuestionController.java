package controller.servlets.admin;

import domain.dao.EmptyResultException;
import domain.model.Option;
import domain.model.Question;
import domain.model.Test;
import domain.service.OptionService;
import domain.service.QuestionService;
import domain.service.TestService;
import domain.service.impl.OptionServiceImpl;
import domain.service.impl.QuestionServiceImpl;
import domain.service.impl.TestServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/add_question")
public class AddQuestionController extends HttpServlet {
    private TestService testService = new TestServiceImpl();
    private QuestionService questionService = new QuestionServiceImpl();
    private OptionService optionService = new OptionServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(Arrays.toString(req.getParameterValues("options")));
        System.out.println(Arrays.toString(req.getParameterValues("correct")));
        System.out.println(req.getParameter("test"));

        String[] options = req.getParameterValues("options");
        String[] correct = req.getParameterValues("correct");
        Long testId = Long.valueOf(req.getParameter("test"));
        String text = req.getParameter("question_text");

        try {
            Test test = testService.findById(testId).orElseThrow(EmptyResultException::new);
            Question question = new Question(text, test);
            questionService.insert(question);
            List<Option> optionsList = new ArrayList<>();
            for (int i = 0; i < options.length; i++) {
                if (!options[i].equals("")) {
                    Option option = new Option(options[i], question, Arrays.asList(correct).contains(i + ""));
                    optionService.insert(option);
                    optionsList.add(option);
                }
            }
            question.setOptions(optionsList);
            questionService.update(question);

            System.out.println(optionsList);
            System.out.println(question);
        } catch (EmptyResultException e) {
            e.printStackTrace();
        }


    }
}
