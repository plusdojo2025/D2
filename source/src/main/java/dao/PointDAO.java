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
	public List<Point> selectByUser_id(java.awt.Point point) {
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
			String sql = "SELECT user_id, year, month, total_calorie_consumed, total_nosmoke, total_alcohol_consumed, total_calorie_intake, total_sleeptime  FROM POINT ORDER BY year, month";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//SQL文を完成させる

			// SQL文を実行して検索結果を取得する
			ResultSet rs = pStmt.executeQuery();

			// 検索結果をコレクションに格納する
			while (rs.next()) {
				Point p = new Point(rs.getString("user_id"), rs.getInt("year"),rs.getInt("month"), rs.getInt("total_calorie_consumed"), rs.getInt("total_nosmoke"), rs.getInt("total_alcohol_consumed"), rs.getInt("total_calorie_intake"), rs.getInt("total_sleeptime"));
				pointList.add(p);
			}

			
		}
		catch (SQLException e) {
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
	
	public boolean update(Point point) {
	    Connection conn = null;
	    boolean result = false;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
	                + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	                "root", "password");

	        String sql = "UPDATE POINT SET "
	                   + "total_calorie_consumed = ?, "
	                   + "total_nosmoke = ?, "
	                   + "total_alcohol_consumed = ?, "
	                   + "total_calorie_intake = ?, "
	                   + "total_sleeptime = ? "
	                   + "WHERE user_id = ? AND year = ? AND month = ?";

	        PreparedStatement pStmt = conn.prepareStatement(sql);

	        // プレースホルダに値を設定
	        pStmt.setInt(1, point.getTotal_calorie_consumed());
	        pStmt.setInt(2, point.getTotal_nosmoke());
	        pStmt.setInt(3, point.getTotal_alcohol_consumed());
	        pStmt.setInt(4, point.getTotal_calorie_intake());
	        pStmt.setInt(5, point.getTotal_sleeptime());
	        pStmt.setString(6, point.getUser_id());
	        pStmt.setInt(7, point.getYear());
	        pStmt.setInt(8, point.getMonth());

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
}