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


<!-- 月セレクトボタン -->
<!-- 前の月に移動 -->
  <div>
    <form method="get" action="simpleCalendar.jsp" style="display: inline;">
      <input type="hidden" name="month" value=""/>
      <input type="submit" value="◀ 前月"/>
    </form>
   <h2>月</h2>
<!-- 次の月に移動 -->
    <form method="get" action="simpleCalendar.jsp" style="display: inline;">
      <input type="hidden" name="month" value=""/>
      <input type="submit" value="翌月 ▶"/>
    </form>
  </div>
  
  <!-- TODO: 〇月と書かれているところをクリックすると年と月を選べるプルダウンを作成する。 -->
  <a href="CalendarServlet?month=6&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=7&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=8&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=9&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=10&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=11&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=12&year=2025">カレンダー</a> 
  <a href="CalendarServlet?month=1&year=2026">カレンダー</a> 
  
  


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
<div>
<p>アバター・街並み表示</p>
<%
    String clothImagePath = (String) request.getAttribute("clothImagePath");
    String shoeImagePath = (String) request.getAttribute("shoeImagePath");
    String hatImagePath = (String) request.getAttribute("hatImagePath");
    String costumeImagePath = (String) request.getAttribute("costumeImagePath");
%>

<p>服の画像:</p>
<% if (clothImagePath != null && !clothImagePath.isEmpty()) { %>
    <img src="<%= clothImagePath %>" alt="服の画像" />
<% } else { %>
    <p>服の画像はありません</p>
<% } %>

<p>靴の画像:</p>
<% if (shoeImagePath != null && !shoeImagePath.isEmpty()) { %>
    <img src="<%= shoeImagePath %>" alt="靴の画像" />
<% } else { %>
    <p>靴の画像はありません</p>
<% } %>

<p>帽子の画像:</p>
<% if (hatImagePath != null && !hatImagePath.isEmpty()) { %>
    <img src="<%= hatImagePath %>" alt="帽子の画像" />
<% } else { %>
    <p>帽子の画像はありません</p>
<% } %>

<p>民族衣装の画像:</p>
<% if (costumeImagePath != null && !costumeImagePath.isEmpty()) { %>
    <img src="<%= costumeImagePath %>" alt="民族衣装の画像" />
<% } else { %>
    <p>民族衣装の画像はありません</p>
<% } %>

<%
    java.util.List<dto.Image> buildingImages = (java.util.List<dto.Image>) request.getAttribute("buildingImages");
%>

<p>建物の画像:</p>
<%
    if (buildingImages != null && !buildingImages.isEmpty()) {
        for (int i = 0; i < buildingImages.size(); i++) {
            dto.Image img = buildingImages.get(i);
%>
            <p><%= (i + 1) %>つ目の建物画像:</p>
            <img src="<%= img.getImagePath() %>" alt="建物の画像 <%= (i + 1) %>" />
<%
        }
    } else {
%>
    <p>建物の画像はありません</p>
<%
    }
%>

<% 
    String faceImagePath = (String) request.getAttribute("faceImagePath");
%>
<p>顔色の画像:</p>
<% if (faceImagePath != null && !faceImagePath.isEmpty()) { %>
    <img src="<%= faceImagePath %>" alt="顔色の画像" />
<% } else { %>
    <p>顔色の画像はありません</p>
<% } %>

<%
    String peopleImagePath = (String) request.getAttribute("peopleImagePath");
    Integer peopleCount = (Integer) request.getAttribute("peopleCount");
    if (peopleCount == null) peopleCount = 0;
%>

<p>禁煙達成者の人数: <%= peopleCount %></p>

<% if (peopleImagePath != null && !peopleImagePath.isEmpty() && peopleCount > 0) { %>
    <% for (int i = 0; i < peopleCount; i++) { %>
        <img src="<%= peopleImagePath %>" alt="禁煙者の人の画像" />
    <% } %>
<% } else { %>
    <p>禁煙達成者の画像はありません</p>
<% } %>
</div>

<!-- 
/* TODO:カレンダーの日付をクリックしたときに、その日の健康記録登録・更新フォームをポップアップ
		 * 	healthRecoed.jspのコピペ
		 *  その日に既に健康記録がある場合は、健康記録内容をデフォルトでフォーム入力欄に表示
 -->
		 




</body>
</html>