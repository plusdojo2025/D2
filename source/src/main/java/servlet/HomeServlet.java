package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TargetValueDAO;
import dto.TargetValue;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションからuser_idを取得
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // user_idがnullでない場合に目標値を取得
        if (userId != null) {
            TargetValueDAO dao = new TargetValueDAO();
            TargetValue targetValue = dao.getTargetValueByUserId(userId);
            request.setAttribute("targetValue", targetValue);
        }

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher(("/WEB-INF/jsp/home.jsp"));
        dispatcher.forward(request, response);
    }
}