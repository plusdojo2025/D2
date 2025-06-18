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
//パスワード入力プログラム!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // すべてのプログラムを入力しているか?
        // TODO: ここの処理をjsファイルに移行
        if (id == null || id.isEmpty() ||
//            idConfirm == null || idConfirm.isEmpty() ||
            pw == null || pw.isEmpty() ||
            pwConfirm == null || pwConfirm.isEmpty()) {

            out.println("<html><body>");
            out.println("<h3 style='color:red;'>すべての項目を入力してください。</h3>");
            out.println("<a href=\"UserRegistServlet\">戻る</a>");
            out.println("</body></html>");
            return;
        }

        

        // 2つの項目のパスワード一致しているか?
        // TODO: ここの処理をjsファイルに移行
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
        
        // ユーザの空の目標値レコードを追加
        if(result) {
        	//TargetValueDAOのinsertVoid()
        }
        
//上記をすべて満たしていてもう登録されていないか確認のプログラム
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
