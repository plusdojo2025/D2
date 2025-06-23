package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.HealthRecordDAO;
import dto.HealthRecord;

@WebServlet("/HealthRecordFormServlet")
public class HealthRecordFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String date = request.getParameter("date");

		System.out.println("=== HealthRecordFormServlet デバッグ ===");
		System.out.println("userId = " + userId);
		System.out.println("date = " + date);

		HealthRecordDAO dao = new HealthRecordDAO();
		HealthRecord record = dao.selectByDate(userId, date);

		System.out.println("record = " + record);
		if (record != null) {
			System.out.println("→ 取得成功: " + record.getNowWeight());
		} else {
			System.out.println("→ データなし");
		}

		response.setContentType("text/javascript; charset=UTF-8");
		PrintWriter out = response.getWriter();

		if (record != null) {
		    // --- 基本情報 ---
		    out.println("document.querySelector('[name=now_weight]').value = '" + record.getNowWeight() + "';");
		    out.println("document.querySelector('[name=calorie_intake]').value = '" + record.getCalorieIntake() + "';");
		    out.println("document.querySelector('[name=sleep_hours]').value = '" + record.getSleepHours() + "';");
		    out.println("document.querySelector('[name=free]').value = '" + escapeJs(record.getFree()) + "';");
		    out.println("document.querySelector('[name=no_smoke][value=\"" + record.getNosmoke() + "\"]').checked = true;");

		    // --- 運動データ（複数）---
		    if (record.getExerciseList() != null) {
		        for (int i = 0; i < record.getExerciseList().size(); i++) {
		            out.println("addExercise();");
		            out.println("document.querySelector('[name=exercise_type" + i + "]').value = '" + record.getExerciseList().get(i).getType() + "';");
		            out.println("document.querySelector('[name=mets" + i + "]').value = '" + record.getExerciseList().get(i).getMets() + "';");
		            out.println("document.querySelector('[name=exercise_time" + i + "]').value = '" + record.getExerciseList().get(i).getTime() + "';");
		        }
		    }

		    // --- 飲酒データ（複数）---
		    if (record.getAlcoholList() != null) {
		        for (int i = 0; i < record.getAlcoholList().size(); i++) {
		            out.println("addAlcohol();");
		            out.println("document.querySelector('[name=alcohol_content" + i + "]').value = '" + record.getAlcoholList().get(i).getAlcoholContent() + "';");
		            out.println("document.querySelector('[name=alcohol_consumed" + i + "]').value = '" + record.getAlcoholList().get(i).getAlcoholConsumed() + "';");
		        }
		    }

		    // --- 編集不可にする ---
		    out.println("document.querySelectorAll('#healthForm input, #healthForm select, #healthForm textarea').forEach(el => { el.disabled = true; });");
		    out.println("document.querySelector('#healthForm input[type=submit]').disabled = true;");

		} else {
		    // --- 新規入力モード ---
		    out.println("document.querySelector('form').reset();");
		    out.println("document.querySelectorAll('#healthForm input, #healthForm select, #healthForm textarea').forEach(el => { el.disabled = false; });");
		    out.println("document.querySelector('#healthForm input[type=submit]').disabled = false;");
		}
	}


	private String escapeJs(String str) {
		if (str == null)
			return "";
		return str.replace("'", "\\'").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
	}
}