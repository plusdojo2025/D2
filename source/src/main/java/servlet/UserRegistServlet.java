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
import dto.User;


@WebServlet("/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET：登録画面にフォワード
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userRegist.jsp");
        dispatcher.forward(request, response);
    }

    // POST：登録処理＋エラーチェック
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String idConfirm = request.getParameter("idConfirm");
        String pw = request.getParameter("pw");
        String pwConfirm = request.getParameter("pwConfirm");

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 入力チェック
        if (id == null || id.isEmpty() ||
            idConfirm == null || idConfirm.isEmpty() ||
            pw == null || pw.isEmpty() ||
            pwConfirm == null || pwConfirm.isEmpty()) {

            out.println("<html><body>");
            out.println("<h3 style='color:red;'>すべての項目を入力してください。</h3>");
            out.println("<a href=\"UserRegistServlet\">戻る</a>");
            out.println("</body></html>");
            return;
        }

        // ID一致チェック
        if (!id.equals(idConfirm)) {
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>IDが一致していません。</h3>");
            out.println("<a href=\"UserRegistServlet\">戻る</a>");
            out.println("</body></html>");
            return;
        }

        // パスワード一致チェック
        if (!pw.equals(pwConfirm)) {
            out.println("<html><body>");
            out.println("<h3 style='color:red;'>パスワードが一致していません。</h3>");
            out.println("<a href=\"UserRegistServlet\">戻る</a>");
            out.println("</body></html>");
            return;
        }

        // DTOにセット
        User user = new User();
        user.setId(id);
        user.setPw(pw);

        // DB登録処理
        UserDAO dao = new UserDAO();
        boolean result = dao.insert(user);

        out.println("<html><body>");
        if (result) {
            out.println("<h2>登録に成功しました！</h2>");
            out.println("<a href=\"LoginServlet\">ログインページへ</a>");
        } else {
            out.println("<h2 style='color:red;'>登録に失敗しました。IDが重複していませんか？</h2>");
            out.println("<a href=\"UserRegistServlet\">戻る</a>");
        }
        out.println("</body></html>");
    }
}
