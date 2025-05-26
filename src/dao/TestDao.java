package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao {
	private String baseSql = "select  test.subject_cd, test.no,test.point from test";
	private List<Test>postFilter(ResultSet rSet, School school) throws Exception{
		//リストを初期化
		List<Test> list = new ArrayList<>();
		try{
			//リザルトセットを全権走査
			while (rSet.next()){
				//学生別リストインスタンスを初期化
				Test tls = new Test();
				SubjectDao sDao = new SubjectDao();
				SchoolDao sl = new SchoolDao();
				//学生別リストインスタンスに検索結果をセット
				tls.setSubject(sDao.get(rSet.getString("subject_cd"), sl.get(rSet.getString("School_cd"))));
				tls.setNo(rSet.getInt("no"));
				tls.setPoint(rSet.getInt("point"));
				//リストに追加
				list.add(tls);
			}
		} catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}

	public List<Test>filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception{
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の結合
		String join = " JOIN student ON test.student_id = student.student_id;";
		//SQL文の条件
		String condition =
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
			    ") AS test ON student.no = test.student_no " +
			    "WHERE ent_year = ? AND student.class_num = ? AND student.school_cd = ? " +
			    "ORDER BY student.no ASC";

		//SQL文のソート
		String order = " order by subject_cd asc, no asc";
			try{
				//プリペアードステートメントにSQL文をセット
				statement = connection.prepareStatement(baseSql + join + condition + order);
				//プリペアードステートメントに入学年度をバインド
				statement.setString(1, subject.getCd());
				//プリペアードステートメントクラス番号をバインド
				statement.setInt(2, num);
				//プリペアードステートメントに科目をバインド
				statement.setInt(3, entYear);
				//プリペアードステートメントに番号をバインド
				statement.setString(4, classNum);
				//プリペアードステートメントに学校名をバインド
				statement.setString(5, school.getCd());
				//プリペアードステートメントを実行
				rSet = statement.executeQuery();
				//リストへの格納処理を実行
				list = postFilter(rSet, school);
			} catch (Exception e){
				throw e;
			}finally {
				//プリペアードステートメントを閉じる
				if (statement != null){
					try{
						statement.close();
					} catch(SQLException sqle){
						throw sqle;
					}
				}
				//コネクションを閉じる
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException sqle){
						throw sqle;
					}
				}
			}
			return list;
	}

	public void save(List<Test> testList, Connection connection) throws Exception {
	    PreparedStatement statement = null;
	    String sql = "INSERT INTO test (student_id, subject_cd, no, point) VALUES (?, ?, ?, ?)";

	    try {
	        statement = connection.prepareStatement(sql);

	        for (Test test : testList) {
	            statement.setString(1, test.getStudent().getNo()); // クラス番号
	            statement.setString(2, test.getSubject().getCd()); // 科目コード
	            statement.setInt(3, test.getNo());                 // テスト回数など
	            statement.setInt(4, test.getPoint());              // 点数
	            statement.addBatch(); // バッチに追加
	        }

	        statement.executeBatch(); // 一括で実行
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }
	}

}