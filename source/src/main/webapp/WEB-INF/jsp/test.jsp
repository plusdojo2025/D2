<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	th, td {
		border: 1px solid #999;
	}   
</style>

</head>
<body>

<!-- ここから各々のjspファイル中の街並みアバターを表示したい場所にコピー -->
<!-- 同じようにTestServlet.javaにもコピー項目がある -->

<!-- jsファイル内で加工するためここでは非表示--------------------------------- -->
<c:forEach var="imgPathBuild" items="${elmsImage.imgPathSetBuild}">
	<img src="${imgPathBuild}" class="imgBuild" style="display: none;">
</c:forEach>
<c:forEach var="imgPathCloth" items="${elmsImage.imgPathSetCloth}">
	<img src="${imgPathCloth}" class="imgCloth" style="display: none;">
</c:forEach>
<img src="${elmsImage.imgPathFace}" id="imgFace" style="display: none;">
<img src="${elmsImage.imgPathPeople}" id="imgPeople" style="display: none;">
<input type="hidden" id="peopleNum" value="${elmsImage.peopleNum}">
<!-- --------------------------------------------------------------- -->

<c:set var="width" value="500" /> <!-- 実際に画面に表示する際の横幅[px] -->
<c:set var="height" value="500" /> <!-- 実際に画面に表示する際の高さ[px] -->
<canvas id="myCanvas" width="2000" height="2000" style="width:${width}px; height:${height}px;"></canvas>

<!-- ここまでコピー -->


<!-- 健康記録のカレンダー表示のテスト -->
<table>
<tr>
	<th>ユーザーID</th>
	<th>日付</th>
	<th>運動の種類</th>
	<th>運動時間</th>
	<th>現在の体重</th>
	<th>消費カロリー</th>
	<th>禁煙できたか(0か1)</th>
	<th>アルコール度数</th>
	<th>お酒の摂取量</th>
	<th>純アルコール摂取量</th>
	<th>睡眠時間</th>
	<th>摂取カロリー</th>
	<th>自由欄</th>
</tr>
<c:forEach var="hr" items="${hrList}">
	<tr>
	<td><c:out value="${hr.userId}" /></td>
	<td><c:out value="${hr.date}" /></td>
	<td><c:out value="${hr.exerciseType}" /></td>
	<td><c:out value="${hr.exerciseTime}" /></td>
	<td><c:out value="${hr.nowWeight}" /></td>
	<td><c:out value="${hr.calorieConsu}" /></td>
	<td><c:out value="${hr.nosmoke}" /></td>
	<td><c:out value="${hr.alcoholContent}" /></td>
	<td><c:out value="${hr.alcoholConsumed}" /></td>
	<td><c:out value="${hr.pureAlcoholConsumed}" /></td>
	<td><c:out value="${hr.sleepHours}" /></td>
	<td><c:out value="${hr.calorieIntake}" /></td>
	<td><c:out value="${hr.free}" /></td>
	</tr>
</c:forEach>
</table>

</body>

<script src="js/createTownAvatar.js"></script>

</html>