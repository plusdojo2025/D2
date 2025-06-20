package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

@WebServlet("/Summary")
public class Summary extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        List<TownAvatarElements> avatars = new ArrayList<>();
        List<Integer> monthList = new ArrayList<>();

        PointDAO pointDAO = new PointDAO();
        ImageAllDAO imageAllDAO = new ImageAllDAO();

        String userId = "kazutoshi_t"; // 実際にはセッションなどから取得すべき

        // 今月の月番号を取得（1〜12）
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;

        // 今月から12か月前までのループ
        for (int i = 0; i < 12; i++) {
            int targetMonth = currentMonth - i;
            if (targetMonth <= 0) {
                targetMonth += 12;
            }
            monthList.add(targetMonth); // 表示用にJSPに渡す

            Point point = pointDAO.selectByUserIdMonth(userId, targetMonth);

            if (point == null) {
                avatars.add(null);
                continue;
            }

            TownAvatarElements avatar = imageAllDAO.select(
                    point.getTotal_calorie_intake(),
                    point.getTotal_alcohol_consumed(),
                    point.getTotal_sleeptime(),
                    point.getTotal_nosmoke(),
                    ImageDAO.getCountryOrder(point.getTotal_calorie_consumed())
            );

          
            avatars.add(avatar);
            
            
        }

        
        // JSPに渡す
        request.setAttribute("avatars", avatars);
        request.setAttribute("monthList", monthList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/summary.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
