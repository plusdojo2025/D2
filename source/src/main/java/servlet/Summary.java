package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ImageAllDAO;
import dao.ImageDAO;
import dao.PointDAO;
import dto.Point;
import dto.TownAvatarElements;

@WebServlet("/Summary")
public class Summary extends HttpServlet {

  private boolean test = true;
	private String testUserId = "kazutoshi_t"; // テスト用のユーザID 

  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");


    HttpSession session = req.getSession();

    if (this.test) {
      session.setAttribute("user_id", testUserId);
    }
    
    String userId = (String) session.getAttribute("user_id");
    if (userId == null) {
      res.sendRedirect(req.getContextPath() + "/LoginServlet");
      return;
    }

    List<TownAvatarElements> avatars = new ArrayList<>();
    List<Integer> monthList = new ArrayList<>();
    PointDAO pointDAO = new PointDAO();
    ImageAllDAO imageAllDAO = new ImageAllDAO();

    Calendar cal = Calendar.getInstance();
    int currentMonth = cal.get(Calendar.MONTH); // 0〜11 (0=1月)
    req.setAttribute("currentMonth", currentMonth);

    // 「今月」から「11ヶ月前」へ逆順に並べる
    for (int i = 0; i <= 11; i++) {
      int m = (currentMonth - i + 12) % 12;
      monthList.add(m + 1); // 月を1〜12に変換
      Point p = pointDAO.selectByUserIdMonth(userId, m + 1);
      if (p == null) avatars.add(null);
      else {
        avatars.add(imageAllDAO.select(
		  p.getYear(),
		  p.getMonth(),
          p.getTotal_calorie_intake(),
          p.getTotal_alcohol_consumed(),
          p.getTotal_sleeptime(),
          p.getTotal_nosmoke(),
          ImageDAO.getCountryOrder(p.getTotal_calorie_consumed())
        ));
      }
    }

    req.setAttribute("monthList", monthList);
    req.setAttribute("avatars", avatars);
    req.getRequestDispatcher("/WEB-INF/jsp/summary.jsp").forward(req, res);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    doGet(req, res);
  }
}