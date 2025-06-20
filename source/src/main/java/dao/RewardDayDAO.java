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
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password")) {

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

	public boolean insert(RewardDay rewardday) {
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

			String date;
			if (month >= 1 && month <= 9) {
				date = year + "-0" + month + "-%";
			} else {
				date = year + "-" + month + "-%";
			}

			String sql = "DELETE FROM reward_day WHERE user_id = ? AND date like ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, userId);

			pStmt.setString(2, date);
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
	
	//insertメソッド（引数userIdで指定した報酬日レコードを全て取得する）
	public List<String> select(String userId) {
		Connection conn = null;
		List<String> dateList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password");
			String sql = "SELECT date FROM reward_day WHERE user_id = ? ORDER BY date";
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

	public static void main(String[] args) {
		RewardDayDAO rewardDayDAO = new RewardDayDAO();
		RewardDay rewardDay = new RewardDay("kazutoshi_t", Date.valueOf("2025-06-16"), "人であふれた！");
		rewardDayDAO.insert(rewardDay);
		rewardDayDAO.delete("kazutoshi_t", 2025, 5);
	}
}
