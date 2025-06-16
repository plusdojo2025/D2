<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ヒストリー機能</title>
</head>
<body>

		<h1>
			<a href="HomeServlet">ケンコークラフト</a>
		</h1>
		<h2>
			<a href="ヘルプを表示">ヘルプ</a>
		</h2>
		<td><label> インポートされたファイル <select name="history_date">
								<option value="未定">未定</option>
								<option value="未定">未定</option>
								<option value="未定">未定2025年6月</option>
						</select> 選択
					</label></td>
		
				
				
		<h4>
			<a href="まちをみる">街を見る</a>
		</h4>
		<h5>
			<a href="まちをみる">画像の表示</a>
		</h5>
		<h2>年と月を選択してください</h2>
    <form action="calendar" method="get">
        年：
        <select name="year">
            <% for (int y = 2023; y <= 2025; y++) { %>
                <option value="<%= y %>"><%= y %>年</option>
            <% } %>
        </select>
        月：
        <select name="month">
            <% for (int m = 1; m <= 12; m++) { %>
                <option value="<%= m %>"><%= m %>月</option>
            <% } %>
        </select>
        <input type="submit" value="検索">
    </form>		
		
		<form method="get">
        開始年: <input type="number" name="startYear" value="<%= request.getParameter("startYear") != null ? request.getParameter("startYear") : "2023" %>" required>
        開始月: <input type="number" name="startMonth" min="1" max="12" value="<%= request.getParameter("startMonth") != null ? request.getParameter("startMonth") : "1" %>" required><br><br>
        終了年: <input type="number" name="endYear" value="<%= request.getParameter("endYear") != null ? request.getParameter("endYear") : "2023" %>" required>
        終了月: <input type="number" name="endMonth" min="1" max="12" value="<%= request.getParameter("endMonth") != null ? request.getParameter("endMonth") : "12" %>" required><br><br>
        <input type="submit" value="表示">
    </form>
		
		
</body>
</html>