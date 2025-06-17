package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import dto.History;



public class HistoryDAO {

	public List<History> select(String userId) {
        Connection conn = null;
        List<History> hrlist = new ArrayList<>();
		String sql = "SELECT * FROM history WHERE user_id=?";

		// ここに処理を書く
		
		return hrlist;
    }  
	
//    public List<History> selectByDate(String userId, int year, int month) {
//        Connection conn = null;
//        List<History> hrlist = new ArrayList<>();
//		String sql = "SELECT * FROM history WHERE user_id=? AND date=?";
//
//		// ここに処理を書く
//		
//		return hrlist;
//    }    
}
