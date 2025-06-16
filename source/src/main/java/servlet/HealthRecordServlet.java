package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HealthRecordDAO;
import dto.HealthRecord;
import dto.Result;

/**
 * Servlet implementation class HealthRecord
 */
@WebServlet("/HealthRecordServlet")
public class HealthRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		/*HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/D2/LoginServlet");
			return;
		}*/
//		String date = (String) request.getAttribute("date");
		String date = "2025-06-12";
		request.setAttribute("date", date);
		// 登録ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthRecord.jsp");
		dispatcher.forward(request, response);
		request.getAttribute("date");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得し、数値型で取りたいものは変換処理を行う。
		request.setCharacterEncoding("UTF-8");

		// いったんユーザーIDはkazutoshi_tということにしておく
		String userId = "kazutoshi_t";

		// JSPからの受け取り
		String date = request.getParameter("date");
		String exerciseType = request.getParameter("exercise_type");
		int exerciseTime = 0;
		try {
			exerciseTime = Integer.parseInt(request.getParameter("exercise_time"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		int nowWeight = 0;
		try {
			nowWeight = Integer.parseInt(request.getParameter("now_weight"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}
		// 消費カロリーを計算し、保持する（消費カロリー＝メッツ*体重kg*運動時間*1.05
		// jspで保持しておいた運動種類で記録しているメッツ量を持ってくる
		double mets = Double.parseDouble(request.getParameter("mets"));
		double calorieConsu = mets * nowWeight * exerciseTime * 1.05;

		int noSmoke = 0;
		try {
			noSmoke = Integer.parseInt(request.getParameter("no_smoke"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		double alcoholContent = 0.0;
		try {
			alcoholContent = Double.parseDouble(request.getParameter("alcohol_content"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		int alcoholConsumed = 0;
		try {
			alcoholConsumed = Integer.parseInt(request.getParameter("alcohol_consumed"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		// 純アルコール摂取量を計算し保持する。（(お酒に含まれる純アルコール量の算出式)
		// 摂取量(ml) × アルコール濃度（度数/100）× 0.8（アルコールの比重））
		double pureAlcoholConsumed = alcoholConsumed * (alcoholContent / 100.0) * 0.8;

		double sleepHours = 0.0;
		try {
			sleepHours = Double.parseDouble(request.getParameter("sleep_hours"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		int calorieIntake = 0;
		try {
			calorieIntake = Integer.parseInt(request.getParameter("calorie_intake"));
		} catch (NumberFormatException e) {
			// ログやデフォルト値処理
		}

		String free = request.getParameter("free");

		// 登録処理を行う
		HealthRecordDAO HRDao = new HealthRecordDAO();
		if (HRDao.insert(new HealthRecord(userId, date, exerciseType, exerciseTime, nowWeight, calorieConsu, noSmoke,
				alcoholContent, alcoholConsumed, pureAlcoholConsumed, sleepHours, calorieIntake, free))) {// 登録成功
			request.setAttribute("result", new Result("登録成功！", "レコードを登録しました。", "/D2/HomeServlet"));// メニューページでなく検索ページに戻す
		} else { // 登録失敗
			request.setAttribute("result", new Result("登録失敗！", "レコードを登録できませんでした。", "/D2/HomeServlet"));// メニューページでなく検索ページに戻す
		}
		
		/* 現在の体重に入力された数字をもとにユーザテーブルの現在の体重を更新
		 * UserDAOのupdataWeight()
		 */
		
		/* ポイント獲得処理
		 * 	目標値達成しているかの処理(たぶんif文めっちゃ使う)
		 * 	目標値達成した項目のポイントだけ更新
		 * 	PointDaoのアップデートを使う
		 * */
		
		/* 報酬受け取り処理
		 * 	更新されたポイントの更新前と更新後のポイントと
		 * 	各種達成ポイントテーブル(DAO作って)を比較して
		 * 	ポイントが達成していたら報酬受け取り処理を行う
		 * 		（RewardDayDAOのinsertを使う）
		 */
		
		
		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		dispatcher.forward(request, response);
	}

}
