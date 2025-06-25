document.addEventListener('wheel', function(event) {
	const target = event.target;
	if (target.tagName.toLowerCase() === 'input' && target.type === 'number') {
		event.preventDefault();
		const delta = Math.sign(event.deltaY);
		target.stepUp(-delta);
	}
}, { passive: false });