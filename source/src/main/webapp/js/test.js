// avatar.js
"use strict"

// Get the canvas and context
const canvasList = document.getElementsByClassName('imageCanvas');


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
const drawAvatar = async (year, month) => {
	const buildImages = [];
	const clothImages = [];
	const canvas = canvasList[yearMonthList.findIndex(item => item.year === year && item.month === month)];
	const ctx = canvas.getContext('2d');
	try {
		const x = 0; // アバターの位置
		const y = 0; // アバターの位置
		const x_people = 800; // 周りの人の位置
		const y_people = 500; // 周りの人の位置
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
            [-600, 150, -0.25], // 左激遠
            [-450, 100, 0.4], // 左中
            [700, 150, 0.25], // 右激遠
            [100, 80, 0.5], // 右中
            [270, 120, -0.55], // 右中近
            [400, 100, 1],// 右近
            [-1200, 90, -1.75], // 左激近
        ];

		// Load base images
		const avatar = taList[`${year}-${month}`];
        let backgroundImage;       
		const defaultClothImage = await loadImage('img/cloth0.png')
		const faceImage = await loadImage(avatar.facePath); 
		const peopleImage = await loadImage(avatar.peoplePath);
		const peopleNum = avatar.peopleCount;
		
		// build, cloth, peopleの中にどれか一つでもuniverseが含まれていたら宇宙の背景をloadする
		const universe = [avatar.buildPaths, [avatar.peoplePath], [avatar.costumePath]].some(arr =>
            Array.from(arr).some(item => item.includes('universe'))
        );
        if (universe) {
            backgroundImage = await loadImage('img/universe_background.png');
        } else {
            backgroundImage = await loadImage('img/background.png');
        }
		
		for (let i = 0; i < avatar.buildPaths.length; i++) {
			buildImages[i] = await loadImage(avatar.buildPaths[i]);
		}

		if(avatar.costumePath != ""){
            clothImages[3] = await loadImage(avatar.costumePath);
        }
        else{
			let clothNames = ["cloth", "shoe", "hat"];
			for (let i = 0; i < clothNames.length; i++) {
				
				if (clothNames[i] === "cloth") {
					if (avatar.clothPath == "") break;
					clothImages[i] = await loadImage(avatar.clothPath);
				} else if (clothNames[i] === "shoe") {
					if (avatar.shoePath == "") break;
					clothImages[i] = await loadImage(avatar.shoePath);
				} else if (clothNames[i] == "hat") {
					if (avatar.hatPath == "") break;
					clothImages[i] = await loadImage(avatar.hatPath);
				}
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
			const scale = relPeoplePos[i][2];
            const drawWidth = peopleImage.width * scale;
            const drawHeight = peopleImage.height * scale;

            if (0 < scale){
                ctx.drawImage(
                    peopleImage, 
                    x_people + relPeoplePos[i][0], 
                    y_people + relPeoplePos[i][1],
                    drawWidth, 
                    drawHeight
                );
            } else { //左右反転
                ctx.save();
                ctx.scale(-1, 1);
                ctx.drawImage(
                    peopleImage, 
                    -(x_people + relPeoplePos[i][0]), 
                    y_people + relPeoplePos[i][1],
                    drawWidth, 
                    -drawHeight
                ); 
                ctx.restore();
            }
		}
		// cloth and face
		if (clothImages.length === 4) {
			ctx.drawImage(clothImages[3], x, y, clothImages[3].width * scaleCloth4, clothImages[3].height * scaleCloth4);
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
		} else {
			ctx.drawImage(defaultClothImage, x, y, defaultClothImage.width * scaleAvatar, defaultClothImage.height * scaleAvatar);
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
			for (let i = 0; i < clothImages.length; i++) {
				ctx.drawImage(clothImages[i], x + relClothPos[i][0], y + relClothPos[i][1], clothImages[i].width * relClothPos[i][2], clothImages[i].height * relClothPos[i][2]);
			}
		}

	} catch (error) {
		console.error('Error loading images:', error);
	}
};

