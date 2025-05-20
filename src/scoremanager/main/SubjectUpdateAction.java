package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//ローカル変数の宣言1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String subject_cd = "";
		String subject_name = "";
		Subject subject= new Subject();
		SubjectDao subjectDao = new SubjectDao ();


		//リクエストパラメーターの取得2
		subject_cd = req.getParameter("cd");

		//DBからのデータ取得3
		//科目の詳細データを取得
		subject = subjectDao.get(subject_cd , teacher.getSchool());

		//ビジネスロック4
		subject_name = subject.getName();

		// レスポンス値をセット 6
		// リクエストに科目コードをセット
		req.setAttribute("cd", subject_cd);
		// リクエストに科目名をセット
		req.setAttribute("name", subject_name);

		// JSPへフォワード 7
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}
