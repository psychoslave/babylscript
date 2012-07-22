babyltest = false;
try {
	if (babyl) babyltest = true;
} catch (e) {}

if (!babyltest) {
	babyl = {};
}