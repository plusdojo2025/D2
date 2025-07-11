package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.User;
//ここからserectの文!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
public class UserDAO {
	// 引数で指定されたidpwでログイン成功ならtrueを返す
	public boolean isLoginOK(User idpw) {
		Connection conn = null;
		boolean loginResult = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SELECT文を準備する
			String sql = "SELECT count(*) FROM user WHERE id=? AND pw=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, idpw.getId());
			pStmt.setString(2, idpw.getPw());

			// SELECT文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// ユーザーIDとパスワードが一致するユーザーがいれば結果をtrueにする
			rs.next();
			if (rs.getInt("count(*)") == 1) {
				loginResult = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			loginResult = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			loginResult = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					loginResult = false;
				}
			}
		}

		// 結果を返す
		return loginResult;
	}
 //ここからinsertの文!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean insert(User user) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// INSERT文を準備する
			String sql = "INSERT INTO user (id, pw) VALUES (?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, user.getId());
			pStmt.setString(2, user.getPw());

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
	
	/*
	 *  updataWeight()
	 *  	健康記録登録のたびに現在の体重を更新
	 */
	public boolean updateWeight(String userID, double weight) {
		Connection conn = null;
		boolean result = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// UPDATE文を準備する
			String sql = "UPDATE user SET weight=? WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setDouble(1, weight);
			pStmt.setString(2, userID);

			// UPDATE文を実行
			int rowsUpdated = pStmt.executeUpdate();
			if (rowsUpdated > 0) {
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
	
	/* updateProfile()
	 * 		毎月の目標値設定のたびにユーザプロフィールを更新
	 */
	public boolean updateProfile(
			String userID, 
			double weight,
			double height,
			Character sex,
			int age,
			int activeLevelID
			) {
		Connection conn = null;
		boolean result = false;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// UPDATE文を準備する
			String sql = "UPDATE user SET"
					+ " weight=?, height=?, sex=?, age=?, act_level_id=?"
					+ " WHERE id=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setDouble(1, weight);
			pStmt.setDouble(2, height);
			pStmt.setString(3, sex.toString());
			pStmt.setInt(4, age);
			pStmt.setInt(5, activeLevelID);
			pStmt.setString(6, userID);

			// UPDATE文を実行
			int rowsUpdated = pStmt.executeUpdate();
			if (rowsUpdated > 0) {
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
		UserDAO userDAO = new UserDAO();
//		userDAO.updateProfile("kazutoshi_t", 70.0, 170.0, '男', 53, 1);
		userDAO.updateWeight("kazutoshi_t", 69.0);
	}
}

