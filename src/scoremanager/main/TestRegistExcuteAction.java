package scoremanager.main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExcuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject");
        int count = Integer.parseInt(req.getParameter("count"));
        int studentCount = Integer.parseInt(req.getParameter("student_count"));

        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        TestDao testDao = new TestDao();

        Subject subject = subjectDao.get(subjectCd, school);
        List<Student> studentList = studentDao.filter(school, entYear, classNum, true);

        List<Test> testList = new ArrayList<>();

        for (int i = 0; i < studentCount; i++) {
            String studentNo = req.getParameter("student_id" + i);
            String scoreStr = req.getParameter("score" + i);

            if (studentNo == null || scoreStr == null || studentNo.isEmpty()) continue;

            int point;
            try {
                point = Integer.parseInt(scoreStr);
            } catch (NumberFormatException e) {
                point = 0;
            }

            // studentList を使って該当Studentを取得
            Student student = studentList.stream()
                .filter(s -> s.getNo().equals(studentNo))
                .findFirst()
                .orElse(null);

            if (student == null) continue;

            Test test = new Test();
            test.setStudent(student); // 詳細な学生情報あり
            test.setSubject(subject);
            test.setNo(count);
            test.setPoint(point);

            testList.add(test);
        }

        Connection connection = testDao.getConnection();
        testDao.save(testList, connection);

        res.sendRedirect("test_regist_done.jsp");
    }
}
