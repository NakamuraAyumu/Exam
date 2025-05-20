package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//ローカル変数宣言1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String class_num = ""; //入力されたクラス名
		ClassNum classNum = new ClassNum();
		ClassNumDao cnDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		//リクエストパラメーターの取得2
		class_num = req.getParameter("class_num");

		// DBからデータ取得 3
		// なし

		// ビジネスロック4
		if (cnDao.get(class_num , teacher.getSchool()) != null) { // クラス番号が重複している場合
			errors.put("1", "クラス番号が重複しています");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// classNumにセット
			classNum.setClass_num(class_num);
			classNum.setSchool(teacher.getSchool());
			// saveメソッドで情報を登録
			cnDao.save(classNum);
		}

		// レスポンス値をセット 6
		// リクエストに科目コードをセット
		req.setAttribute("class_num",class_num);

		// JSPへフォワード 7
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("class_create.jsp").forward(req, res);
		}

	}

}
