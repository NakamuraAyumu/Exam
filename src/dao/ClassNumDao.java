package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {
	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from class_num where class_num = ? ");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに学校コードと学校名をセット
				school.setCd(rSet.getString("id"));
				school.setName(rSet.getString("name"));
			} else {
				// 存在しない場合
				// 学校インスタンスにnullをセット
				school = null;
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
		return classNum;
		}

	public List<String> filter(School school)throws Exception{
		List<String> list = new ArrayList<>();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select class_num from class_num where school_cd = ? order by class_num");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに学校コードと学校名をセット
				list.add(rSet.getString("class_num"));
			} else {
				// 存在しない場合
				// 学校インスタンスにnullをセット
				school = null;
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

	public boolean save(ClassNum classNum)throws Exception{
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//実行件数
		int count = 0;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("insert into class_num(school_cd, class_num values(?, ?)");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClass_num());
			// プリペアードステートメントを実行
			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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
		return (count > 0);
	}
	public boolean save(ClassNum classNum, String newClassNum) throws Exception{
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		//実行件数
		int count = 0;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("update class_num set class_num = ? where school_cd = ? and class_num =?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, newClassNum);
			statement.setString(2, classNum.getSchool().getCd());
			statement.setString(3, classNum.getClass_num());

			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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
		return (count > 0);
	}
}