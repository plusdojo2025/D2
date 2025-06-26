"use strict"

const fileInput = document.getElementById('file-input');
const fileNameSpan = document.getElementById('file-name');

// ラベルクリックでファイル選択を開く
document.querySelector('.select-button').addEventListener('click', () => {
	fileInput.click();
});

// ファイルが選択されたときにファイル名を表示
fileInput.addEventListener('change', () => {
	const files = Array.from(fileInput.files);
	if (files.length > 0) {
		// ファイル名をカンマ区切りで表示
		fileNameSpan.textContent = files.map(file => file.name).join(', ');
	} else {
		fileNameSpan.textContent = 'ファイルを選択してください';
	}
});

document.getElementById("downloadForm").addEventListener("submit", function(e) {
	const checkboxes = this.querySelectorAll('input[name="fileNames"]:checked');
	if (checkboxes.length === 0) {
		alert("少なくとも1つのファイルを選択してください。");
		e.preventDefault(); // フォーム送信を中断
	}
});

function showHelp() {
	// このページでは、１年以上前の過去ファイルのダウンロード・街並みの表示ができます。「ダウンロード」で過去ファイルの保存 → 保存したファイルを展開 → 展開したテキストファイルを「選択」 → 「街を見る」で表示できます。
	alert(
		"過去ファイルページ\n ～１年以上前の過去の街並みを振り返る場所～\n\n"
    + "概要：\n"
    + "本アプリではサーバーへの負担を軽減するため、過去の記録は1年分しか保存されていません。\n"
    + "しかし、過去の街並みを振り返りたいという要望に応えるため、削除された過去の街並みを再構成できるファイル(txt)をダウンロードして保存する機能を提供しています。\n\n"
		+ "このページでできること\n"
		+ "1. 「ダウンロード」で過去ファイルの保存\n"
		+ "2. 保存したファイルを展開 → 展開したテキストファイルを「選択」 → 「街を見る」で表示"
	);
}