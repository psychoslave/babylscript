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

	var NullBabylObject = new BabylObject(null, null);
	 
	var babylwrap = function(obj) {
		if (obj == null) {
			return NullBabylObject;
		} else if (obj.constructor === BabylObject) {
			return obj;  // already wrapped
		} else if (typeof obj == 'number' || obj.constructor === Number) {
			return new BabylObject(obj, null);
		} else if (typeof obj == 'string' || obj.constructor === String) {
			return new BabylObject(obj, null);
		} else if (typeof obj == 'boolean' || obj.constructor === Boolean) {
			return new BabylObject(obj, null);
		} else if (typeof obj == 'function' || obj.constructor === Function) {
			return new BabylObject(obj, null);
		} else {
			return new BabylObject(obj, null);
		} 
	};
}