package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		ClassNumDao cnDao = new ClassNumDao();//クラスDAO

		//DBからデータ取得3
		List<String>list = cnDao.filter(teacher.getSchool());
		//レスポンス値をセット6
		req.setAttribute("list", list);

		// JSPへフォワード 7
		req.getRequestDispatcher("class_list.jsp").forward(req, res);

	}

}
