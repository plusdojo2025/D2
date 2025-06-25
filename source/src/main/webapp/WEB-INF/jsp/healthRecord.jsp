<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/css/healthRecord.css'/>">
<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
<title>健康記録登録</title>
</head>

<body>

	<div class="wrapper">
		<!-- ヘッダー（ここから） -->
		<h1 class="logo-header">
			<a href="<c:url value='/HomeServlet' />"> <img
				src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo"
				class="logo-img"></a>
		</h1>
		<!-- ヘッダー（ここまで） -->
		<!-- メイン（ここから） -->
		<h2>健康記録登録</h2>
		<form method="POST" id="form"
			action="<c:url value='/HealthRecordServlet' />">

			<label> 現在の体重[kg]： <input type="number" step="0.5"
				name="now_weight" value="60.0">
			</label> <br> 運動の種類と時間：
			<!-- ボタンを押したら新しい入力欄が追加される -->
			<button type="button" id="add_exercise_button">＋ 運動を追加</button>
			<button type="button" id="remove_exercise_button">－</button>
			<br>
			<div id="exercise_container" style="display: none;">
				<div class="exercise_field">
					<div class="input-group">
						<label for="exercise_type0">種類とメッツ：</label> <input type="text"
							name="exercise_type0" id="exercise_type0" placeholder="例：ジョギング">
						<input type="number" name="mets0" id="mets0" placeholder="例：6.0"
							step="0.5" min="0.0">
					</div>

					<div class="input-group">
						<label for="exercise_time0">時間：</label> <input type="number"
							name="exercise_time0" id="exercise_time0" min="0"
							placeholder="例：30">分
					</div>
					<select name="exercise_select0" id="exercise_select0">
						<option value="">参考：種類とメッツ</option>
					</select>
				</div>
			</div>


			<br> <label> 禁煙できたか： <input type="radio" name="no_smoke"
				value="1" checked>できた
			</label> <label> <input type="radio" name="no_smoke" value="0">できなかった
			</label> <br> 飲酒量とアルコール度数：
			<!-- ボタンを押したら新しい入力欄が追加される -->
			<button type="button" id="add_alcohol_button">＋ 飲酒を追加</button>
			<button type="button" id="remove_alcohol_button">－</button>
			<br>
			<div id="alcohol_container" style="display: none;">
				<div class="alcohol_field">
					<!-- アルコール度数 -->
					<div class="input-group">
						<label for="alcohol_content0">アルコール度数 [%]:</label> <input
							type="number" id="alcohol_content0" name="alcohol_content0"
							min=0.0 max=100.0 step="1.0" placeholder="例：5.0"> <select
							id="categorySelect0">
							<option value="">参考: アルコール度数</option>
						</select> <select id="typeSelect0" disabled>
							<option value=""></option>
						</select>
					</div>

					<!-- 摂取量 -->
					<div class="input-group">
						<label for="alcohol_consumed0">摂取量 [ml]:</label> <input
							type="number" id="alcohol_consumed0" name="alcohol_consumed0"
							step="10" min="0" placeholder="例：500"> <select
							id="glassSelect0">
							<option value="">参考: 摂取量</option>
						</select> <input type="number" id="cupCount0" min="1" value="1" disabled>杯
					</div>
				</div>
			</div>

			<br> <label> 睡眠時間[h]： <input type="number"
				name="sleep_hours" step="0.5" default="7.0" value="7.0"> <!-- <select name="sleep_hours">
						<option value="0.0">0</option>
						<option value="7.0">7.0</option>
						<option value="7.5">7.5</option>
						<option value="8.0">8.0</option>
					</select> 時間 -->
			</label> <br> <label> 摂取カロリー[kcal]： <input type="number"
				name="calorie_intake" min="0" step="100" value="2000"> <!-- <select name="calorie_intake">
						<option value="0">0</option>
						<option value="500">500</option>
						<option value="600">600</option>
						<option value="700">700</option>
						<option value="2700">2700</option>
					</select> kcal -->
			</label> <br> <label>自由欄<br> <input type="text" name=free></label>
			<br>
			<!-- 今リクエストスコープで保持している日付データは登録ボタンを押すとなくなってしまうため、hiddenでデータを保持しておく -->
			<!-- 登録か更新ボタンの切換え -->
			<input type="hidden" name="date" value="${date}"> <input
				type="submit" id="register" name="submit" value="登録">
			<p id="error_message"></p>

		</form>
		<!-- メイン（ここまで） -->
		<!-- フッター（ここから） -->
		<div id="footer">
			<p class="copyright">&copy;2025 視力検査Dチーム 健康管理アプリ</p>
		</div>
		<!-- フッター（ここまで） -->
	</div>

	<script src="<c:url value='/js/healthRecord.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>