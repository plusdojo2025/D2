<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>健康記録登録</title>
</head>
<body>

  <div class="wrapper">
  <!-- ヘッダー（ここから） -->
  　<h1>
    <a href="MenuServlet">ケンコークラフト</a>
  　</h1>
  
  

  <!-- ヘッダー（ここまで） -->

  <!-- メイン（ここから） -->
  <h2>健康記録登録</h2>
  <form method="POST" id="" action="HealthRecordServlet">
    <table class="">
      <tr>
        <td>
         運動の種類と時間・現在の体重
        </td>
      </tr>
      <tr>
        <td>
          <label>種類</label>
          <select name="exercise_kind" id="">
            <option value="walking">ウォーキング 3.5メッツ</option>
            <option value="cycling">サイクリング4.0メッツ</option>
          </select>
        </td>
        <td>
          <label>時間 
          <input type="text" name="exercise_time">
          </label>
        </td>
        <td>
          <label>現在の体重
          <input type="text" name="now_weight">
          </label>
        </td>
      </tr>

      <tr>
        <td colspan="2" class="searchandreset">
          <input type="submit" id="register" name="submit" value="登録">
          <input type="reset" name="reset" value="リセット">
          <p id="error_message"></p>
        </td>
      </tr>
    </table>
  </form>
  <a href="MenuServlet">ホームへ戻る</a>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->
  <div id="footer">
    <p>&copy;Copyright Kyosuke Kimura. All rights reserved.</p>
  </div>
  <!-- フッター（ここまで） -->
</div>

</body>
</html>