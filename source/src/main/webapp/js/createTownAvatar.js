// avatar.js
"use strict"

// Get the canvas and context
const canvas = document.getElementById('imageCanvas');
const ctx = canvas.getContext('2d');

// get element from HTML
const build = document.getElementsByClassName("imgBuild");
const cloth = document.getElementsByClassName("cloth");
const face = document.getElementsByClassName("imgFace");
const people = document.getElementsByClassName("imgPeople");
const peopleNum = document.getElementById("peopleNum").value;
const buildImages = [];
const clothImages = [];

// Load images for layering
const loadImage = (src) => {
    return new Promise((resolve, reject) => {
        const img = new Image();
        img.onload = () => resolve(img);
        img.onerror = reject;
        img.src = src;
    });
};

// Draw images on the canvas
const drawAvatar = async () => {
	try {
		const x = 0; // アバターの位置
		const y = 0; // アバターの位置
		const x_people = 400; // 周りの人の位置
		const y_people = 400; // 周りの人の位置
		const scaleAvatar = 1.0; // アバターの大きさ
		const scaleCloth4 = 1.0; //　民族衣装の大きさ

		const relClothPos = [ // 衣服の相対位置dx,dy, スケールs 
			[0, 0, 1.0], // 服
			[0, 0, 1.0], // 靴
			[0, 0, 1.0] // 帽子
		];

		const relBuildPos = [ // 建物の相対位置dx,dy, スケールs 
			[0, 0, 1.0], // 建物1
			[0, 0, 1.0], // 建物2
			[0, 0, 1.0] // 建物 3
		];

		const relPeoplePos = [ // 周囲の人物の相対位置dx,dy, スケールs 
			[-400, 400, 0.6], // 1人目
			[400, 0, 0.6], // 2人目
			[-300, -100, 0.6], // 3人目
			[500, 400, 0.6], // 4人目
			[700, -300, 0.6], // 5人目
			[100, 350, 0.6], // 6人目
			[-100, -400, 0.5] // 7人目
		];

		// Load base images
		const backgroundImage = await loadImage('img/background.png');
		const faceImage = await loadImage(face[0].id); // Face image
		const peopleImage = await loadImage(people[0].id); // People image
		for (let i = 0; i < build.length; i++) {
			buildImages[i] = await loadImage(build[i].id);
		}

		if(cloth[3].id != ""){
            clothImages[3] = await loadImage(cloth[3].id);
        }
        else{
            for (let i = 0; i < cloth.length; i++) {
                if(cloth[i].id == "") break;
                clothImages[i] = await loadImage(cloth[i].id);
            }
        }


		// Clear canvas
		ctx.clearRect(0, 0, canvas.width, canvas.height);

		// Draw layers in order
		ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
		
		
		// Draw buildings
		for (let i = 0; i < buildImages.length; i++) {
			ctx.drawImage(buildImages[i], x + relBuildPos[i][0], y + relBuildPos[i][1],
				buildImages[i].width * relBuildPos[i][2], buildImages[i].height * relBuildPos[i][2]
			);
		}
		// Draw surrounding people
		for (let i = 0; i < peopleNum; i++) {
			ctx.drawImage(peopleImage, x_people + relPeoplePos[i][0], y_people + relPeoplePos[i][1],
				peopleImage.width * relPeoplePos[i][2], peopleImage.height * relPeoplePos[i][2]
			);
		}
		// cloth and face
		if (clothImages.length === 4) {
			ctx.drawImage(clothImages[3], x, y, clothImages[3].width * scaleCloth4, clothImages[3].height * scaleCloth4);
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
		} else {
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
			for (let i = 0; i < clothImages.length; i++) {
				ctx.drawImage(clothImages[i], x + relClothPos[i][0], y + relClothPos[i][1], clothImages[i].width * relClothPos[i][2], clothImages[i].height * relClothPos[i][2]);
			}
		}

	} catch (error) {
		console.error('Error loading images:', error);
	}
}

// Initialize the avatar creation
drawAvatar();