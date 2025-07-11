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
<script src="<c:url value='/js/targetvalueform.js' />"></script>
<script src="<c:url value='/js/logout.js' />"></script>
<script src="<c:url value='/js/common.js' />"></script>

<link rel="stylesheet" href="<c:url value='/css/home.css'/>">
<link rel="stylesheet" href="<c:url value='/css/common.css'/>">

</head>
<body>

<header>
    <h1 class="logo-header">
    <img src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo" class="logo-img">
  </h1>
    <%
    	java.time.LocalDate now = java.time.LocalDate.now();
    	String currentDateStr = now.toString();
    	int currentYear = now.getYear();
    	int currentMonth = now.getMonthValue();
	%>
	<button id="help-btn" style="float: right;" onclick="showHelp()">ヘルプ</button>
    <nav class = "header-nav">
        <a href="<c:url value='/HealthRecordServlet' />?date=<%= currentDateStr %>">健康記録登録</a>
        <a href="<c:url value='/CalendarServlet' />?year=<%= currentYear %>&month=<%= currentMonth %>">カレンダー</a>
        <a href="<c:url value='/Summary' />">サマリー</a>
        <a href="<c:url value='/HistoryServlet' />">過去のファイル</a>
        <a href="javascript:void(0);" onclick="confirmLogout()">ログアウト</a>
    </nav>
</header>
<div class="info-box">
<h2>お知らせ</h2>
<p>現在の累計消費カロリー: ${totalCaloriesSum} kcal</p>
<p>次の国まであと ${requiredSteps} 歩 (消費カロリー ${remainingCalories} kcal)です。</p>
</div>
<div class="info-box2">
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
</div>
<!-- ポップアップフォーム（閉じられない） -->
<div id="popup" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:#ffffffcc; z-index:1000; padding:30px;">
    <!-- フォーム内容 -->
    <h2>今月の目標値とプロフィールを入力してください</h2>
    <form id="targetForm" action="<c:url value='/HomeServlet' />" method="POST" onsubmit="return validateForm()">
        <h3>目標値</h3>
        <label>目標体重 (kg): <input type="number" step="0.1" name="target_weight" min="0" required></label><br>
        <label>純アルコール摂取量 (g/日): 
            <input type="number" step="1" name="pure_alcohol_consumed" min="0" required>
        </label>
        <span class="hint-text">(男性は40g、女性は20g以下が推奨)</span><br>
        <label>睡眠時間 (時間): 
            <input type="number" step="0.5" name="sleep_time" min="0" required>
        </label>
        <span class="hint-text">(6時間以上推奨)</span><br>
        <label>カロリー摂取量 (kcal/日): 
            <input type="number" step="100" name="calorie_intake" min="0" required>
        </label>
        <span class="hint-text">(男性は2600前後、女性は2200前後推奨)</span><br>

        <h3>プロフィール</h3>
        <label>体重 (kg): <input type="number" step="0.1" name="weight" min="0" required></label><br>
        <label>身長 (cm): <input type="number" step="0.1" name="height" min="0" required></label><br>
        <label>年齢: <input type="number" name="age" min="0" max="120" required></label><br>
        <label>活動レベル: 
            <select name="active_level_id" required>
                <option value="">選択してください</option>
                <option value="1">1: 非常に低い（デスクワーク中心）</option>
                <option value="2">2: 低い（軽い日常活動）</option>
                <option value="3">3: 中程度（週3〜5回運動）</option>
                <option value="4">4: 高い（毎日運動・肉体労働）</option>
                <option value="5">5: 非常に高い（アスリートレベル）</option>
            </select>
        </label><br>
		<h3>性別</h3>
         <div class="target-label">
            <label for="sex">性別:</label>
            <select name="sex" id="sex" required>
                <option value="">選択してください</option>
                <option value="M">男性</option>
                <option value="F">女性</option>
            </select>
        </div>
        <br>
        <button type="submit">登録</button>
    </form>

</div>


<h3>今月の街並み・アバター</h3>


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

<c:set var="width" value="1000" /> <!-- 実際に画面に表示する際の横幅[px] -->
<canvas id="imageCanvas" width="1627" height="1021" style="width:${width}px;"></canvas>

<script src="<c:url value='/js/createTownAvatar.js' />"></script>
<script src="<c:url value='/js/home.js' />"></script>

  <div id="footer">
    <p class="copyright">&copy;2025 視力検査Dチーム 健康管理アプリ</p>
  </div>
  
</body>
</html>
