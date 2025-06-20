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
<a href="HomeServlet">ケンコークラフト</a>


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
    合計消費カロリー：<%= String.format("%.1f", request.getAttribute("sumCalorieConsumed")) %> kcal　
    1日の平均純アルコール量：<%= String.format("%.1f", request.getAttribute("avgPureAlcohol")) %> g　
    1日の平均睡眠時間：<%= String.format("%.1f", request.getAttribute("avgSleep")) %> 時間
  </p>
  <p>
    合計禁煙日数：<%= request.getAttribute("sumNosmokeDays") %> 日　
    1日の平均消費カロリー：<%= String.format("%.1f", request.getAttribute("avgConsumed")) %> kcal　
    1日の平均摂取カロリー：<%= String.format("%.1f", request.getAttribute("avgIntake")) %> kcal
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
		 
		 <!-- ポップアップエリア -->
<%
  String yearStr = request.getParameter("year");
  String monthStr = request.getParameter("month");

  int year = now.getYear(); // デフォルトは今
  int month = now.getMonthValue();

  try {
      if (yearStr != null) year = Integer.parseInt(yearStr);
      if (monthStr != null) month = Integer.parseInt(monthStr);
  } catch (NumberFormatException e) {
      // 無視して今月のままでOK
  }

  for (int i = 1; i <= 31; i++) {
      String day = String.format("%02d", i);
      String date = year + "-" + String.format("%02d", month) + "-" + day;
%>
  <a href="#" onclick="openPopup('<%= date %>'); return false;"><%= i %>日</a><br>
<%
  }
%>

<!-- ポップアップ本体（非表示にしておく） -->
<div id="popup" style="display:none; position:fixed; top:5%; left:5%; width:90%; height:90%; background:white; border:2px solid black; overflow:auto; z-index:9999; padding:1em;">
  <button onclick="closePopup()">閉じる</button>

  <h2>健康記録登録</h2>
  <form method="POST" action="HealthRecordServlet">
    <table>
      <tr>
        <td><label>現在の体重 <input type="text" name="now_weight" required>kg</label></td>
      </tr>

      <tr><td>運動の種類と時間</td></tr>
      <tr>
        <td>
          <label>種類
            <select name="exercise_type1" onchange="updateMets(1)" id="exercise_type1">
              <option value="ウォーキング">ウォーキング 3.5メッツ</option>
              <option value="サイクリング">サイクリング 4.0メッツ</option>
            </select>
            <input type="hidden" name="mets1" id="mets1" value="3.5">
          </label>
        </td>
        <td><label>時間 <input type="text" name="exercise_time1" required>分</label></td>
      </tr>

      <tr>
        <td>
          <label>種類
            <select name="exercise_type2" onchange="updateMets(2)" id="exercise_type2">
              <option value="ウォーキング">ウォーキング 3.5メッツ</option>
              <option value="サイクリング">サイクリング 4.0メッツ</option>
            </select>
            <input type="hidden" name="mets2" id="mets2" value="3.5">
          </label>
        </td>
        <td><label>時間 <input type="text" name="exercise_time2">分</label></td>
      </tr>

      <tr>
        <td><label>禁煙できたか：<input type="radio" name="no_smoke" value="1" checked>できた</label></td>
        <td><input type="radio" name="no_smoke" value="0">できなかった</td>
      </tr>

      <tr>
        <td>飲酒量とアルコール度数</td>
        <td><label>度数：
          <select name="alcohol_content1">
            <option value="5">ビール</option>
            <option value="40">ウイスキー</option>
          </select> %
        </label></td>
        <td><label>量：
          <select name="alcohol_consumed1">
            <option value="300">中ジョッキ</option>
            <option value="100">ロックグラス</option>
          </select> ml
        </label></td>
      </tr>

      <tr>
        <td><label>度数：
          <select name="alcohol_content2">
            <option value="5">ビール</option>
            <option value="40">ウイスキー</option>
          </select> %
        </label></td>
        <td><label>量：
          <select name="alcohol_consumed2">
            <option value="300">中ジョッキ</option>
            <option value="100">ロックグラス</option>
          </select> ml
        </label></td>
      </tr>

      <tr>
        <td><label>睡眠時間
          <select name="sleep_hours">
            <option value="0.0">0</option>
            <option value="7.0">7.0</option>
            <option value="7.5">7.5</option>
            <option value="8.0">8.0</option>
          </select> 時間
        </label></td>
      </tr>

      <tr>
        <td><label>摂取カロリー：
          <select name="calorie_intake">
            <option value="0">0</option>
            <option value="500">500</option>
            <option value="600">600</option>
            <option value="700">700</option>
            <option value="2700">2700</option>
          </select> kcal
        </label></td>
      </tr>

      <tr>
        <td><label>自由欄<br><input type="text" name="free"></label></td>
      </tr>

      <tr>
        <td colspan="2">
          <input type="hidden" name="date" id="popupDate">
          <input type="hidden" name="fromCalendar" value="true">
          <input type="submit" value="登録">
        </td>
      </tr>
    </table>
  </form>
</div>
<!--  -->
<script>
function openPopup(date) {
  document.getElementById("popup").style.display = "block";
  document.getElementById("popupDate").value = date;

  fetch('HealthRecordFormServlet?date=' + date)
    .then(response => response.text())
    .then(script => {
      console.log("返ってきたスクリプト:");
      console.log(script);
      eval(script); // サーバーからのJSを実行
    });
}


function closePopup() {
  document.getElementById("popup").style.display = "none";
}
</script>

 
		 




</body>
</html>