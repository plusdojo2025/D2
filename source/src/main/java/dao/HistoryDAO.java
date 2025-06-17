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

    // DB接続情報（MySQLの場合）
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_db_name";
    private static final String DB_USER = "your_username";
    private static final String DB_PASS = "your_password";

    public List<History> getFilesByDate(String keyword) {
        List<History> list = new ArrayList<>();
        String sql = "SELECT * FROM files WHERE date LIKE ?";

        try {
            // JDBCドライバの読み込み（初回のみ）
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    History dto = new History();           // ✅ DTOを使う
                    dto.setId(rs.getInt("id"));
                    dto.setFileName(rs.getString("file_name"));
                    dto.setDate(rs.getString("date"));
                    dto.setImagePath(rs.getString("image_path"));
                    list.add(dto);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }
}
