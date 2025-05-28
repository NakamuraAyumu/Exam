package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestListStudentExecuteAction {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
        	///学生番号
			String no = req.getParameter("NO");
			///名前
			String name = req.getParameter("NAME");
			///入学年度
			String ent_year = req.getParameter("ENT_YEAR");
			///クラス番号
			String class_num = req.getParameter("CLASS_NUM");
			///在学中フラグ
			String is_attend = req.getParameter("IS_ATTEND");
			///学校コード
			String school_cd = req.getParameter("SCHOOL_CD");


            // JSPに渡す
			req.setAttribute("no", no);
			req.setAttribute("name", name);
			req.setAttribute("ent_year", ent_year);
			req.setAttribute("class_num", class_num);
			req.setAttribute("is_attend", is_attend);
			req.setAttribute("school_cd", school_cd);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("一覧の取得に失敗しました", e);
        }
    }
}
