package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HealthRecordDAO;
import dao.UserDAO;
import dto.HealthAlcohol;
import dto.HealthExercise;
import dto.HealthWhole;
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
		/*
		 * HttpSession session = request.getSession(); if (session.getAttribute("id") ==
		 * null) { response.sendRedirect("/D2/LoginServlet"); return; }
		 */
//		String date = (String) request.getAttribute("date");
		String date = "2025-06-26";
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
		double sleepHours = 0.0;
		sleepHours = Double.parseDouble(request.getParameter("sleep_hours"));

		int calorieIntake = 0;
		calorieIntake = Integer.parseInt(request.getParameter("calorie_intake"));

		String free = request.getParameter("free");

		int noSmoke = 0;
		noSmoke = Integer.parseInt(request.getParameter("no_smoke"));
		// hwに格納
		HealthWhole hw = new HealthWhole(userId, date, noSmoke, sleepHours, calorieIntake, free);

		// 運動テーブル関係
		int nowWeight = 0;
		nowWeight = Integer.parseInt(request.getParameter("now_weight"));
		
		List<HealthExercise> heList = new ArrayList<HealthExercise>();
		int i = 1;
		while (request.getParameter("exercise_type" + i) != null) {
			String exerciseType = request.getParameter("exercise_type" + i);
			int exerciseTime = 0;

			exerciseTime = Integer.parseInt(request.getParameter("exercise_time" + i));

			// 消費カロリーを計算し、保持する（消費カロリー＝メッツ*体重kg*運動時間*1.05
			// jspで保持しておいた運動種類で記録しているメッツ量を持ってくる
			double mets = Double.parseDouble(request.getParameter("mets" + i));
			double calorieConsu = mets * nowWeight * (exerciseTime/60) * 1.05;
			// リストに格納
			HealthExercise he = new HealthExercise(userId, date, calorieConsu, exerciseType, exerciseTime);
			heList.add(he);
			i++;
		}

		List<HealthAlcohol> haList = new ArrayList<HealthAlcohol>();
		int m = 1;
		while (request.getParameter("alcohol_content" + m) != null) {
			double alcoholContent = 0.0;
			alcoholContent = Double.parseDouble(request.getParameter("alcohol_content" + m));

			int alcoholConsumed = 0;
			alcoholConsumed = Integer.parseInt(request.getParameter("alcohol_consumed" + m));

			// 純アルコール摂取量を計算し保持する。（(お酒に含まれる純アルコール量の算出式)
			// 摂取量(ml) × アルコール濃度（度数/100）× 0.8（アルコールの比重））
			double pureAlcoholConsumed = alcoholConsumed * (alcoholContent / 100.0) * 0.8;
			// リストに格納
			HealthAlcohol ha = new HealthAlcohol(userId, date, alcoholContent, alcoholConsumed, pureAlcoholConsumed);
			haList.add(ha);
			m++;
		}

		// 登録処理を行う
		HealthRecordDAO HRDao = new HealthRecordDAO();
		if (HRDao.insert(hw, haList, heList)) { // 登録成功
			request.setAttribute("result", new Result("登録成功！", "レコードを登録しました。", "/D2/HomeServlet"));
		} else { // 登録失敗!
			request.setAttribute("result", new Result("登録失敗！", "レコードを登録できませんでした。", "/D2/HomeServlet"));
		}

		// 現在の体重に入力された数字をもとにユーザテーブルの現在の体重を更新 UserDAOのupdataWeight()
		 UserDAO uDao = new UserDAO();
		 uDao.updateWeight(userId, nowWeight);

		 //ポイント獲得処理 目標値達成しているかの処理(たぶんif文めっちゃ使う) 目標値達成した項目のポイントだけ更新 PointDaoのアップデートを使う
		 //TargetValue tv = new TargetValue(userId);
		/*
		 * 報酬受け取り処理 更新されたポイントの更新前と更新後のポイントと 各種達成ポイントテーブル(DAO作って)を比較して
		 * ポイントが達成していたら報酬受け取り処理を行う （RewardDayDAOのinsertを使う）
		 */

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		dispatcher.forward(request, response);
	}

}
