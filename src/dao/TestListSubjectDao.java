package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    // SQL-ийн үндсэн select хэсэг
    private String baseSql =
        "SELECT s.name, t.subject_cd, t.no, t.point, st.no AS student_no, st.name AS student_name, st.ent_year, st.class_num " +
        "FROM test t " +
        "JOIN subject s ON t.subject_cd = s.cd " +
        "JOIN student st ON t.student_no = st.no ";

    // ResultSet-ээс жагсаалт гаргах метод
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                TestListSubject tls = new TestListSubject();
                tls.setEntYear(rSet.getInt("ent_year"));
                tls.setStudentNo(rSet.getString("student_no"));
                tls.setStudentName(rSet.getString("student_name"));
                tls.setClassNum(rSet.getString("class_num"));
                tls.setSubjectCd(rSet.getString("subject_cd"));
                tls.setSubjectCd(rSet.getString("name"));
                tls.getPoint(rSet.getInt("point"));
                list.add(tls);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Филтер хийх гол метод
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition =
            "WHERE t.school_cd = ? AND t.subject_cd = ? AND st.ent_year = ? AND st.class_num = ? " +
            "ORDER BY t.subject_cd ASC, t.no ASC";

        try {
            statement = connection.prepareStatement(baseSql + condition);
            statement.setString(1, school.getCd());
            statement.setString(2, subject.getCd());
            statement.setInt(3, entYear);
            statement.setString(4, classNum);

            rSet = statement.executeQuery();
            list = postFilter(rSet);
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) try { rSet.close(); } catch (SQLException ignore) {}
            if (statement != null) try { statement.close(); } catch (SQLException ignore) {}
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }

        return list;
    }
}
