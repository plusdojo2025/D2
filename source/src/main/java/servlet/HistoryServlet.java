package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.HealthRecordDAO;
import dao.HistoryDAO;
import dao.ImageAllDAO;
import dao.ImageDAO;
import dao.PointDAO;
import dao.RewardDayDAO;
import dto.History;
import dto.Point;
import dto.TownAvatarElements;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/HistoryServlet")
//@MultipartConfig(location = "C:\\plusdojo2025\\D2\\source\\src\\main\\webapp", maxFileSize = 1048576)
@MultipartConfig(maxFileSize = 1048576)
public class HistoryServlet extends HttpServlet {
	
	private boolean test = false;
	private String testUserId = "kazutoshi_t"; // テスト用のユーザID
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userId;
		String folderPath;

		if(this.test){
			session.setAttribute("user_id", testUserId);
		}

		if (session.getAttribute("user_id") == null) {
			// もしもログインしていなかったらログインサーブレットにリダイレクト
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}

		userId = (String) session.getAttribute("user_id");

		if (this.test) {
			folderPath = "c:/plusdojo2025/D2/source/src/main/webapp/"
					+ "history/" + userId;
		} else {
			folderPath = request.getContextPath() + "/source/src/main/webapp/history/" + userId;
			}

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
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
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

						String filename;						
						filename = (oldestDayYear3 + 1) + "-" + (oldestDayMonth3 + i) + ".txt";
						String filePath = folderPath + "/" + filename;
						try {

							File folder, file;

							folder = new File(folderPath);
							file = new File(filePath);

							// フォルダの作成
							if (folder.mkdirs()) {
								System.out.println("フォルダが作成されました: " + file.getName());
							} else {
								System.out.println("フォルダは既に存在します。");
							}

							// ファイルの作成
							if (file.createNewFile()) {
								System.out.println("ファイルが作成されました: " + file.getName());
							} else {
								System.out.println("ファイルは既に存在します。");
							}

						} catch (IOException e) {
							System.out.println("エラーが発生しました。");
							e.printStackTrace();
						}

