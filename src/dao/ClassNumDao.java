package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    // 入学年度リストを取得
    public List<Integer> getEntYearList(School school) throws Exception {
        List<Integer> list = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT DISTINCT ent_year FROM student WHERE school_cd = ? ORDER BY ent_year";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ent_year"));
            }
        }
        connection.close();
        return list;
    }

    // クラスリストを取得（studentテーブルから）
    public List<String> getClassNumList(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT DISTINCT class_num FROM student WHERE school_cd = ? ORDER BY class_num";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }
        }
        connection.close();
        return list;
    }

    // クラス番号保存
    public boolean save(ClassNum classNum) throws Exception {
        Connection connection = getConnection();
        String sql = "INSERT INTO class_num (school_cd, class_num) VALUES (?, ?)";
        int count = 0;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, classNum.getSchool().getCd());
            st.setString(2, classNum.getClass_num());
            count = st.executeUpdate();
        }
        connection.close();
        return count > 0;
    }

    // クラス番号更新
    public boolean update(ClassNum classNum, String newClassNum) throws Exception {
        Connection connection = getConnection();
        String sql = "UPDATE class_num SET class_num = ? WHERE school_cd = ? AND class_num = ?";
        int count = 0;
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newClassNum);
            st.setString(2, classNum.getSchool().getCd());
            st.setString(3, classNum.getClass_num());
            count = st.executeUpdate();
        }
        connection.close();
        return count > 0;
    }

    // クラス番号の存在確認
    public ClassNum get(String class_num, School school) throws Exception {
        ClassNum classNum = null;
        Connection connection = getConnection();
        String sql = "SELECT * FROM class_num WHERE school_cd = ? AND class_num = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            st.setString(2, class_num);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                classNum = new ClassNum();
                classNum.setClass_num(rs.getString("class_num"));
                classNum.setSchool(school);
            }
        }
        connection.close();
        return classNum;
    }

    // クラス番号のリストを取得（class_numテーブルから）
    public List<String> filter(School school) throws Exception {
        List<String> list = new ArrayList<>();
        Connection connection = getConnection();
        String sql = "SELECT class_num FROM class_num WHERE school_cd = ? ORDER BY class_num";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, school.getCd());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("class_num"));
            }
        }
        connection.close();
        return list;
    }
}
