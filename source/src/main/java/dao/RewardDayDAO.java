package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RewardDay;

public class RewardDayDAO {
	public List<RewardDay> select(RewardDay rewardday) {
		Connection conn = null;
		List<RewardDay> rewardList = new ArrayList<RewardDay>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?" + "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
					"root", "password");

			String sql = "SELECT user_id, date, reward_explain FROM reward_day ORDER BY user_id, date";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			ResultSet rs = pStmt.executeQuery();

			while (rs.next()) {
				RewardDay rd = new RewardDay(rs.getString("user_id"), rs.getDate("date"),
						rs.getString("reward_explain"));
				rewardList.add(rd);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			rewardList = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			rewardList = null;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					rewardList = null;
				}
			}
		}

		return rewardList;
	}
}
