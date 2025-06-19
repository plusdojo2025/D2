<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>カレンダー</title>
</head>
<body>
<!-- タイトル -->
<h1>ケンコークラフト</h1>


  <div>
  <!-- TODO: 〇月と書かれているところをクリックすると年と月を選べるプルダウンを作成する。 -->
 <%@ page import="java.time.LocalDate" %>
<%
    LocalDate now = LocalDate.now();
    LocalDate lastYearNextMonth = now.minusYears(1).plusMonths(1);

    // 例えば今が2025年6月なら
    // lastYearNextMonth は 2024年7月 になる

    String min = lastYearNextMonth.getYear() + "-" + String.format("%02d", lastYearNextMonth.getMonthValue());
    String max = now.getYear() + "-" + String.format("%02d", now.getMonthValue());
%>

<input type="month" id="monthInput" min="<%= min %>" max="<%= max %>" />

<script>
  document.getElementById('monthInput').addEventListener('change', function() {
    const ym = this.value;
    if (!ym) return;

    const parts = ym.split('-');
    const year = parts[0];
    const month = parseInt(parts[1]);

    const url = '/D2/CalendarServlet?month=' + month + '&year=' + year; // TODO: /D2/...に変更
    window.location.href = url;
  });
</script>



<!-- 次の月に移動 -->

  </div>
  


  
  


<div>
<!-- チェックボックス -->
<label><input type = "checkbox" name = "healthRecord">健康記録</label>
<label><input type = "checkbox" name = "rewardRecord">報酬記録</label>
<label><input type = "checkbox" name = "exercise">運動記録</label>
<label><input type = "checkbox" name = "noSmoke">禁煙</label>
<label><input type = "checkbox" name = "calorieIntake">摂取カロリー</label>
<label><input type = "checkbox" name = "sleepingTime">睡眠</label>
<label><input type = "checkbox" name = "alcohol">お酒</label>
</div>

<div>
<p>カレンダー</p>
</div>

<!-- 報酬記録 -->
<%
    java.util.List<dto.RewardDay> rewardList = (java.util.List<dto.RewardDay>) request.getAttribute("rewardList");

    if (rewardList != null && !rewardList.isEmpty()) {
        for (int i = 0; i < rewardList.size(); i++) {
            dto.RewardDay reward = rewardList.get(i);
%>
<p>
    <%= reward.getUserId() %> | <%= reward.getDate() %> | <%= reward.getRewardExplain() %>
</p>
<%
        }
    } else {
%>
<p>報酬記録はありません</p>
<%
    }
%>

<!-- 健康記録表示 -->

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.HealthRecord" %>

<h2>健康記録</h2>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.HealthRecord" %>

<h2>健康記録（簡易表示）</h2>

<%
    List<HealthRecord> healthList = (List<HealthRecord>) request.getAttribute("healthList");

    if (healthList != null && !healthList.isEmpty()) {
        for (HealthRecord record : healthList) {
%>
            <p>
                日付: <%= record.getDate() %><br>
                摂取カロリー: <%= record.getCalorieIntake() %><br>
                消費カロリー: <%= record.getCalorieConsu() %><br>
                運動: <%= record.getExerciseType() %>（<%= record.getExerciseTime() %>分）<br>
                飲酒量: <%= record.getAlcoholConsumed() %><br>
                禁煙ポイント: <%= record.getNosmoke() %><br>
                睡眠時間: <%= record.getSleepHours() %>時間<br>
                メモ: <%= record.getFree() %>
            </p>
            <hr>
<%
        }
    } else {
%>
        <p>健康記録はありません。</p>
<%
    }
%>
<div class="summary-box">
  <p>6月の統計</p>
  <p>
    合計消費カロリー：<%= request.getAttribute("sumCalorieConsumed") %> kcal　
    1日の平均純アルコール量：<%= request.getAttribute("avgPureAlcohol") %> g　
    1日の平均睡眠時間：<%= request.getAttribute("avgSleep") %> 時間
  </p>
  <p>
    合計禁煙日数：<%= request.getAttribute("sumNosmokeDays") %> 日　
    1日の平均消費カロリー：<%= request.getAttribute("avgConsumed") %> kcal　
    1日の平均摂取カロリー：<%= request.getAttribute("avgIntake") %> kcal
  </p>
</div>
<!-- アバター表示 -->
<div>
<%
    dto.TownAvatarElements avatar = (dto.TownAvatarElements) request.getAttribute("avatar");
%>

<p>服の画像:</p>
<% if (avatar.getCloth() != null) { %>
    <img src="<%= avatar.getCloth().getImagePath() %>" alt="服" />
<% } %>

<p>靴の画像:</p>
<% if (avatar.getShoe() != null) { %>
    <img src="<%= avatar.getShoe().getImagePath() %>" alt="靴" />
<% } %>

<p>帽子の画像:</p>
<% if (avatar.getHat() != null) { %>
    <img src="<%= avatar.getHat().getImagePath() %>" alt="帽子" />
<% } %>

<p>民族衣装の画像:</p>
<% if (avatar.getCostume() != null) { %>
    <img src="<%= avatar.getCostume().getImagePath() %>" alt="民族衣装" />
<% } %>

<p>建物の画像:</p>
<% if (avatar.getBuildings() != null) {
    for (dto.Image img : avatar.getBuildings()) { %>
        <img src="<%= img.getImagePath() %>" alt="建物" />
<%  } 
} %>

<p>顔色の画像:</p>
<% if (avatar.getFace() != null) { %>
    <img src="<%= avatar.getFace().getImagePath() %>" alt="顔色" />
<% } %>

<p>人数: <%= avatar.getPeopleCount() %></p>
<p>人の画像:</p>
<% if (avatar.getPeopleImage() != null) {
    for (int i=0; i < avatar.getPeopleCount(); i++) { %>
        <img src="<%= avatar.getPeopleImage().getImagePath() %>" alt="人" />
<%  }
} %>
</div>

<!-- 
/* TODO:カレンダーの日付をクリックしたときに、その日の健康記録登録・更新フォームをポップアップ
		 * 	healthRecoed.jspのコピペ
		 *  その日に既に健康記録がある場合は、健康記録内容をデフォルトでフォーム入力欄に表示
 -->
		 




</body>
</html>