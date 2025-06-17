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
			<a href="HomeServlet">ケンコークラフト</a>
		</h1>



		<!-- ヘッダー（ここまで） -->

		<!-- メイン（ここから） -->
		<h2>健康記録登録</h2>
		<form method="POST" id="" action="HealthRecordServlet">
			<table class="">
				<tr>
					<td><label>現在の体重 <input type="text" name="now_weight">kg
					</label></td>
				</tr>
				<tr>
					<td>運動の種類と時間・現在の体重</td>
				</tr>
				<tr>
					<td><label>種類 <select name="exercise_type1" id="">
								<option value="ウォーキング">ウォーキング 3.5メッツ</option>
								<option value="サイクリング">サイクリング4.0メッツ</option>
						</select> <input type="hidden" name="mets1" id="mets1" value="3.5"> 
						<script>
							function updateMets() {
								const select = document.getElementById('exercise_type1');
								const metsInput = document.getElementById('mets1');

								if (select.value === 'ウォーキング') {
									metsInput.value = '3.5';
								} else if (select.value === 'サイクリング') {
									metsInput.value = '4.0';
								} else {
									metsInput.value = '0';
								}
							}

							// ページ読み込み時にも初期化しておく
							window.onload = updateMets;
						</script>

					</label></td>
					<td><label>時間 <input type="text" name="exercise_time1">分
					</label></td>
					
				</tr>
				<tr>
					<td><label>種類 <select name="exercise_type2" id="">
								<option value="ウォーキング">ウォーキング 3.5メッツ</option>
								<option value="サイクリング">サイクリング 4.0メッツ</option>
						</select> <input type="hidden" name="mets2" id="mets2" value="3.5"> 
						<script>
							function updateMets() {
								const select = document.getElementById('exercise_type2');
								const metsInput = document.getElementById('mets2');

								if (select.value === 'ウォーキング') {
									metsInput.value = '3.5';
								} else if (select.value === 'サイクリング') {
									metsInput.value = '4.0';
								} else {
									metsInput.value = '0';
								}
							}

							// ページ読み込み時にも初期化しておく
							window.onload = updateMets;
						</script>

					</label></td>
					<td><label>時間 <input type="text" name="exercise_time2">分
					</label></td>
				</tr>


				<tr>
					<td><label> 禁煙できたか： <input type="radio"
							name="no_smoke" value="1" checked>できた
					</label></td>
					<td><label> <input type="radio" name="no_smoke"
							value="0">できなかった
					</label></td>
				</tr>

				<tr>
					<td>飲酒量とアルコール度数</td>
					<td><label> 度数： <select name="alcohol_content1">
								<option value="5">ビール</option>
								<option value="40">ウイスキー</option>
						</select> %
					</label></td>
					<td><label> 量： <select name="alcohol_consumed1">
								<option value="300">中ジョッキ</option>
								<option value="100">ロックグラス</option>
						</select> ml
					</label></td>
				</tr>

				<tr>
					<td>飲酒量とアルコール度数</td>
					<td><label> 度数： <select name="alcohol_content2">
								<option value="5">ビール</option>
								<option value="40">ウイスキー</option>
						</select> %
					</label></td>
					<td><label> 量： <select name="alcohol_consumed2">
								<option value="300">中ジョッキ</option>
								<option value="100">ロックグラス</option>
						</select> ml
					</label></td>
				</tr>

				<tr>
					<td><label> 睡眠時間 <select name="sleep_hours">
								<option value="7">7.0</option>
								<option value="7.5">7.5</option>
								<option value="8.0">8.0</option>
						</select> 時間
					</label></td>
				</tr>

				<tr>
					<td><label> 摂取カロリー： <select name="calorie_intake">
								<option value="0">0</option>
								<option value="500">500</option>
								<option value="600">600</option>
								<option value="700">700</option>
						</select> kcal
					</label></td>
				</tr>

				<tr>
					<td><label>自由欄<br> <input type="text" name=free>
					</label></td>
				</tr>

				<tr>
					<td colspan="2" class="searchandreset">
						<!-- 今リクエストスコープで保持している日付データは登録ボタンを押すとなくなってしまうため、hiddenでデータを保持しておく -->
						<!-- 登録か更新ボタンの切換え --> <input type="hidden" name="date"
						value="${date}"> <input type="submit" id="register"
						name="submit" value="登録"> <input type="hidden" name="date"
						value="${date}"> <input type="submit" id="update"
						name="submit" value="更新">
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