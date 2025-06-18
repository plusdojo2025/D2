package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Image;

public class ImageDAO {

	// 接続情報はここにまとめておく
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
	    private static final String DB_USER = "root";
	    private static final String DB_PASS = "password";
	   // トータルカロリーからカントリーオーダーを判別する
	    public int getCountryOrder(int totalCalorieConsumed) {
	        int countryOrder = 0;
	        String sql = "SELECT country_order FROM world_tour WHERE necessary_calorie <= ? ORDER BY necessary_calorie DESC LIMIT 1";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, totalCalorieConsumed);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    countryOrder = rs.getInt("country_order");
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return countryOrder;
	    }
	   //ポイントからステージを判定する 
	    public int getEatStage(int caloriePoint) {
	        int stage = 0;
	        String sql = "SELECT stage FROM point_eat WHERE achievement_point <= ? ORDER BY achievement_point DESC LIMIT 1";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, caloriePoint);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    stage = rs.getInt("stage");
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        return stage;
	    }
//服の画像
	    public Image getClothImage(int stage) {
	        return getImageByStage(stage);
	    }

	    // 靴の画像
	    public Image getShoeImage(int stage) {
	        return getImageByStage(stage);
	    }

	    // 帽子の画像
	    public Image getHatImage(int stage) {
	        return getImageByStage(stage);
	    }

	    private Image getImageByStage(int stage) {
	        String sql = "SELECT image_path FROM reward_cloth WHERE stage = ? AND country_order = 0 LIMIT 1";
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, stage);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return new Image(rs.getString("image_path"));
	                }
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return new Image(null);
	    }

	    // 民族衣装の画像（stage=4 & countryOrder指定）
	    public Image getCountryCostumeImage(int countryOrder) {
	        String sql = "SELECT image_path FROM reward_cloth WHERE stage = 4 AND country_order = ?";
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, countryOrder);
	            
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return new Image(rs.getString("image_path"));
	                }
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return new Image(null);
	    }
	    //アルコールポイントに応じて建物のステージ判定
	    public int getAlcoholStage(int alcoholPoint) {
	        int stage = 0;
	        String sql = "SELECT stage FROM point_alcohol WHERE achievement_point <= ? ORDER BY achievement_point DESC LIMIT 1";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, alcoholPoint);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    stage = rs.getInt("stage");
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        return stage;
	    }
	 // 建物画像複数取得
	    public List<Image> getBuildingImages(int buildingStage, int countryOrder) {
	        List<Image> list = new ArrayList<>();
	        String sql = "SELECT image_path FROM reward_build WHERE stage <= ? AND country_order = ? ORDER BY id";
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, buildingStage);
	            ps.setInt(2, countryOrder);

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    list.add(new Image(rs.getString("image_path")));
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	 //睡眠ポイントからステージ判断
	    public int getSleepStage(int sleepPoint) {
	        int stage = 0;
	        String sql = "SELECT stage FROM point_sleep WHERE achievement_point <= ? ORDER BY achievement_point DESC LIMIT 1";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, sleepPoint);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    stage = rs.getInt("stage");
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        return stage;
	    }
	    // 顔色画像取得
	    public Image getFaceImage(int faceStage) {
	        String sql = "SELECT image_path FROM reward_face WHERE stage = ?";
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, faceStage);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return new Image(rs.getString("image_path"));
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return new Image(null);
	    }

	    //人数カウント（achievementポイントから）
	    public int getNoSmokingCount(int achievementPoint) {
	        int peopleCount = 0;
	        String sql = "SELECT people FROM point_smoke WHERE achievement_point <= ? ORDER BY achievement_point DESC LIMIT 1";

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, achievementPoint);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    peopleCount = rs.getInt("people");
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }

	        return peopleCount;
	    }
	 // 禁煙達成者の画像取得（1枚だけ、複製はJSP側で）
	    public Image getPeopleImage(int countryOrder) {
	        String sql = "SELECT image_path FROM reward_people WHERE country_order = ?";
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	             PreparedStatement ps = conn.prepareStatement(sql)) {

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            ps.setInt(1, countryOrder);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return new Image(rs.getString("image_path"));
	                }
	            }

	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return new Image(null);
	    }

}