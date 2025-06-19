package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AchievementPoint;

public class AchievementPointDAO {	
	public List<AchievementPoint> selectEat() {
		return this.selectCommon("eat");
	}

	public List<AchievementPoint> selectAlcohol() {
		return this.selectCommon("alcohol");
	}

	public List<AchievementPoint> selectSleep() {
		return this.selectCommon("sleep");
	}

	public List<AchievementPoint> selectSmoke() {
		List<AchievementPoint> apList = new ArrayList<AchievementPoint>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM point_smoke ORDER BY achievement_point";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				AchievementPoint ap = new AchievementPoint(rs.getInt("people"), rs.getInt("achievement_point"));
				apList.add(ap);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

		return apList;

	}

	
	private List<AchievementPoint> selectCommon(String mode) {
		// mode is "eat" or "alcohol" or "sleep"
		List<AchievementPoint> apList = new ArrayList<AchievementPoint>();
		Connection conn = null;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SQL文を準備する
			String sql = "SELECT * FROM point_" + mode + " ORDER BY achievement_point";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			// SQL文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// 結果表をコレクションにコピーする
			while (rs.next()) {
				AchievementPoint ap = new AchievementPoint(rs.getInt("stage"), rs.getInt("achievement_point"));
				apList.add(ap);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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

		return apList;
	}
	
	public static void main(String[] args) {
		AchievementPointDAO achievementPointDAO = new AchievementPointDAO();
//		List<AchievementPoint> apList = achievementPointDAO.selectEat();
//		List<AchievementPoint> apList = achievementPointDAO.selectSleep();
		List<AchievementPoint> apList = achievementPointDAO.selectAlcohol();
		//List<AchievementPoint> apList = achievementPointDAO.selectSmoke();
		
		for (AchievementPoint ap: apList) {
			System.out.println(ap.getAchievementPoint() + ", " + ap.getStage());
		}
	}

}