package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RewardDayDAO;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		RewardDayDAO dao = new RewardDayDAO();
        List<RewardDay> rewardList = dao.select(new RewardDay());
        
        System.out.println("取得したrewardListの件数：" + (rewardList != null ? rewardList.size() : "null"));

        
        request.setAttribute("rewardList", rewardList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/calendar.jsp");
		dispatcher.forward(request, response);
	}

}
