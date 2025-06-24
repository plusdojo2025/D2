<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>カレンダー</title>
	<link rel="stylesheet" href="<c:url value='/css/calendar.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>

<body>
	<!-- タイトル -->
	<h1><a href="<c:url value='/HomeServlet' />">ケンコークラフト</a></h1>

	<div>
		<!-- TODO: 〇月と書かれているところをクリックすると年と月を選べるプルダウンを作成する。 -->
		<%@ page import="java.time.LocalDate"%>
		<%
		LocalDate now = LocalDate.now();
		LocalDate lastYearNextMonth = now.minusYears(1).plusMonths(1);

		// 例えば今が2025年6月なら
		// lastYearNextMonth は 2024年7月 になる

		String min = lastYearNextMonth.getYear() + "-" + String.format("%02d", lastYearNextMonth.getMonthValue());
		String max = now.getYear() + "-" + String.format("%02d", now.getMonthValue());
		%>

		<input type="month" id="monthInput" min="<%=min%>" max="<%=max%>" />

		<script>
			document.getElementById('monthInput').addEventListener(
					'change',
					function() {
						const ym = this.value;
						if (!ym)
							return;

						const parts = ym.split('-');
						const year = parts[0];
						const month = parseInt(parts[1]);

						const url = '/D2/CalendarServlet?month=' + month
								+ '&year=' + year; // TODO: /D2/...に変更
						window.location.href = url;
					});
		</script>



		<!-- 次の月に移動 -->

	</div>



	<!-- カレンダー -->
	<!-- --------------------------------------------------------------- -->
	<div>
		<!-- チェックボックス -->
		<label><input type="checkbox" name="cbhealthRecord"	id="cbHealth" checked>健康記録</label>
		<label><input type="checkbox" name="rewardRecord" id="cbReward" checked>報酬記録</label>
		<label><input type="checkbox" name="exercise" id="cbExercise" checked>運動記録</label>
		<label><input type="checkbox" name="noSmoke" id="cbSmoke" checked>禁煙</label> 
		<label><input type="checkbox" name="calorieIntake" id="cbCalorieIntake" checked>摂取カロリー</label>
		<label><input type="checkbox" name="calorieCounsu" id="cbCalorieConsu" checked>消費カロリー</label> 
		<label><input type="checkbox" name="sleepingTime" id="cbSleep" checked>睡眠</label>
		<label><input type="checkbox" name="alcohol" id="cbAlcohol" checked>お酒</label>
		<label><input type="checkbox" name="memo" id="cbMemo" checked>メモ</label>
	</div>
	
	<table>
        <tr>
            <th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
        </tr>
        <tbody id="calendar-body"></tbody>
    </table> 
	<!-- --------------------------------------------------------------- -->

	<!-- アバター画像 -->
	<!-- --------------------------------------------------------------- -->
	 <c:forEach var="build" items="${avatar.buildings}">
		<div class="imgBuild" id="${build.imagePath}"></div>
	</c:forEach>
	<div class="cloth" id="${avatar.cloth.imagePath}"></div>
	<div class="cloth" id="${avatar.shoe.imagePath}"></div>
	<div class="cloth" id="${avatar.hat.imagePath}"></div>
	<div class="cloth" id="${avatar.costume.imagePath}"></div>
	<div class="imgPeople" id="${avatar.peopleImage.imagePath}"></div>
	<div class="imgFace" id="${avatar.face.imagePath}"></div>
	<input type="hidden" id="peopleNum" value="${avatar.peopleCount}">

	<c:set var="width" value="500" /> <!-- 実際に画面に表示する際の横幅[px] -->
	<canvas id="imageCanvas" width="1627" height="1021" style="width:${width}px;"></canvas>
	<!-- --------------------------------------------------------------- -->
	

	

	<!-- 統計値 -->
	<!-- --------------------------------------------------------------- -->
	<div class="summary-box">
		<%
	String[] monthNames = {
		"1月", "2月", "3月", "4月", "5月", "6月",
		"7月", "8月", "9月", "10月", "11月", "12月"
	};
	int displayMonth = (Integer) request.getAttribute("displayMonth");
	%>

	<p><%= monthNames[displayMonth - 1] %>の統計</p>
		<p>
			合計消費カロリー：<%=String.format("%.1f", request.getAttribute("sumCalorieConsumed"))%>
			kcal 1日の平均純アルコール量：<%=String.format("%.1f", request.getAttribute("avgPureAlcohol"))%>
			g 1日の平均睡眠時間：<%=String.format("%.1f", request.getAttribute("avgSleep"))%>
			時間
		</p>
		<p>
			合計禁煙日数：<%=request.getAttribute("sumNosmokeDays")%>
			日 1日の平均消費カロリー：<%=String.format("%.1f", request.getAttribute("avgConsumed"))%>
			kcal 1日の平均摂取カロリー：<%=String.format("%.1f", request.getAttribute("avgIntake"))%>
			kcal
		</p>
	</div>

	<!-- --------------------------------------------------------------- -->
	

	<!-- 
	/* TODO:カレンダーの日付をクリックしたときに、その日の健康記録登録・更新フォームをポップアップ
		 * 	healthRecoed.jspのコピペ
		 *  その日に既に健康記録がある場合は、健康記録内容をデフォルトでフォーム入力欄に表示
		 */
	-->
		 

	<!-- ポップアップ本体（非表示にしておく） -->

