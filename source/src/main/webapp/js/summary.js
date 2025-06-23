"use strict";

const canvas = document.getElementById("myCanvas");
const ctx = canvas.getContext("2d");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const monthDisplay = document.getElementById("month-display");

// 今月がavatarDataListの0番目なので、currentMonthIndex=0スタート
let currentMonthIndex = 0;

// 描画位置とスケール定義
const centerX = 400;
const centerY = 400;
const SCALE = 0.25;

// 相対位置（服・靴・帽子）
const relClothPos = [
  [-120, 150], // 服
  [-100, 375], // 靴
  [-100, -200] // 帽子
];
const relBuildPos = [
  [-400, -400],
  [400, 400],
  [400, -300]
];
const relPeoplePos = [
  [-400, 400],
  [400, 0],
  [-300, -100],
  [500, 400],
  [400, -300],
  [100, 500],
  [-100, -500]
];

// 画像読み込み用Promise関数
function loadImage(src) {
  return new Promise((resolve, reject) => {
    if (!src) {
      resolve(null);
      return;
    }
    const img = new Image();
    img.onload = () => resolve(img);
    img.onerror = () => reject(new Error("Failed to load image: " + src));
    img.src = src;
  });
}

// 中央位置でスケール付き描画
function drawScaledImage(img, x, y, scale = SCALE) {
  if (!img) return;
  const w = img.width * scale;
  const h = img.height * scale;
  ctx.drawImage(img, x - w / 2, y - h / 2, w, h);
}

// 描画処理
async function drawAvatarOnCanvas(avatar) {
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  if (!avatar || !avatar.face) {
    ctx.font = "20px sans-serif";
    ctx.textAlign = "center";
    ctx.fillText("データなし", centerX, centerY);
    return;
  }

  // 画像をまとめて読み込み
  const faceImg = await loadImage(avatar.face);
  const clothImg = await loadImage(avatar.cloth);
  const shoeImg = await loadImage(avatar.shoe);
  const hatImg = await loadImage(avatar.hat);
  const costumeImg = await loadImage(avatar.costume);
  const peopleImg = await loadImage(avatar.peopleImage);
  const buildingImgs = await Promise.all(
    avatar.buildings.map(src => loadImage(src))
  );

  // コスチュームがあれば中心に描画
  if (costumeImg) {
    drawScaledImage(costumeImg, centerX, centerY);
  } else {
    if (faceImg) drawScaledImage(faceImg, centerX, centerY);

    // 服・靴・帽子を相対位置で描画
    [clothImg, shoeImg, hatImg].forEach((img, i) => {
      if (img) drawScaledImage(img, centerX + relClothPos[i][0], centerY + relClothPos[i][1]);
    });
  }

  // 建物を描画
  buildingImgs.forEach((img, i) => {
    if (img) drawScaledImage(img, centerX + relBuildPos[i][0], centerY + relBuildPos[i][1]);
  });

  // 人物を描画（最大7人）
  for (let i = 0; i < Math.min(avatar.peopleCount, relPeoplePos.length); i++) {
    if (peopleImg) {
      drawScaledImage(peopleImg, centerX + relPeoplePos[i][0], centerY + relPeoplePos[i][1]);
    }
  }
}

// 表示更新とボタン状態切り替え
function updateDisplay() {
  const month = monthList[currentMonthIndex];
  monthDisplay.textContent = month + "月";
  drawAvatarOnCanvas(avatarDataList[currentMonthIndex]);
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