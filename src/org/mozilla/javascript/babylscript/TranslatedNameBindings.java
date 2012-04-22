package org.mozilla.javascript.babylscript;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * This class holds all the translated names used in the JavaScript standard
 * library. By putting everything in this central location, it's easier to
 * to make a translation, hopefully.
 */

public class TranslatedNameBindings
{
    public static Map<String, String[]> EquivalentLanguageNames;  // read-only
    static {
        EquivalentLanguageNames = new HashMap<String, String[]>();
        // The equivalent language name mappings must always show ALL the equivalent names and in the same order
        EquivalentLanguageNames.put("ar", new String[] {"ar", "\u0639\u0631\u0628\u064a"});
        EquivalentLanguageNames.put("\u0639\u0631\u0628\u064a", new String[] {"ar", "\u0639\u0631\u0628\u064a"});

        EquivalentLanguageNames.put("zh", new String[] {"zh", "\u4e2d\u6587", "\u7b80\u4f53"});
        EquivalentLanguageNames.put("\u4e2d\u6587", new String[] {"zh", "\u4e2d\u6587", "\u7b80\u4f53"});
        EquivalentLanguageNames.put("\u7b80\u4f53", new String[] {"zh", "\u4e2d\u6587", "\u7b80\u4f53"});

        EquivalentLanguageNames.put("hi", new String[] {"hi", "\u0939\u093f"});
        EquivalentLanguageNames.put("\u0939\u093f", new String[] {"hi", "\u0939\u093f"});

        EquivalentLanguageNames = Collections.unmodifiableMap(EquivalentLanguageNames);
    }