<div id="popup"
data-readonly="${isReadonly ? 'true' : 'false'}"
	style="display: none; position: fixed; top: 5%; left: 5%; width: 90%; height: 90%; background: white; border: 2px solid black; overflow: auto; z-index: 9999; padding: 1em;">
	
	<button onclick="closePopup()">閉じる</button>

	<h2>健康記録登録</h2>
	<form method="POST" action="HealthRecordServlet" id="healthForm">
		<!-- 現在の体重 -->
		<label>現在の体重[kg]：
			<input type="number" step="0.5" name="now_weight" required>
		</label>
		<br><br>

		<!-- 運動 -->
		運動の種類と時間：
		<button type="button" id="add_exercise_button">＋ 運動を追加</button>
		<button type="button" id="remove_exercise_button">－</button>
		<br>

		<!-- 運動入力欄コンテナ -->
		<div id="exercise_container" style="display: none;">
			<div class="exercise_field">
				<label for="exercise_select0">運動の種類：</label>
				<select id="exercise_select0"></select>

				<input type="hidden" name="exercise_type0" id="exercise_type0">
				<input type="hidden" name="mets0" id="mets0">

				<label for="exercise_time0">時間（分）：</label>
				<input type="number" name="exercise_time0" id="exercise_time0" min="0" required>
			</div>
		</div>

		<br>

		<!-- 禁煙 -->
		<label>禁煙できたか：
			<input type="radio" name="no_smoke" value="1" checked>できた
		</label>
		<label>
			<input type="radio" name="no_smoke" value="0">できなかった
		</label>

		<br><br>

		<!-- 飲酒 -->
		飲酒量とアルコール度数：
		<button type="button" id="add_alcohol_button">＋ 飲酒を追加</button>
		<button type="button" id="remove_alcohol_button">－</button>
		<br>

		<!-- 飲酒入力欄コンテナ -->
		<div id="alcohol_container" style="display: none;">
			<div class="alcohol_field">
				<label for="alcohol_content0">アルコール度数：</label>
				<input type="number" name="alcohol_content0" id="alcohol_content0" readonly>

				<select id="categorySelect0">
					<option value="">--カテゴリー--</option>
				</select>

				<select id="typeSelect0" disabled>
					<option value="">--種類--</option>
				</select>

				<label for="alcohol_consumed0">摂取量（ml）：</label>
				<input type="number" name="alcohol_consumed0" id="alcohol_consumed0" readonly>

				<select id="glassSelect0">
					<option value="">参考: 器の種類</option>
				</select>

				<input type="number" id="cupCount0" value="1" min="1" disabled>
			</div>
		</div>

		<br>

		<!-- 睡眠・カロリー -->
		<label>睡眠時間[h]：
			<input type="number" name="sleep_hours" step="0.5" value="7.0">
		</label>
		<br>
		<label>摂取カロリー[kcal]：
			<input type="number" name="calorie_intake" min="0" step="100" value="2000">
		</label>

		<br><br>

		<!-- 自由記述 -->
		<label>自由欄<br>
			<input type="text" name="free">
		</label>

		<br><br>

		<!-- 日付と送信 -->
		<input type="hidden" name="date" id="popupDate">
		<input type="hidden" name="fromCalendar" value="true">
		<input type="submit" value="登録">
	</form>
