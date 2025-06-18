package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorldTourDAO {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";
    
	public String select(int totalCalorieConsu) {
		String countryName = null;
        String sql = "SELECT * FROM world_tour WHERE country_order = ?";
        ImageDAO imageDao = new ImageDAO();
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, imageDao.getCountryOrder(totalCalorieConsu));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	countryName = rs.getString("country");
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return countryName;
	}
	
	public static void main(String[] args) {
		WorldTourDAO wtDAO = new WorldTourDAO();
		String countryName = wtDAO.select(0);
		System.out.println(countryName);
		countryName = wtDAO.select(100);
		System.out.println(countryName);
		countryName = wtDAO.select(3700);
		System.out.println(countryName);
		countryName = wtDAO.select(7300);
		System.out.println(countryName);
		countryName = wtDAO.select(10900);
		System.out.println(countryName);
	}
}
