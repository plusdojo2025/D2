package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.HealthRecord;

public class HealthRecordDAO {
	// insertメソッド：引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(HealthRecord record) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// 1つ目の健康記録（全体）に記録するSQL文を準備する
			String sql1 = "INSERT INTO health_whole(user_id, date, nosmoke, sleep_time, calorie_intake, free)"
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt1 = conn.prepareStatement(sql1);

			// SQL文を完成させる
			// ユーザーIDを？に挿入
			if (record.getUserId() != null) {
				pStmt1.setString(1, record.getUserId());
			} else {
				pStmt1.setString(1, "");
			}
			// 日付を？に挿入
			if (record.getDate() != null) {
				pStmt1.setString(2, record.getDate());
			} else {
				pStmt1.setString(2, "");
			}
			// 禁煙できたかを？に挿入
			pStmt1.setInt(3, record.getNosmoke());

			// 睡眠時間を？に挿入
			pStmt1.setDouble(4, record.getSleepHours());

			// 摂取カロリーを？に挿入
			pStmt1.setInt(5, record.getCalorieIntake());

			// 自由欄を？に挿入
			if (record.getFree() != null) {
				pStmt1.setString(6, record.getFree());
			} else {
				pStmt1.setString(6, "");
			}

			// 2つ目の健康記録（アルコール）に記録するSQL文を準備する(idはinsertに含めない)
			String sql2 = "INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content)"
					+ "VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pStmt2 = conn.prepareStatement(sql2);

			// SQL文を完成させる
			// ユーザーIDを？に挿入
			if (record.getUserId() != null) {
				pStmt2.setString(1, record.getUserId());
			} else {
				pStmt2.setString(1, "");
			}
			// 日付を？に挿入
			if (record.getDate() != null) {
				pStmt2.setString(2, record.getDate());
			} else {
				pStmt2.setString(2, "");
			}
			// 純アルコール摂取量を？に挿入
			pStmt2.setDouble(3, record.getPureAlcoholConsumed());

			// 酒摂取量を？に挿入
			pStmt2.setInt(4, record.getAlcoholConsumed());

			// アルコール度数を？に挿入
			pStmt2.setDouble(5, record.getAlcoholContent());

			// 3つ目の健康記録（アルコール）に記録するSQL文を準備する（idはinsertに含めない））
			String sql3 = "INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time)"
					+ "VALUES(?, ?, ?, ?, ?)";
			PreparedStatement pStmt3 = conn.prepareStatement(sql3);

			// SQL文を完成させる
			// ユーザーIDを？に挿入
			if (record.getUserId() != null) {
				pStmt3.setString(1, record.getUserId());
			} else {
				pStmt3.setString(1, "");
			}
			// 日付を？に挿入
			if (record.getDate() != null) {
				pStmt3.setString(2, record.getDate());
			} else {
				pStmt3.setString(2, "");
			}

			// 消費カロリーを？に挿入
			pStmt3.setDouble(3, record.getCalorieConsu());

			// 運動の種類を？に挿入
			if (record.getExerciseType() != null) {
				pStmt3.setString(4, record.getExerciseType());
			} else {
				pStmt3.setString(4, "");
			}

			// 運動時間を？に挿入
			pStmt3.setInt(5, record.getExerciseTime());

			// SQL文を実行する
			if (pStmt1.executeUpdate() == 1 && pStmt2.executeUpdate() == 1 && pStmt3.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	// selectメソッド 指定された項目で検索して、取得されたデータのリストを返す
	public List<HealthRecord> select(String userId, int month) {
		Connection conn = null;
		List<HealthRecord> RecordList = new ArrayList<HealthRecord>();

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM health_whole AS hw LEFT JOIN health_alcohol AS ha ON hw.user_id = ha.user_id AND hw.date = ha.date "
					+ "LEFT JOIN health_exercise he ON hw.user_id = he.user_id AND hw.date = he.date "
					+ "WHERE hw.user_id =? AND hw.date LIKE?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// ユーザーIDで指定
			pStmt.setString(1, userId);

			// 月で指定
			if (month >= 1 && month <= 9) {
				pStmt.setString(2, "%-0" + month + "-%");
			} else {
				pStmt.setString(2, "%-" + month + "-%");
			}

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// ResultSetはDAOでしか使えないため結果表をコレクションにコピーする
			while (rs.next()) {
				HealthRecord record = new HealthRecord(rs.getString("hw.user_id"), rs.getString("hw.date"),
						rs.getString("exercise_type"), rs.getInt("exercise_time"), 0, // 現在の体重はカレンダーに載せないため値はなんでもよい
						rs.getDouble("calorie_consu"), rs.getInt("nosmoke"), rs.getDouble("alcohol_content"),
						rs.getInt("alcohol_consumed"), rs.getDouble("pure_alcohol_consumed"),
						rs.getDouble("sleep_time"), rs.getInt("calorie_intake"), rs.getString("free"));
				RecordList.add(record);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			RecordList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			RecordList = null;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					RecordList = null;
				}
			}
		}
		// 結果を返す
		return RecordList;
	}

	// updateメソッド：引数cardで指定されたレコードを更新し、成功したらtrueを返す
	public boolean update(HealthRecord record) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql1 = "UPDATE SET UPDATE health_whole SET nosmoke=? sleep_time=? calorie_intake=? WHERE user_id=?, date=? ";
			PreparedStatement pStmt1 = conn.prepareStatement(sql1);

			// SQL文を完成させる
			// 禁煙できたかを上書きする
			pStmt1.setInt(1, record.getNosmoke());

			// 睡眠時間を上書きする
			pStmt1.setDouble(2, record.getSleepHours());
			
			//摂取カロリーを上書きする
			pStmt1.setInt(3, record.getCalorieIntake());
			
			//ユーザーIDを指定する
			pStmt1.setString(4, record.getUserId());
			
			//日付を指定する
			pStmt1.setString(5, record.getDate());
//_________06/13(金）ここまで進めた。			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}
	

}
