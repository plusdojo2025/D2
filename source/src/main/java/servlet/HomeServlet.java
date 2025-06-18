package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PointDAO;
import dao.TargetValueDAO;
import dao.UserDAO;
import dto.Point;
import dto.TargetValue;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// もしもログインしていなかったらログインサーブレットにリダイレクトする
		HttpSession session = request.getSession();
		
		// セッションからuser_idを取得
		String userId = (String) session.getAttribute("user_id");
//		String userId = "kazutoshi_t";

		// user_idがnullでない場合に目標値を取得
		if (userId != null) {
			// === 現在の年月を取得 ===
			java.time.LocalDate now = java.time.LocalDate.now();
			int currentYear = now.getYear();
			int currentMonth = now.getMonthValue();
//			int currentMonth = 8;

			PointDAO pointDAO = new PointDAO();

			// === 今月のレコードがあるか確認 ===
			Point searchPoint = new Point();
			searchPoint.setUser_id(userId);
			searchPoint.setMonth(currentMonth);

			List<Point> existingPoints = pointDAO.select(searchPoint);

			if (existingPoints == null || existingPoints.isEmpty()) {
				// === ない場合、前月までの累計カロリーを引き継いでINSERT ===
				List<Point> allPoints = pointDAO.selectByUserId(userId);
				int latestCalorie = 0;

				if (allPoints != null && !allPoints.isEmpty()) {
					Point latestPoint = allPoints.get(allPoints.size() - 1);
					latestCalorie = latestPoint.getTotal_calorie_consumed();
				}

				boolean inserted = pointDAO.insert(userId, currentMonth, currentYear, latestCalorie);

				if (!inserted) {
					request.setAttribute("errorMsg", "今月のポイントレコードの作成に失敗しました。");
				}
			}

			// === 通常の目標値とポイントデータの取得処理 ===
			TargetValueDAO tdao = new TargetValueDAO();
			TargetValue targetValue = tdao.getTargetValueByUserId(userId);
			request.setAttribute("targetValue", targetValue);

			boolean needsInput = false;

			if (targetValue == null) {
				needsInput = true; // 初回ユーザーは入力必須
			} else if (targetValue.getMonth() != currentMonth) {
				needsInput = true; // 月が変わったら入力必須
			} else if (targetValue.getPure_alcohol_consumed() == 0.0f || targetValue.getSleep_time() == 0.0f
					|| targetValue.getCalorie_intake() == 0 || targetValue.getTarget_weight() == 0.0f) {
				needsInput = true; // 値が未設定のときも入力必須
			}
			request.setAttribute("needsInput", needsInput);
			request.setAttribute("targetValue", targetValue);

			List<Point> allPoints = pointDAO.selectByUserId(userId);

			// 累計カロリー取得
			int totalCaloriesSum = 0;
			if (allPoints != null) {
				for (Point p : allPoints) {
					totalCaloriesSum += p.getTotal_calorie_consumed();
				}
			}

			request.setAttribute("totalCaloriesSum", totalCaloriesSum);

			double Step = 0.03;
			int CountryCalorie = 3600;
			int nextcountry = 120000;

			int currentCountryIndex = totalCaloriesSum / CountryCalorie;
			int nextTargetCalories = CountryCalorie * (currentCountryIndex + 1);
			int remainingCalories = Math.max(0, nextTargetCalories - totalCaloriesSum);
			int requiredSteps = (int) Math.ceil(remainingCalories / Step);
			double displayStepsToNext = nextcountry * ((double) remainingCalories / CountryCalorie);

			request.setAttribute("requiredSteps", requiredSteps);
			request.setAttribute("remainingCalories", remainingCalories);
			request.setAttribute("displayStepsToNext", (int) Math.ceil(displayStepsToNext));
			request.setAttribute("currentCountry", currentCountryIndex + 1);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);
	}

	// 目標値入力フォームからのPOST
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost called");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("user_id"); // ユーザーIDはセッションから取得
		System.out.println("userId from session: " + userId); // 追加
		request.setCharacterEncoding("UTF-8");

		// フォームから取得
		java.time.LocalDate now = java.time.LocalDate.now();
		int currentMonth = now.getMonthValue();
