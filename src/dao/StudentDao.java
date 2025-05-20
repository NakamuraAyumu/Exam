package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao{
	private String baseSql ="select * from student where school_cd=?";

	@SuppressWarnings("unused")
	public Student get(String no) throws Exception{
				//学生インスタンスを初期化
				Student student = new Student();
				// データベースへのコネクションを確率
				Connection connection = getConnection();
				// プリペアードステートメント
				PreparedStatement statement = null;

				try {
					// プリペアードステートメントにSQL文をセット
					statement = connection.prepareStatement("select * from student where no = ?");
					// プリペアードステートメントに学校コードをバインド
					statement.setString(1, no);
					// プリペアードステートメントを実行
					ResultSet rSet = statement.executeQuery();

					SchoolDao schoolDao = new SchoolDao();

					if (rSet.next()) {
						// リザルトセットが存在する場合
						// 学校インスタンスに学校コードと学校名をセット
						student.setNo(rSet.getString("no"));
						student.setName(rSet.getString("name"));
						student.setEntYear(rSet.getInt("ent_year"));
						student.setName(rSet.getString("class_num"));
						student.setName(rSet.getString("name"));
						student.setName(rSet.getString("name"));
					} else {
						// 存在しない場合
						// 学校インスタンスにnullをセット
						student = null;
					}
				} catch (Exception e) {
					throw e;
				} finally {
					// プリペアードステートメントを閉じる
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}
					// コネクションを閉じる
					if (connection != null) {
						try {
							connection.close();
						} catch (SQLException sqle) {
							throw sqle;
						}
					}
				}
				return student;
			}


	private List<Student> postFilter(ResultSet resultSet, School school)throws Exception{
		List<Student> list = new ArrayList<>();

		try{
			while(resultSet.next()){
				//リザルトセットが存在する場合
				Student student = new Student();

				//学生インスタンスに検索結果をセット
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(school);

				list.add(student);
			}
		}catch(SQLException | NullPointerException e){
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend)throws Exception{
		// 学校インスタンスを初期化
		List<Student> list = new ArrayList<>();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		String conditionAttend ="";
		if(isAttend) {
			conditionAttend =" and is_attend=true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					baseSql + " and ent_year=? and class_num=? " + conditionAttend + " order by no asc");

			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			list = postFilter(rSet, school);

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

	public List<Student> filter(School school, int entYear, boolean isAttend)throws Exception{
		// 学校インスタンスを初期化
		List<Student> list = new ArrayList<>();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		String conditionAttend ="";
		if(isAttend) {
			conditionAttend = " and is_attend=true ";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					baseSql + " and ent_year = ?" + conditionAttend + " order by no asc");

			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);

			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			list = postFilter(rSet, school);
			while (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに学校コードと学校名をセット
				school.setCd(rSet.getString("cd"));
				Student student = new Student();
				student.setEntYear(rSet.getInt("ent_year"));
				student.setAttend(rSet.getBoolean("is_Attend"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

	public List<Student> filter(School school, boolean isAttend)throws Exception{
		// 学校インスタンスを初期化
		List<Student> list = new ArrayList<>();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		String conditionAttend = "";
		if(isAttend){
			conditionAttend = " and is_attend=true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					baseSql + conditionAttend + " order by no asc");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			list = postFilter(rSet, school);

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}

	public boolean save(Student student) throws Exception{
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		//実行件数
		int count = 0;

		try{
			//データベースから学生情報を取得
			Student old = get(student.getNo());
			if(old == null){
				//存在しなければINSERT
				//プリペアードステータメントにSQL文をセット
				statement = connection.prepareStatement("insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?,?,?,?,?,?)");
				//プリペアードステータメントに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			}else{
				//存在すればUPDATE
				//プリペアードステートメントにSQL文をセット
				statement = connection.prepareStatement("update student set name = ?, ent_year = ?, class_num = ?, is attend = ?, school_cd = ? where no = ?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getSchool().getCd());
				statement.setString(6, student.getNo());
			}
			//プリペアードステートメントを実行
			count = statement.executeUpdate();
		} catch (Exception e){
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if(statement != null){
				try{
					statement.close();
				} catch (SQLException sqle){
					throw sqle;
				}
			}
			//コネクションを閉じる
			if(connection != null){
				try  {
					connection.close();
				} catch (SQLException sqle){
					throw sqle;
				}
			}
		}
		return (count > 0);
	}
}

