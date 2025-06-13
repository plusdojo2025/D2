package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dto.LoginUser;
import dto.User;
/**
 * Servlet implementation class UserRegistServlet
 */
@WebServlet("/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ログインページにフォワードする！
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

		
		
		    protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {

		        request.setCharacterEncoding("UTF-8");
		    	String id = request.getParameter("id");
				String pw = request.getParameter("pw");

		        User user = new User(id, pw);
		        UserDAO dao = new UserDAO();
		        boolean success = dao.insert(user);

		        response.setContentType("text/html; charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        out.println("<html><body>");

		        if (success) {
		            out.println("<h2>登録成功！</h2>");
		            out.println("<a href=\"login.jsp\">ログインページへ</a>");
		        } else {
		            out.println("<h2 style='color:red;'>登録に失敗しました。</h2>");
		        }

		        out.println("</body></html>");
		    }
		
	}

}
