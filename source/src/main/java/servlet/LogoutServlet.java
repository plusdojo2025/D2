package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 現在のセッションを取得し、無効化
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // ログアウトメッセージを設定してリダイレクト
        response.sendRedirect(request.getContextPath() + "/LoginServlet");
    }
}
