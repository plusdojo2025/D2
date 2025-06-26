<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
	<meta charset="UTF-8">
	<title>カレンダー</title>
	<link rel="stylesheet" href="<c:url value='/css/calendar.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/healthRecord.css'/>">
	<link rel="stylesheet" href="<c:url value='/css/common.css'/>">
</head>

<body>
	<!-- タイトル -->
	<h1 class="logo-header"><a href="<c:url value='/HomeServlet' />"> <img src="${pageContext.request.contextPath}/img/logo1.png" alt="Logo" class="logo-img"></a></h1>

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

		<div class="bar-top">
  <!-- 年月選択 -->


	</div>



	<!-- カレンダー -->
	<!-- --------------------------------------------------------------- -->
	<div class="checkbox-group">
	
	 <label class="month-label">
	  <%--  ${displayYear}年 ${displayMonth}月 --%>
	   <input type="month" id="monthInput" min="<%=min%>" max="<%=max%>" 
	   value="${displayYear}-${String.format('%02d', displayMonth)}">
	</label>
	
		<!-- チェックボックス -->
		<label class="cb-label health"><input type="checkbox" name="cbhealthRecord"	id="cbHealth" checked>健康記録</label>
		<label class="cb-label reward"><input type="checkbox" name="rewardRecord" id="cbReward" checked>報酬記録</label>
		<label class="cb-label weight"><input type="checkbox" name="nowweight" id="cbNowWeight" checked>体重</label>
		<label class="cb-label intake"><input type="checkbox" name="calorieIntake" id="cbCalorieIntake" checked>摂取カロリー</label>
		<label class="cb-label consu"><input type="checkbox" name="calorieCounsu" id="cbCalorieConsu" checked>消費カロリー</label> 
		<label class="cb-label smoke"><input type="checkbox" name="noSmoke" id="cbSmoke" checked>禁煙</label>
		<label class="cb-label sleep-label"><input type="checkbox" name="sleepingTime" id="cbSleep" checked>睡眠</label>
		<label class="cb-label memo"><input type="checkbox" name="memo" id="cbMemo" checked>メモ</label>
		<label class="cb-label exercise-label"><input type="checkbox" name="exercise" id="cbExercise" checked>運動記録</label>
		<label class="cb-label alcohol-label"><input type="checkbox" name="alcohol" id="cbAlcohol" checked>お酒</label>
		
	</div>
	
	<table>
        <tr>
            <th>日</th><th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th>
        </tr>
        <tbody id="calendar-body"></tbody>
    </table> 
    
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

  <p>合計消費カロリー：<%= String.format("%.1f", request.getAttribute("sumCalorieConsumed")) %> kcal</p>
  <p>1日の平均純アルコール量：<%= String.format("%.1f", request.getAttribute("avgPureAlcohol")) %> g</p>
  <p>1日の平均睡眠時間：<%= String.format("%.1f", request.getAttribute("avgSleep")) %> 時間</p>

  <p>合計禁煙日数：<%= request.getAttribute("sumNosmokeDays") %> 日</p>
  <p>1日の平均消費カロリー：<%= String.format("%.1f", request.getAttribute("avgConsumed")) %> kcal</p>
  <p>1日の平均摂取カロリー：<%= String.format("%.1f", request.getAttribute("avgIntake")) %> kcal</p>

	</div>
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

	<c:set var="width" value="1000" /> <!-- 実際に画面に表示する際の横幅[px] -->
	<canvas id="imageCanvas" width="1627" height="1021" style="width:${width}px;"></canvas>
	<!-- --------------------------------------------------------------- -->
	

	

	

	<!-- --------------------------------------------------------------- -->
	

	<!-- 
	/* TODO:カレンダーの日付をクリックしたときに、その日の健康記録登録・更新フォームをポップアップ
		 * 	healthRecoed.jspのコピペ
		 *  その日に既に健康記録がある場合は、健康記録内容をデフォルトでフォーム入力欄に表示
		 */
	-->
		 


	<!-- ポップアップ本体（非表示にしておく） -->
	<div id="layer"></div> <!-- ポップアップ時に背景を薄くする -->
	<div id="popup" data-readonly=true>
		
		<button onclick="closePopup()">閉じる</button>
	
		<h2>健康記録登録</h2>
		<form method="POST" id="form" action="<c:url value='/HealthRecordServlet' />">

			<label>
				現在の体重[kg]：
					<input type="number" step="0.1" name="now_weight" value="60.0" id="input_weight" min="0" required>	
			</label>

			<br>
			
			運動の種類と時間：
			<!-- ボタンを押したら新しい入力欄が追加される -->				
			<button type="button" id="add_exercise_button">＋ 運動を追加</button>
			<button type="button" id="remove_exercise_button">－</button>
			<br>
			<div id="exercise_container" style="display: none;">
				<div class="exercise_field">
					<div class="input-group">
						<label for="exercise_type0">種類とメッツ：</label>
						<input type="text" name="exercise_type0" id="exercise_type0" placeholder="例：ジョギング">
						<input type="number" name="mets0" id="mets0" placeholder="例：6.0" step="0.5" min="0.0">
					</div>
					
					<div class="input-group">
						<label for="exercise_time0">時間：</label>
						<input type="number" name="exercise_time0" id="exercise_time0" min="0" placeholder="例：30">分
					</div>
					<select name="exercise_select0" id="exercise_select0">
						<option value="">参考：種類とメッツ</option>
					</select>
				</div> 
			</div>


			<br>

			<label>
				禁煙できたか： 
				<input type="radio" name="no_smoke" value="1" class="input_nosmoke" checked>できた
			</label>
			<label> 
				<input type="radio" name="no_smoke"	value="0" class="input_nosmoke">できなかった
			</label>

			<br>

			飲酒量とアルコール度数：
			<!-- ボタンを押したら新しい入力欄が追加される -->				
			<button type="button" id="add_alcohol_button">＋ 飲酒を追加</button>
			<button type="button" id="remove_alcohol_button">－</button>
			<br>
			<div id="alcohol_container" style="display: none;">
				<div class = "alcohol_field">
					<!-- アルコール度数 -->
					<div class="input-group">
						<label for="alcohol_content0">アルコール度数 [%]:</label>
						<input type="number" id="alcohol_content0" name="alcohol_content0" min = 0.0 max=100.0 step="1.0" placeholder="例：5.0">

						<select id="categorySelect0">
							<option value="">参考: アルコール度数</option>
						</select>
						<select id="typeSelect0" disabled>
							<option value=""></option>
						</select>
					</div>

					<!-- 摂取量 -->
					<div class="input-group">
						<label for="alcohol_consumed0">摂取量 [ml]:</label>
						<input type="number" id="alcohol_consumed0" name="alcohol_consumed0" step="10" min="0" placeholder="例：500">

						<select id="glassSelect0">
							<option value="">参考: 摂取量</option>
						</select>

						<input type="number" id="cupCount0" min="1" value="1" disabled>杯
					</div>
				</div>
			</div>				



			<br>

			<label> 
				睡眠時間[h]：
					<input type="number" name="sleep_hours" step="0.5" default="7.0" value="7.0" id="input_sleep" min="0" required>
			</label>

			<br>

			<label> 
				摂取カロリー[kcal]： 
					<input type="number" name="calorie_intake" min="0" step="100" value="2000" id="input_calorie_intake" required>
			</label>

			<br>

			<label for="free">自由欄</label>
<textarea name="free" id="input_free"></textarea>

			<br>

			<!-- 今リクエストスコープで保持している日付データは登録ボタンを押すとなくなってしまうため、hiddenでデータを保持しておく -->
			<!-- 登録か更新ボタンの切換え --> 
			<input type="hidden" name="date" value="" id="input_date">
			<input type="submit" id="register" name="submit" value="登録">
			<p id="error_message"></p>
					
		</form>
	</div>

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
	<div id="footer">
    <p class="copyright">&copy;2025 視力検査Dチーム 健康管理アプリ</p>
  </div>
</body>
</html>