    static ResourceBundle romanian = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("ro"));
    static ResourceBundle french = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("fr"));
    static ResourceBundle arabic = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("ar"));
    static ResourceBundle portuguese = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("pt"));
    static ResourceBundle chineseSimplified = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("zh"));
    static ResourceBundle hindi = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("hi"));
    static ResourceBundle spanish = BabylscriptNoDefaultResourceBundle.getBundle("org/mozilla/javascript/babylscript/resources/Objects", new Locale("es"));

    static Map<String, ResourceBundle> langResourceMap = new HashMap<String, ResourceBundle>();
    static {
        langResourceMap.put("fr", french);
        langResourceMap.put("ro", romanian);
        langResourceMap.put("pt", portuguese);
        langResourceMap.put("ar", arabic);
        langResourceMap.put("zh", chineseSimplified);
        langResourceMap.put("hi", hindi);
        langResourceMap.put("es", spanish);
    }
    
    private static void fillTranslationsFromResourceBundle(Scriptable obj, String lang, ResourceBundle res, String[] english)
    {
        for (String key: english)
            obj.putTranslatedName(lang, res.getString(key), obj, key);
    }

    static class CustomPropertyResourceBundle extends PropertyResourceBundle
    {
        CustomPropertyResourceBundle(Properties translations, ResourceBundle parent) throws IOException
        {
            super(PropertiesToReader(translations));
            setParent(parent);
        }
        static Reader PropertiesToReader(Properties translations) throws IOException
        {
            StringWriter writer = new StringWriter();
            translations.store(writer, null);
            return new StringReader(writer.toString());            
        }
    }

    public static void initStandardTranslations(Scriptable scope)
    {
        for (Map.Entry<String, ResourceBundle> entry: langResourceMap.entrySet())
        {
            configureAllTranslations(scope, entry.getKey(), entry.getValue());
        }
    }

    public static void initCustomTranslation(Scriptable scope, Properties translations)
    {
        String lang = "test";
        try {
            ResourceBundle res = new CustomPropertyResourceBundle(translations,
                ResourceBundle.getBundle("org.mozilla.javascript.babylscript.resources.Objects", new Locale("")));
            configureAllTranslations(scope, lang, res);
        } catch (IOException e)
        {
        
        }
    }
    
    public static void configureAllTranslations(Scriptable scope, String lang, ResourceBundle res)
    {
        ScriptableObject.clearTranslations(scope, lang);
        configureGlobalScopeTranslations(scope, lang, res);
        Scriptable obj = ScriptableObject.getFunctionPrototype(scope);
        configureBaseFunctionPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getObjectPrototype(scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureObjectPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Error");
        ScriptableObject.clearTranslations(obj, lang);
        configureErrorPrototypeTranslations(obj, lang, res);
        obj = (Scriptable)scope.get("Array", scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureArrayConstructorTranslations((Function)obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Array");
        ScriptableObject.clearTranslations(obj, lang);
        configureArrayPrototypeTranslations(obj, lang, res);
        obj = (Scriptable)scope.get("String", scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureStringConstructorTranslations((Function)obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "String");
        ScriptableObject.clearTranslations(obj, lang);
        configureStringPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Boolean");
        ScriptableObject.clearTranslations(obj, lang);
        configureBooleanPrototypeTranslations(obj, lang, res);
        obj = (Scriptable)scope.get("Number", scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureNumberConstructurTranslations((Function)obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Number");
        ScriptableObject.clearTranslations(obj, lang);
        configureNumberPrototypeTranslations(obj, lang, res);
        obj = (Scriptable)scope.get("Date", scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureDateConstructorTranslations((Function)obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Date");
        ScriptableObject.clearTranslations(obj, lang);
        configureDatePrototypeTranslations(obj, lang, res);
        obj = (Scriptable)scope.get("Math", scope);
        ScriptableObject.clearTranslations(obj, lang);
        configureMathTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Call");
        ScriptableObject.clearTranslations(obj, lang);
        configureCallPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Script");
        ScriptableObject.clearTranslations(obj, lang);
        configureScriptPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "Iterator");
        ScriptableObject.clearTranslations(obj, lang);
        configureIteratorPrototypeTranslations(obj, lang, res);
        obj = ScriptableObject.getClassPrototype(scope, "RegExp");
        ScriptableObject.clearTranslations(obj, lang);
        configureRegExpPrototypeTranslations(obj, lang, res);
//        configureRegExpMatchesPrototypeTranslations(ScriptableObject.getClassPrototype(scope, "Object"), lang, res);
    }
    
    // TODO: the activation object also has translations
    // +callee
    // +length
    // +caller

    protected static void configureGlobalScopeTranslations(Scriptable scope, String lang, ResourceBundle res)
    {
        Scriptable obj = scope;
        String [] names = new String[] {
                // TODO: arguments object needs special handling because the compiler 
                // identifies it and treats it specially
                "arguments",
                
                "Array",
                "Boolean",
                "Call",
                "ConversionError",
                "Date",
                "decodeURI",
                "decodeURIComponent",
                "encodeURI",
                "encodeURIComponent",
                "Error",
                "escape",
                "eval",
                "EvalError",
                "Function",
                "Infinity",
                "InternalError",
                "isFinite",
                "isNaN",
                "isXMLName",
                "Iterator",
                "JavaException",
                "LIBRARY_SCOPE",
                "Math",
                "NaN",
                "Number",
                "Object",
                "parseFloat",
                "parseInt",
                "RangeError",
                "ReferenceError",
                "RegExp",
                "Script",
                "StopIteration",
                "String",
                "SyntaxError",
                "TypeError",
                "undefined",
                "unescape",
                "uneval",
                "URIError",
                "With",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureBaseFunctionPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "name",
                "arity",
                "length",
                "prototype",

                // TODO: Things like arguments are usually handled specially
                "arguments",
        //      "constructor",
                "toString",
                "toSource",
                "apply",
                "call",
        };

        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureObjectPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "constructor",
                "toString",
                "toLocaleString",
                "valueOf",
                "hasOwnProperty",
                "propertyIsEnumerable",
                "isPrototypeOf",
                "toSource",
                "__defineGetter__",
                "__defineSetter__",
                "__lookupGetter__",
                "__lookupSetter__",
        }; 
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureErrorPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "name",
                "message",
                "fileName",
                "lineNumber",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureArrayConstructorTranslations(Function obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "join",
                "reverse",
                "sort",
                "push",
                "pop",
                "shift",
                "unshift",
                "splice",
                "concat",
                "slice",
                "indexOf",
                "lastIndexOf",
                "every",
                "filter",
                "forEach",
                "map",
                "some",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureArrayPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "length",
                "join",
                "reverse",
                "sort",
                "push",
                "pop",
                "shift",
                "unshift",
                "splice",
                "concat",
                "slice",
                "indexOf",
                "lastIndexOf",
                "every",
                "filter",
                "forEach",
                "map",
                "some",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureStringConstructorTranslations(Function obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "fromCharCode",

                // The methods below are defined for the constructor in Rhino, but they
                // make no sense there, and they don't seem to be consistently defined,
                // and I don't think they are defined any more in modern versions of
                // JavaScript
                "charAt",
                "charCodeAt",
                "indexOf",
                "lastIndexOf",
                "split",
                "substring",
                "toLowerCase",
                "toUpperCase",
                "substr",
                "concat",
                "slice",
                "equalsIgnoreCase",
                "match",
                "search",
                "replace",
                "localeCompare",
                "toLocaleLowerCase",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureStringPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "length",
                "valueOf",
                "charAt",
                "charCodeAt",
                "indexOf",
                "lastIndexOf",
                "split",
                "substring",
                "toLowerCase",
                "toUpperCase",
                "substr",
                "concat",
                "slice",
                "bold",
                "italics",
                "fixed",
                "strike",
                "small",
                "big",
                "blink",
                "sup",
                "sub",
                "fontsize",
                "fontcolor",
                "link",
                "anchor",
                "equals",
                "equalsIgnoreCase",
                "match",
                "search",
                "replace",
                "localeCompare",
                "toLocaleLowerCase",
                "toLocaleUpperCase",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureBooleanPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "valueOf",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureNumberConstructurTranslations(Function obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "NaN",
                "POSITIVE_INFINITY",
                "NEGATIVE_INFINITY",
                "MAX_VALUE",
                "MIN_VALUE",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureNumberPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "valueOf",
                "toFixed",
                "toExponential",
                "toPrecision",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureDateConstructorTranslations(Function obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "now",
                "parse",
                "UTC",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureDatePrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "toTimeString",
                "toDateString",
                "toLocaleTimeString",
                "toLocaleDateString",
                "toUTCString",
                "valueOf",
                "getTime",
                "getYear",
                "getFullYear",
                "getUTCFullYear",
                "getMonth",
                "getUTCMonth",
                "getDate",
                "getUTCDate",
                "getDay",
                "getUTCDay",
                "getHours",
                "getUTCHours",
                "getMinutes",
                "getUTCMinutes",
                "getSeconds",
                "getUTCSeconds",
                "getMilliseconds",
                "getUTCMilliseconds",
                "getTimezoneOffset",
                "setTime",
                "setMilliseconds",
                "setUTCMilliseconds",
                "setSeconds",
                "setUTCSeconds",
                "setMinutes",
                "setUTCMinutes",
                "setHours",
                "setUTCHours",
                "setDate",
                "setUTCDate",
                "setMonth",
                "setUTCMonth",
                "setFullYear",
                "setUTCFullYear",
                "setYear",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureMathTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "abs",
                "acos",
                "asin",
                "atan",
                "atan2",
                "ceil",
                "cos",
                "exp",
                "floor",
                "log",
                "max",
                "min",
                "pow",
                "random",
                "round",
                "sin",
                "sqrt",
                "tan",
                "E",
                "PI",
                "LN10",
                "LN2",
                "LOG2E",
                "LOG10E",
                "SQRT1_2",
                "SQRT2",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureCallPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        // TODO: arguments are usually handled specially
        String [] names = new String[] {
                "arguments",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureScriptPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "exec",
                "compile",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureIteratorPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                "next",
                "__iterator__",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureRegExpPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                // TODO: Ignoring RegExp constructor translations for now
                "lastIndex",
                "source",
                "global",
                "ignoreCase",
                "multiline",
                "compile",
                "exec",
                "test",
                "prefix",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }

    protected static void configureRegExpMatchesPrototypeTranslations(Scriptable obj, String lang, ResourceBundle res)
    {
        String [] names = new String[] {
                // TODO: JavaScript might not have a prototype for its match objects (uses Array objects)
                "index",
                "input",
        };
        fillTranslationsFromResourceBundle(obj, lang, res, names);
    }
}
