package servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HealthRecordDAO;
import dao.HistoryDAO;
import dao.PointDAO;
import dao.RewardDayDAO;
import dto.History;
import dto.Point;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userId = "kazutoshi_t";
		/*
		 * 今日の西暦と月から、12か月以上前の健康記録と報酬達成日のレコードをそれぞれのテーブルから削除する HealthRecordDAOのdelete(),
		 * RewardDayDAOのdelete()を使用
		 */
		// 健康記録の削除
		LocalDate today = java.time.LocalDate.now(); // 今日の日付を取得
		// 2025-06-19
		HealthRecordDAO hrDao = new HealthRecordDAO();
		List<String> dateList = hrDao.select(userId); // 全ての健康記録の日付だけ古い順に取得
		Date oldestDayTmp = Date.valueOf(dateList.get(0));// 一番古い日付を取得 2024-04-01
		LocalDate oldestDay = oldestDayTmp.toLocalDate();

		// 今日の年月を取得
		int todayYear = today.getYear();// 2025
		int todayMonth = today.getMonthValue();// 6
		// 一番古い健康記録データの年月を取得
		int oldestDayYear = oldestDay.getYear();// 2024
		int oldestDayMonth = oldestDay.getMonthValue();// 4
		int gap = 0;

		if (todayYear - oldestDayYear >= 1) {
			gap = 12 * (todayYear - oldestDayYear) + (todayMonth - oldestDayMonth);
		}
		if (gap >= 12) {
			int loopCount = gap - 12 + 1;
			for (int i = 0; i < loopCount; i++) {
				if (oldestDayMonth + i > 12) {
					hrDao.delete(userId, oldestDayYear + 1, oldestDayMonth + i - 12);
				} else {
					hrDao.delete(userId, oldestDayYear, oldestDayMonth + i);
				}
			}
		}

		// 報酬達成日のレコードを削除
		RewardDayDAO rdDao = new RewardDayDAO();
		List<String> dateList2 = rdDao.select(userId); // 全ての健康記録の日付だけ古い順に取得
		Date oldestDayTmp2 = Date.valueOf(dateList2.get(0));// 一番古い日付を取得 2024-04-01
		LocalDate oldestDay2 = oldestDayTmp2.toLocalDate();
		// 一番古い報酬達成日の年月を取得
		int oldestDayYear2 = oldestDay2.getYear();// 2024
		int oldestDayMonth2 = oldestDay2.getMonthValue();// 4
		if (todayYear - oldestDayYear2 >= 1) {
			gap = 12 * (todayYear - oldestDayYear2) + (todayMonth - oldestDayMonth2);
		}
		// 削除工程
		if (gap >= 12) {
			int loopCount = gap - 12 + 1;
			for (int i = 0; i < loopCount; i++) {
				if (oldestDayMonth2 + i > 12) {
					rdDao.delete(userId, oldestDayYear2 + 1, oldestDayMonth2 + i - 12);
				} else {
					rdDao.delete(userId, oldestDayYear2, oldestDayMonth2 + i);
				}
			}
		}

		/*
		 * 今日の西暦と月から、12か月以上前のポイントのレコードをテキストファイルとして書き出し、テーブルから削除する 書き出すパスは
		 * "history/ユーザーID/西暦-月.txt" (ユーザーIDと西暦と月は適切な名前にする) 削除はPointDAOのdelete()を使用
		 */
		PointDAO pDao = new PointDAO();
		// ポイントテーブルのレコードを日付の古い順に取得
		List<Point> pointList = pDao.selectByUserId(userId);
		// 一番古いポイントレコードの年月を取得
		int oldestDayYear3 = pointList.get(0).getYear();
		int oldestDayMonth3 = pointList.get(0).getMonth();
		if (todayYear - oldestDayYear3 >= 1) {
			gap = 12 * (todayYear - oldestDayYear3) + (todayMonth - oldestDayMonth3);
		}
		if (gap >= 12) {
			int loopCount = gap - 12 + 1;
			int j = 0;
			for (int i = 0; i < loopCount; i++) {
				if (oldestDayMonth3 + i > 12) {// 2024-03 2024-05
					if (pointList.get(j).getYear() == (oldestDayYear3 + 1)
							&& pointList.get(j).getMonth() == (oldestDayMonth3 + i)) {
						try {
				            // 新しいファイルのインスタンスを作成
				            File file = new File("history/" + userId + "/" + (oldestDayYear3 + 1) + "-"
									+ (oldestDayMonth3 + i) + ".txt");

				            // ファイルが存在しない場合、新規作成
				            if (file.createNewFile()) {
				                System.out.println("ファイルが作成されました: " + file.getName());
				            } else {
				                System.out.println("ファイルは既に存在します。");
				            }
				        } catch (IOException e) {
				            System.out.println("エラーが発生しました。");
				            e.printStackTrace();
				        }
						
						try (FileWriter writer = new FileWriter("history/" + userId + "/" + (oldestDayYear3 + 1) + "-"
								+ (oldestDayMonth3 + i) + ".txt")) {
							writer.write(pointList.get(i).getUser_id() + "," + pointList.get(i).getYear() + ","
									+ pointList.get(i).getMonth() + "," + pointList.get(i).getTotal_calorie_consumed()
									+ "," + pointList.get(i).getTotal_nosmoke() + ","
									+ pointList.get(i).getTotal_alcohol_consumed() + ","
									+ pointList.get(i).getTotal_calorie_intake() + ","
									+ pointList.get(i).getTotal_sleeptime());

						} catch (IOException e) {
							e.printStackTrace();
						}
						HistoryDAO hDao = new HistoryDAO();
						History history = new History(userId, oldestDayYear + 1, oldestDayMonth + i, "history/" + userId
								+ "/" + (oldestDayYear3 + 1) + "-" + (oldestDayMonth3 + i) + ".txt");
						hDao.insert(history);

						pDao.delete(userId, oldestDayYear3 + 1, oldestDayMonth + i - 12);
						j++;
					}

				} else {
					if (pointList.get(j).getYear() == oldestDayYear3
							&& pointList.get(j).getMonth() == (oldestDayMonth3 + i)) {
						try {
				            // 新しいファイルのインスタンスを作成
				            File file = new File("history/" + userId + "/" + oldestDayYear3 + "-"
									+ (oldestDayMonth3 + i) + ".txt");

				            // ファイルが存在しない場合、新規作成
				            if (file.createNewFile()) {
				                System.out.println("ファイルが作成されました: " + file.getName());
				            } else {
				                System.out.println("ファイルは既に存在します。");
				            }
				        } catch (IOException e) {
				            System.out.println("エラーが発生しました。");
				            e.printStackTrace();
				        }
						try (FileWriter writer = new FileWriter(
								"history/" + userId + "/" + oldestDayYear3 + "-" + (oldestDayMonth3 + i) + ".txt")) {
							writer.write(pointList.get(i).getUser_id() + "," + pointList.get(i).getYear() + ","
									+ pointList.get(i).getMonth() + "," + pointList.get(i).getTotal_calorie_consumed()
									+ "," + pointList.get(i).getTotal_nosmoke() + ","
									+ pointList.get(i).getTotal_alcohol_consumed() + ","
									+ pointList.get(i).getTotal_calorie_intake() + ","
									+ pointList.get(i).getTotal_sleeptime());

						} catch (IOException e) {
							e.printStackTrace();
						}
						HistoryDAO hDao = new HistoryDAO();
						History history = new History(userId, oldestDayYear, oldestDayMonth + i, "history/" + userId
								+ "/" + (oldestDayYear3 + 1) + "-" + (oldestDayMonth3 + i) + ".txt");
						hDao.insert(history);
						pDao.delete(userId, oldestDayYear3, oldestDayMonth + i);
						j++;
					}
				}
			}

		}

		// 過去ファイルのリストを取得
		HistoryDAO dao = new HistoryDAO();
		List<History> hrList = dao.select("kazutoshi_t");
		request.setAttribute("fileList", hrList);

		// ログインページにフォワードする
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ヘルプはjavascriptで表示!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	}
}