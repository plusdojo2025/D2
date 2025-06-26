"use strict";

const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const monthDisplay = document.getElementById("month-display");
const avatarContainer = document.getElementById("avatar-container");




// 今月がavatarDataListの0番目なので、currentMonthIndex=0スタート
let currentMonthIndex = 0;

const monthBlockList = document.getElementsByClassName("month-block");


// ポップアップ制御 -------------------------------------
// imageCanvasクラスの要素それぞれにaddEventListenerを設定
document.addEventListener("DOMContentLoaded", function() { // 
	const canvasList = document.getElementsByClassName('imageCanvas');
	for (let i = 0; i < canvasList.length; i++) {
		canvasList[i].addEventListener("click", function() {
			currentMonthIndex = i;
			updateDisplay();
			openPopup();
		});
	}
});

function openPopup() {
	document.getElementById("layer").style.display = "block";
	document.getElementById("popup").style.display = "block";
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


// 表示更新とボタン状態切り替え -----------------------------------
function updateDisplay() {
	const yearMonth = yearMonthList[currentMonthIndex];
	if (!yearMonth) return;
	monthDisplay.textContent = `${yearMonth.year}年 ${yearMonth.month}月`;
	avatarContainer.innerHTML = ""; // 既存のアバターをクリア

	// canvas要素を作成
	const canvas = document.createElement("canvas");
	canvas.id = `canvas2`;
	canvas.width = 1627; // 適切な幅を設定
	canvas.height = 1021; // 適切な高さを設定
	canvas.style.width = "800px"; // 表示幅を設定
	avatarContainer.appendChild(canvas);
	drawAvatar2(yearMonth.year, yearMonth.month);
	updateButtons();
}

function updateButtons() {
	const canvasList = document.getElementsByClassName('imageCanvas');
	prevBtn.disabled = currentMonthIndex <= 0;
	nextBtn.disabled = currentMonthIndex >= canvasList.length - 1;
}

prevBtn.addEventListener("click", () => {
	if (currentMonthIndex > 0) {
		currentMonthIndex--;
		updateDisplay();
	}
});

nextBtn.addEventListener("click", () => {
	const canvasList = document.getElementsByClassName('imageCanvas');
	if (currentMonthIndex < canvasList.length - 1) {
		currentMonthIndex++;
		updateDisplay();
	}
});

// 初期表示
updateDisplay();


function showHelp() {
	alert(
		"サマリーページ\n ～あなたの過去1年間の街並みを振り返る場所～\n\n"
		+ "このページでできること\n"
		+ "1. 街並みをクリックで拡大表示"
	)
}