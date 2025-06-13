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
			String sql = "INSERT INTO health_whole(user_id, date, nosmoke, sleep_time, calorie_intake, free)"
					+ "VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			// ユーザーIDを？に挿入
			if (record.getUserId() != null) {
				pStmt.setString(1, record.getUserId());
			} else {
				pStmt.setString(1, "");
			}
			// 日付を？に挿入
			if (record.getDate() != null) {
				pStmt.setString(2, record.getDate());
			} else {
				pStmt.setString(2, "");
			}
			// 禁煙できたかを？に挿入
			pStmt.setInt(3, record.getNosmoke());

			// 睡眠時間を？に挿入
			pStmt.setDouble(4, record.getSleepHours());

			// 摂取カロリーを？に挿入
			pStmt.setInt(5, record.getCalorieIntake());

			// 自由欄を？に挿入
			if (record.getFree() != null) {
				pStmt.setString(6, record.getFree());
			} else {
				pStmt.setString(6, "");
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
