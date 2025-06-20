"use strict"
let currentExerciseCount = 0;
const maxExerciseCount = 10;

function addExercise() {
	if (currentExerciseCount >= maxExerciseCount) {
		alert("これ以上は追加できません（最大10件まで）");
		return;
	}

	currentExerciseCount++;
	const row = document.getElementById(`exercise_row${currentExerciseCount}`);
	if (row) {
		row.style.display = "";
		updateMets(currentExerciseCount);
	}
}

function updateMets(num) {
	const type = document.getElementById(`exercise_type${num}`);
	const mets = document.getElementById(`mets${num}`);
	if (type && mets) {
		mets.value = (type.value === 'ウォーキング') ? '3.5' :
		             (type.value === 'サイクリング') ? '4.0' : '0';
	}
}

// ページ読み込み時に各選択肢の初期METS値を設定（非表示でも準備しておく）
window.addEventListener('DOMContentLoaded', () => {
	for (let i = 1; i <= maxExerciseCount; i++) {
		updateMets(i);
	}
});
