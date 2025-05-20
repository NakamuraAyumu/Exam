package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//ローカル変数の指定１
		ClassNumDao classNumDao = new ClassNumDao();//クラス番号Daoを初期化
		LocalDate todayDate = LocalDate.now();//LocalDateインスタンスを取得
		int year = todayDate.getYear();//現在の年を取得

		//リクエストパラメーターの取得２
		//なし

		//DBからデータ取得２
		//ログインユーザーの学校コードを元にクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());

		//ビジネスロジック４
		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		//１０年前から１０年後まで年をリストに追加
		for (int i = year - 10; i < year + 11; i++){
			entYearSet.add(i);
		}
		//レスポンス値をセット６
		//リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		//JSPへフォワード７
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}

}
