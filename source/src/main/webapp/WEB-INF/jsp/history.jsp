<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="dto.History"%>
<%
List<History> fileList = (List<History>) request.getAttribute("fileList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ケンコークラフト</title>


</head>
<body>

	<!-- ① タイトル（ホームボタン） -->
	<h1>
		<a href="HomeServlet">ケンコークラフト</a>
	</h1>

	<!-- ② ヘルプボタン -->
	<button class="btn" style="float: right;" onclick="showHelp()">ヘルプ</button>

	<h2>過去ファイルのダウンロード・閲覧</h2>

	<!-- ③ ファイル選択（今回は使用しない） -->
	<input type="file" disabled>
	<button class="btn" disabled>選択</button>

	<!-- ④ 街を見るボタン -->
	<form action="HistoryServlet" method="post" style="display: inline;">
		<input type="text" name="searchDate" placeholder="日付を入力">
		<button class="btn" type="submit">街を見る</button>
	</form>

	<br>
	<br>

	<!-- ⑤ 表示領域 -->
	<div>
		<%
		if (fileList != null) {
			for (History h : fileList) {
		%>
		<div class="file-box">
			<strong><%=h.getDate()%>の街並み</strong><br> <img
				src="<%=h.getImagePath()%>" width="100" height="100" alt="街の画像">
		</div>
		<%
		}
		} else {
		%>
		<p>街並みはまだ表示されていません。</p>
		<%
		}
		%>
	</div>

	<!-- ⑥ ファイルリスト（フィルタ検索と連動はしていない簡易版） -->
	<div style="float: right; border: 1px solid #000; padding: 10px;">
		<form method="post" action="DownloadServlet">
			<!-- ダウンロード処理用 -->
			<%
			if (fileList != null) {
				for (History h : fileList) {
			%>
			<label> <input type="checkbox" name="fileNames"
				value="<%=h.getFileName()%>"> <%=h.getFileName()%>
			</label><br>
			<%
			}
			}
			%>

			<!-- ⑦ ダウンロードボタン -->
			<button class="btn" type="submit">ダウンロード</button>
		</form>
	</div>

</body>
</html>