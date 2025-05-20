package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//ローカル変数の宣言1
		String class_num = "";


		//リクエストパラメーターの取得2
		class_num = req.getParameter("class_num");

		//DBからのデータ取得3
		//なし

		//ビジネスロック4
		//なし

		// レスポンス値をセット 6
		// リクエストにクラス番号をセット
		req.setAttribute("class_num", class_num);


		// JSPへフォワード 7
		req.getRequestDispatcher("class_update.jsp").forward(req, res);

	}

}
