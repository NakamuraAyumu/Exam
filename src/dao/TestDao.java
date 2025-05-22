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
		String condition = "select"+
				"student.no as student_no,"
				+"student.name,"
				+"student.ent_year,"
				+"student.class_num,"
				+"student.is_attend,"
				+"student.school_cd,"
				+"test.subject_cd,"
				+"test.no as count,"
				+"test.point"
				+"from student"
				+"left join ("
						+"select"
						+"*"
						+"from"
						+"test"
						+"where"
						+"subject_cd = ? and"
								+"no = ?"
						+") as test on"
				+"student.no = test.student_no"
				+"where"
				+"ent_year = ? and"
						+"student.class_num = ? and"
								+"student.school_cd = ?"
										+"order by"
										+"student.no asc";
		//SQL文のソート
		String order = " ordey by subject_cd asc, no asc";
			try{
				//プリペアードステートメントにSQL文をセット
				statement = connection.prepareStatement(baseSql + join + condition + order);
				//プリペアードステートメントに入学年度をバインド
				statement.setInt(1, entYear);
				//プリペアードステートメントクラス番号をバインド
				statement.setString(2, classNum);
				//プリペアードステートメントに科目をバインド
				statement.setString(3, subject.getCd());
				//プリペアードステートメントに番号をバインド
				statement.setInt(4, num);
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
}
}
