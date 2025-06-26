"use strict";

document.addEventListener('DOMContentLoaded', function() {
	// 月入力変更時にURL遷移
	const monthInput = document.getElementById('monthInput');
	if (monthInput) {
		monthInput.addEventListener('change', function() {
			const ym = this.value;
			if (!ym) return;

			const parts = ym.split('-');
			const year = parts[0];
			const month = parseInt(parts[1]);

			// 現在のURLをベースにクエリパラメータを変更
			const url = new URL(window.location.href);
			url.searchParams.set('year', year);
			url.searchParams.set('month', month);

			// URLを変更して遷移
			window.location.href = url.toString();
		});
	}

	// 健康記録のチェック制御
	const checkMap = {
		cbExercise: 'exercise',
		cbAlcohol: 'alcohol',
		cbSmoke: 'nosmoke',
		cbSleep: 'sleep',
		cbCalorieIntake: 'calorieIntake',
		cbCalorieConsu: 'calorieConsu',
		cbMemo: 'free',
		cbNowWeight: 'nowweight'
	};

	const healthRecordChk = document.getElementById('cbHealth');

	if (healthRecordChk) {
		for (const [chkId, className] of Object.entries(checkMap)) {
			const checkbox = document.getElementById(chkId);
			if (checkbox) {
				checkbox.addEventListener('change', function() {
					toggleSection(className, this.checked && healthRecordChk.checked);
				});
			}
		}

		healthRecordChk.addEventListener('change', function() {
			const enabled = this.checked;

			// 子チェックボックスもON/OFF（報酬記録以外）
			for (const chkId of Object.keys(checkMap)) {
				const childChk = document.getElementById(chkId);
				if (childChk) {
					childChk.checked = enabled; // ✅ 健康記録ON → 子もON / OFF → 子もOFF
				}
			}

			// セクションの表示制御
			for (const [chkId, className] of Object.entries(checkMap)) {
				toggleSection(className, enabled && document.getElementById(chkId).checked);
			}

			// 全体の.health-record 表示（あれば）
			document.querySelectorAll('.health-record').forEach(el => {
				el.style.display = enabled ? '' : 'none';
			});
		});
	}

	function toggleSection(className, show) {
		document.querySelectorAll('.' + className).forEach(el => {
			el.style.display = show ? '' : 'none';
		});
	}


	// 報酬記録チェックボックス制御
	const rewardChk = document.getElementById('cbReward');
	if (rewardChk) {
		rewardChk.addEventListener('change', function() {
			const show = this.checked;
			document.querySelectorAll('.reward-record').forEach(el => {
				el.style.display = show ? '' : 'none';
			});
		});

		// 初期表示制御
		document.querySelectorAll('.reward-record').forEach(el => {
			el.style.display = rewardChk.checked ? '' : 'none';
		});
	}
});

// ポップアップ制御 -------------------------------------
function openPopup(date) {
	document.getElementById("layer").style.display = "block";
	document.getElementById("popup").style.display = "block";

	// 健康記録whole
	const inputWeight = document.getElementById("input_weight");
	const inputSleep = document.getElementById("input_sleep");
	const inputCalorieIntake = document.getElementById("input_calorie_intake");
	const inputFree = document.getElementById("input_free");
	const inputSmokeTwin = document.getElementsByClassName("input_nosmoke")
	const inputDate = document.getElementById("input_date");
	if (hwList[date]) {
		// 記録があったらフォームにデフォで表示
		inputWeight.value = hwList[date].nowWeight;
		inputSleep.value = hwList[date].sleepHours;
		inputCalorieIntake.value = hwList[date].calorieIntake;
		inputFree.value = hwList[date].free || "";
		for (let i = 0; i < inputSmokeTwin.length; i++) {
			if (inputSmokeTwin[i].value == hwList[date].nosmoke) {
				inputSmokeTwin[i].checked = true;
			}
		}
	} else {
		// 記録がなかったらフォームは空
		inputWeight.value = "";
		inputSleep.value = "";
		inputCalorieIntake.value = "";
		inputFree.value = "";
		inputSmokeTwin[0].checked = true;
		inputDate.value = date; // 登録用にセット
	}

	// 健康記録exercise
	for (let i = 0; i < 30; i++) {
		deleteExercise();
	}
	if (heList[date]) {
		// 健康運動記録があったらフォームにデフォで表示
		for (let i = 0; i < heList[date].length; i++) {
			addExercise();
			const exerciseType = document.getElementById("exercise_type" + i);
			const exerciseTime = document.getElementById("exercise_time" + i);
			exerciseType.value = heList[date][i].exerciseType;
			exerciseTime.value = heList[date][i].exerciseTime;
		}
	}

	// 健康記録alcohol
	for (let i = 0; i < 30; i++) {
		deleteAlcohol();
	}
	if (haList[date]) {
		// 健康アルコール記録があったらフォームにデフォで表示
		for (let i = 0; i < haList[date].length; i++) {
			addAlcohol();
			const alcoholContent = document.getElementById("alcohol_content" + i);
			const alcoholConsumed = document.getElementById("alcohol_consumed" + i);
			alcoholContent.value = haList[date][i].alcoholContent;
			alcoholConsumed.value = haList[date][i].alcoholConsumed;
		}
	}

	controlFormAccessibility(date);
}


function closePopup() {
	document.getElementById("layer").style.display = "none"
	document.getElementById("popup").style.display = "none";
}

