import domain.dao.impl.TestDAOImpl;
import domain.model.Option;
import domain.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TestDAOImpl testDAO = new TestDAOImpl(null);
        List<Option> list = new ArrayList<>();
        Question q1 = new Question("q1");
        Option o1 = new Option(1L, "o1", q1, false);
        Option o2 = new Option(2L, "o2", q1, true);
        Option o3 = new Option(3L, "o3", q1, false);
        list.add(o1);
        list.add(o2);
        list.add(o3);
        q1.setId(5L);
        q1.setOptions(list);

        System.out.println(q1);
        list = new ArrayList<>();
        Question q2 = new Question("q2");
        Option o4 = new Option(6L, "o4", q2, false);
        Option o5 = new Option(7L, "o5", q2, true);
        list.add(o4);
        list.add(o5);
        q2.setId(4L);
        q2.setOptions(list);

        System.out.println(q2);
        list = new ArrayList<>();
        list.add(o1);
        list.add(o2);
        list.add(o3);
        list.add(o4);
        list.add(o5);

        System.out.println(list);

//        System.out.println(testDAO.getQuestionsWithOptions(list));
    }

}
