package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	public Subject get(String cd, School school) throws Exception {

		// 科目インスタンスを初期化
		Subject subject = new Subject();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ?");
			// プリペアードステートメントに値をバインド
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao sDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				// 学校フィールドには学校コードをセット
				subject.setSchool(sDao.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 科目インスタンスにnullをセット
				subject = null;
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

		return subject;
	}

	public List<Subject> filter(School school) throws Exception {

		// リストを初期化
		List<Subject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select cd, name from subject where school_cd = ?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントを実行
			rSet = statement.executeQuery();

			// リザルトセットを全権走査
			while (rSet.next()) {
				// 科目インスタンスを初期化
				Subject subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
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

	public boolean save(Subject subject) throws Exception {

		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// データベースから科目を取得
			Subject old = get(subject.getCd(), subject.getSchool());
			if (old == null) {
				// 科目が存在しなかった場合
				// プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement("insert into subject(school_cd, cd, name) values(?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				// 科目が存在した場合
				// プリペアードステートメントにUPDATE文をセット
				statement = connection.prepareStatement("update subject set name = ? where school_cd = ? and cd = ?");
				// プリペアードステートメントに値をバインド
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getSchool().getCd());
				statement.setString(3, subject.getCd());
			}

			// プリペアードステートメントを実行
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

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	public boolean delete(Subject subject) throws Exception {

		//データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("delete from subject where school_cd = ? and cd = ?");
			// プリペアードステートメントに値をセット
			statement.setString(1, subject.getSchool().getCd());
			statement.setString(2, subject.getCd());
			// プリペアードステートメントを実行
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

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}
