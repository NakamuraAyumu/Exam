package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Test;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        session.getAttribute("user");

        req.getParameter("ent_year");
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        String countStr = req.getParameter("count");
        String cd = req.getParameter("school_cd");
        String[] studentNos = req.getParameterValues("student_no");
        String[] points = req.getParameterValues("point");
        Student student = new Student();
        School school = new School();
        List<Test> testList = new ArrayList<>();
        int count = Integer.parseInt(countStr);

        TestDao dao = new TestDao();

        for (int i = 0; i < studentNos.length; i++) {
            String studentNo = studentNos[i];
            String pointStr = points[i];

            if (pointStr == null || pointStr.isEmpty()) continue;

            try {
                int point = Integer.parseInt(pointStr);

                Test test = new Test();
                student.setNo(studentNo);
                student.setClassNum(classNum);
                school.setCd(cd);
                test.setStudent(student);
                test.setClassNum(subjectCd);
                test.setSchool(school);
                test.setNo(count);
                test.setPoint(point);
                test.setClassNum(classNum);

                testList.add(test);

            } catch (NumberFormatException e) {
                // 入力ミス（文字など）はスキップ
                continue;
            }
        }
        dao.save(testList);
        req.getRequestDispatcher("test_regist_result.jsp").forward(req, res);
    }
}
