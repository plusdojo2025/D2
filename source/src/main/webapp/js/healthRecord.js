"use strict"

// 運動の種類とメッツ値のリスト
const exerciseTypeMetsList = {
    "ボウリング": 3.0,
    "バレーボール": 3.0,
    "社交ダンス（ワルツ、サンバ、タンゴ）": 3.0,
    "ピラティス": 3.0,
    "太極拳": 3.0,
    "自転車エルゴメーター（30～50ワット）": 3.5,
    "体操（家で、軽・中等度）": 3.5,
    "ゴルフ（手引きカートを使って）": 3.5,
    "ほどほどの強度で行う筋トレ（腕立て伏せ・腹筋運動）": 4.0,
    "卓球": 4.3,
    "パワーヨガ": 4.3,
    "ラジオ体操第１": 4.3,
    "やや速歩（平地、やや速めに＝93m/分）": 4.3,
    "ゴルフ（クラブを担いで運ぶ）": 4.3,
    "テニス（ダブルス）": 5.0,
    "水中歩行（中等度）": 5.0,
    "ラジオ体操第２": 5.0,
    "水泳（ゆっくりとした背泳）": 4.8,
    "かなり速歩（平地、速く＝107m/分）": 5.0,
    "野球": 5.0,
    "ソフトボール": 5.0,
    "サーフィン": 5.0,
    "バレエ（モダン、ジャズ）": 5.0,
    "筋トレ（スクワット）": 5.0,
    "水泳（ゆっくりとした平泳ぎ）": 6.0,
    "スキー": 6.0,
    "アクアビクス": 6.0,
    "バドミントン": 5.5,
    "ゆっくりとしたジョギング": 6.0,
    "ウェイトトレーニング（高強度、パワーリフティング、ボディビル）": 6.0,
    "バスケットボール": 6.0,
    "水泳（のんびり泳ぐ）": 6.0,
    "山を登る（0～4.1kgの荷物を持って）": 6.5,
    "自転車エルゴメーター（90～100ワット）": 6.8,
    "ジョギング": 7.0,
    "サッカー": 7.0,
    "スキー": 7.0,
    "スケート": 7.0,
    "ハンドボール": 7.0,
    "エアロビクス": 7.3,
    "テニス（シングルス）": 7.3,
    "山を登る（約4.5～9.0kgの荷物を持って）": 7.3,
    "サイクリング（約20km/時）": 8.0,
    "激しい強度で行う筋トレ（腕立て伏せ・腹筋運動）": 8.0,
    "ランニング（134m/分）": 8.3,
    "水泳（クロール、ふつうの速さ、46m/分未満）": 8.3,
    "ラグビー": 8.3,
    "ランニング（139m/分）": 9.0,
    "ランニング（161m/分）": 9.8,
    "水泳（クロール、速い、69m/分）": 10.0,
    "武道・武術（柔道、柔術、空手、キックボクシング、テコンドー）": 10.3,
    "ランニング（188m/分）": 11.0,
    "自転車エルゴメーター（161～200ワット）": 11.0
};

// 飲酒の種類とアルコール度数のリスト
const drinks = [
    { category: "ビール(5%)", type: null, strength: 5 },
    { category: "ワイン(14%)", type: null, strength: 14 },
    { category: "シャンパン(12%)", type: null, strength: 12 },
    { category: "日本酒(15%)", type: null, strength: 15 },
    { category: "焼酎", type: "割りもの(5%)", strength: 5 },
    { category: "焼酎", type: "ロック(25%)", strength: 25 },
    { category: "ウイスキー", type: "割りもの(10%)", strength: 10 },
    { category: "ウイスキー", type: "ロック(40%)", strength: 40 },
    { category: "カクテル", type: "レッドアイ(2.5%)", strength: 2.5 },
    { category: "カクテル", type: "ファジーネーブル(3%)", strength: 3 },
    { category: "カクテル", type: "ピーチウーロン(4%)", strength: 4 },
    { category: "カクテル", type: "カシスオレンジ(4%)", strength: 4 },
    { category: "カクテル", type: "チャイナブルー(8%)", strength: 8 },
    { category: "カクテル", type: "カルーアミルク(5%)", strength: 5 },
    { category: "カクテル", type: "ジントニック(9%)", strength: 9 },
    { category: "カクテル", type: "サファイアトニック(9.4%)", strength: 9.4 },
    { category: "カクテル", type: "スクリュードライバー(10%)", strength: 10 },
    { category: "カクテル", type: "ウォッカトニック(11%)", strength: 11 },
    { category: "カクテル", type: "ソルティドッグ(13%)", strength: 13 },
    { category: "カクテル", type: "ジンバック(14%)", strength: 14 },
    { category: "カクテル", type: "モヒート(20%)", strength: 20 },
    { category: "カクテル", type: "スカイ・ダイビング(28%)", strength: 28 },
    { category: "カクテル", type: "ブラックルシアン(30%)", strength: 30 },
    { category: "カクテル", type: "マティーニ(36%)", strength: 36 }
];

