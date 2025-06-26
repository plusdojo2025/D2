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
import javax.servlet.http.HttpSession;

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

@WebServlet("/HealthRecordServlet")
public class HealthRecordServlet extends HttpServlet {

	private boolean test = false;
	private String testUserId = "kazutoshi_t"; // テスト用のユーザID
	private String testDate = "2024-05-20"; // テスト用の日付

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String date;

		if (!this.test) {
			date = (String) request.getParameter("date");
		} else {
			date = testDate; 
			session.setAttribute("user_id", testUserId);
		}

		if (session.getAttribute("user_id") == null) {
			// もしもログインしていなかったらログインサーブレットにリダイレクト
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		// スコープに日付をセット
		request.setAttribute("date", date);

		// 健康記録登録ページにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/healthRecord.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 変数宣言
		String userId;
		String date = request.getParameter("date");
		double sleepHours = Double.parseDouble(request.getParameter("sleep_hours"));
		int calorieIntake = Integer.parseInt(request.getParameter("calorie_intake"));
		String free = request.getParameter("free");
		int noSmoke = Integer.parseInt(request.getParameter("no_smoke"));
		double nowWeight = Double.parseDouble(request.getParameter("now_weight"));
		if (!test) {
			// セッションからユーザIDを取得
			HttpSession session = request.getSession();
			userId = (String) session.getAttribute("user_id");
		} else {
			userId = testUserId; // テスト用のユーザIDをセット
		}
		HealthWhole hw = new HealthWhole(userId, date, noSmoke, sleepHours, calorieIntake, free, nowWeight);
		List<HealthExercise> heList = new ArrayList<HealthExercise>();
		List<HealthAlcohol> haList = new ArrayList<HealthAlcohol>();

		HealthRecordDAO HRDao = new HealthRecordDAO(); // 健康記録登録用DAO
		UserDAO uDao = new UserDAO(); // ユーザの現在体重更新用DAO

		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);

		// リクエストパラメータから運動記録を取得し、リストに格納
		int heIndex = 0;
		while ((request.getParameter("exercise_type" + heIndex) != null)
				&& (request.getParameter("mets" + heIndex) != "")
				&& (request.getParameter("exercise_time" + heIndex) != "")) {
			String exerciseType = request.getParameter("exercise_type" + heIndex);
			double mets = Double.parseDouble(request.getParameter("mets" + heIndex));
			int exerciseTime = Integer.parseInt(request.getParameter("exercise_time" + heIndex));
			double calorieConsu;

			// 消費カロリー(=メッツ*体重kg*運動時間*1.05)を計算
			calorieConsu = mets * nowWeight * (exerciseTime / 60.0) * 1.05;
			// リストに格納
			heList.add(new HealthExercise(userId, date, calorieConsu, exerciseType, exerciseTime));
			heIndex++;
		}

		// リクエストパラメータから飲酒記録を取得し、リストに格納
		int haIndex = 0;
		while ((request.getParameter("alcohol_content" + haIndex) != null)
				&& (request.getParameter("alcohol_consumed" + haIndex)!="")) {
			double alcoholContent = Double.parseDouble(request.getParameter("alcohol_content" + haIndex));
			int alcoholConsumed = Integer.parseInt(request.getParameter("alcohol_consumed" + haIndex));
			double pureAlcoholConsumed;

			// 純アルコール量(=摂取量[ml] * アルコール濃度[%] * 0.8)を計算
			pureAlcoholConsumed = alcoholConsumed * (alcoholContent / 100.0) * 0.8;
			// リストに格納
			haList.add(new HealthAlcohol(userId, date, alcoholContent, alcoholConsumed, pureAlcoholConsumed));
			haIndex++;
		}

		// 健康記録登録・現在の体重登録
	    String referer = request.getHeader("Referer"); // リファラ（送信元）のURLを取得

	    if (referer == null || referer.isEmpty() || referer.contains("HealthRecord")) {
	    	// リファラがない場合はデフォルトのページにリダイレクト
	    	referer = request.getContextPath() + "/HomeServlet";
	    } 
	    
