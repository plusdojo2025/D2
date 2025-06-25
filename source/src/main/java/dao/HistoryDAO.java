package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.History;

public class HistoryDAO {

	public List<History> select(String userId) {
		Connection conn = null;
		List<History> hrlist = new ArrayList<>();
		String sql = "SELECT * FROM history WHERE user_id=?";
		// ここから処理を書く
		// eclipse、Dao user_idでsqlのHistoryからそのidのデータをすべてを取得するプログラム
		try {
			// JDBCドライバをロード
			Class.forName("com.mysql.cj.jdbc.Driver");

			// DB接続
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				History h = new History();

				h.setId(rs.getString("user_id"));
				h.setYear(rs.getInt("year"));
				h.setMonth(rs.getInt("month"));
				h.setFileName(rs.getString("file_path"));// ---
				// userIdフィールドがある場合
				// h.setUserId(rs.getInt("user_id"));

				hrlist.add(h);
			}

			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hrlist;
	}

	public boolean insert(History history) {
		Connection conn = null;
		boolean result = false;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/d2?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// INSERT文を準備する
			String sql = "INSERT INTO history VALUES (?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, history.getUserId());
			pStmt.setInt(2, history.getYear());
			pStmt.setInt(3, history.getMonth());
			pStmt.setString(4, history.getFileName());

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
		HistoryDAO historyDao = new HistoryDAO();
		History hr = new History("kazutoshi_t", 2021, 4, "history/kazutoshi_t/2024-6.txt");
		historyDao.insert(hr);
	}
}
