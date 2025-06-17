package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HistoryDAO;
import dto.History;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* 今日の西暦と月から、12か月以上前の健康記録と報酬達成日のレコードをそれぞれのテーブルから削除する
		 * 	HealthRecordDAOのdelete(), RewardDayDAOのdelete()を使用
		 */
		
		/* 今日の西暦と月から、12か月以上前のポイントのレコードをテキストファイルとして書き出し、テーブルから削除する
		 *  書き出すパスは "history/ユーザーID/西暦-月.txt" (ユーザーIDと西暦と月は適切な名前にする)
		 *  削除はPointDAOのdelete()を使用
		 */
		
		// 過去ファイルのリストを取得
		HistoryDAO dao = new HistoryDAO();
        List<History> hrList = dao.select("kazutoshi_t");
        request.setAttribute("fileList", hrList);
        
		
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
		dispatcher.forward(request, response);
		
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ヘルプはjavascriptで表示!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		
		
	}
}