		if (HRDao.insert(hw, haList, heList)) { // 登録成功
			request.setAttribute("result", new Result("登録成功！", "レコードを登録しました。", referer));
			uDao.updateWeight(userId, nowWeight);
		} else { // 登録失敗!
			request.setAttribute("result", new Result("登録失敗！", "レコードを登録できませんでした。", referer));
			// 結果ページにフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// ポイント獲得処理 目標値達成しているかの処理
		// 目標値達成した項目のポイントだけ更新
		TargetValueDAO tvDao = new TargetValueDAO();
		TargetValue tv = tvDao.getTargetValueByUserId(userId); // 目標値を取得
		PointDAO pDao = new PointDAO();
		Point point = pDao.selectByUserIdMonth(userId, month); // 今現在のポイントを取得

		double totalPureAlcConsu = 0; // 合計純アルコール摂取量
		for (HealthAlcohol ha : haList) {
			totalPureAlcConsu += ha.getPureAlcoholConsumed();

		}
		int totalCalorieConsu = 0; // 合計消費カロリー
		for (HealthExercise he : heList) {
			totalCalorieConsu += he.getCalorieConsu();
		}

		boolean alcoholPointChanged = false;
		int sleepPointChanged = 0;
		boolean caloriePointChanged = false;
		int nosmokePointChanged = 0;

		// 飲酒ポイントの処理
		// 目標値を達成していたら累計報酬ポイント（飲酒）に10足す
		if (totalPureAlcConsu < tv.getPure_alcohol_consumed()) {
			alcoholPointChanged = true;
			pDao.updateTotalAlcoholConsumed(userId, year, month, point.getTotal_alcohol_consumed() + 10);
		}

		// 睡眠時間ポイントの処理
		// 目標値を達成していたら累計報酬ポイント（顔色）に1足す
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
		int shoToday = (point.getTotal_calorie_consumed() + totalCalorieConsu) / 3600;
		if (shoPrevious != shoToday) {
			WorldTourDAO WTDao = new WorldTourDAO();
			String country = WTDao.select(point.getTotal_calorie_consumed() + totalCalorieConsu);
			RewardDay rewardDay = new RewardDay(userId, date1, country + "に到達した！");
			RDDao.insert(rewardDay);
		}

		// 報酬受け取り処理 更新されたポイントの更新前と更新後のポイントと 各種達成ポイントテーブル(DAO作って)を比較して

		// ポイントが達成していたら報酬受け取り処理を行う （RewardDayDAOのinsertを使う）

		// Date date1 = Date.valueOf(date);

		// RewardDayDAO RDDao = new RewardDayDAO();

		if (alcoholPointChanged) {

			request.setAttribute("alcoholMessage", "飲酒ポイントが増加し、もらえました。");

		} else {

			request.setAttribute("alcoholMessage", "飲酒ポイントが増加せず、もらえませんでした。");

		}

		if (sleepPointChanged == 1) {

			request.setAttribute("sleepMessage", "睡眠ポイントが増加し、もらえました。");

		} else if (sleepPointChanged == -1) {

			request.setAttribute("sleepMessage", "睡眠ポイントが減少し、もらえませんでした。");

		} else {

			request.setAttribute("sleepMessage", "睡眠ポイントは変更されませんでした。");

		}

		if (caloriePointChanged) {

			request.setAttribute("calorieMessage", "摂取カロリーポイントが増加し、もらえました。");

		} else {

			request.setAttribute("calorieMessage", "摂取カロリーポイントが増加せず、もらえませんでした。");

		}

		if (nosmokePointChanged == 1) {

			request.setAttribute("nosmokeMessage", "禁煙ポイントが増加し、もらえました。");

		} else if (nosmokePointChanged == -1) {

			request.setAttribute("nosmokeMessage", "禁煙ポイントが減少し、もらえませんでした。");

		} else {

			request.setAttribute("nosmokeMessage", "禁煙ポイントは変更されませんでした。");

		}

		// 結果ページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
		dispatcher.forward(request, response);
	}

}
