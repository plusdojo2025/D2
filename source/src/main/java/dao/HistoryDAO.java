package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.History;

public class HistoryDAO {

    // DB接続情報（必要に応じて書き換えてください）
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_db_name?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "your_username";
    private static final String DB_PASS = "your_password";

    public List<History> select(String userId) {
        Connection conn = null;
        List<History> hrlist = new ArrayList<>();
        String sql = "SELECT * FROM history WHERE user_id=?";
        //ここから処理を書く
        //eclipse、Dao user_idでsqlのHistoryからそのidのデータをすべてを取得するプログラム
        try {
            // JDBCドライバをロード
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                History h = new History();
                
              
                h.setId(rs.getString("userid"));
                h.setYear(rs.getInt("year"));
                h.setMonth(rs.getInt("month"));
                h.setFileName(rs.getString("filepath"));
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
}


