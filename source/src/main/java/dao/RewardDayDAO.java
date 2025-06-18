package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.RewardDay;

public class RewardDayDAO {
	public List<RewardDay> select(String userId, int month) {
	    List<RewardDay> rewardList = new ArrayList<>();

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        try (Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
	                "root", "password")) {

	            // 今年の年を使う想定
	            int year = java.time.LocalDate.now().getYear();
	            LocalDate startDate = LocalDate.of(year, month, 1);
	            LocalDate endDate = startDate.plusMonths(1);

	            String sql = "SELECT user_id, date, reward_explain FROM reward_day "
	                       + "WHERE user_id = ? AND date >= ? AND date < ? ORDER BY date";

	            PreparedStatement pStmt = conn.prepareStatement(sql);
	            pStmt.setString(1, userId);
	            pStmt.setDate(2, java.sql.Date.valueOf(startDate));
	            pStmt.setDate(3, java.sql.Date.valueOf(endDate));

	            ResultSet rs = pStmt.executeQuery();

	            while (rs.next()) {
	                RewardDay rd = new RewardDay(rs.getString("user_id"), rs.getDate("date"),
	                        rs.getString("reward_explain"));
	                rewardList.add(rd);
	            }
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        rewardList = null;
	    }

	    return rewardList;
	}
	
	public boolean insert(RewardDay rewardday){
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/D2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// INSERT文を準備する
			String sql = "INSERT INTO reward_day (user_id, date, reward_explain) VALUES (?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, rewardday.getUserId());
			pStmt.setString(2, rewardday.getDate().toString());
			pStmt.setString(3, rewardday.getRewardExplain());

			// INSERT文を実行
			int rowsInserted = pStmt.executeUpdate();
			if (rowsInserted > 0) {
				result = true;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			result = false;
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
	
	public static void main(String[] args) {
		RewardDayDAO rewardDayDAO = new RewardDayDAO();
		RewardDay rewardDay = new RewardDay("kazutoshi_t", Date.valueOf("2025-06-16"), "人であふれた！");
		rewardDayDAO.insert(rewardDay);
	}
}
