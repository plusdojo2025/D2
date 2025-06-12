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
          <label>種類
          <select name="exercise_kind" id="">
            <option value="walking">ウォーキング 3.5メッツ</option>
            <option value="cycling">サイクリング4.0メッツ</option>
          </select>
          </label>
        </td>
        <td>
          <label>時間 
          <input type="text" name="exercise_time">分
          </label>
        </td>
        <td>
          <label>現在の体重
          <input type="text" name="now_weight">kg
          </label>
        </td>
      </tr>
      
      <tr>
      　　<td>
        <label>
          禁煙できたか：
          <input type="radio" name="smoking" value="success">できた
        </label>
        </td>
        <td>
        <label>
          <input type="radio" name="smoking" value="failure">できなかった
        </label>
        </td>
      </tr>
      
      <tr>
        <td>
          飲酒量とアルコール度数
        </td>
        <td>
        <label>
          度数：
          <select name="">
          　　<option value="5">ビール</option>
          　　<option value="40">ウイスキー</option>
          </select>
          %
         </label>
        </td>
        <td>
        <label>
           量：
           <select name="">
             <option value="300">中ジョッキ</option>
             <option value="100">ロックグラス</option>
           </select> 
           ml
        </label>
        </td>
      </tr>
      
      <tr>
       <td>
       <label>
       睡眠時間
        <select name="">
          <option value="7">7.0</option>
          <option value="7.5">7.5</option>
          <option value="8.0">8.0</option>
        </select>
        時間
        </label>
       </td>
      </tr>
      
      <tr>
        <td>
        <label>
        摂取カロリー：
        <select>
          <option value="0">0</option>
          <option value="500">500</option>
          <option value="600">600</option>
          <option value="700">700</option>
        </select>
        kcal
        </label>
        </td>
      </tr>
      
      <tr>
        <td>
        <label>自由欄<br>
        <input type="text" name=free>
        </label>
        </td>
      </tr>
      
      <tr>
        <td colspan="2" class="searchandreset">
          <input type="submit" id="register" name="submit" value="登録">
          <p id="error_message"></p>
        </td>
      </tr>
    </table>
  </form>
  <!-- メイン（ここまで） -->
  <!-- フッター（ここから） -->
  <!-- フッター（ここまで） -->
</div>

</body>
</html>