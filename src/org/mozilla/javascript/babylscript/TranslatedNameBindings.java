package org.mozilla.javascript.babylscript;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * This class holds all the translated names used in the JavaScript standard
 * library. By putting everything in this central location, it's easier to
 * to make a translation, hopefully.
 * 
 * 
 */

public class TranslatedNameBindings
{
    public static Map<String, String[]> EquivalentLanguageNames;  // read-only
    static {
        EquivalentLanguageNames = new HashMap<String, String[]>();
        // The equivalent language name mappings must always show ALL the equivalent names and in the same order
        EquivalentLanguageNames.put("ar", new String[] {"ar", "\u0639\u0631\u0628\u064a"});
        EquivalentLanguageNames.put("\u0639\u0631\u0628\u064a", new String[] {"ar", "\u0639\u0631\u0628\u064a"});
        EquivalentLanguageNames = Collections.unmodifiableMap(EquivalentLanguageNames);
    }
}
