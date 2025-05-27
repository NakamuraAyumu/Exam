package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    /**
     * 学生別のテスト結果一覧を取得するメソッド
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String sql =
            "SELECT " +
            "  student.no AS student_no, " +
            "  student.name, " +
            "  student.ent_year, " +
            "  student.class_num, " +
            "  student.is_attend, " +
            "  student.school_cd, " +
            "  test.subject_cd, " +
            "  test.no AS count, " +
            "  test.point " +
            "FROM student " +
            "LEFT JOIN ( " +
            "  SELECT * FROM test WHERE subject_cd = ? AND no = ? " +
            ") AS test ON student.no = test.student_id " +
            "WHERE student.ent_year = ? AND student.class_num = ? AND student.school_cd = ? " +
            "ORDER BY student.no ASC";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getCd());
            statement.setInt(2, num);
            statement.setInt(3, entYear);
            statement.setString(4, classNum);
            statement.setString(5, school.getCd());

            rSet = statement.executeQuery();
            list = postFilter(rSet, school, subject);

        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    /**
     * 結果セットから Test オブジェクトのリストを生成する補助メソッド
     */
    private List<Test> postFilter(ResultSet rSet, School school, Subject subject) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rSet.next()) {
            Test test = new Test();

            // 学生情報をセット
            Student student = new Student();
            student.setNo(rSet.getString("student_no"));
            student.setName(rSet.getString("name"));
            student.setEntYear(rSet.getInt("ent_year"));
            student.setClassNum(rSet.getString("class_num"));
            student.setIsAttend(rSet.getBoolean("is_attend"));
            student.setSchool(school);
            test.setStudent(student);

            // テスト情報をセット
            test.setSubject(subject);
            test.setNo(rSet.getInt("count"));
            test.setPoint(rSet.getInt("point"));

            list.add(test);
        }

        return list;
    }

    /**
     * テスト情報を一括で登録するメソッド
     */
    public void save(List<Test> testList, Connection connection) throws Exception {
        PreparedStatement statement = null;
        String sql = "INSERT INTO test (student_id, subject_cd, no, point) VALUES (?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(sql);

            for (Test test : testList) {
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setInt(3, test.getNo());
                statement.setInt(4, test.getPoint());
                statement.addBatch();
            }

            statement.executeBatch();

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
 // データベース接続情報（環境に応じて変更してください）
    private static final String URL = "jdbc:mysql://localhost:3306/score_db?useSSL=false&characterEncoding=utf8";
    private static final String USER = "root"; // ユーザー名
    private static final String PASSWORD = "your_password"; // パスワード

    /**
     * DB接続を取得する共通メソッド
     */
    public Connection getConnection() throws SQLException {
        try {
            // JDBCドライバをロード（MySQL用）
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBCドライバのロードに失敗しました。", e);
        }

        // 接続を返す
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}