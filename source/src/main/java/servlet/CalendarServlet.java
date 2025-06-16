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
		/*HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) {
			response.sendRedirect("/D2/LoginServlet");
			return;
		}*/
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		if (userId == null) {
			// 仮にかずとしにしとくね
			userId = "kazutoshi_t";
		}
		
		int month = (int) request.getAttribute("month"); // TODO:DBから持ってくるレコードはこの月をもとにする)
		

		// 達成した報酬をDBから持ってくる
		RewardDayDAO rewarddao = new RewardDayDAO();
		List<RewardDay> rewardList = rewarddao.select(new RewardDay()); // TODO: UserID, 月を引数にする
		request.setAttribute("rewardList", rewardList);
		
		/* 健康記録をDBから持ってくる
		 *  HealthRecordDAOのselectを使用
		 *  リクエストスコープに格納
		 */
		
		/* カレンダーに表示する統計値の計算処理
		 * 	持ってきた健康記録のリストをもとに統計値を計算しリクエストスコープに格納
		 */	
		
		/*
		 *  街並み・アバター表示のための処理
		 *   ImageAllDAOのselectを使用
		 *   返り値のTownAvatarElementsをリクエストスコープに格納
		 */
		

//		いったん仮のステージ
		int countryOrder = 1;
		int clothstage = 3;
		int buildingStage = 2;
		int faceStage = 3; // ５段階評価
		int noSmokingCount = 5; // 禁煙達成日数（最大7）
//		イメージDAOの定義まとめてここでやります
		ImageDAO imagedao = new ImageDAO();

		Image cloth = null;
		Image shoe = null;
		Image hat = null;
		Image costume = null;
//		if文できなかったからswitch文にした
		switch (clothstage) {
		case 1:
			cloth = imagedao.getClothImage(clothstage);
			break;
		case 2:
			cloth = imagedao.getClothImage(clothstage);
			shoe = imagedao.getShoeImage(clothstage);
			break;
		case 3:
			cloth = imagedao.getClothImage(clothstage);
			shoe = imagedao.getShoeImage(clothstage);
			hat = imagedao.getHatImage(clothstage);
			break;
		case 4:
			costume = imagedao.getCountryCostumeImage(countryOrder);
			break;
		default:
	
			break;
		}
//画像がnullじゃなければJSPへおねがいします
		if (cloth != null) {
			request.setAttribute("clothImagePath", cloth.getImagePath());
		}
		if (shoe != null) {
			request.setAttribute("shoeImagePath", shoe.getImagePath());
		}
		if (hat != null) {
			request.setAttribute("hatImagePath", hat.getImagePath());
		}
		if (costume != null) {
			request.setAttribute("costumeImagePath", costume.getImagePath());
		}

// 建物はリスト
		List<Image> buildingImages = imagedao.getBuildingImages(buildingStage, countryOrder);

		request.setAttribute("buildingImages", buildingImages);

//顔色画像もステージから考える
		Image face = imagedao.getFaceImage(faceStage);
		request.setAttribute("faceImagePath", face.getImagePath());
//たばこはその国のやつを人数分増やす
		Image peopleImage = imagedao.getPeopleImage(countryOrder);

		request.setAttribute("peopleImagePath", peopleImage.getImagePath());
		request.setAttribute("peopleCount", noSmokingCount);

		request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	
}