//		int currentMonth = 8;
		String pureAlcoholStr = request.getParameter("pure_alcohol_consumed");
		String sleepTimeStr = request.getParameter("sleep_time");
		String calorieIntakeStr = request.getParameter("calorie_intake");
		String targetWeightStr = request.getParameter("target_weight");

		TargetValueDAO tdao = new TargetValueDAO();
		TargetValue targetValue = tdao.getTargetValueByUserId(userId);

		if (targetValue == null) {
			// まだ目標値レコードがない -> INSERT
			String insertSql = "INSERT INTO target_value (user_id, month, pure_alcohol_consumed, sleep_time, calorie_intake, target_weight) VALUES (?, ?, ?, ?, ?, ?)";
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password"); PreparedStatement pStmt = conn.prepareStatement(insertSql)) {

				pStmt.setString(1, userId);
				pStmt.setInt(2, currentMonth);
				pStmt.setFloat(3, Float.parseFloat(pureAlcoholStr));
				pStmt.setFloat(4, Float.parseFloat(sleepTimeStr));
				pStmt.setInt(5, Integer.parseInt(calorieIntakeStr));
				pStmt.setFloat(6, Float.parseFloat(targetWeightStr));

				pStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMsg", "目標値の新規登録に失敗しました");
			}
		} else {
			// 既存のレコードがあれば UPDATE
			String updateSql = "UPDATE target_value SET pure_alcohol_consumed = ?, sleep_time = ?, calorie_intake = ?, target_weight = ?, month = ? WHERE user_id = ?";
			try (Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9", "root",
					"password"); PreparedStatement pStmt = conn.prepareStatement(updateSql)) {

				pStmt.setFloat(1, Float.parseFloat(pureAlcoholStr));
				pStmt.setFloat(2, Float.parseFloat(sleepTimeStr));
				pStmt.setInt(3, Integer.parseInt(calorieIntakeStr));
				pStmt.setFloat(4, Float.parseFloat(targetWeightStr));
				pStmt.setInt(5, currentMonth);
				pStmt.setString(6, userId);

				pStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("errorMsg", "目標値の更新に失敗しました");
			}
		}
		// 目標値入力フォームのプロフィール項目に記載された内容をもとに更新 UserDAOのupdateProfile()
		String weightStr = request.getParameter("weight");
		String heightStr = request.getParameter("height");
		String sexStr = request.getParameter("sex");
		String ageStr = request.getParameter("age");
		String activeLevelIdStr = request.getParameter("active_level_id");

		if (userId != null) {
			// プロフィールの更新処理
			UserDAO userDAO = new UserDAO();
			userDAO.updateProfile(userId, Double.parseDouble(weightStr), Double.parseDouble(heightStr),
					sexStr.charAt(0), Integer.parseInt(ageStr), Integer.parseInt(activeLevelIdStr));

			// 更新後の画面表示
			doGet(request, response);
		}

// Pointテーブルのにその月のレコードを追加
		if (userId != null) {
			int year = now.getYear();
			int month = now.getMonthValue();

			PointDAO pointDAO = new PointDAO();

			// その月のレコードが存在するか確認するために検索
			Point searchPoint = new Point();
			searchPoint.setUser_id(userId);
			searchPoint.setMonth(month);

			List<Point> existingPoints = pointDAO.select(searchPoint);

			if (existingPoints == null || existingPoints.isEmpty()) {
				// まだレコードがなければ、最新の累計消費カロリーを取得
				List<Point> allPoints = pointDAO.selectByUserId(userId);

				int latestCalorie = 0;
				if (allPoints != null && !allPoints.isEmpty()) {
					// 年月で最新のポイントを取得（最新順に並んでいる前提で）
					Point latestPoint = allPoints.get(allPoints.size() - 1);
					latestCalorie = latestPoint.getTotal_calorie_consumed();
				}

				// 新しい月のレコードを追加（累計消費カロリーだけ引き継ぐ）
				boolean inserted = pointDAO.insert(userId, month, year, latestCalorie);

				if (!inserted) {
					// 挿入に失敗した場合のエラーハンドリング
					request.setAttribute("errorMsg", "ポイントレコードの追加に失敗しました。");
				}
			}
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
			dispatcher.forward(request, response);
		}
	}
	
}
