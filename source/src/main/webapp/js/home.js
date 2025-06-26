document.addEventListener('wheel', function(event) {
	const target = event.target;
	if (target.tagName.toLowerCase() === 'input' && target.type === 'number') {
		event.preventDefault();
		const delta = Math.sign(event.deltaY);
		target.stepUp(-delta);
	}
}, { passive: false });

function showHelp() {
	alert(
		"ケンコークラフト\n"
		+ "～健康習慣を維持し、街並み・アバターを発展させよう～\n\n"
		+ "概要：\n"
		+ "あなたの日報(健康記録)とあなたの設定した目標値をもとに、目標を達成したら内部的にポイントがもらえます。\n"
		+ "ポイントが一定の値を超えると、街並みが発展し、アバターの服装や顔が変わります。\n\n"
		+ "このページでできること\n"
		+ "1. 月初めにその月の目標値の登録\n"
		+ "2. 各ページへの遷移"
		
	)
}