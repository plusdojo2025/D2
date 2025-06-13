<%@ page import="dto.TargetValue" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ホーム画面</title>
</head>
<body>
    <header>
        <h1>ケンコークラフト ホーム</h1>
        <nav>
            <a href="record.jsp">健康記録登録</a>
            <a href="calendar.jsp">カレンダー</a>
            <a href="logout">ログアウト</a>
        </nav>
    </header>

    <h2>お知らせ</h2>
    <p>次の目的地まで○○</p>

    <h1>目標値</h1>
    <%
    TargetValue targetValue = (TargetValue) request.getAttribute("targetValue");
    if (targetValue != null) {
%>
    <p>月: <%= targetValue.getMonth() %></p>
    <p>純アルコール摂取量: <%= targetValue.getPure_alcohol_consumed() %> g</p>
    <p>睡眠時間: <%= targetValue.getSleep_time() %> 時間</p>
    <p>カロリー摂取量: <%= targetValue.getCalorie_intake() %> kcal</p>
    <p>目標体重: <%= targetValue.getTarget_weight() %> kg</p>
<%
    } else {
%>
    <p>目標値が設定されていません。</p>
<%
    }
%>
    <h3>今月の街並み・アバター</h3>
    <p>ここに画像やアバターが表示されます。</p>

</body>
</html>