</div>
<!--  
	<div id="popup"
		style="display: none; position: fixed; top: 5%; left: 5%; width: 90%; height: 90%; background: white; border: 2px solid black; overflow: auto; z-index: 9999; padding: 1em;">
		<button onclick="closePopup()">閉じる</button>

		<h2>健康記録登録</h2>
		<form method="POST" action="HealthRecordServlet">
			<table>
				<tr>
					<td><label>現在の体重 <input type="text" name="now_weight"
							required>kg
					</label></td>
				</tr>

				<tr>
					<td>運動の種類と時間</td>
				</tr>
				<tr>
					<td><label>種類 <select name="exercise_type1"
							onchange="updateMets(1)" id="exercise_type1">
								<option value="ウォーキング">ウォーキング 3.5メッツ</option>
								<option value="サイクリング">サイクリング 4.0メッツ</option>
						</select> <input type="hidden" name="mets1" id="mets1" value="3.5">
					</label></td>
					<td><label>時間 <input type="text" name="exercise_time1"
							required>分
					</label></td>
				</tr>

				<tr>
					<td><label>種類 <select name="exercise_type2"
							onchange="updateMets(2)" id="exercise_type2">
								<option value="ウォーキング">ウォーキング 3.5メッツ</option>
								<option value="サイクリング">サイクリング 4.0メッツ</option>
						</select> <input type="hidden" name="mets2" id="mets2" value="3.5">
					</label></td>
					<td><label>時間 <input type="text" name="exercise_time2">分
					</label></td>
				</tr>

				<tr>
					<td><label>禁煙できたか：<input type="radio" name="no_smoke"
							value="1" checked>できた
					</label></td>
					<td><input type="radio" name="no_smoke" value="0">できなかった</td>
				</tr>

				<tr>
					<td>飲酒量とアルコール度数</td>
					<td><label>度数： <select name="alcohol_content1">
								<option value="5">ビール</option>
								<option value="40">ウイスキー</option>
						</select> %
					</label></td>
					<td><label>量： <select name="alcohol_consumed1">
								<option value="300">中ジョッキ</option>
								<option value="100">ロックグラス</option>
						</select> ml
					</label></td>
				</tr>

				<tr>
					<td><label>度数： <select name="alcohol_content2">
								<option value="5">ビール</option>
								<option value="40">ウイスキー</option>
						</select> %
					</label></td>
					<td><label>量： <select name="alcohol_consumed2">
								<option value="300">中ジョッキ</option>
								<option value="100">ロックグラス</option>
						</select> ml
					</label></td>
				</tr>

				<tr>
					<td><label>睡眠時間 <select name="sleep_hours">
								<option value="0.0">0</option>
								<option value="7.0">7.0</option>
								<option value="7.5">7.5</option>
								<option value="8.0">8.0</option>
						</select> 時間
					</label></td>
				</tr>

				<tr>
					<td><label>摂取カロリー： <select name="calorie_intake">
								<option value="0">0</option>
								<option value="500">500</option>
								<option value="600">600</option>
								<option value="700">700</option>
								<option value="2700">2700</option>
						</select> kcal
					</label></td>
				</tr>

				<tr>
					<td><label>自由欄<br> <input type="text" name="free"></label></td>
				</tr>

				<tr>
					<td colspan="2"><input type="hidden" name="date"
						id="popupDate"> <input type="hidden" name="fromCalendar"
						value="true"> <input type="submit" value="登録"></td>
				</tr>
			</table>
		</form>
	</div>
-->




	<script>
		const rwList = {};
		const hwList = {};
		const heList = {};
		const haList = {};
		const year = ${displayYear};
		const month = ${displayMonth};

		<c:forEach var="rw" items="${rewardList}">
			if (!rwList['${rw.date}']){
				rwList['${rw.date}'] = [];
			}
			rwList['${rw.date}'].push("${rw.rewardExplain}");
		</c:forEach>;

		<c:forEach var="hw" items="${healthList}">
			if (!hwList['${hw.date}']){
				hwList['${hw.date}'] = [];
			}
			hwList['${hw.date}'] = {
				nowWeight: ${hw.nowWeight},
				calorieIntake: ${hw.calorieIntake},
				calorieConsu: ${hw.calorieConsu},
				nosmoke: ${hw.nosmoke},
				sleepHours: ${hw.sleepHours},
				free: "${hw.free}"
			};

			if("${hw.exerciseList}" != null && ${hw.exerciseList.size()} > 0) {
				heList['${hw.date}'] = [];
				<c:forEach var="ex" items="${hw.exerciseList}">
					heList['${hw.date}'].push({
						exerciseType: "${ex.exerciseType}",
						exerciseTime: ${ex.exerciseTime},
						calorieConsu: ${ex.calorieConsu}
					});
				</c:forEach>;
			};

			if("${hw.alcoholList}" != null && ${hw.alcoholList.size()} > 0) {
				haList['${hw.date}'] = [];
				<c:forEach var="alc" items="${hw.alcoholList}">
					haList['${hw.date}'].push({
						pureAlcoholConsumed: ${alc.pureAlcoholConsumed},
						alcoholContent: ${alc.alcoholContent},
						alcoholConsumed: ${alc.alcoholConsumed}
					});
				</c:forEach>;
			};
			
		</c:forEach>;

		


	</script>
	
	
	<script src="<c:url value='/js/calendar.js' />"></script>
	<script src="<c:url value='/js/healthRecord.js' />"></script>
	<script src="<c:url value='/js/createTownAvatar.js' />"></script>
	<script src="<c:url value='/js/common.js' />"></script>
</body>
</html>