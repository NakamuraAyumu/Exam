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
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class TestRegistAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		HttpSession session = req.getSession();
		Teacher teacher =(Teacher)session.getAttribute("user");

		//ローカル変数の指定１
		String entYearStr ="";//入力された入学年度
		String classNum ="";//入力されたクラス番号
		String subject ="";//入力された科目
		int entYear = 0;//入学年度
		int[] count = {1,2,3};//回数
		String countstr;
		List<Student> students = null;//学生リスト
		LocalDate todaysDate = LocalDate.now();//LocalDateインスタンスを取得
		int year = todaysDate.getYear();//現在の年を取得
		StudentDao studentDao = new StudentDao();//学生Dao
		ClassNumDao classNumDao = new ClassNumDao();//クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメーターの取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		countstr = req.getParameter("f4");
		//ビジネスロジック４
		if (entYearStr != null){
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		//１０年前から１年後まで年のリストに追加
		for (int i = year - 10; i < year + 1; i++){
			entYearSet.add(i);
		}

		//DBからデータ取得３
		//ログインユーザーの学校コードを元にクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")){
			//入学年度とクラス番号を指定
			students = studentDao.filter(teacher.getSchool(), entYear, classNum, true);
		} else if (entYear != 0 && classNum.equals("0")){
			//入学年度のみ指定
			students = studentDao.filter(teacher.getSchool(), entYear, true);
		} else if (entYear== 0 && classNum == null || entYear == 0 && classNum.equals("0")){
			//指定なし
			//全学生情報を取得
			students = studentDao.filter(teacher.getSchool(),true);
		} else{
			errors.put("f1","クラスを指定する場合は入学年度も指定してください");
			//リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
			//全学生情報を取得
			students = studentDao.filter(teacher.getSchool(), true);
		}

		//レスポンス値をセット６
		//リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		//リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		//科目をセット
		req.setAttribute("f3", subject);
		//リクエストに学生リストをセット
		req.setAttribute("students", students);
		//リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set", subject);
		//プルダウンに回数をセット
		req.setAttribute("count_set", count);
		//回数をセット
		req.setAttribute("f4", countstr);
		//JSPへフォワード７
		req.getRequestDispatcher("test_regist.jsp").forward(req,res);

	}

}