window.addEventListener('load', function() {
	document.querySelectorAll('#layer').forEach(elm => {
		elm.onclick = function() {
			closePopup()
		};
	});
});

function controlFormAccessibility(date) {
	let isNewest = false; // 初期値は登録不可
	const today = new Date();
	const selectedDate = new Date(date);
	today.setHours(0, 0, 0, 0);
	selectedDate.setHours(0, 0, 0, 0);


	// 年月が一致なら次の処理へ
	if (selectedDate.getFullYear() === today.getFullYear() && selectedDate.getMonth() === today.getMonth()) {
		// 選択した日付以降健康記録が登録されていなければ、登録可能
		while (selectedDate <= today) {
			const dateStr = toISOStringWithTimezone(selectedDate).split('T')[0];
			if (hwList[dateStr]) { // 健康記録があった...
				isNewest = false; // 登録不可
				break;
			} else {
				isNewest = true; // 登録可能
			}
			selectedDate.setDate(selectedDate.getDate() + 1);
		}
	} else {
		// 年月が一致しない場合は登録不可
	}

	const inputs = document.querySelectorAll('#popup input, #popup select, #popup textarea, #add_exercise_button, #remove_exercise_button, #add_alcohol_button, #remove_alcohol_button');
	inputs.forEach(el => {
		el.disabled = !isNewest; // 当日なら有効、それ以外は無効
	});

	const saveButton = document.getElementById('save_button'); // 保存ボタンも対象
	if (saveButton) {
		saveButton.disabled = !isNewest;
	}
}

function toISOStringWithTimezone(date) {
	// https://qiita.com/h53/items/05139982c6fd81212b08
	const pad = function(str) {
		return ('0' + str).slice(-2);
	};
	const year = (date.getFullYear()).toString();
	const month = pad((date.getMonth() + 1).toString());
	const day = pad(date.getDate().toString());
	const hour = pad(date.getHours().toString());
	const min = pad(date.getMinutes().toString());
	const sec = pad(date.getSeconds().toString());
	const tz = -date.getTimezoneOffset();
	const sign = tz >= 0 ? '+' : '-';
	const tzHour = pad((tz / 60).toString());
	const tzMin = pad((tz % 60).toString());

	return `${year}-${month}-${day}T${hour}:${min}:${sec}${sign}${tzHour}:${tzMin}`;
}

// カレンダー関連 -------------------------------------
function generateCalendar(year, month) {
	const firstDay = new Date(year, month - 1, 1);
	const lastDay = new Date(year, month, 0).getDate();
	const startDay = firstDay.getDay();

	const calendarBody = document.getElementById("calendar-body");
	calendarBody.innerHTML = "";
	let row = document.createElement("tr");

	for (let i = 0; i < startDay; i++) {
		row.appendChild(document.createElement("td"));
	}

	for (let day = 1; day <= lastDay; day++) {
		if ((startDay + day - 1) % 7 === 0 && day !== 1) {
			calendarBody.appendChild(row);
			row = document.createElement("tr");
		}

		const dateStr = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
		const cell = document.createElement("td");
		cell.addEventListener("click", function() {
			openPopup(dateStr);
		});
		cell.innerHTML = `<strong>${day}</strong><br>`;

		if (rwList[dateStr]) {
			cell.classList.add("has-reward");
			rwList[dateStr].forEach(item => {
				cell.innerHTML += `<div class="reward-record" reward>${item}</div>`;
			});
		}

		if (hwList[dateStr]) {
			cell.classList.add("has-health-record");
			cell.innerHTML += `<div class="health-record nowweight">${hwList[dateStr].nowWeight}kg</div>`;
			cell.innerHTML += `<div class="health-record calorieIntake">${hwList[dateStr].calorieIntake}kcal</div>`;
			cell.innerHTML += `<div class="health-record calorieConsu">${hwList[dateStr].calorieConsu}kcal</div>`;
			const smokeText = hwList[dateStr].nosmoke == 1 ? "喫煙なし" : "喫煙あり";
			cell.innerHTML += `<div class="health-record nosmoke">${smokeText}</div>`;
			cell.innerHTML += `<div class="health-record sleep">${hwList[dateStr].sleepHours} 時間</div>`;
			cell.innerHTML += `<div class="health-record free">${hwList[dateStr].free}</div>`;
		}

		if (heList[dateStr]) {
			cell.classList.add("has-health-exercise");
			heList[dateStr].forEach(item => {
				cell.innerHTML += `<div class="health-record exercise">${item.exerciseType} ${item.exerciseTime}分</div>`;
			});
		}

		if (haList[dateStr]) {
			cell.classList.add("has-health-alcohol");
			haList[dateStr].forEach(item => {
				cell.innerHTML += `<div class="health-record alcohol">${item.pureAlcoholConsumed}g ${item.alcoholContent}% ${item.alcoholConsumed}ml</div>`;
			});
		}

		row.appendChild(cell);
	}

	calendarBody.appendChild(row);
}

generateCalendar(year, month);

function showHelp() {
	alert(
		"カレンダーページ\n ～あなたの健康・報酬記録を月ごとに確認する場所～\n\n"
		+ "このページでできること\n"
		+ "1. 1年前までの記録・街並みの確認\n"
		+ "2. 健康記録の登録(今月の最終登録日～今日)"
	)
}
