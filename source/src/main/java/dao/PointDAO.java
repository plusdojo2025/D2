package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Point;

public class PointDAO {
	public List<Point> select(Point pt) {
		// 結果セットを格納するコレクション
		List<Point> pointList = new ArrayList<Point>();

		// データベースに接続と切断を行うオブジェクト
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を作成する
			// 取得されたデータのリストを返す
			String sql = "SELECT user_id, year, month, total_calorie_consumed, total_nosmoke, total_alcohol_consumed, total_calorie_intake, total_sleeptime  FROM point WHERE user_id=? and month=? ORDER BY year, month";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を完成させる
			pStmt.setString(1, pt.getUser_id());
			pStmt.setInt(2, pt.getMonth());
			// SQL文を実行して検索結果を取得する
			ResultSet rs = pStmt.executeQuery();

			// 検索結果をコレクションに格納する
			while (rs.next()) {
				Point p = new Point(rs.getString("user_id"), rs.getInt("year"), rs.getInt("month"),
						rs.getInt("total_calorie_consumed"), rs.getInt("total_nosmoke"),
						rs.getInt("total_alcohol_consumed"), rs.getInt("total_calorie_intake"),
						rs.getInt("total_sleeptime"));
				pointList.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			pointList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			pointList = null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					pointList = null;
				}
			}
		}

		return pointList;
	}

	public List<Point> selectByUserId(String userId) {
		List<Point> pointList = new ArrayList<>();
		Connection conn = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"

					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",

					"root", "password");

			String sql = "SELECT user_id, year, month, total_calorie_consumed, total_nosmoke, total_alcohol_consumed, total_calorie_intake, total_sleeptime FROM point WHERE user_id=? ORDER BY year, month";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userId);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {

				Point p = new Point(rs.getString("user_id"), rs.getInt("month"), rs.getInt("year"),

						rs.getInt("total_calorie_consumed"), rs.getInt("total_nosmoke"),

						rs.getInt("total_alcohol_consumed"), rs.getInt("total_calorie_intake"),

						rs.getInt("total_sleeptime"));

				pointList.add(p);

			}

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

			pointList = null;

		} finally {

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}

		return pointList;

	}

	public Point selectByUserIdMonth(String userId, int month) {
		Point point = new Point();
		Connection conn = null;
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"

					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",

					"root", "password");

			String sql = "SELECT user_id, year, month, total_calorie_consumed, total_nosmoke, total_alcohol_consumed, total_calorie_intake, total_sleeptime FROM point WHERE user_id=? AND month=?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, userId);
			pStmt.setInt(2, month);

			ResultSet rs = pStmt.executeQuery();

			rs.next();

			point = new Point(rs.getString("user_id"), rs.getInt("month"), rs.getInt("year"),
					rs.getInt("total_calorie_consumed"), rs.getInt("total_nosmoke"),
					rs.getInt("total_alcohol_consumed"), rs.getInt("total_calorie_intake"),
					rs.getInt("total_sleeptime"));

		} catch (SQLException | ClassNotFoundException e) {

			e.printStackTrace();

			point = null;

		} finally {

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}

		return point;

	}

	public boolean update(Point pt) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			String sql = "UPDATE point SET " + "total_calorie_consumed = ?, " + "total_nosmoke = ?, "
					+ "total_alcohol_consumed = ?, " + "total_calorie_intake = ?, " + "total_sleeptime = ? "
					+ "WHERE user_id = ? AND year = ? AND month = ?";

			PreparedStatement pStmt = conn.prepareStatement(sql);

			// プレースホルダに値を設定
			pStmt.setInt(1, pt.getTotal_calorie_consumed());
			pStmt.setInt(2, pt.getTotal_nosmoke());
			pStmt.setInt(3, pt.getTotal_alcohol_consumed());
			pStmt.setInt(4, pt.getTotal_calorie_intake());
			pStmt.setInt(5, pt.getTotal_sleeptime());
			pStmt.setString(6, pt.getUser_id());
			pStmt.setInt(7, pt.getYear());
			pStmt.setInt(8, pt.getMonth());

			// 実行
			if (pStmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	// TODO:ポイント更新処理のための項目ごとのupdateを作ってほしい
	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
				"root", "password");
	}

	// ==============================
	// 各項目ごとの更新メソッド
	// ==============================

	public boolean updateTotalCalorieConsumed(String userId, int year, int month, int value) {
		return updateSingleField("total_calorie_consumed", value, userId, year, month);
	}

	public boolean updateTotalNosmoke(String userId, int year, int month, int value) {
		return updateSingleField("total_nosmoke", value, userId, year, month);
	}

	public boolean updateTotalAlcoholConsumed(String userId, int year, int month, int value) {
		return updateSingleField("total_alcohol_consumed", value, userId, year, month);
	}

	public boolean updateTotalCalorieIntake(String userId, int year, int month, int value) {
		return updateSingleField("total_calorie_intake", value, userId, year, month);
	}

	public boolean updateTotalSleeptime(String userId, int year, int month, int value) {
		return updateSingleField("total_sleeptime", value, userId, year, month);
	}

	// ==============================
	// 共通の単一項目更新ロジック
	// ==============================
	private boolean updateSingleField(String fieldName, int value, String userId, int year, int month) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
	                + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	                "root", "password");
	        
	        String sql = "UPDATE point SET " + fieldName + " = ? WHERE user_id = ? AND year = ? AND month = ?";
	        
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, value);
			pstmt.setString(2, userId);
			pstmt.setInt(3, year);
			pstmt.setInt(4, month);

			if (pstmt.executeUpdate() == 1) {
				result = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	// TODO: 毎月の目標値設定の後に、その月のポイントレコードをinsertするメソッド

	public boolean insert(String userID, int month, int year, int total_calorie_consumed) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
	                + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	                "root", "password");
	        
	        String sql = "INSERT INTO point (user_id, year, month, total_calorie_consumed, total_nosmoke, total_alcohol_consumed, total_calorie_intake, total_sleeptime) "
					+ "VALUES (?, ?, ?, ?, 0, 0, 0, 0)";
	        
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userID);
			pstmt.setInt(2, year);
			pstmt.setInt(3, month);
			pstmt.setInt(4, total_calorie_consumed);
			int count = pstmt.executeUpdate();
			if (count == 1) {
				result = true;
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	// deleteメソッド 引数userId,year,monthで指定したレコードを削除する
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

			String sql = "DELETE FROM point WHERE user_id = ? AND year = ? AND month =?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			pStmt.setInt(2, year);
			
			pStmt.setInt(3, month);
			
			// SQL文を実行
			pStmt.executeUpdate();
			result = true;
		} catch (SQLException | ClassNotFoundException e) {
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