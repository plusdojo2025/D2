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

<div>
<p>アバター・街並み表示</p>
</div>



</body>
</html>