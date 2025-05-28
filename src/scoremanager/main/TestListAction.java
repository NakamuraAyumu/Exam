package scoremanager.main;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        List<Integer> entYearList = classNumDao.getEntYearList(school);
        List<String> classNumList = classNumDao.getClassNumList(school);
        List<Subject> subjectList = subjectDao.filter(school);
        req.setAttribute("entYearList", entYearList);
        req.setAttribute("classNumList", classNumList);
        req.setAttribute("subjectList", subjectList);

        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");
        String subjectCd = req.getParameter("subjectCd");
        String studentNo = req.getParameter("studentNo");

        if ((entYearStr == null || entYearStr.isEmpty()) ||
            (classNum == null || classNum.isEmpty()) ||
            (subjectCd == null || subjectCd.isEmpty())) {
            req.setAttribute("error", "入学年度とクラスと科目を選択してください");
        } else {
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = subjectDao.get(subjectCd, school);
            TestListSubjectDao testListDao = new TestListSubjectDao();
            List<TestListSubject> testList = testListDao.filter(entYear, classNum, subject, school, studentNo);
            req.setAttribute("testList", testList);
            req.setAttribute("selectedSubject", subject);
        }

        req.getRequestDispatcher("/scoremanager/main/test_list.jsp").forward(req, res);
    }
}
