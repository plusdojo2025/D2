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
import dto.HealthRecord;
import dto.Point;
import dto.RewardDay;
import dto.TownAvatarElements;
/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalendarServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// もしもログインしていなかったらログインサーブレットにリダイレクトする
//		HttpSession session = request.getSession();
//		if (session.getAttribute("id") == null) {
//			response.sendRedirect("/D2/LoginServlet");
//			return;
//		}

		HttpSession session = request.getSession();
//		String userId = (String) session.getAttribute("user_id");
		String userId = "kazutoshi_t";
		
		
	

		String monthParam = request.getParameter("month");
		int month = 0;
		try {
			month = Integer.parseInt(monthParam);
		} catch (NumberFormatException | NullPointerException e) {
			month = java.time.LocalDate.now().getMonthValue(); // デフォルトは今月
		}

		
		// 達成した報酬をDBから持ってくる
		RewardDayDAO rewardDao = new RewardDayDAO();
		List<RewardDay> rewardList = rewardDao.select(userId, month); // TODO: UserID, 月を引数にする
		request.setAttribute("rewardList", rewardList);

		/*
		 * 健康記録をDBから持ってくる HealthRecordDAOのselectを使用 リクエストスコープに格納
		 */
		HealthRecordDAO healthDao = new HealthRecordDAO();
		List<HealthRecord> healthList = healthDao.select(userId, month);
		request.setAttribute("healthList", healthList);
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
		    totalPureAlcohol += record.getAlcoholContent() * record.getAlcoholConsumed() * 0.8 / 100.0;

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

		int caloriePoint = 0;
		int alcoholPoint = 0;
		int sleepPoint = 0;
		int noSmokePoint = 0;
		int totalCalorieConsu = 0;

		if (point != null) {
		    caloriePoint = point.getTotal_calorie_intake();
		    alcoholPoint = point.getTotal_alcohol_consumed();
		    sleepPoint = point.getTotal_sleeptime();
		    noSmokePoint = point.getTotal_nosmoke();
		    totalCalorieConsu = point.getTotal_calorie_consumed();
		}

		// ImageAllDAOを使って画像情報を取得
		ImageAllDAO imageAllDAO = new ImageAllDAO();
		
		int countryOrder = ImageDAO.getCountryOrder(totalCalorieConsu);
		
		TownAvatarElements avatar = imageAllDAO.select(caloriePoint, alcoholPoint, sleepPoint, noSmokePoint, countryOrder);

		// JSPにセットしてフォワード
		request.setAttribute("avatar", avatar);
		


		// リクエストスコープにセット
		request.setAttribute("avatar", avatar);
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}