						try (FileWriter writer = new FileWriter(filePath)) {
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
						History history = new History(userId, oldestDayYear3 + 1, oldestDayMonth3 + i, filename);
						hDao.insert(history);

						pDao.delete(userId, oldestDayYear3 + 1, oldestDayMonth3 + i - 12);
						j++;
					}

				} else {
					if (pointList.get(j).getYear() == oldestDayYear3
							&& pointList.get(j).getMonth() == (oldestDayMonth3 + i)) {
						String filename;
						filename = (oldestDayYear3) + "-" + (oldestDayMonth3 + i) + ".txt";
						String filePath = folderPath + "/" + filename;
						try {

							File folder, file;

							folder = new File(folderPath);
							file = new File(filePath);

							// フォルダの作成
							if (folder.mkdirs()) {
								System.out.println("フォルダが作成されました: " + file.getName());
							} else {
								System.out.println("フォルダは既に存在します。");
							}

							// ファイルの作成
							if (file.createNewFile()) {
								System.out.println("ファイルが作成されました: " + file.getName());
							} else {
								System.out.println("ファイルは既に存在します。");
							}

						} catch (IOException e) {
							System.out.println("エラーが発生しました。");
							e.printStackTrace();
						}
						try (FileWriter writer = new FileWriter(filePath)) {
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
						History history = new History(userId, oldestDayYear3, oldestDayMonth3 + i, filename);
						hDao.insert(history);
						pDao.delete(userId, oldestDayYear3, oldestDayMonth3 + i);
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

	    HttpSession session = req.getSession();
	    String userIdSession;
	    String uploadFolderPath;
	    String historyFolderPath;

	    if (this.test) {
	        session.setAttribute("user_id", testUserId);
	        uploadFolderPath = "C:/plusdojo2025/D2/source/src/main/webapp/uploaded";
	    } else {
	        uploadFolderPath = req.getServletContext().getRealPath("/uploaded");
	    }

	    userIdSession = (String) session.getAttribute("user_id");

	    if (this.test) {
	        historyFolderPath = "C:/plusdojo2025/D2/source/src/main/webapp/history/" + userIdSession;
	    } else {
	        historyFolderPath = req.getServletContext().getRealPath("/history/" + userIdSession);
	    }

	    // アップロードフォルダがなければ作成
	    File uploadFolder = new File(uploadFolderPath);
	    if (!uploadFolder.exists()) {
	        uploadFolder.mkdirs();
	    }

	    // ---- ダウンロード処理（複数ZIP） ----
	    if ("download".equals(req.getParameter("mode"))) {
	        String[] fileNames = req.getParameterValues("fileNames");

	        if (fileNames == null || fileNames.length == 0) {
	            resp.getWriter().write("ファイルが選択されていません。");
	            return;
	        }

	        resp.setContentType("application/zip");
	        resp.setHeader("Content-Disposition", "attachment; filename=\"history_files.zip\"");

	        try (BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
	             java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(out)) {

	            for (String fileName : fileNames) {
	                if (fileName == null || fileName.trim().isEmpty()) continue;

	                File file = new File(historyFolderPath, fileName.trim());
	                if (!file.exists()) {
	                    System.out.println("存在しないファイルをスキップ: " + file.getName());
	                    continue;
	                }

	                try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
	                    java.util.zip.ZipEntry entry = new java.util.zip.ZipEntry(file.getName());
	                    zos.putNextEntry(entry);

	                    byte[] buffer = new byte[4096];
	                    int bytesRead;
	                    while ((bytesRead = in.read(buffer)) != -1) {
	                        zos.write(buffer, 0, bytesRead);
	                    }
	                    zos.closeEntry();
	                }
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	            resp.getWriter().write("ZIP作成中にエラーが発生しました。");
	        }

	        return; // ダウンロード完了後に他の処理をしない
	    }

	    // ---- アップロード処理 ----
	    if ("upload".equals(req.getParameter("mode"))) {
	        List<TownAvatarElements> avatarList = new ArrayList<>();

	        for (Part part : req.getParts()) {
	            if (!"file".equals(part.getName()) || part.getSize() == 0) {
	                continue;
	            }

	            String fileName = getFileName(part);
	            part.write("/uploaded/" + fileName); // location に保存される

	            File file = new File(uploadFolderPath + "/" + fileName);
	            if (!file.exists()) continue;

	            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	                String data = reader.readLine();
	                if (data == null) continue;

	                String[] parts = data.split(",");
	                String userId = parts[0];
	                int year = Integer.parseInt(parts[1]);
	                int month = Integer.parseInt(parts[2]);
	                int calCon = Integer.parseInt(parts[3]);
	                int nosmoke = Integer.parseInt(parts[4]);
	                int alcohol = Integer.parseInt(parts[5]);
	                int calIntake = Integer.parseInt(parts[6]);
	                int sleep = Integer.parseInt(parts[7]);

	                Point point = new Point(userId, year, month, calCon, nosmoke, alcohol, calIntake, sleep);

	                ImageAllDAO imageAllDAO = new ImageAllDAO();
	                TownAvatarElements avatar = imageAllDAO.select(
	                    point.getMonth(),
	                    point.getYear(),
	                    point.getTotal_calorie_intake(),
	                    point.getTotal_alcohol_consumed(),
	                    point.getTotal_sleeptime(),
	                    point.getTotal_nosmoke(),
	                    ImageDAO.getCountryOrder(point.getTotal_calorie_consumed())
	                );
	                avatarList.add(avatar);

	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        // 過去ファイルのリストを取得してセット
	        HistoryDAO dao = new HistoryDAO();
	        List<History> hrList = dao.select(userIdSession);
	        req.setAttribute("fileList", hrList);
	        req.setAttribute("avatarList", avatarList);

	        // JSPへフォワード
	        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/history.jsp");
	        dispatcher.forward(req, resp);
	    }
	}

	// ヘルプはjavascriphtで表示!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	private String getFileName(Part part) {
		String name = null;
		for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
			if (dispotion.trim().startsWith("filename")) {
				name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
				name = name.substring(name.lastIndexOf("\\") + 1);
				break;
			}
		}
		return name;
	}

}