// グラスの種類と容量のリスト
const glassSizes = [
    { name: "ジョッキ(300ml)", volume: 300 },
    { name: "ワイングラス(120ml)", volume: 120 },
    { name: "シャンパングラス(120ml)", volume: 120 },
    { name: "おちょこ(45ml)", volume: 45 },
    { name: "ロックグラス(45ml)", volume: 45 },
    { name: "カクテルグラス(60ml)", volume: 60 },
    { name: "トール・グラス(270ml)", volume: 270 },
    { name: "ショット（シングル）(30ml)", volume: 30 },
    { name: "ショット（ダブル）(60ml)", volume: 60 }
];

let exerciseCount = 0; // 運動入力欄の数(初期値は0)
let alcoholCount = 0; // 飲酒入力欄の数(初期値は0)
let exercise_flag = false; // 運動選択欄が追加されたかどうかのフラグ
let alcohol_flag = false; // 飲酒入選択欄1が追加されたかどうかのフラグ
let grass_flag = false; // グラス選択欄が追加されたかどうかのフラグ

// 運動追加ボタンが押されたらaddExercise関数を呼び出す
document.getElementById('add_exercise_button').addEventListener('click', addExercise);
// 運動削除ボタンが押されたら、運動入力欄を削除
document.getElementById('remove_exercise_button').addEventListener('click', deleteExercise);
// 飲酒追加ボタンが押されたら、飲酒入力欄を追加
document.getElementById('add_alcohol_button').addEventListener('click', addAlcohol);
// 飲酒削除ボタンが押されたら、飲酒入力欄を削除
document.getElementById('remove_alcohol_button').addEventListener('click', deleteAlcohol);


// 以下、関数
// 運動入力欄関係-----------------------------------------------
function addExercise() {
	// 運動入力欄のコンテナを取得
	const container = document.getElementById('exercise_container');
	if (exerciseCount == 0) {
		container.style.display = ''; // 初回追加時に元の要素を表示
	} else {
		// 元の要素をクローン
		const originalInputGroup = document.querySelectorAll('.exercise_field');
		const newExerciseField = originalInputGroup[0].cloneNode(true);

		// 
		const exerciseTypeLabel = newExerciseField.querySelectorAll('label')[0];
		const timeLabel = newExerciseField.querySelectorAll('label')[1];
		const exerciseTypeInput = newExerciseField.querySelectorAll('input')[0];
		const metsInput = newExerciseField.querySelectorAll('input')[1];
		const timeInput = newExerciseField.querySelectorAll('input')[2];
		const exerciseSelect = newExerciseField.querySelector('select');

		// 各name, id, valueを書き換える
		exerciseTypeLabel.setAttribute('for', `exercise_type${exerciseCount}`);

		timeLabel.setAttribute('for', `exercise_time${exerciseCount}`);

		exerciseTypeInput.name = `exercise_type${exerciseCount}`;
		exerciseTypeInput.id = `exercise_type${exerciseCount}`;
		exerciseTypeInput.value = '';

		metsInput.name = `mets${exerciseCount}`;
		metsInput.id = `mets${exerciseCount}`;
		metsInput.value = '';

		timeInput.name = `exercise_time${exerciseCount}`;
		timeInput.value = '';

		exerciseSelect.name = `exercise_select${exerciseCount}`;
		exerciseSelect.id = `exercise_select${exerciseCount}`;

		// DOMに追加
		container.appendChild(newExerciseField);
	}

	// セレクトのイベントリスナーを新たに設定
	setupExerciseSelect(exerciseCount);
	exerciseCount++;
}

