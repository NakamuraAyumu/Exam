package scoremanager.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestListStudentExecuteAction {

    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // パラメータ取得（nullチェック不要。JSP側で空文字チェックがあるので）
            String no = safeParam(req.getParameter("NO"));
            String name = safeParam(req.getParameter("NAME"));
            String ent_year = safeParam(req.getParameter("ENT_YEAR"));
            String class_num = safeParam(req.getParameter("CLASS_NUM"));
            String school_cd = safeParam(req.getParameter("SCHOOL_CD"));

            // JSPに渡す
            req.setAttribute("no", no);
            req.setAttribute("name", name);
            req.setAttribute("ent_year", ent_year);
            req.setAttribute("class_num", class_num);
            req.setAttribute("school_cd", school_cd);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("一覧の取得に失敗しました", e);
        }
    }

    // nullを空文字に変換するユーティリティ
    private String safeParam(String param) {
        return param == null ? "" : param;
    }
}
