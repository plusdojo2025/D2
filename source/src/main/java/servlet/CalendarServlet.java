package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ImageDAO;
import dao.RewardDayDAO;
import dto.Image;
import dto.RewardDay;

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
		String userId = (String) session.getAttribute("user_id");

		String yearParam = request.getParameter("year");
		int year = 0;
		try {
			year = Integer.parseInt(yearParam);
		} catch (NumberFormatException | NullPointerException e) {
			year = java.time.LocalDate.now().getYear();
		}

		String monthParam = request.getParameter("month");
		int month = 0;
		try {
			month = Integer.parseInt(monthParam);
		} catch (NumberFormatException | NullPointerException e) {
			month = java.time.LocalDate.now().getMonthValue(); // デフォルトは今月
		}

		System.out.println("userId in session: " + userId);
		// 達成した報酬をDBから持ってくる
		RewardDayDAO rewardDao = new RewardDayDAO();
		List<RewardDay> rewardList = rewardDao.select(userId, month); // TODO: UserID, 月を引数にする(達成？）
		request.setAttribute("rewardList", rewardList);

		/*
		 * 健康記録をDBから持ってくる HealthRecordDAOのselectを使用 リクエストスコープに格納
		 */
//		HealthRecordDAO healthDao = new HealthRecordDAO();
//		List<HealthRecord> healthList = healthDao.select(userId, month);
//		request.setAttribute("healthList", healthList);
		/*
		 * カレンダーに表示する統計値の計算処理 持ってきた健康記録のリストをもとに統計値を計算しリクエストスコープに格納
		 */

		/*
		 * 街並み・アバター表示のための処理 ImageAllDAOのselectを使用 返り値のTownAvatarElementsをリクエストスコープに格納
		 */

//		いったん仮のステージ

		int countryOrder = 1;
		int caloriePoint = 210;
		int buildingPoint = 250;  // 例えば250ポイント// ユーザーのポイント例
		int sleepPoint = 5; // ユーザーの睡眠ポイント（例）
		int noSmokingPoint = 2; // ユーザーの禁煙ポイント（例）
//		int clothstage = 3;
//		int buildingStage = 3;
//		int faceStage = 3;
//		int noSmokingCount = 7;

		ImageDAO imagedao = new ImageDAO();
		
		int stage = imagedao.getEatStage(caloriePoint);  // ポイントからステージ判定

		Image cloth = null;
		Image shoe = null;
		Image hat = null;
		Image costume = null;

		switch (stage) {
		    case 1:
		        cloth = imagedao.getClothImage(stage);
		        break;
		    case 2:
		        cloth = imagedao.getClothImage(stage);
		        shoe = imagedao.getShoeImage(stage);
		        break;
		    case 3:
		        cloth = imagedao.getClothImage(stage);
		        shoe = imagedao.getShoeImage(stage);
		        hat = imagedao.getHatImage(stage);
		        break;
		    case 4:
		        costume = imagedao.getCountryCostumeImage(countryOrder);
		        break;
		}

		if (cloth != null) request.setAttribute("clothImagePath", cloth.getImagePath());
		if (shoe != null) request.setAttribute("shoeImagePath", shoe.getImagePath());
		if (hat != null) request.setAttribute("hatImagePath", hat.getImagePath());
		if (costume != null) request.setAttribute("costumeImagePath", costume.getImagePath());
		
		//建物

		int buildingStage = imagedao.getAlcoholStage(buildingPoint);
		List<Image> buildingImages = imagedao.getBuildingImages(buildingStage, countryOrder);
		request.setAttribute("buildingImages", buildingImages);

	
		//顔色
	
		int faceStage = imagedao.getSleepStage(sleepPoint);
		// ステージから顔色画像を取得
		Image faceImage = imagedao.getFaceImage(faceStage);
		request.setAttribute("faceImagePath", faceImage.getImagePath());

		//人望
		
		int noSmokingCount = imagedao.getNoSmokingCount(noSmokingPoint);

		// 禁煙達成者の画像は既存のpeopleImageで取得済み
		Image peopleImage = imagedao.getPeopleImage(countryOrder);
		request.setAttribute("peopleImagePath", peopleImage.getImagePath());
		request.setAttribute("peopleCount", noSmokingCount);

		
		
		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}