// 運動の種類セレクトを設定
function setupExerciseSelect(index) {
	const exerciseSelect = document.getElementById(`exercise_select${index}`);


	if (index === 0) {
		if (!exercise_flag) {
			// 運動の種類を追加
			for(let key in exerciseTypeMetsList) {
				const option = document.createElement('option');
				option.value = `${key}`;
				option.textContent = `${exerciseTypeMetsList[key].toFixed(1).padEnd(5, ' ')}Mets：${key}`;
				exerciseSelect.appendChild(option);
			}		
			exercise_flag = true; // フラグを立てる
		}
	}

	exerciseSelect.addEventListener('change', () => {
		const selectedExerciseKey = exerciseSelect.value;
		const typeInput = document.getElementById(`exercise_type${index}`);
		const metsInput = document.getElementById(`mets${index}`);

		if (!selectedExerciseKey) {
			// 選択がない場合は入力欄を空にする
			typeInput.value = "";
			metsInput.value = "";
		} else {
			// 選択された運動のメッツ値を取得
			typeInput.value = selectedExerciseKey;
			metsInput.value = exerciseTypeMetsList[selectedExerciseKey];
		}
	});
	
}

function deleteExercise() {
	const exerciseFields = document.querySelectorAll('.exercise_field');
	if (exerciseFields.length === 1) {
		// 最後のフィールドを削除する場合は非表示にするだけ
		if (exerciseCount != 0) {
			document.getElementById('exercise_container').style.display = 'none'; 
			exerciseCount--;
		}
	} else if (exerciseFields.length > 1) {
		// フィールドを削除
		const lastField = exerciseFields[exerciseFields.length - 1];
		lastField.remove();
		exerciseCount--;
	}
}

// 飲酒入力欄関係-----------------------------------------------
function addAlcohol() {
	const container = document.getElementById('alcohol_container');
	
	if (alcoholCount == 0) {
		 container.style.display = ''; // 初回追加時に元の要素を表示
	} else {
		// 元の要素をクローン
		const originalInputGroup = document.querySelectorAll('.alcohol_field');
		const newAlcoholField = originalInputGroup[0].cloneNode(true);

		//
		const alcoholFieldLabel = newAlcoholField.querySelectorAll('label')[0];
		const alcoholConsumedLabel = newAlcoholField.querySelectorAll('label')[1];
		const percentageInput = newAlcoholField.querySelectorAll('input')[0];
		const categorySelect = newAlcoholField.querySelectorAll('select')[0];
		const typeSelect = newAlcoholField.querySelectorAll('select')[1];
		const volumeInput = newAlcoholField.querySelectorAll(`input`)[1];
		const glassSelect = newAlcoholField.querySelectorAll('select')[2];
		const cupCountInput = newAlcoholField.querySelectorAll('input')[2];
		
		// 各id, name, valueを書き換える
		alcoholFieldLabel.setAttribute('for', `alcohol_content${alcoholCount}`);
		alcoholConsumedLabel.setAttribute('for', `alcohol_consumed${alcoholCount}`);

		percentageInput.id = `alcohol_content${alcoholCount}`;
		percentageInput.name = `alcohol_content${alcoholCount}`;
		percentageInput.value = '';

		categorySelect.id = `categorySelect${alcoholCount}`;
		categorySelect.value = '';

		typeSelect.id = `typeSelect${alcoholCount}`;
		typeSelect.innerHTML = '';
		typeSelect.disabled = true;

		volumeInput.id = `alcohol_consumed${alcoholCount}`;
		volumeInput.name = `alcohol_consumed${alcoholCount}`;
		volumeInput.value = '';

		glassSelect.id = `glassSelect${alcoholCount}`;
		glassSelect.innerHTML = '<option value="">参考: 器の種類</option>';

		cupCountInput.id = `cupCount${alcoholCount}`;
		cupCountInput.value = 1;
		cupCountInput.disabled = true;

		// DOMに追加
		container.appendChild(newAlcoholField);
	}


	// セレクトのイベントリスナーを新たに設定
	setupAlcoholSelect(alcoholCount);
	setupGlassSelect(alcoholCount);

	alcoholCount++;
};

