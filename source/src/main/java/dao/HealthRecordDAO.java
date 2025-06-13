package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
			if (pStmt1.executeUpdate() == 1 && pStmt2.executeUpdate() == 1 && pStmt3.executeUpdate() ==1) {
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
}
