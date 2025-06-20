package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AchievementPointDAO;
import dao.HealthRecordDAO;
import dao.PointDAO;
import dao.RewardDayDAO;
import dao.TargetValueDAO;
import dao.UserDAO;
import dao.WorldTourDAO;
import dto.AchievementPoint;
import dto.HealthAlcohol;
import dto.HealthExercise;
import dto.HealthWhole;
import dto.Point;
import dto.Result;
import dto.RewardDay;
import dto.TargetValue;

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
		String date = (String) request.getAttribute("date");
//		String date = "2025-05-28";
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
		//ポップアップから健康記録した際は、カレンダーページに戻るようにする。
		String fromCalendar = request.getParameter("fromCalendar");
		String backPage = "HomeServlet"; // デフォルトはホーム

		if ("true".equals(fromCalendar)) {
		    backPage = "calendar.jsp";
		}

		request.setAttribute("result", new Result("登録完了", "健康記録を登録しました", backPage));
		
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
			double calorieConsu = mets * nowWeight * (exerciseTime / 60.0) * 1.05;
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

		// ポイント獲得処理 目標値達成しているかの処理(たぶんif文めっちゃ使う) 目標値達成した項目のポイントだけ更新 PointDaoのアップデートを使う
		// 年と月と日を取得
		String tempYear = date.split("-")[0];
		int year = Integer.parseInt(tempYear);
		String tempMonth = date.split("-")[1];
		int month = Integer.parseInt(tempMonth);
		
		TargetValueDAO tvDao = new TargetValueDAO();
		TargetValue tv = new TargetValue();
		// 目標値を取得
		tv = tvDao.getTargetValueByUserId(userId);

		// 今現在の累計ポイントを取得する
		PointDAO pDao = new PointDAO();
		Point point = new Point();
		point = pDao.selectByUserIdMonth(userId, month);
		// 飲酒ポイントの処理
		// まず合計純アルコール摂取量を取得する
		double totalPureAlcConsu = 0;
		for (HealthAlcohol ha : haList) {
			totalPureAlcConsu += ha.getPureAlcoholConsumed();
		}
		// 目標値を達成していたら累計報酬ポイント（飲酒）に10足す
		boolean alcoholPointChanged = false;
		if (totalPureAlcConsu < tv.getPure_alcohol_consumed()) {
			alcoholPointChanged = true;
			pDao.updateTotalAlcoholConsumed(userId, year, month, point.getTotal_alcohol_consumed() + 10);
		}

		// 睡眠時間ポイントの処理
		// 目標値を達成していたら累計報酬ポイント（顔色）に1足す
		int sleepPointChanged = 0;
		if (sleepHours >= tv.getSleep_time() - 1 && sleepHours <= tv.getSleep_time() + 1) {
			if (point.getTotal_sleeptime() < 5) {
				sleepPointChanged = 1;
				pDao.updateTotalSleeptime(userId, year, month, point.getTotal_sleeptime() + 1);
			}
		} else {
			if (point.getTotal_sleeptime() > 1) {
				sleepPointChanged = -1;
				pDao.updateTotalSleeptime(userId, year, month, point.getTotal_sleeptime() - 1);
			}
		}

		// 摂取カロリーのポイントの処理
		// 目標値を達成していたら累計報酬ポイント（食事）に10を足す
		// 現在体重が目標体重＋1kgより多い場合、目標摂取カロリー-500～目標摂取カロリーにおさまっていればポイントゲット
		boolean caloriePointChanged = false;
		if (nowWeight >= tv.getTarget_weight() + 1) {
			if (calorieIntake >= tv.getCalorie_intake() - 500 && calorieIntake <= tv.getCalorie_intake()) {
				pDao.updateTotalCalorieIntake(userId, year, month, point.getTotal_calorie_intake() + 10);
				caloriePointChanged = true;
			}
		}
		// 現在体重が目標体重-1kgより少ない場合、目標摂取カロリー～目標摂取カロリー+500におさまっていればポイントゲット
		else if (nowWeight <= tv.getTarget_weight() - 1) {
			if (calorieIntake >= tv.getCalorie_intake() && calorieIntake <= tv.getCalorie_intake() + 500) {
				pDao.updateTotalCalorieIntake(userId, year, month, point.getTotal_calorie_intake() + 10);
				caloriePointChanged = true;
			}
		}
		// 現在体重が目標体重+-1以内の場合、目標摂取カロリー-300～目標摂取カロリー+300におさまっていればポイントゲット
		else {
			if (calorieIntake >= tv.getCalorie_intake() - 300 && calorieIntake <= tv.getCalorie_intake() + 300) {
				pDao.updateTotalCalorieIntake(userId, year, month, point.getTotal_calorie_intake() + 10);
				caloriePointChanged = true;
			}
		}

		// 禁煙ポイントの処理
		// 禁煙ポイントが７より小さい場合、禁煙できたら1を足す。喫煙したら0にする。
		int nosmokePointChanged = 0;
		if (noSmoke == 1) {
			if (point.getTotal_nosmoke() != 7) {
				pDao.updateTotalNosmoke(userId, year, month, point.getTotal_nosmoke() + 1);
				nosmokePointChanged = 1;
			}
		} else {
			pDao.updateTotalNosmoke(userId, year, month, 0);
			nosmokePointChanged = -1;

		}

		// 累計消費カロリーを更新
		// まず合計消費カロリーを取得する
		int totalCalorieConsu = 0;
		for (HealthExercise he : heList) {
			totalCalorieConsu += he.getCalorieConsu();
		}
		pDao.updateTotalCalorieConsumed(userId, year, month, point.getTotal_calorie_consumed() + totalCalorieConsu);
		
		/*
		 * 報酬受け取り処理 更新されたポイントの更新前と更新後のポイントと 各種達成ポイントテーブル(DAO作って)を比較して
		 * ポイントが達成していたら報酬受け取り処理を行う （RewardDayDAOのinsertを使う）
		 */
		Date date1 = Date.valueOf(date);
		RewardDayDAO RDDao = new RewardDayDAO();
		// 飲酒ポイントの報酬（建物）の受け取り処理
		if (alcoholPointChanged) {
			AchievementPointDAO apDao = new AchievementPointDAO();
			List<AchievementPoint> apList = apDao.selectAlcohol();
			if ((point.getTotal_alcohol_consumed() + 10) == apList.get(0).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "1つ目の建物が建った！");
				RDDao.insert(rewardDay);
			} else if ((point.getTotal_alcohol_consumed() + 10) == apList.get(1).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "2つ目の建物が建った！");
				RDDao.insert(rewardDay);
			} else if ((point.getTotal_alcohol_consumed() + 10) == apList.get(2).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "3つ目の建物が建った！");
				RDDao.insert(rewardDay);
			}
		}

		// 睡眠時間ポイントの報酬（顔色）の受け取り処理
		if (sleepPointChanged == 1) {
			RewardDay rewardDay = new RewardDay(userId, date1, "顔色が良くなった！");
			RDDao.insert(rewardDay);
		} else if (sleepPointChanged == -1) {
			RewardDay rewardDay = new RewardDay(userId, date1, "顔色が悪くなった！");
			RDDao.insert(rewardDay);
		} else if (sleepPointChanged == 0) {
			// 何もしない
		}

		// 摂取カロリーポイントの報酬（衣服）の受け取り処理
		if (caloriePointChanged) {
			AchievementPointDAO apDao = new AchievementPointDAO();
			List<AchievementPoint> apList = apDao.selectEat();
			if ((point.getTotal_calorie_intake() + 10) == apList.get(0).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "服を受け取った！");
				RDDao.insert(rewardDay);
			} else if ((point.getTotal_calorie_intake() + 10) == apList.get(1).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "靴を受け取った！");
				RDDao.insert(rewardDay);
			} else if ((point.getTotal_calorie_intake() + 10) == apList.get(2).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "帽子を受け取った！");
				RDDao.insert(rewardDay);
			} else if ((point.getTotal_calorie_intake() + 10) == apList.get(3).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "民族衣装を受け取った！");
				RDDao.insert(rewardDay);
			}
		}

		// 禁煙ポイントの報酬（人）の受け取り処理
		if (nosmokePointChanged == 1) {
			AchievementPointDAO apDao = new AchievementPointDAO();
			List<AchievementPoint> apList = apDao.selectSmoke();
			if ((point.getTotal_nosmoke() + 1) == apList.get(apList.size() - 1).getAchievementPoint()) {
				RewardDay rewardDay = new RewardDay(userId, date1, "人であふれた！");
				RDDao.insert(rewardDay);
			} else {
				RewardDay rewardDay = new RewardDay(userId, date1, "人が増えた！");
				RDDao.insert(rewardDay);
			}
		} else if (nosmokePointChanged == -1) {
			RewardDay rewardDay = new RewardDay(userId, date1, "人がいなくなった！");
			RDDao.insert(rewardDay);
		} else if (nosmokePointChanged == 0) {
			// 何もしない
		}

		// 消費カロリーポイントの報酬の受け取り処理
		int shoPrevious = point.getTotal_calorie_consumed() / 3600;
		int shoToday = (point.getTotal_calorie_consumed()+ totalCalorieConsu) / 3600;
		if (shoPrevious != shoToday) {
			WorldTourDAO WTDao = new WorldTourDAO();
			String country = WTDao.select(point.getTotal_calorie_consumed() + totalCalorieConsu);
			RewardDay rewardDay = new RewardDay(userId, date1, country + "に到達した！");
			RDDao.insert(rewardDay);
		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		dispatcher.forward(request, response);
	}

}
