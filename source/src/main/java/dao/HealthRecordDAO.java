package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.HealthAlcohol;
import dto.HealthExercise;
import dto.HealthRecord;
import dto.HealthWhole;

public class HealthRecordDAO {
	// insertメソッド：引数cardで指定されたレコードを登録し、成功したらtrueを返す
	public boolean insert(HealthWhole hw, List<HealthAlcohol> haList, List<HealthExercise> heList) {
		Connection conn = null;
		boolean result = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// トランザクション開始
			conn.setAutoCommit(false);
			// 1つ目の健康記録（全体）に記録するSQL文を準備する
			String sqlWhole = "INSERT INTO health_whole(user_id, date, nosmoke, sleep_time, calorie_intake, free, now_weight)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pStmtWhole = conn.prepareStatement(sqlWhole)) {
				// SQL文を完成させる
				// ユーザーIDを？に挿入
				if (hw.getUserId() != null) {
					pStmtWhole.setString(1, hw.getUserId());
				} else {
					pStmtWhole.setString(1, "");
				}
				// 日付を？に挿入
				if (hw.getDate() != null) {
					pStmtWhole.setString(2, hw.getDate());
				} else {
					pStmtWhole.setString(2, "");
				}
				// 禁煙できたかを？に挿入
				pStmtWhole.setInt(3, hw.getNosmoke());

				// 睡眠時間を？に挿入
				pStmtWhole.setDouble(4, hw.getSleepHours());

				// 摂取カロリーを？に挿入
				pStmtWhole.setInt(5, hw.getCalorieIntake());

				// 自由欄を？に挿入
				if (hw.getFree() != null) {
					pStmtWhole.setString(6, hw.getFree());
				} else {
					pStmtWhole.setString(6, "");
				}
				
				pStmtWhole.setDouble(7, hw.getNowWeight());
				// SQL文を実行
				pStmtWhole.executeUpdate();
			}

			// 2つ目の健康記録（アルコール）に記録するSQL文を準備する(idはinsertに含めない)
			for (HealthAlcohol ha : haList) {
				String sqlAlc = "INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content)"
						+ "VALUES(?, ?, ?, ?, ?)";
				try (PreparedStatement pStmtAlc = conn.prepareStatement(sqlAlc)) {
					// SQL文を完成させる
					// ユーザーIDを？に挿入
					if (ha.getUserId() != null) {
						pStmtAlc.setString(1, ha.getUserId());
					} else {
						pStmtAlc.setString(1, "");
					}
					// 日付を？に挿入
					if (ha.getDate() != null) {
						pStmtAlc.setString(2, ha.getDate());
					} else {
						pStmtAlc.setString(2, "");
					}
					// 純アルコール摂取量を？に挿入
					pStmtAlc.setDouble(3, ha.getPureAlcoholConsumed());

					// 酒摂取量を？に挿入
					pStmtAlc.setInt(4, ha.getAlcoholConsumed());

					// アルコール度数を？に挿入
					pStmtAlc.setDouble(5, ha.getAlcoholContent());

					// SQL文を実行
					pStmtAlc.executeUpdate();
				}
			}

			// 3つ目の健康記録（運動）に記録するSQL文を準備する（idはinsertに含めない））
			for (HealthExercise he : heList) {
				String sqlExer = "INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time)"
						+ "VALUES(?, ?, ?, ?, ?)";
				try (PreparedStatement pStmtExer = conn.prepareStatement(sqlExer)) {
					// SQL文を完成させる
					// ユーザーIDを？に挿入
					if (he.getUserId() != null) {
						pStmtExer.setString(1, he.getUserId());
					} else {
						pStmtExer.setString(1, "");
					}
					// 日付を？に挿入
					if (he.getDate() != null) {
						pStmtExer.setString(2, he.getDate());
					} else {
						pStmtExer.setString(2, "");
					}

					// 消費カロリーを？に挿入
					pStmtExer.setDouble(3, he.getCalorieConsu());

					// 運動の種類を？に挿入
					if (he.getExerciseType() != null) {
						pStmtExer.setString(4, he.getExerciseType());
					} else {
						pStmtExer.setString(4, "");
					}

					// 運動時間を？に挿入
					pStmtExer.setInt(5, he.getExerciseTime());

					// SQL文を実行する
					pStmtExer.executeUpdate();
				}
			}

