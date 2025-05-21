package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{
	private String baseSql = "select subject.name, test.subject_cd, test.no,test.point from test";
	private List<TestListStudent>postFilter(ResultSet rSet) throws Exception{
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		try{
			//リザルトセットを全権走査
			while (rSet.next()){
				//学生別リストインスタンスを初期化
				TestListStudent tls = new TestListStudent();
				//学生別リストインスタンスに検索結果をセット
				tls.setSubjectName(rSet.getString("name"));
				tls.setSbujectCd(rSet.getString("subject_cd"));
				tls.setNum(rSet.getInt("no"));
				tls.setPoint(rSet.getInt("point"));
				//リストに追加
				list.add(tls);
			}
		} catch (SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}
	public List<TestListStudent>filter(Student student) throws Exception{
		//リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の結合
		String join = " join subject on test.subject_cd = subject.cd";
		//SQL文の条件
		String condition = " where test.student_no = ? and test.school_cd = ?";
		//SQL文のソート
		String order = " ordey by subject_cd asc, no asc";
			try{
				//プリペアードステートメントにSQL文をセット
				statement = connection.prepareStatement(baseSql + join + condition + order);
				//プリペアードステートメントに学生番号をバインド
				statement.setString(1, student.getNo());
				//プリペアードステートメントに学校コードをバインド
				statement.setString(2, student.getSchool().getCd());
				//プリペアードステートメントを実行
				rSet = statement.executeQuery();
				//リストへの格納処理を実行
				list = postFilter(rSet);
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

}
