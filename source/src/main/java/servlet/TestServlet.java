package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImageAllDAO;
import dao.ImageDAO;
import dao.PointDAO;
import dto.Point;
import dto.TownAvatarElements;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ここからコピー-------------------------------------------
		// 同じようにtest.jspにもコピー項目がある
//		List<String> imgPathSetBuild = new ArrayList<String>();
//		List<String> imgPathSetCloth = new ArrayList<String>();
//		
//		imgPathSetBuild.add("img/build1_jp.png");
//		imgPathSetBuild.add("img/build2_jp.png");
//		imgPathSetBuild.add("img/build3_jp.png");
//		imgPathSetCloth.add("img/cloth1.png");
//		imgPathSetCloth.add("img/cloth2.png");
//		imgPathSetCloth.add("img/cloth3.png");
		// imgPathSetCloth.add("img/cloth4_jp.png"); //　コメント外すと民族衣装に切り替わる
	
//		TownAvatarElements elementsTownAvatar = new TownAvatarElements(
//				imgPathSetBuild,
//				imgPathSetCloth,
//				"img/face1.png", // face2, face3, face4, face5 選べる
//				"img/people_jp.png",
//				7);
		
//		request.setAttribute("elmsImage", elementsTownAvatar);
		// ここまでコピー-------------------------------------------
		
		// 健康記録のカレンダー表示のテスト-------------------
//		HealthRecordDAO HRDao = new HealthRecordDAO();
//		List<HealthRecord> hrList = HRDao.select("kazutoshi_t", 6);
//		request.setAttribute("hrList", hrList);

		// -------------------------------------------

		// 街並み表示のテスト-----------------------------
		PointDAO pointDAO = new PointDAO();
		Point point = pointDAO.selectByUserIdMonth("kazutoshi_t", 5);
		ImageAllDAO imageAllDAO = new ImageAllDAO();		
		TownAvatarElements avatar = imageAllDAO.select(
				point.getYear(),
				point.getMonth(),
				point.getTotal_calorie_intake(), 
				point.getTotal_alcohol_consumed(), 
				point.getTotal_sleeptime(), 
				point.getTotal_nosmoke(), 
				ImageDAO.getCountryOrder(point.getTotal_calorie_consumed()));

		// JSPにセットしてフォワード
		request.setAttribute("avatar", avatar);
		// -------------------------------------------
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/test.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
