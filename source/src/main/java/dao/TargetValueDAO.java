package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.TargetValue;

public class TargetValueDAO {
	public TargetValue getTargetValueByUserId(String userId) {
		TargetValue targetValue = null;
		try {
			Connection conn = null;

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?" + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");
			String sql = "SELECT * FROM target_value WHERE user_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					targetValue = new TargetValue(rs.getString("user_id"), rs.getInt("month"),
							rs.getFloat("pure_alcohol_consumed"), rs.getFloat("sleep_time"),
							rs.getInt("calorie_intake"), rs.getFloat("target_weight"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return targetValue;
	}
	
	public boolean insertVoid(String UserID, int month, float pure_alcohol_consumed, float sleep_time, int calorie_intake, float target_weight) {
		/* 空の目標値レコードを追加する処理
		 * ユーザ登録したときに呼ばれる
		 */
		 Connection conn = null;
		 
		    PreparedStatement pstmt = null;
		    boolean result = false;
		    try {
		    String sql = "INSERT INTO target_value (user_id, month, pure_alcohol_consumed, sleep_time, calorie_intake, target_weight) "
		               + "VALUES (?, ?, ?, ?, ?, ?)";
		    
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

		        pstmt = conn.prepareStatement(sql);
		  
		   
		    	pstmt.setString(1, UserID);
		        pstmt.setInt(2, month);
		        
		     // 以下の値を NULL として登録する
		        pstmt.setNull(3, java.sql.Types.FLOAT); // pure_alcohol_consumed
		        pstmt.setNull(4, java.sql.Types.FLOAT); // sleep_time
		        pstmt.setNull(5, java.sql.Types.INTEGER); // calorie_intake
		        pstmt.setNull(6, java.sql.Types.FLOAT); // target_weight
		    
		        int rowsAffected = pstmt.executeUpdate();
		        if (rowsAffected > 0) {
		            result = true;
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (pstmt != null) pstmt.close();
		            if (conn != null) conn.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 // 結果を返す
		    return result;
		}
		    
		   
	
	public boolean update(TargetValue targetValue) {
		/* 毎月の目標値更新 */
		Connection conn = null;
	    boolean result = false;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
	                + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
	                "root", "password");
	        
	        String sql = "UPDATE target_value SET "
	                   + "pure_alcohol_consumed = ?, "
	                   + "sleep_time = ?, "
	                   + "calorie_intake = ?, "
	                   + "target_weight = ? "
	                   + "WHERE user_id = ? AND month = ?";
	        
	        PreparedStatement pStmt = conn.prepareStatement(sql);
	        
	        pStmt.setFloat(1, targetValue.getPure_alcohol_consumed());
	        pStmt.setFloat(2, targetValue.getSleep_time());
	        pStmt.setInt(3, targetValue.getCalorie_intake());
	        pStmt.setFloat(4, targetValue.getTarget_weight());
	        pStmt.setString(5, targetValue.getUser_id());
	        pStmt.setInt(6, targetValue.getMonth());
	       
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
