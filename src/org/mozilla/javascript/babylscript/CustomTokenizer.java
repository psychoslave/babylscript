/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Rhino code, released
 * May 6, 1999.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1997-1999
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *   Roger Lawrence
 *   Mike McCabe
 *   Igor Bukanov
 *   Ethan Hugg
 *   Bob Jervis
 *   Terry Lucas
 *   Milen Nankov
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU General Public License Version 2 or later (the "GPL"), in which
 * case the provisions of the GPL are applicable instead of those above. If
 * you wish to allow use of your version of this file only under the terms of
 * the GPL and not to allow others to use your version of this file under the
 * MPL, indicate your decision by deleting the provisions above and replacing
 * them with the notice and other provisions required by the GPL. If you do
 * not delete the provisions above, a recipient may use your version of this
 * file under either the MPL or the GPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.mozilla.javascript.babylscript;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.mozilla.javascript.Parser;
import org.mozilla.javascript.TokenCharStream;
import org.mozilla.javascript.TokenStream;
import org.mozilla.javascript.babylscript.gen.Keywords;

public class CustomTokenizer extends BabylGenericTokenizer
{
//    static class CustomPropertyResourceBundle extends ResourceBundle
//    {
//        Properties translations;
//        CustomPropertyResourceBundle(Properties translations)  
//        {
//            this.translations = translations;
//        }
//        protected Object handleGetObject(String key) 
//        {
//            if (translations.containsKey(key))
//                return translations.get(key);
//            else
//                return key;
//        }
//        public Enumeration<String> getKeys() 
//        {
//            return (Enumeration<String>)translations.propertyNames();
//        }
//    }
    
    public CustomTokenizer(Parser p, TokenCharStream in, TokenStream ts, CustomTokenizerConfig config) 
    {
        super(p, in, ts, new DecimalNumberReader('.'), BabylGenericTokenizer.arrayToMap(Keywords.en));
    }

    protected boolean isStringDelimiter(int ch)
    {
        return (ch == '\'' || ch == '\"' || ch == '\u00AB');
    }

    protected int getMatchingStringDelimiter(int ch)
    {
        if (ch == '\'') return '\'';
        if (ch == '\u00AB') return '\u00BB';
        return '\"';
    }
}
