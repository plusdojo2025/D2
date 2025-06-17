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
			TargetValueDAO tdao = new TargetValueDAO();
			TargetValue targetValue = tdao.getTargetValueByUserId(userId);
			request.setAttribute("targetValue", targetValue);

			PointDAO dao = new PointDAO();
			List<Point> allPoints = dao.selectByUserId(userId);

			// 累計カロリー取得
			int totalCaloriesSum = 0;
			if (allPoints != null) {
				for (Point p : allPoints) {
					totalCaloriesSum += p.getTotal_calorie_consumed();
				}
			}

			request.setAttribute("totalCaloriesSum", totalCaloriesSum);

			double Step = 0.03;
			int CountryCalorie = 3600; // 1国 = 3600 kcal
			int nextcountry = 120000; // 画面上で1国を進むのに必要な表示歩数

			// 今どこの国か？（0から始まる）
			int currentCountryIndex = totalCaloriesSum / CountryCalorie;

			// 次の目的地（国）までに必要なカロリー
			int nextTargetCalories = CountryCalorie * (currentCountryIndex + 1);
			int remainingCalories = Math.max(0, nextTargetCalories - totalCaloriesSum);

			// 実際に必要な歩数（運動として）
			int requiredSteps = (int) Math.ceil(remainingCalories / Step);

			// 表示用の「進捗歩数」：1国＝12000歩として正規化
			double displayStepsToNext = nextcountry * ((double) remainingCalories / CountryCalorie);

			// JSPに渡す
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

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("user_id"); // ユーザーIDはセッションから取得

		request.setCharacterEncoding("UTF-8");

		// フォームから取得
		String monthStr = request.getParameter("month");
		String pureAlcoholStr = request.getParameter("pure_alcohol_consumed");
		String sleepTimeStr = request.getParameter("sleep_time");
		String calorieIntakeStr = request.getParameter("calorie_intake");
		String targetWeightStr = request.getParameter("target_weight");

		if (userId != null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/d2?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9",
						"root", "password");

				// 目標値テーブルの更新 TargetValueDAOのupdate
				String sql = "UPDATE target_value SET pure_alcohol_consumed = ?,"
						+ " sleep_time = ?, calorie_intake = ?, target_weight = ?"
						+ " WHERE user_id = ? AND month = ? ";

				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setInt(1, Integer.parseInt(monthStr)); // user_idはそのままWHERE句で利用
				pStmt.setFloat(2, Float.parseFloat(pureAlcoholStr));
				pStmt.setFloat(3, Float.parseFloat(sleepTimeStr));
				pStmt.setInt(4, Integer.parseInt(calorieIntakeStr));
				pStmt.setFloat(5, Float.parseFloat(targetWeightStr));
				pStmt.setString(6, userId); // user_idはそのままWHERE句で利用

				pStmt.executeUpdate();

				pStmt.close();
				conn.close();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			// 更新が終わったら home.jsp に反映するため doGet を呼び出す
			doGet(request, response);
		} else {
			// セッションに user_id がない場合はログインページへリダイレクト
			response.sendRedirect("/D2/LoginServlet");
		}

		// 目標値入力フォームのプロフィール項目に記載された内容をもとに更新 UserDAOのupdateProfile()
		String weightStr = request.getParameter("weight");
		String heightStr = request.getParameter("height");
		String sexStr = request.getParameter("sex");
		String ageStr = request.getParameter("age");
		String activeLevelIdStr = request.getParameter("active_level_id");

		if (userId != null) {
			// ...（目標値更新の処理）...

			// プロフィールの更新処理
			UserDAO userDAO = new UserDAO();
			userDAO.updateProfile(userId, Double.parseDouble(weightStr), Double.parseDouble(heightStr),
					sexStr.charAt(0), Integer.parseInt(ageStr), Integer.parseInt(activeLevelIdStr));

			// 更新後の画面表示
			doGet(request, response);
		}

// Pointテーブルのにその月のレコードを追加
		String yearStr = request.getParameter("year");
		
		if (userId != null && monthStr != null && yearStr != null) {
			int month = Integer.parseInt(monthStr);
			int year = Integer.parseInt(yearStr);

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
			doGet(request, response);

		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
			dispatcher.forward(request, response);
		}
	}
}
