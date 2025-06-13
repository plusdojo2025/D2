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
}
