babyltest = false;
try {
	if (babyl) babyltest = true;
} catch (e) {}

if (!babyltest) {
	var babyl = {};
	
	// A wrapper object over normal JavaScript objects. 
	//   The member obj refers to the object being wrapped
	//   the member trans holds all the translation mappings
	// trans is an associative array where keys of the form
	// fr->afficher refers to forward mappings and fr<-show
	// refers to reverse mappings. The translation mappings
	// inherits from the translation mappings of the parent
	// so as to take advantage of JavaScripts handling of
	// prototype chains to handle the traversals of
	// different translation mappings
	//
	function BabylObject(obj, parentTrans)
	{
		this.obj = obj;
		if (parentTrans) 
		{
			var objCreator = function() {};
			objCreator.prototype = parentTrans;
			this.trans = new objCreator(); 
		} else 
		{
			this.trans = {};
		}
	}
	BabylObject.prototype.obj = null;
	BabylObject.prototype.trans = {};
	BabylObject.prototype.toString = function() { return this.obj.toString(); };

	// Wrap the global object so that we can localize them (NOTE: we use "this" as
	// the global object, so it needs to change if we put this code in a function)
	//
	var babylroot = new BabylObject(this, null);

	// Code that does the lookup of translated names
	//
	function babyllookup(babylobj, lang, name)
	{
		return babylobj.trans[lang + '->' + name] || name;
	}

	// Various constant Babyl objects that mirror various JavaScript constants
	//
	var NullBabylObject = new BabylObject(null, null);
	var UndefinedBabylObject = new BabylObject(null, null);
	delete UndefinedBabylObject.obj;
	var TrueBabylObject = new BabylObject(true, null);
	var FalseBabylObject = new BabylObject(false, null);
	 
	var babylwrap = function(obj) {
		if (obj === null) 
			return NullBabylObject;
	
		switch(typeof obj) {
			case 'object':
				break;
			case 'undefined':
				return UndefinedBabylObject;
			case 'number':
				return new BabylObject(obj, null);
			case 'string':
				return new BabylObject(obj, null);
			case 'boolean':
				if (obj === true) 
					return TrueBabylObject;
				else
					return FalseBabylObject; 
			case 'function':
				return new BabylObject(obj, null);
			default:
				break;
		}
		if (obj.constructor === BabylObject) {
			return obj;  // already wrapped
		} else if (obj.constructor === Number) {
			return new BabylObject(obj, null);
		} else if (obj.constructor === String) {
			return new BabylObject(obj, null);
		} else if (obj.constructor === Boolean) {
			return new BabylObject(obj, null);
		} else if (obj.constructor === Function) {
			return new BabylObject(obj, null);
		} else {
			return new BabylObject(obj, null);
		} 
	};
}
