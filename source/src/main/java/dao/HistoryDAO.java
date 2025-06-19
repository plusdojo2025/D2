package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.History;

public class HistoryDAO {

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
                h.setFileName(rs.getString("file_path"));//---
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


