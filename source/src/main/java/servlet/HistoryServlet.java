package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HealthRecordDAO;
import dao.HistoryDAO;
import dao.RewardDayDAO;
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

		String userId = "kazutoshi_t";
		/*
		 * 今日の西暦と月から、12か月以上前の健康記録と報酬達成日のレコードをそれぞれのテーブルから削除する HealthRecordDAOのdelete(),
		 * RewardDayDAOのdelete()を使用
		 */
		// 健康記録の削除
		LocalDate today = java.time.LocalDate.now(); // 今日の日付を取得
		// 2025-06-19
		HealthRecordDAO hrDao = new HealthRecordDAO();
		List<String> dateList = hrDao.select(userId); // 全ての健康記録の日付だけ古い順に取得
		Date oldestDayTmp = Date.valueOf(dateList.get(0));// 一番古い日付を取得 2024-04-01
		LocalDate oldestDay = oldestDayTmp.toLocalDate();

		// 今日の年月を取得
		int todayYear = today.getYear();// 2025
		int todayMonth = today.getMonthValue();// 6
		// 一番古い健康記録データの年月を取得
		int oldestDayYear = oldestDay.getYear();// 2024
		int oldestDayMonth = oldestDay.getMonthValue();// 4
		int gap = 0;

		if (todayYear - oldestDayYear >= 1) {
			gap = 12 + (todayMonth - oldestDayMonth);
		}
		if (gap >= 12) {
			int loopCount = gap - 12 + 1;
			for (int i = 0; i < loopCount; i++) {
				if (oldestDayMonth + i > 12) {
					hrDao.delete(userId, oldestDayYear + 1, oldestDayMonth + i - 12);
				} else {
					hrDao.delete(userId, oldestDayYear, oldestDayMonth + i);
				}
			}
		}

		// 報酬達成日のレコードを削除
		RewardDayDAO rdDao = new RewardDayDAO();
		List<String> dateList2 = rdDao.select(userId); // 全ての健康記録の日付だけ古い順に取得
		Date oldestDayTmp2 = Date.valueOf(dateList2.get(0));// 一番古い日付を取得 2024-04-01
		LocalDate oldestDay2 = oldestDayTmp2.toLocalDate();
		// 一番古い報酬達成日の年月を取得
		int oldestDayYear2 = oldestDay2.getYear();// 2024
		int oldestDayMonth2 = oldestDay2.getMonthValue();// 4
		if (todayYear - oldestDayYear2 >= 1) {
			gap = 12 + (todayMonth - oldestDayMonth2);
		}
		//削除工程
		if (gap >= 12) {
			int loopCount = gap - 12 + 1;
			for (int i = 0; i < loopCount; i++) {
				if (oldestDayMonth2 + i > 12) {
					rdDao.delete(userId, oldestDayYear2 + 1, oldestDayMonth2 + i - 12);
				} else {
					rdDao.delete(userId, oldestDayYear2, oldestDayMonth2 + i);
				}
			}
		}

		/*
		 * 今日の西暦と月から、12か月以上前のポイントのレコードをテキストファイルとして書き出し、テーブルから削除する 書き出すパスは
		 * "history/ユーザーID/西暦-月.txt" (ユーザーIDと西暦と月は適切な名前にする) 削除はPointDAOのdelete()を使用
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

		// ヘルプはjavascriptで表示!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	}
}