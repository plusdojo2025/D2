<%@ page import="dto.TargetValue" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
TargetValue targetValue = (TargetValue) request.getAttribute("targetValue");
boolean needsInput = false;
if (targetValue == null ||
    targetValue.getPure_alcohol_consumed() == 0.0f ||
    targetValue.getSleep_time() == 0.0f ||
    targetValue.getCalorie_intake() == 0 ||
    targetValue.getTarget_weight() == 0.0f) {
    needsInput = true;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<script>
window.onload = function () {
    const needsInput = <%= needsInput %>;
    if (needsInput) {
        document.getElementById("popup").style.display = "block";
    }
};
</script>
</head>
<body>
<header>
    <h1>ケンコークラフト ホーム</h1>
    <nav>
        <a href="HealthRecordServlet?date=">健康記録登録</a>
        <a href="CalendarServlet?month=6&year=2025">カレンダー</a>
        <a href="logout">ログアウト</a>
    </nav>
</header>

<h2>お知らせ</h2>
<p>現在の累計消費カロリー: ${totalCaloriesSum} kcal</p>
<p>次の目的地まであと ${requiredSteps} 歩 (消費カロリー ${remainingCalories} kcal)です。</p>

<h2>今月の目標値</h2>
<% if (targetValue != null) { %>
<p>
    ユーザーID: <%= targetValue.getUser_id() %><br>
    月: <%= targetValue.getMonth() %><br>
    目標体重: <%= targetValue.getTarget_weight() %> kg<br>
    純アルコール摂取量: <%= targetValue.getPure_alcohol_consumed() %> g/日<br>
    睡眠時間: <%= targetValue.getSleep_time() %> 時間<br>
    カロリー摂取量: <%= targetValue.getCalorie_intake() %> kcal/日
</p>
<% } else { %>
<p>目標値が設定されていません。</p>
<% } %>

<!-- ポップアップフォーム（閉じられない） -->
<div id="popup" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%; background:#ffffffcc; z-index:1000; padding:30px;">
    <!-- フォーム内容 -->
    <h2>今月の目標値とプロフィールを入力してください</h2>
    <form id="targetForm" action="${pageContext.request.contextPath}/HomeServlet" method="POST">
        <h3>目標値</h3>
        <label>目標体重 (kg): <input type="number" step="0.1" name="target_weight" required></label><br>
        <label>純アルコール摂取量 (g/日): <input type="number" step="0.1" name="pure_alcohol_consumed" required></label><br>
        <label>睡眠時間 (時間): <input type="number" step="0.1" name="sleep_time" required></label><br>
        <label>カロリー摂取量 (kcal/日): <input type="number" name="calorie_intake" required></label><br>

        <h3>プロフィール</h3>
        <label>体重 (kg): <input type="number" step="0.1" name="weight" required></label><br>
        <label>身長 (cm): <input type="number" step="0.1" name="height" required></label><br>
        <label>年齢: <input type="number" name="age" required></label><br>
        <label>活動レベル (1~5): <input type="number" name="active_level_id" min="1" max="5" required></label><br>
性別:<select name="sex" required>
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
</body>
</html>
