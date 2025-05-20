package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession(); //セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		String no =""; //学生番号
		String name= ""; //氏名
		int ent_year = 0; //入学年度
		String class_num = ""; //クラス番号

		boolean isAttend = false; //在学フラグ

		Student student = new Student();
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		//リクエストパラメーターの取得２
		no = req.getParameter("no");

		//DBからデータ取得３
		//学生の詳細データを取得
		student = studentDao.get(no);
		//ログインユーザーの学校コードを元にクラス番号の一覧を取得
		List<String> class_num_set = classNumDao.filter(teacher.getSchool());
		//ビジネスロジック４
		ent_year = student.getEntYear();
		name = student.getName();
		class_num = student.getClassNum();
		isAttend = student.isAttend();

		//レスポンス値をセット６
		//リクエストに入学年度をセット
		req.setAttribute("ent_year", ent_year);
		//リクエストに学生番号をセット
		req.setAttribute("no", no);
		//リクエストに氏名をセット
		req.setAttribute("name", name);
		//リクエストにクラス番号をセット
		req.setAttribute("class_num", class_num);
		//リクエストにクラス番号の一覧をセット
		req.setAttribute("class_num_set", class_num_set);
		//リクエストに在学フラグをセット
		req.setAttribute("is_attend", isAttend);

		//JSPへフォワード７
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
