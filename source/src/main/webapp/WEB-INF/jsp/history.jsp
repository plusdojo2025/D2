<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="dto.History"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
List<History> fileList = (List<History>) request.getAttribute("fileList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ケンコークラフト</title>

<script>
	function showHelp() {
		alert("このページでは、過去ファイルのダウンロード・街並みの表示ができます。\n"
				+ "検索ボックスで日付をフィルタし、「街を見る」で表示、「ダウンロード」で保存できます。");
	}
</script>
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
	<!--  <input type="file" disabled>
	<button class="btn" disabled>選択</button>-->

	<!-- ④ 街を見るボタン -->
	<form method="POST" enctype="multipart/form-data"
		action="HistoryServlet?mode=upload">
		<input type="file" name="file" /><br /> <input type="submit"
			value="アップロード" />
	</form>
	<!--  <form action="HistoryServlet" method="post" style="display: inline;">
		
		<button class="btn" type="submit">街を見る</button>
	</form> -->

	<br>
	<br>
	
	<c:forEach var="build" items="${avatar.buildings}">
			<div class="imgBuild" id="${build.imagePath}"></div>
		</c:forEach>
		<div class="cloth" id="${avatar.cloth.imagePath}"></div>
		<div class="cloth" id="${avatar.shoe.imagePath}"></div>
		<div class="cloth" id="${avatar.hat.imagePath}"></div>
		<div class="cloth" id="${avatar.costume.imagePath}"></div>
		<div class="imgPeople" id="${avatar.peopleImage.imagePath}"></div>
		<div class="imgFace" id="${avatar.face.imagePath}"></div>
		<input type="hidden" id="peopleNum" value="${avatar.peopleCount}">
		<!-- --------------------------------------------------------------- -->

		<c:set var="width" value="500" />
		<!-- 実際に画面に表示する際の横幅[px] -->
		<canvas id="imageCanvas" width="1400" height="1000"
			style="width:${width}px;"></canvas>

	<!-- ⑤ 表示領域 -->
	<div>
		<%
		if (fileList != null) {
			for (History h : fileList) {
		%>
		<div class="file-box">
			<strong><%=h.getYear()%>の街並み</strong><br> <img
				src="<%=h.getFileName()%>" width="100" height="100" alt="街の画像">

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

	<!-- ⑥ ファイルリスト -->
	<div style="float: right; border: 1px solid #000; padding: 10px;">
		<form method="post" action="HistoryServlet?mode=download">
			<%-- OK --%>
			<%
			if (fileList != null) {
				for (History h : fileList) {
			%>
			<label> <input type="radio" name="fileName"
				value="<%=h.getFileName()%>"> <%=h.getFileName()%>
			</label><br>
			<%
			}
			}
			%>

			<button class="btn" type="submit">ダウンロード</button>
		</form>
	</div>

</body>

<script src="js/createTownAvatar.js"></script>
</html>