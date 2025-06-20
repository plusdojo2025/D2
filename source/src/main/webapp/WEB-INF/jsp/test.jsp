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

<c:set var="width" value="500" /> <!-- 実際に画面に表示する際の横幅[px] -->
<canvas id="imageCanvas" width="1400" height="1000" style="width:${width}px;"></canvas>

<!-- ここまでコピー -->


<!-- 健康記録のカレンダー表示のテスト -->
<!-- <table>
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
</table> -->

</body>

<script src="js/createTownAvatar.js"></script>

</html>