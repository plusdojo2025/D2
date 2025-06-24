"use strict";

const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const monthDisplay = document.getElementById("month-display");
const avatarContainer = document.getElementById("avatar-container");


// 今月がavatarDataListの0番目なので、currentMonthIndex=0スタート
let currentMonthIndex = 0;



// 表示更新とボタン状態切り替え
function updateDisplay() {
  const yearMonth = yearMonthList[currentMonthIndex];
  if (!yearMonth) return;
  monthDisplay.textContent =  `${yearMonth.year}年 ${yearMonth.month}月`;
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
  prevBtn.disabled = currentMonthIndex <= 0;
  nextBtn.disabled = currentMonthIndex >= monthList.length - 1;
}

prevBtn.addEventListener("click", () => {
  if (currentMonthIndex > 0) {
    currentMonthIndex--;
    updateDisplay();
  }
});

nextBtn.addEventListener("click", () => {
  if (currentMonthIndex < monthList.length - 1) {
    currentMonthIndex++;
    updateDisplay();
  }
});

// 初期表示
updateDisplay();