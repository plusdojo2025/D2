package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.HealthRecordDAO;
import dao.ImageAllDAO;
import dao.ImageDAO;
import dao.PointDAO;
import dao.RewardDayDAO;
import dto.HealthAlcohol;
import dto.HealthRecord;
import dto.Point;
import dto.RewardDay;
import dto.TownAvatarElements;

@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {

	private boolean test = false;
	private String testUserId = "kazutoshi_t"; // テスト用のユーザID

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId;
		int month;
		int year;

		if(this.test){
			session.setAttribute("user_id", testUserId);
		}

		if (session.getAttribute("user_id") == null) {
			// もしもログインしていなかったらログインサーブレットにリダイレクト
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		userId = (String) session.getAttribute("user_id");
		
		try {
			month = Integer.parseInt(request.getParameter("month"));
			year = Integer.parseInt(request.getParameter("year"));
		} catch (Exception e) {
			month = java.time.LocalDate.now().getMonthValue(); // デフォルトは今月
			year = java.time.LocalDate.now().getYear(); // デフォルトは今年
		}
		request.setAttribute("displayMonth", month);
		request.setAttribute("displayYear", year);
		
		// 達成した報酬をDBから持ってくる
		RewardDayDAO rewardDao = new RewardDayDAO();
		List<RewardDay> rewardList = rewardDao.select(userId, month);
		request.setAttribute("rewardList", rewardList);

		// 健康記録をDBから持ってくる
		HealthRecordDAO healthDao = new HealthRecordDAO();
//		List<HealthRecord> healthList = healthDao.select(userId, month);
//		request.setAttribute("healthList", healthList);
		List<HealthRecord> healthList = healthDao.selectWithExercises(userId, month);

		// アルコールつきHealthRecordを取得
		List<HealthRecord> alcoholList = healthDao.selectWithAlcohols(userId, month);

		// alcoholList の情報を healthList にマージ
		for (HealthRecord record : healthList) {
		    for (HealthRecord alcRecord : alcoholList) {
		        if (record.getDate().equals(alcRecord.getDate())) {
		            record.setAlcoholList(alcRecord.getAlcoholList()); // アルコールリストだけ上書き
		            break;
		        }
		    }
		}

		request.setAttribute("healthList", healthList);
		
		// 日付指定があれば、その日の記録を1件だけ取り出してスコープに入れる
		String date = request.getParameter("date");  // 例: 2025-06-15
		if (date != null && !date.isEmpty()) {
		    for (HealthRecord record : healthList) {
		        if (date.equals(record.getDate())) {
		            request.setAttribute("record", record);  // ← これがJSPで使える
		            break;
		        }
		    }
		}

		/*
		 * カレンダーに表示する統計値の計算処理 持ってきた健康記録のリストをもとに統計値を計算しリクエストスコープに格納
		 */
		double totalCalorieConsumed = 0;
		int totalNosmokeDays = 0;
		double totalPureAlcohol = 0;
		double totalSleep = 0;
		double totalCalorieIntake = 0;

		int recordCount = 0;

		for (HealthRecord record : healthList) {
		    totalCalorieConsumed += record.getCalorieConsu();
		    totalCalorieIntake += record.getCalorieIntake();
		    totalSleep += record.getSleepHours();
		    if (record.getAlcoholList() != null) {
		        for (HealthAlcohol alc : record.getAlcoholList()) {
		            totalPureAlcohol += alc.getPureAlcoholConsumed();
		        }
		    }


		    if (record.getNosmoke() > 0) {
		        totalNosmokeDays++;
		    }

		    recordCount++;
		}

		// 統計を算出
		double avgPureAlcohol = recordCount > 0 ? totalPureAlcohol / recordCount : 0;
		double avgSleep = recordCount > 0 ? totalSleep / recordCount : 0;
		double avgConsumed = recordCount > 0 ? totalCalorieConsumed / recordCount : 0;
		double avgIntake = recordCount > 0 ? totalCalorieIntake / recordCount : 0;

		// スコープに格納
		request.setAttribute("sumCalorieConsumed", totalCalorieConsumed);
		request.setAttribute("sumNosmokeDays", totalNosmokeDays);
		request.setAttribute("avgPureAlcohol", avgPureAlcohol);
		request.setAttribute("avgSleep", avgSleep);
		request.setAttribute("avgConsumed", avgConsumed);
		request.setAttribute("avgIntake", avgIntake);
		
	
		

		// PointDAOでユーザーのポイントを取得
		
		PointDAO pointDAO = new PointDAO();
		Point point = pointDAO.selectByUserIdMonth(userId, month);

		int yearP = 0;
		int monthP = 0;
		int caloriePoint = 0;
		int alcoholPoint = 0;
		int sleepPoint = 0;
		int noSmokePoint = 0;
		int totalCalorieConsu = 0;

		if (point != null) {
			yearP = point.getYear();
			monthP = point.getMonth();
		    caloriePoint = point.getTotal_calorie_intake();
		    alcoholPoint = point.getTotal_alcohol_consumed();
		    sleepPoint = point.getTotal_sleeptime();
		    noSmokePoint = point.getTotal_nosmoke();
		    totalCalorieConsu = point.getTotal_calorie_consumed();
		}
		
		
		

		// ImageAllDAOを使って画像情報を取得
		ImageAllDAO imageAllDAO = new ImageAllDAO();
		
		int countryOrder = ImageDAO.getCountryOrder(totalCalorieConsu);
		
		TownAvatarElements avatar = imageAllDAO.select(yearP, monthP, caloriePoint, alcoholPoint, sleepPoint, noSmokePoint, countryOrder);

		// JSPにセットしてフォワード

		


		// リクエストスコープにセット
		request.setAttribute("avatar", avatar);
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}