			// 全て成功したらコミット
			conn.commit();
			result = true;

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.setAutoCommit(true); // 元に戻す
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
		List<HealthRecord> recordList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password");

			String sql = "SELECT hw.*, "
					+ "  (SELECT alcohol_content FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS alcohol_content, "
					+ "  (SELECT alcohol_consumed FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS alcohol_consumed, "
					+ "  (SELECT pure_alcohol_consumed FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS pure_alcohol_consumed, "
					+ "  (SELECT exercise_type FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS exercise_type, "
					+ "  (SELECT exercise_time FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS exercise_time, "
					+ "  (SELECT calorie_consu FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS calorie_consu "
					+ "FROM health_whole hw " + "WHERE hw.user_id = ? AND MONTH(hw.date) = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			pStmt.setInt(2, month);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				HealthRecord record = new HealthRecord(rs.getString("user_id"), rs.getString("date"),
						rs.getString("exercise_type"), rs.getInt("exercise_time"), 0, // 現体重は使わない想定
						rs.getDouble("calorie_consu"), rs.getInt("nosmoke"), rs.getDouble("alcohol_content"),
						rs.getInt("alcohol_consumed"), rs.getDouble("pure_alcohol_consumed"),
						rs.getDouble("sleep_time"), rs.getInt("calorie_intake"), rs.getString("free"));
				recordList.add(record);
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			recordList = null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return recordList;
	}
	
	//日付で一日分の健康記録取得
	public HealthRecord selectByDate(String userId, String date) {
	    Connection conn = null;
	    HealthRecord record = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	                                           "root", "password");

	        String sql = "SELECT hw.*, "
	                + "  (SELECT alcohol_content FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS alcohol_content, "
	                + "  (SELECT alcohol_consumed FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS alcohol_consumed, "
	                + "  (SELECT pure_alcohol_consumed FROM health_alcohol WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS pure_alcohol_consumed, "
	                + "  (SELECT exercise_type FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS exercise_type, "
	                + "  (SELECT exercise_time FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS exercise_time, "
	                + "  (SELECT calorie_consu FROM health_exercise WHERE user_id = hw.user_id AND date = hw.date ORDER BY id ASC LIMIT 1) AS calorie_consu "
	                + "FROM health_whole hw WHERE hw.user_id = ? AND hw.date = ?";

	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        pStmt.setString(1, userId);
	        pStmt.setString(2, date);

	        ResultSet rs = pStmt.executeQuery();

	        if (rs.next()) {
	            record = new HealthRecord(
	                rs.getString("user_id"),
	                rs.getString("date"),
	                rs.getString("exercise_type"),
	                rs.getInt("exercise_time"),
	                rs.getDouble("now_weight"),
	                rs.getDouble("calorie_consu"),
	                rs.getInt("nosmoke"),
	                rs.getDouble("alcohol_content"),
	                rs.getInt("alcohol_consumed"),
	                rs.getDouble("pure_alcohol_consumed"),
	                rs.getDouble("sleep_time"),
	                rs.getInt("calorie_intake"),
	                rs.getString("free")
	            );
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        record = null;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return record;
	}
	
	// selectAll(健康記録全体の日付だけを古い順に並べなおして取得）
	public List<String> select(String userId) {
		Connection conn = null;
		List<String> dateList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password");
			String sql = "SELECT date FROM health_whole WHERE user_id= ? ORDER BY date";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				dateList.add(rs.getString("date"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			dateList = null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return dateList;

	}

//	public List<HealthRecord> select(String userId, int month) {
//		Connection conn = null;
//		List<HealthRecord> RecordList = new ArrayList<HealthRecord>();
//
//		try {
//			// JDBCドライバを読み込む
//			Class.forName("com.mysql.cj.jdbc.Driver");
//
//			// データベースに接続する
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
//					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
//					"root", "password");
//
//			// SQL文を準備する
//			String sql = "SELECT * FROM health_whole AS hw LEFT JOIN health_alcohol AS ha ON hw.user_id = ha.user_id AND hw.date = ha.date "
//					+ "LEFT JOIN health_exercise he ON hw.user_id = he.user_id AND hw.date = he.date "
//					+ "WHERE hw.user_id =? AND hw.date LIKE?;";
//			PreparedStatement pStmt = conn.prepareStatement(sql);
//
//			// ユーザーIDで指定
//			pStmt.setString(1, userId);
//
//			// 月で指定
//			if (month >= 1 && month <= 9) {
//				pStmt.setString(2, "-0" + month + "-%");
//			} else {
//				pStmt.setString(2, "-" + month + "-%");
//			}
//
//			// SQL文を実行し、結果表を取得する
//			ResultSet rs = pStmt.executeQuery();
//
//			// ResultSetはDAOでしか使えないため結果表をコレクションにコピーする
//			while (rs.next()) {
//				HealthRecord record = new HealthRecord(rs.getString("hw.user_id"), rs.getString("hw.date"),
//						rs.getString("exercise_type"), rs.getInt("exercise_time"), 0, // 現在の体重はカレンダーに載せないため値はなんでもよい
//						rs.getDouble("calorie_consu"), rs.getInt("nosmoke"), rs.getDouble("alcohol_content"),
//						rs.getInt("alcohol_consumed"), rs.getDouble("pure_alcohol_consumed"),
//						rs.getDouble("sleep_time"), rs.getInt("calorie_intake"), rs.getString("free"));
//				RecordList.add(record);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			RecordList = null;
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			RecordList = null;
//		} finally {
//			// データベースを切断
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//					RecordList = null;
//				}
//			}
//		}
//		// 結果を返す
//		return RecordList;
//	}

	// deleteメソッド：引数userId,year, monthで指定されたレコードを削除し、成功したらtrueを返す
	public boolean delete(String userId, int year, int month) {
		Connection conn = null;
		boolean result = false;
		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// トランザクション開始
			conn.setAutoCommit(false);
			String date;
			if (month >= 1 && month <= 9) {
				date = year + "-0" + month + "-%";
			} else {
				date = year + "-" + month + "-%";
			}
			// ２つ目の健康記録（アルコール）を削除するSQL文を準備して完成させる
			String sqlAlcohol = "DELETE FROM health_alcohol WHERE user_id = ? AND date like ?";
			try (PreparedStatement pStmtAlcohol = conn.prepareStatement(sqlAlcohol)) {
				pStmtAlcohol.setString(1, userId);

				pStmtAlcohol.setString(2, date);
				// SQL文を実行
				pStmtAlcohol.executeUpdate();
			}
			// 3つ目の健康記録（運動）を削除するSQL文を準備して完成させる
			String sqlExercise = "DELETE FROM health_exercise where user_id = ? AND date like ?";
			try (PreparedStatement pStmtExercise = conn.prepareStatement(sqlExercise)) {
				pStmtExercise.setString(1, userId);
				pStmtExercise.setString(2, date);
				// SQL文を実行
				pStmtExercise.executeUpdate();
			}
			// １つ目の健康記録（全体）を削除するSQL文を準備して完成させる
			String sqlWhole = "DELETE FROM health_whole WHERE user_id = ? AND date like ?";
			try (PreparedStatement pStmtWhole = conn.prepareStatement(sqlWhole)) {
				pStmtWhole.setString(1, userId);
				pStmtWhole.setString(2, date);
				// SQL文を実行
				pStmtWhole.executeUpdate();
			}
			// 全て成功したらコミット
			conn.commit();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.setAutoCommit(true); // 元に戻す
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// 結果を返す
		return result;
	}

	public static void main(String[] args) {
		HealthRecordDAO DAO = new HealthRecordDAO();
		DAO.delete("kazutoshi_t", 2025, 06);

	}
	/*
	 * // updateメソッド：引数cardで指定されたレコードを更新し、成功したらtrueを返す public boolean
	 * update(HealthRecord record) { Connection conn = null; boolean result = false;
	 * 
	 * try { // JDBCドライバを読み込む Class.forName("com.mysql.cj.jdbc.Driver");
	 * 
	 * // データベースに接続する conn =
	 * DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?" +
	 * "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	 * "root", "password");
	 * 
	 * // トランザクション開始 conn.setAutoCommit(false);
	 * 
	 * 
	 * try{ // 1つ目の健康記録（全体）に記録するSQL文を準備する String sqlWhole =
	 * "UPDATE SET UPDATE health_whole SET nosmoke=? sleep_time=? calorie_intake=? WHERE user_id=?, date=? "
	 * ; PreparedStatement pStmtWhole = conn.prepareStatement(sqlWhole); //
	 * SQL文を完成させる // 禁煙できたかを上書きする pStmtWhole.setInt(1, record.getNosmoke());
	 * 
	 * // 睡眠時間を上書きする pStmtWhole.setDouble(2, record.getSleepHours());
	 * 
	 * // 摂取カロリーを上書きする pStmtWhole.setInt(3, record.getCalorieIntake());
	 * 
	 * // ユーザーIDを指定する pStmtWhole.setString(4, record.getUserId());
	 * 
	 * // 日付を指定する pStmtWhole.setString(5, record.getDate());
	 * 
	 * //SQLを実行する pStmtWhole.executeUpdate(); }
	 * 
	 * try { // 2つ目の健康記録(アルコール)に記録するSQL文を準備する //
	 * まず健康記録（アルコール）で指定されたユーザー、日付のレコードを削除する String sqlDelAlc =
	 * "DELETE FROM health_alcohol WHERE user_id=?, date=? "; PreparedStatement
	 * pStmtDelAlc = conn.prepareStatement(sqlDelAlc);
	 * 
	 * // ユーザーIDを指定する pStmtDelAlc.setString(1, record.getUserId());
	 * 
	 * // 日付を指定する pStmtDelAlc.setString(2, record.getDate()); }
	 * 
	 * try { // 健康記録（アルコール）に新規レコードを追加 String sqlInsAlc =
	 * "INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content)"
	 * + "VALUES(?, ?, ?, ?, ?)"; PreparedStatement pStmtInsAlc =
	 * conn.prepareStatement(sqlInsAlc);
	 * 
	 * // SQL文を完成させる // ユーザーIDを？に挿入 if (record.getUserId() != null) {
	 * pStmtInsAlc.setString(1, record.getUserId()); } else {
	 * pStmtInsAlc.setString(1, ""); } // 日付を？に挿入 if (record.getDate() != null) {
	 * pStmtInsAlc.setString(2, record.getDate()); } else { pStmtInsAlc.setString(2,
	 * ""); } // 純アルコール摂取量を？に挿入 pStmtInsAlc.setDouble(3,
	 * record.getPureAlcoholConsumed());
	 * 
	 * // 酒摂取量を？に挿入 pStmtInsAlc.setInt(4, record.getAlcoholConsumed());
	 * 
	 * // アルコール度数を？に挿入 pStmtInsAlc.setDouble(5, record.getAlcoholContent()); }
	 * 
	 * try { // 3つ目の健康記録(運動)に記録するSQL文を準備する // まず健康記録（運動）で指定されたユーザー、日付のレコードを削除する
	 * String sqlDelExer = "DELETE FROM health_alcohol WHERE user_id=?, date=? ";
	 * PreparedStatement pStmtDelExer = conn.prepareStatement(sqlDelExer);
	 * 
	 * // ユーザーIDを指定する pStmtDelExer.setString(1, record.getUserId());
	 * 
	 * // 日付を指定する pStmtDelExer.setString(2, record.getDate()); }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * // 健康記録（アルコール）に新規レコードを追加 String sql3 =
	 * "INSERT INTO health_alcohol(user_id, date, pure_alcohol_consumed, alcohol_consumed, alcohol_content)"
	 * + "VALUES(?, ?, ?, ?, ?)"; PreparedStatement pStmt3 =
	 * conn.prepareStatement(sql3);
	 * 
	 * // SQL文を完成させる // ユーザーIDを？に挿入 if (record.getUserId() != null) {
	 * pStmt3.setString(1, record.getUserId()); } else { pStmt3.setString(1, ""); }
	 * // 日付を？に挿入 if (record.getDate() != null) { pStmt3.setString(2,
	 * record.getDate()); } else { pStmt3.setString(2, ""); } // 純アルコール摂取量を？に挿入
	 * pStmt3.setDouble(3, record.getPureAlcoholConsumed());
	 * 
	 * // 酒摂取量を？に挿入 pStmt3.setInt(4, record.getAlcoholConsumed());
	 * 
	 * // アルコール度数を？に挿入 pStmt3.setDouble(5, record.getAlcoholContent());
	 * 
	 * }catch(
	 * 
	 * SQLException alcoholEx) { System.out.println("アルコール処理でエラー：ロールバックします");
	 * conn.rollback(sp1); // アルコール処理のみをロールバック }
	 * 
	 * // セーブポイント②（ここから運動の処理） Savepoint sp2 = conn.setSavepoint("ExerciseStart");
	 * 
	 * try {
	 * 
	 * // 3つ目の健康記録(運動)に記録するSQL文を準備する // まず健康記録（運動）で指定されたユーザー、日付のレコードを削除する String
	 * sql4 = "DELETE FROM health_exercise WHERE user_id=?, date=? ";
	 * PreparedStatement pStmt4 = conn.prepareStatement(sql4);
	 * 
	 * // ユーザーIDを指定する pStmt4.setString(4, record.getUserId());
	 * 
	 * // 日付を指定する pStmt4.setString(5, record.getDate());
	 * 
	 * // 健康記録（アルコール）に新規レコードを追加 String sql5 =
	 * "INSERT INTO health_exercise(user_id, date, calorie_consu, exercise_type, exercise_time)"
	 * + "VALUES(?, ?, ?, ?, ?)"; PreparedStatement pStmt5 =
	 * conn.prepareStatement(sql5);
	 * 
	 * // SQL文を完成させる // ユーザーIDを？に挿入 if (record.getUserId() != null) {
	 * pStmt5.setString(1, record.getUserId()); } else { pStmt5.setString(1, ""); }
	 * // 日付を？に挿入 if (record.getDate() != null) { pStmt5.setString(2,
	 * record.getDate()); } else { pStmt5.setString(2, ""); }
	 * 
	 * // 消費カロリーを？に挿入 pStmt5.setDouble(3, record.getCalorieConsu());
	 * 
	 * // 運動の種類を？に挿入 if (record.getExerciseType() != null) { pStmt5.setString(4,
	 * record.getExerciseType()); } else { pStmt5.setString(4, ""); }
	 * 
	 * // 運動時間を？に挿入 pStmt5.setInt(5, record.getExerciseTime());
	 * 
	 * // SQL文を実行する(運動記録は１件だけとは限らないからこれじゃだめじゃね？) if (pStmt1.executeUpdate() == 1 &&
	 * pStmt2.executeUpdate() == 1 && pStmt3.executeUpdate() == 1) { result = true;
	 * }
	 * 
	 * }catch( SQLException e) { e.printStackTrace(); }catch( ClassNotFoundException
	 * e) { e.printStackTrace(); }finally { // データベースを切断 if (conn != null) { try {
	 * conn.close(); } catch (SQLException e) { e.printStackTrace(); } } }
	 * 
	 * // 結果を返す return result;
	 */
}
