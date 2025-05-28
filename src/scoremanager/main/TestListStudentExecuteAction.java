package scoremanager.main;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Action;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import bean.School;
import dao.TestListStudentDao;

public class TestListStudentExecuteAction implements Action {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String schoolCd = req.getParameter("schoolCd");
            String entYear = req.getParameter("entYear");
            String classNum = req.getParameter("classNum");
            String isAttend = req.getParameter("isAttend");

            School school = new School();
            school.setCd(schoolCd);

            TestListStudentDao dao = new TestListStudentDao();
            List studentList = dao.filter(school, entYear, classNum, Boolean.parseBoolean(isAttend));

            req.setAttribute("studentList", studentList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("学生一覧取得に失敗しました");
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public Object getValue(String key) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setEnabled(boolean b) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO 自動生成されたメソッド・スタブ

	}
}