function setupAlcoholSelect(index) {
	const categorySelect = document.getElementById(`categorySelect${index}`);
	const typeSelect = document.getElementById(`typeSelect${index}`);
	const percentageInput = document.getElementById(`alcohol_content${index}`);

	if (index === 0) {
		if (!alcohol_flag) {
			// カテゴリーの重複を排除
			const categories = [...new Set(drinks.map(d => d.category))];
			
			// カテゴリーを追加
			categories.forEach(category => {
				const option = document.createElement('option');
				option.value = category;
				option.textContent = category;
				categorySelect.appendChild(option);
			});
			alcohol_flag = true; // フラグを立てる
		}
	}

	categorySelect.addEventListener('change', () => {
		const selectedCategory = categorySelect.value;

		// 初期化
		typeSelect.innerHTML = '';
		percentageInput.value = '';

		if (!selectedCategory) {
			// カテゴリーが選択されていない場合は種類2を無効化
			typeSelect.disabled = true;
			return;
		}

		// 選択したカテゴリーの飲み物を取得
		const filteredDrinks = drinks.filter(d => d.category === selectedCategory);

		// 種類2のリストを作成（nullの場合は種類1自体が種類2とする）
		if (filteredDrinks.every(d => d.type === null)) {
			// 種類2がない場合、直接度数を表示
			typeSelect.disabled = true;
			percentageInput.value = filteredDrinks[0].strength;
		} else {
			// 種類2がある場合、種類2のセレクトを有効化
			typeSelect.disabled = false;

			const defaultOption = document.createElement('option');
			defaultOption.value = '';
			defaultOption.textContent = '--選択してください--';
			typeSelect.appendChild(defaultOption);

			filteredDrinks.forEach(d => {
				const option = document.createElement('option');
				option.value = d.type;
				option.textContent = d.type;
				typeSelect.appendChild(option);
			});
		}
	});

	typeSelect.addEventListener('change', () => {
		const selectedCategory = categorySelect.value;
		const selectedType = typeSelect.value;

		if (!selectedType) {
			// 種類が選択されていない場合は度数を空にする
			percentageInput.value = '';
			return;
		}

		const selectedDrink = drinks.find(d => d.category === selectedCategory && d.type === selectedType);

		if (selectedDrink) {
			// 選択された飲み物の度数を入力欄に設定
			percentageInput.value = selectedDrink.strength;
		} else {
			// 選択された飲み物が見つからない場合は度数を空にする
			percentageInput.value = '';
		}
	});
}

function setupGlassSelect(index) {
	const glassSelect = document.getElementById(`glassSelect${index}`);
	const cupCountInput = document.getElementById(`cupCount${index}`);
	const volumeInput = document.getElementById(`alcohol_consumed${index}`);

	// グラスサイズのリスト追加
	if (!grass_flag) {
		glassSizes.forEach(glass => {
			const option = document.createElement('option');
			option.value = glass.volume;
			option.textContent = glass.name;
			glassSelect.appendChild(option);
		});
		grass_flag = true; // フラグを立てる
	}

	// グラスサイズの選択時に摂取量計算
	glassSelect.addEventListener('change', () => {
		const selectedVolume = glassSelect.value;
		const cupCount = parseInt(cupCountInput.value, 10) || 0;

		if (selectedVolume) {
			// グラスのサイズが選択された場合、杯数入力欄を有効化
			cupCountInput.disabled = false;
			const totalVolume = selectedVolume * cupCount;
			volumeInput.value = totalVolume;
		} else {
			// グラスのサイズが選択されていない場合、杯数入力欄を無効化
			cupCountInput.disabled = true;
			volumeInput.value = '';
		}
	});

	// グラスのカウント入力欄の変更時に摂取量計算
	cupCountInput.addEventListener('change', () => {
		const selectedVolume = glassSelect.value;
		const cupCount = parseInt(cupCountInput.value, 10) || 0;

		if (selectedVolume) {
			// グラスのサイズが選択されている場合、摂取量を計算
			const totalVolume = selectedVolume * cupCount;
			volumeInput.value = totalVolume;
		} else {
			// グラスのサイズが選択されていない場合、摂取量を空にする
			volumeInput.value = '';
		}
	});
}

function deleteAlcohol() {
	const alcoholFields = document.querySelectorAll('.alcohol_field');
	if (alcoholFields.length === 1) {
		// 最後のフィールドを削除する場合は非表示にするだけ
		if (alcoholCount != 0) { 
			document.getElementById('alcohol_container').style.display = 'none';
			alcoholCount--;
		}
	} else if (alcoholFields.length > 1) {
		// フィールドを削除
		const lastField = alcoholFields[alcoholFields.length - 1];
		lastField.remove();
		alcoholCount--;
	}
};

// ------------------------------------------------------

// ホイール操作でinput numberを操作
// 新しく追加されたinput numberにも対応
document.addEventListener('wheel', function(event) {
	const target = event.target;
	if (target.tagName.toLowerCase() === 'input' && target.type === 'number') {
		event.preventDefault();
		const delta = Math.sign(event.deltaY);
		target.stepUp(-delta);
	}
}, { passive: false });