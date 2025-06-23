<%@ page import="dto.TargetValue" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
TargetValue targetValue = (TargetValue) request.getAttribute("targetValue");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<script>
window.onload = function () {
    const needsInput = "${needsInput}" === "true";
    if (needsInput) {
        document.getElementById("popup").style.display = "block";
    }
};
</script>
<script src="${pageContext.request.contextPath}/js/targetvalueform.js"></script>
<script src="${pageContext.request.contextPath}/js/logout.js"></script>
<script src="${pageContext.request.contextPath}/js/home.js"></script>

</head>
<body>
<header>
    <h1>ケンコークラフト ホーム</h1>
    <%
    	java.time.LocalDate now = java.time.LocalDate.now();
    	String currentDateStr = now.toString();
    	int currentYear = now.getYear();
    	int currentMonth = now.getMonthValue();
	%>
    <nav>
        <a href="HealthRecordServlet?date=<%= currentDateStr %>">健康記録登録</a>
        <a href="CalendarServlet?year=<%= currentYear %>&month=<%= currentMonth %>">カレンダー</a>
        <a href="javascript:void(0);" onclick="confirmLogout()">ログアウト</a>
    </nav>
</header>

<h2>お知らせ</h2>
<p>現在の累計消費カロリー: ${totalCaloriesSum} kcal</p>
<p>次の目的地まであと ${requiredSteps} 歩 (消費カロリー ${remainingCalories} kcal)です。</p>

<h2>今月の目標値</h2>
<% if (targetValue != null) { %>
<p>
    ユーザーID: <%= targetValue.getUser_id() %>
    現在の月: <%= java.time.LocalDate.now().getMonthValue() %>月
    目標体重: <%= targetValue.getTarget_weight() %> kg<br>
    純アルコール摂取量: <%= targetValue.getPure_alcohol_consumed() %> g/日
    睡眠時間: <%= targetValue.getSleep_time() %> 時間
    カロリー摂取量: <%= targetValue.getCalorie_intake() %> kcal/日
</p>
<% } else { %>
<p>目標値が設定されていません。</p>
<% } %>

<!-- ポップアップフォーム（閉じられない） -->
<div id="popup" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:#ffffffcc; z-index:1000; padding:30px;">
    <!-- フォーム内容 -->
    <h2>今月の目標値とプロフィールを入力してください</h2>
    <form id="targetForm" action="${pageContext.request.contextPath}/HomeServlet" method="POST" onsubmit="return validateForm()">
    <h3>目標値</h3>
    <label>目標体重 (kg): <input type="number" step="0.1" name="target_weight" min="0" required></label><br>
    <label>純アルコール摂取量 (g/日): <input type="number" step="0.1" name="pure_alcohol_consumed" min="0" required></label><br>
    <label>睡眠時間 (時間): <input type="number" step="0.5" name="sleep_time" min="0" required></label><br>
    <label>カロリー摂取量 (kcal/日): <input type="number" step="1" name="calorie_intake" min="0" required></label><br>

    <h3>プロフィール</h3>
    <label>体重 (kg): <input type="number" step="0.1" name="weight" min="0" required></label><br>
    <label>身長 (cm): <input type="number" step="0.1" name="height" min="0" required></label><br>
    <label>年齢: <input type="number" name="age" min="0" required></label><br>
    <label>活動レベル (1~5): <input type="number" name="active_level_id" min="1" max="5" required></label><br>

    性別:
    <select name="sex" required>
        <option value="">選択してください</option>
        <option value="M">男性</option>
        <option value="F">女性</option>
    </select>
    <br>
    <button type="submit">登録</button>
</form>
</div>

<h3>今月の街並み・アバター</h3>
<p>ここに画像やアバターが表示されます。</p>

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

<script src="js/createTownAvatar.js"></script>

</body>
</html>
