package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TargetValueDAO;
import dto.TargetValue;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/D2/LoginServlet");
//			return;
//		}
    	
    	// セッションからuser_idを取得
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // user_idがnullでない場合に目標値を取得
        if (userId != null) {
            TargetValueDAO dao = new TargetValueDAO();
            TargetValue targetValue = dao.getTargetValueByUserId(userId);
            request.setAttribute("targetValue", targetValue);
            
            // 累計消費カロリーから次の国までの歩数を計算して、リクエストスコープに格納
            
        }
        
        // 街並み・アバターを表示ための画像パスのリストを取得してリクエストスコープに格納
        

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher(("/WEB-INF/jsp/home.jsp"));
        dispatcher.forward(request, response);
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 目標値入力フォームからのPOST
		
		/*　目標値テーブルの更新
		 * 	TargetValueDAOのupdate
		 */
		
		/* 目標値入力フォームのプロフィール項目に記載された内容をもとに更新
		 * UserDAOのupdateProfile()
		 */
		
		// Pointテーブルのにその月のレコードを追加
		
	}

}