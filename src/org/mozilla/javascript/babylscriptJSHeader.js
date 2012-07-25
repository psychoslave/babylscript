babyltest = false;
try {
	if (babyl) babyltest = true;
} catch (e) {}

if (!babyltest) {
	var babyl = {};
	
	// Insert a translation mapping into every JavaScript object
	Object.defineProperty(Object.prototype, 'babylscript_translations', {value: {}});

	// Wrap the global object so that we can localize them (NOTE: we use "this" as
	// the global object, so it needs to change if we put this code in a function)
	//
	if (!this.window) window = this;
	var babylroot = window;

	// Code that does the lookup of translated names
	//
	function babyllookup(babylobj, lang, name)
	{
		return babylobj.babylscript_translations[lang + '->' + name] || name;
	}

	var babylwrap = function(obj) {
		if (obj != null && !obj.hasOwnProperty('babylscript_translations'))
		{
			var objCreator = function() {};
			objCreator.prototype = babylwrap(Object.getPrototypeOf(obj)).babylscript_translations;
			var trans = new objCreator(); 
			Object.defineProperty(obj, 'babylscript_translations', trans);
		} 
		return obj;
	};
}
