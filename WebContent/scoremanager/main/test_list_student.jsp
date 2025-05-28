<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>成績参照</title>
    <style>
        /* ここにCSSを記述して、画像のレイアウトに近づけます */
        body { font-family: sans-serif; }
        .header { background-color: #f0f0f0; padding: 10px; border-bottom: 1px solid #ccc; }
        .menu { float: left; width: 150px; padding: 10px; border-right: 1px solid #ccc; }
        .main-content { margin-left: 170px; padding: 20px; }
        .search-section, .student-info-section { margin-bottom: 20px; }
        .search-section label { display: inline-block; width: 100px; /* ラベルの幅を少し広げます */ }
        .search-section input, .search-section select { margin-right: 10px; margin-bottom: 5px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #e0e0e0; }
    </style>
</head>
<body>
    <div class="header">
        <h1>得点管理システム</h1>
        <div style="float: right;">大原太郎 | <a href="#">ログアウト</a></div>
    </div>

    <div class="menu">
        <ul>
            <li><a href="#">学生管理</a></li>
            <li><a href="#">成績管理</a></li>
            <li><a href="#">受講登録</a></li>
            <li><a href="#">講座登録</a></li>
            <li><a href="#">得点参照</a></li>
            <li><a href="#">科目管理</a></li>
        </ul>
    </div>

    <div class="main-content">
        <h2>① 成績参照</h2>

        <form action="gradeSearch.jsp" method="get">
            <div class="search-section">
                <label for="NO">② 学生番号:</label>
                <input type="text" id="NO" name="NO" value="<%= request.getParameter("NO") != null ? request.getParameter("NO") : "" %>"><br>

                <label for="NAME">名前:</label>
                <input type="text" id="NAME" name="NAME" value="<%= request.getParameter("NAME") != null ? request.getParameter("NAME") : "" %>"><br>

                <label for="ENT_YEAR">③ 入学年度:</label>
                <select id="ENT_YEAR" name="ENT_YEAR">
                    <option value="">---選択してください---</option>
                    <%
                        int currentYear = java.time.Year.now().getValue();
                        for (int i = currentYear; i >= currentYear - 5; i--) { // 例として過去5年
                            String selected = (request.getParameter("ENT_YEAR") != null && request.getParameter("ENT_YEAR").equals(String.valueOf(i))) ? "selected" : "";
                    %>
                        <option value="<%= i %>" <%= selected %>><%= i %></option>
                    <% } %>
                </select><br>

                <label for="CLASS_NUM">④ クラス番号:</label>
                <select id="CLASS_NUM" name="CLASS_NUM">
                    <option value="">---選択してください---</option>
                    <%
                        // クラスのリストをデータベースから取得するか、固定値で設定
                        String[] classes = {"A組", "B組", "C組"}; // 例
                        for (String c : classes) {
                            String selected = (request.getParameter("CLASS_NUM") != null && request.getParameter("CLASS_NUM").equals(c)) ? "selected" : "";
                    %>
                        <option value="<%= c %>" <%= selected %>><%= c %></option>
                    <% } %>
                </select><br>

                <label for="subject">⑤ 科目:</label>
                <select id="subject" name="subject">
                    <option value="">---選択してください---</option>
                    <%
                        // 科目のリストをデータベースから取得するか、固定値で設定
                        String[] subjects = {"数学", "理科", "英語", "国語"}; // 例
                        for (String s : subjects) {
                            String selected = (request.getParameter("subject") != null && request.getParameter("subject").equals(s)) ? "selected" : "";
                    %>
                        <option value="<%= s %>" <%= selected %>><%= s %></option>
                    <% } %>
                </select><br>

                <label for="IS_ATTEND">在学中フラグ:</label>
                <select id="IS_ATTEND" name="IS_ATTEND">
                    <option value="">---選択してください---</option>
                    <option value="1" <%= (request.getParameter("IS_ATTEND") != null && request.getParameter("IS_ATTEND").equals("1")) ? "selected" : "" %>>在学中</option>
                    <option value="0" <%= (request.getParameter("IS_ATTEND") != null && request.getParameter("IS_ATTEND").equals("0")) ? "selected" : "" %>>卒業・休学など</option>
                </select><br>

                <label for="SCHOOL_CD">学校コード:</label>
                <input type="text" id="SCHOOL_CD" name="SCHOOL_CD" value="<%= request.getParameter("SCHOOL_CD") != null ? request.getParameter("SCHOOL_CD") : "" %>"><br>

                <button type="submit">⑨ 検索</button>
                <button type="reset" onclick="window.location.href='gradeSearch.jsp';">⑮ クリア</button>
            </div>
        </form>

        <div class="student-info-section">
            <h3>⑩ 学生情報</h3>
            <%
                // パラメータの取得
                String no = request.getParameter("NO");
                String name = request.getParameter("NAME");
                String ent_year = request.getParameter("ENT_YEAR");
                String class_num = request.getParameter("CLASS_NUM");
                String subject = request.getParameter("subject"); // 科目は以前と同じ
                String is_attend = request.getParameter("IS_ATTEND");
                String school_cd = request.getParameter("SCHOOL_CD");

                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;

                try {
                    // データベース接続（実際にはConnectionProviderのようなクラスを使用するのが良い）
                    Class.forName("com.mysql.cj.jdbc.Driver"); // 使用するDBドライバーに合わせて変更
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/score_db?serverTimezone=JST", "user", "password"); // DB接続情報に合わせて変更

                    StringBuilder sql = new StringBuilder("SELECT s.no, s.name, s.ent_year, s.class_num, s.is_attend, s.school_cd, sub.subject_name, g.score ");
                    sql.append("FROM students s ");
                    sql.append("LEFT JOIN grades g ON s.no = g.student_no "); // student_noはgradesテーブルの学生番号カラム
                    sql.append("LEFT JOIN subjects sub ON g.subject_cd = sub.subject_cd "); // subject_cdはgradesとsubjectsの結合カラム
                    sql.append("WHERE 1=1 "); // 常にtrueの条件で、後続のANDを簡略化

                    List<String> params = new ArrayList<>();

                    if (no != null && !no.isEmpty()) {
                        sql.append("AND s.no LIKE ? ");
                        params.add("%" + no + "%");
                    }
                    if (name != null && !name.isEmpty()) {
                        sql.append("AND s.name LIKE ? ");
                        params.add("%" + name + "%");
                    }
                    if (ent_year != null && !ent_year.isEmpty()) {
                        sql.append("AND s.ent_year = ? ");
                        params.add(ent_year);
                    }
                    if (class_num != null && !class_num.isEmpty()) {
                        sql.append("AND s.class_num = ? ");
                        params.add(class_num);
                    }
                    if (subject != null && !subject.isEmpty()) { // 科目は以前と同じ
                        sql.append("AND sub.subject_name = ? ");
                        params.add(subject);
                    }
                    if (is_attend != null && !is_attend.isEmpty()) {
                        sql.append("AND s.is_attend = ? ");
                        params.add(is_attend);
                    }
                    if (school_cd != null && !school_cd.isEmpty()) {
                        sql.append("AND s.school_cd = ? ");
                        params.add(school_cd);
                    }

                    // SQLを完成させる前に、GROUP BYやORDER BYを追加する場合があります
                    // 例: sql.append(" ORDER BY s.ent_year, s.class_num, s.no");

                    pstmt = conn.prepareStatement(sql.toString());

                    for (int i = 0; i < params.size(); i++) {
                        pstmt.setString(i + 1, params.get(i));
                    }

                    rs = pstmt.executeQuery();
            %>
            <table>
                <thead>
                    <tr>
                        <th>学生番号</th>
                        <th>名前</th>
                        <th>入学年度</th>
                        <th>クラス番号</th>
                        <th>在学中フラグ</th>
                        <th>学校コード</th>
                        <th>科目</th>
                        <th>得点</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        boolean dataFound = false;
                        while (rs.next()) {
                            dataFound = true;
                    %>
                        <tr>
                            <td><%= rs.getString("no") %></td>
                            <td><%= rs.getString("name") %></td>
                            <td><%= rs.getInt("ent_year") %></td>
                            <td><%= rs.getString("class_num") %></td>
                            <td><%= rs.getBoolean("is_attend") ? "在学中" : "卒業・休学など" %></td>
                            <td><%= rs.getString("school_cd") %></td>
                            <td><%= rs.getString("subject_name") != null ? rs.getString("subject_name") : "N/A" %></td>
                            <td><%= rs.getObject("score") != null ? rs.getInt("score") : "N/A" %></td>
                        </tr>
                    <%
                        }
                        if (!dataFound) {
                    %>
                        <tr><td colspan="8">検索条件に合致するデータが見つかりませんでした。</td></tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <%
                } catch (ClassNotFoundException e) {
                    out.println("<p>データベースドライバーが見つかりません: " + e.getMessage() + "</p>");
                } catch (SQLException e) {
                    out.println("<p>データベースエラーが発生しました: " + e.getMessage() + "</p>");
                } finally {
                    try { if (rs != null) rs.close(); } catch (SQLException e) {}
                    try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
                    try { if (conn != null) conn.close(); } catch (SQLException e) {}
                }
            %>
            <p>14 検索情報表示エリアまたは学生番号を入力して検索ボタンをクリックしてください</p>
        </div>
    </div>
</body>
</html>