// Initialize the avatar creation
yearMonthList.forEach(({ year, month }) => {
	drawAvatar(year, month);
});

// Draw images on the canvas
const drawAvatar2 = async (year, month) => {
	const buildImages = [];
	const clothImages = [];
	const canvas = document.getElementById('canvas2');
	const ctx = canvas.getContext('2d');
	try {
		const x = 0; // アバターの位置
		const y = 0; // アバターの位置
		const x_people = 800; // 周りの人の位置
		const y_people = 500; // 周りの人の位置
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
			[-600, 150, -0.25], // 左激遠
            [-450, 100, 0.4], // 左中
            [700, 150, 0.25], // 右激遠
            [100, 80, 0.5], // 右中
            [270, 120, -0.55], // 右中近
            [400, 100, 1],// 右近
            [-1200, 90, -1.75], // 左激近
		];

		// Load base images
		const avatar = taList[`${year}-${month}`];
		let backgroundImage;
		const defaultClothImage = await loadImage('img/cloth0.png')
		const faceImage = await loadImage(avatar.facePath); 
		const peopleImage = await loadImage(avatar.peoplePath);
		const peopleNum = avatar.peopleCount;
		
		// build, cloth, peopleの中にどれか一つでもuniverseが含まれていたら宇宙の背景をloadする
		const universe = [avatar.buildPaths, [avatar.peoplePath], [avatar.costumePath]].some(arr =>
            Array.from(arr).some(item => item.includes('universe'))
        );
        if (universe) {
            backgroundImage = await loadImage('img/universe_background.png');
        } else {
            backgroundImage = await loadImage('img/background.png');
        }
        
		for (let i = 0; i < avatar.buildPaths.length; i++) {
			buildImages[i] = await loadImage(avatar.buildPaths[i]);
		}

		if(avatar.costumePath != ""){
            clothImages[3] = await loadImage(avatar.costumePath);
        }
        else{
			let clothNames = ["cloth", "shoe", "hat"];
			for (let i = 0; i < clothNames.length; i++) {
				
				if (clothNames[i] === "cloth") {
					if (avatar.clothPath == "") break;
					clothImages[i] = await loadImage(avatar.clothPath);
				} else if (clothNames[i] === "shoe") {
					if (avatar.shoePath == "") break;
					clothImages[i] = await loadImage(avatar.shoePath);
				} else if (clothNames[i] == "hat") {
					if (avatar.hatPath == "") break;
					clothImages[i] = await loadImage(avatar.hatPath);
				}
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
			const scale = relPeoplePos[i][2];
            const drawWidth = peopleImage.width * scale;
            const drawHeight = peopleImage.height * scale;

            if (0 < scale){
                ctx.drawImage(
                    peopleImage, 
                    x_people + relPeoplePos[i][0], 
                    y_people + relPeoplePos[i][1],
                    drawWidth, 
                    drawHeight
                );
            } else { //左右反転
                ctx.save();
                ctx.scale(-1, 1);
                ctx.drawImage(
                    peopleImage, 
                    -(x_people + relPeoplePos[i][0]), 
                    y_people + relPeoplePos[i][1],
                    drawWidth, 
                    -drawHeight
                ); 
                ctx.restore();
            }
		}
		// cloth and face
		if (clothImages.length === 4) {
			ctx.drawImage(clothImages[3], x, y, clothImages[3].width * scaleCloth4, clothImages[3].height * scaleCloth4);
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
		} else {
			ctx.drawImage(defaultClothImage, x, y, defaultClothImage.width * scaleAvatar, defaultClothImage.height * scaleAvatar);
			ctx.drawImage(faceImage, x, y, faceImage.width * scaleAvatar, faceImage.height * scaleAvatar);
			for (let i = 0; i < clothImages.length; i++) {
				ctx.drawImage(clothImages[i], x + relClothPos[i][0], y + relClothPos[i][1], clothImages[i].width * relClothPos[i][2], clothImages[i].height * relClothPos[i][2]);
			}
		}

	} catch (error) {
		console.error('Error loading images:', error);
	}
};