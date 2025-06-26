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
<link rel="stylesheet" href="<c:url value='/css/history.css'/>">
<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>

<body>

	<!-- ① タイトル（ホームボタン） -->
	<h1 class="logo-header">
		<a href="<c:url value='/HomeServlet' />"> <img
			src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo"
			class="logo-img"></a>
	</h1>

	<!-- ② ヘルプボタン -->
	<button class="btn" style="float: right;" onclick="showHelp()">ヘルプ</button>

	<h2 class="view">過去ファイルのダウンロード・閲覧</h2>
	<hr>

	<!-- ③ ファイル選択（今回は使用しない） -->
	<!--  <input type="file" disabled>
	<button class="btn" disabled>選択</button>-->

	<!-- ④ 街を見るボタン -->
	<form method="POST" enctype="multipart/form-data"
		action="HistoryServlet?mode=upload" class="upload-form">
		<!-- ファイル選択エリア -->
		<label class="file-box"> <span id="file-name">ファイルを選択してください</span>
			<input type="file" name="file" id="file-input" multiple
			hidden="hidden" />
			<button type="button" class="select-button"
				onclick="document.getElementById('file-input').click();">選択</button>
		</label>

		<!-- アップロードボタン -->
		<button type="submit" class="upload-button">街を見る</button>
	</form>
	<!--  <form action="HistoryServlet" method="post" style="display: inline;">
		
		<button class="btn" type="submit">街を見る</button>
	</form> -->

	<br>
	<br>


	<c:set var="width" value="500" />
	<!-- 実際に画面に表示する際の横幅[px] -->
	<c:forEach var="avatar" items="${avatarList}">
		<canvas class="imageCanvas" id="canvas_${avatar.year}_${avatar.month}"
			width="1627" height="1021" style="width:${width}px;"></canvas>
	</c:forEach>


	<!-- ⑥ ファイルリスト -->
	<div class="file_list" style="float: right;">
		<form method="post"
			action="<c:url value='/HistoryServlet?mode=download' />">
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
			<button class="btn" type="submit">ダウンロード</button>
		</form>
	</div>
	<div id="footer">
		<p class="copyright">&copy;2025 視力検査Dチーム 健康管理アプリ</p>
	</div>

	<script>
		const taList = {}; // タウンアバターのリスト
		const yearMonthList = [];
		<c:forEach var="avatar" items="${avatarList}">
			if(!taList[`${avatar.year}-${avatar.month}`]) {
				taList[`${avatar.year}-${avatar.month}`] = [];
			}
			taList[`${avatar.year}-${avatar.month}`] = {
				buildPaths: [<c:forEach var="build" items="${avatar.buildings}">
					"${build.imagePath}",</c:forEach>],
				peopleCount: ${avatar.peopleCount},
				peoplePath: "${avatar.peopleImage.imagePath}",
				clothPath: "${avatar.cloth.imagePath}",
				shoePath: "${avatar.shoe.imagePath}",
				hatPath: "${avatar.hat.imagePath}",
				costumePath: "${avatar.costume.imagePath}",
				facePath: "${avatar.face.imagePath}"
			}

			yearMonthList.push({
				year: ${avatar.year},
				month: ${avatar.month},
			});
		</c:forEach>;


		function showHelp() {
			alert("このページでは、１年以上前の過去ファイルのダウンロード・街並みの表示ができます。\n"
					+ "「ダウンロード」で過去ファイルの保存、保存したファイルを「選択」し、「街を見る」で表示できます。");
		}
		
	</script>
	<script src="<c:url value='/js/history.js' />"></script>
	<script src="<c:url value='/js/test.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>

</html>