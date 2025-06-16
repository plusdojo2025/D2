

/*
街並みやアバターをjspファイル内のcanvasタグで表示する
	TODO：一つのcanvasにのみ対応しているため、サマリーではこのファイルは使えない
*/

"use strict"

const canvas = document.getElementById('myCanvas');
const ctx = canvas.getContext('2d');

var images = {};

const build = document.getElementsByClassName("imgBuild");
const cloth = document.getElementsByClassName("imgCloth");
const face = document.getElementById("imgFace");
const people = document.getElementById("imgPeople");
const peopleNum = document.getElementById("peopleNum").value;

drawTownAvatar();


// ----------------------------------------------------------
function drawTownAvatar() {
	const x = 400; // アバターの位置
	const y = 400; // アバターの位置

	const relClothPos = [ // 衣服の相対位置
		[-120, 150], // 服
		[-100, 375], // 靴
		[-100, -200] // 帽子
	];

	const relBuildPos = [ // 建物の相対位置
		[-400, -400], // 建物1
		[400, 400], // 建物2
		[400, -300] // 建物 3
	]

	const relPeoplePos = [ // 周囲の人物の相対位置
		[-400, 400], // 1人目
		[400, 0], // 2人目
		[-300, -100], // 3人目
		[500, 400], // 4人目
		[400, -300], // 5人目
		[100, 500], // 6人目
		[-100, -500] // 7人目
	];

	// アバターの描画
	if (cloth.length == 4) {
		ctx.drawImage(cloth[3], x, y);
	} else {
		ctx.drawImage(face, x, y);

		for (let i = 0; i < cloth.length; i++) {
			ctx.drawImage(cloth[i], x + relClothPos[i][0], y + relClothPos[i][1]);
		}
	}

	// 建物の描画
	for (let i = 0; i < build.length; i++) {
		ctx.drawImage(build[i], x + relBuildPos[i][0], y + relBuildPos[i][1]);
	}

	// 周囲の人物の描写;
	for (let i = 0; i < peopleNum; i++) {		
		ctx.drawImage(people, x + relPeoplePos[i][0], y + relPeoplePos[i][1]);
	}
};

