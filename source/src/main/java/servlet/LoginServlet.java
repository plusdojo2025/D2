package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストパラメータを取得する！
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		// === ID・パスワードの入力チェックを追加 ===
		// TODO: ここの処理をjsファイルに移行
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		boolean isIdEmpty = (id == null || id.trim().isEmpty());
		boolean isPwEmpty = (pw == null || pw.trim().isEmpty());

		if (isIdEmpty && isPwEmpty) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head><meta charset=\"UTF-8\"><title>入力エラー</title></head><body>");
			out.println("<h2 style='color:red;'>ID、パスワードが入力されていません。</h2>");
			out.println("<a href=\"LoginServlet\">ログイン画面へ戻る</a>");
			out.println("</body></html>");
			return;
			// ここでID、PWの入力がないとエラーを表示//
		} else if (isIdEmpty) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head><meta charset=\"UTF-8\"><title>入力エラー</title></head><body>");
			out.println("<h2 style='color:red;'>IDを入力してください。</h2>");
			out.println("<a href=\"LoginServlet\">ログイン画面へ戻る</a>");
			out.println("</body></html>");
			return;
			// ここでIDの入力がないとエラーを表示//
		} else if (isPwEmpty) {
			out.println("<!DOCTYPE html>");
			out.println("<html><head><meta charset=\"UTF-8\"><title>入力エラー</title></head><body>");
			out.println("<h2 style='color:red;'>パスワードを入力してください。</h2>");
			out.println("<a href=\"LoginServlet\">ログイン画面へ戻る</a>");
			out.println("</body></html>");
			return;
			// ここでPWの入力がないとエラーを表示////
		}
		// === 入力チェックここまで ===

		// ログイン処理を行う
		UserDAO iDao = new UserDAO();
		if (iDao.isLoginOK(new User(id, pw))) { // ログイン成功
			// セッションスコープにIDを格納する
			HttpSession session = request.getSession();
			session.setAttribute("user_id",id);

			// メニューサーブレットにリダイレクトする
			response.sendRedirect(request.getContextPath() + "/HomeServlet"); // TODO: /D2/...に変更
		} else { 
			// ログイン失敗時に表示する文字

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>ログインエラー</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>IDまたはパスワードが間違っています。</h2>");
			out.println("<a href=\"LoginServlet\">ログイン画面へ戻る</a>");
			out.println("</body>");
			out.println("</html>");
			// IDまたはパスワードが間違っているとエラーが表示//
		}
	}

}
