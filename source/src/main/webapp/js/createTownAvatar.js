

/*
街並みやアバターをjspファイル内のcanvasタグで表示する
	TODO：一つのcanvasにのみ対応しているため、このファイルはサマリーページで使えない
	TODO:個々の画像の大きさ調整が上手くいかない
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
	const scaleAvatar = 1.0; // アバターの大きさ
	const scaleCloth4 = 1.0; //　民族衣装の大きさ
	
	const relClothPos = [ // 衣服の相対位置dx,dy, スケールs 
		[-120, 150, 1.0], // 服
		[-100, 375, 0.8], // 靴
		[-100, -200, 0.5] // 帽子
	];

	const relBuildPos = [ // 建物の相対位置dx,dy, スケールs 
		[-400, -400, 1.0], // 建物1
		[400, 400, 1.0], // 建物2
		[400, -300, 1.0] // 建物 3
	]

	const relPeoplePos = [ // 周囲の人物の相対位置dx,dy, スケールs 
		[-400, 400, 0.8], // 1人目
		[400, 0, 0.8], // 2人目
		[-300, -100, 0.8], // 3人目
		[500, 400, 0.8], // 4人目
		[400, -300, 0.8], // 5人目
		[100, 500, 0.8], // 6人目
		[-100, -500, 0.8] // 7人目
	];

	// アバターの描画
	if (cloth.length == 4) {
		//ctx.scale(scaleCloth4, scaleCloth4);
		ctx.drawImage(cloth[3], x, y);
		//ctx.scale(1/scaleCloth4, 1/scaleCloth4);
	} else {
		//ctx.scale(scaleAvatar,scaleAvatar);
		ctx.drawImage(face, x, y);
		//ctx.scale(1/scaleAvatar,1/scaleAvatar);

		for (let i = 0; i < cloth.length; i++) {
			//ctx.scale(relClothPos[i][2], relClothPos[i][2]);
			ctx.drawImage(cloth[i], x + relClothPos[i][0], y + relClothPos[i][1]);
			//ctx.scale(1/relClothPos[i][2], 1/relClothPos[i][2]);
		}
	}

	// 建物の描画
	for (let i = 0; i < build.length; i++) {
		//ctx.scale(relBuildPos[i][2],relBuildPos[i][2]);
		ctx.drawImage(build[i], x + relBuildPos[i][0], y + relBuildPos[i][1]);
		//ctx.scale(1/relBuildPos[i][2],1/relBuildPos[i][2]);
	}

	// 周囲の人物の描写;
	for (let i = 0; i < peopleNum; i++) {		
		//ctx.scale(relPeoplePos[i][2],relPeoplePos[i][2]);
		ctx.drawImage(people, x + relPeoplePos[i][0], y + relPeoplePos[i][1]);
		//ctx.scale(1/relPeoplePos[i][2],1/relPeoplePos[i][2]);
	}
};

