
package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッションから学校情報取得
        School school = (School) req.getSession().getAttribute("school");

        // 科目リストを取得してセット
        SubjectDao sDao = new SubjectDao();
        List<Subject> subjectList = sDao.filter(school); // 科目一覧取得
        req.setAttribute("subjects", subjectList);

        // 検索条件の取得
        String entYearStr = req.getParameter("entYear");
        String classNum = req.getParameter("classNum");
        String subjectCd = req.getParameter("subject_cd");

        if (entYearStr != null && !entYearStr.isEmpty()
         && classNum != null && !classNum.isEmpty()
         && subjectCd != null && !subjectCd.isEmpty()) {

            int entYear = Integer.parseInt(entYearStr);

            // 科目情報を取得
            Subject subject = sDao.get(subjectCd, school);

            // 得点リスト取得
            TestDao testDao = new TestDao();
            List<Test> pointList = testDao.filter(entYear, classNum, subject, 1, school);

            // 表示用の科目名
            req.setAttribute("subjectName", subject.getName());

            // 成績リストをセット
            req.setAttribute("pointList", pointList);
        }

        // 画面に遷移
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}
