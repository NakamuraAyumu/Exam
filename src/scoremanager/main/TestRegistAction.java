package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力値取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subject = req.getParameter("f3");
        String countStr = req.getParameter("f4");

        int entYear = 0;
        int[] count = {1, 2, 3};
        List<Student> students = null;
        Map<String, String> errors = new HashMap<>();

        // 現在の年
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        // 入学年度リスト作成（過去10年）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            entYearSet.add(i);
        }

        // DAO初期化
        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        List<String> classNumList = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        // 入学年度バリデーション
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                errors.put("entYear", "入学年度は数字で入力してください");
            }
        }

        // 学生データ取得
        if (entYear != 0 && classNum != null && !classNum.equals("0")) {
            students = studentDao.filter(teacher.getSchool(), entYear, classNum, true);
        } else if (entYear != 0) {
            students = studentDao.filter(teacher.getSchool(), entYear, true);
        } else {
            if (classNum != null && !classNum.equals("0")) {
                errors.put("class_without_year", "クラスを指定する場合は入学年度も指定してください");
            }
            students = studentDao.filter(teacher.getSchool(), true);
        }

        // 画面に渡す
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subject);
        req.setAttribute("f4", countStr);
        req.setAttribute("students", students);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("subject_set", subjectList);
        req.setAttribute("count_set", count);
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
