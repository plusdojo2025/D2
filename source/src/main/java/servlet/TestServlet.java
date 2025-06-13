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
		List<String> imgPathSetBuild = new ArrayList<String>();
		List<String> imgPathSetCloth = new ArrayList<String>();
		
		imgPathSetBuild.add("img/build1_jp.png");
		imgPathSetBuild.add("img/build2_jp.png");
		imgPathSetBuild.add("img/build3_jp.png");
		imgPathSetCloth.add("img/cloth1.png");
		imgPathSetCloth.add("img/cloth2.png");
		imgPathSetCloth.add("img/cloth3.png");
		// imgPathSetCloth.add("img/cloth4_jp.png"); //　コメント外すと民族衣装に切り替わる
	
		TownAvatarElements elementsTownAvatar = new TownAvatarElements(
				imgPathSetBuild,
				imgPathSetCloth,
				"img/face1.png", // face2, face3, face4, face5 選べる
				"img/people_jp.png",
				7);
		
		request.setAttribute("elmsImage", elementsTownAvatar);
		// ここまでコピー-------------------------------------------
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/test.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
