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

//SQLの繋ぎ方うまくできなかったからchatGPTにここきいちゃったごめん泣
    private static final String DB_URL = "jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    // 服の画像
    public Image getClothImage(int stage) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
    	
        String sql = "SELECT image_path FROM reward_cloth WHERE stage = ? AND country_order = 0 ORDER BY id LIMIT 1";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            ps.setInt(1, stage);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	String imagePath = rs.getString("image_path");
                    System.out.println("Cloth image path: " + imagePath);
                    return new Image(rs.getString("image_path"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Image(null);
    }

    // 靴の画像
    public Image getShoeImage(int stage) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
        String sql = "SELECT image_path FROM reward_cloth WHERE id = 2";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	String imagePath = rs.getString("image_path");
                    System.out.println("Shoe image path: " + imagePath); 
                    return new Image(rs.getString("image_path"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Image(null);
    }

    // 帽子の画像
    public Image getHatImage(int stage) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
        String sql = "SELECT image_path FROM reward_cloth WHERE id = 3";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
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

    // 民族衣装の画像
    public Image getCountryCostumeImage(int countryOrder) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
        String sql = "SELECT image_path FROM reward_cloth WHERE stage = 4 AND country_order = ?";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
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
    
//    建物
    public List<Image> getBuildingImages(int stage, int countryOrder) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
        List<Image> images = new ArrayList<>();
        String sql = "SELECT image_path FROM reward_build WHERE stage <= ? AND country_order = ? ORDER BY stage";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, stage);
            ps.setInt(2, countryOrder);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    images.add(new Image(rs.getString("image_path")));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return images;
    }
    
//    顔色
    public Image getFaceImage(int stage) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントからステージを得る
        String sql = "SELECT image_path FROM reward_face WHERE stage = ?";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, stage);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String imagePath = rs.getString("image_path");
                    System.out.println("Face image path: " + imagePath);
                    return new Image(imagePath);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Image(null);
    }
    
//    人の写真
    public Image getPeopleImage(int countryOrder) {
    	// TODO:達成ポイントテーブルと引数の累計ポイントから人数を得る
        String sql = "SELECT image_path FROM reward_people WHERE country_order = ?";
        try (
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ps.setInt(1, countryOrder);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String imagePath = rs.getString("image_path");
                    System.out.println("People image path: " + imagePath);
                    return new Image(imagePath);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Image(null);
    }
}
