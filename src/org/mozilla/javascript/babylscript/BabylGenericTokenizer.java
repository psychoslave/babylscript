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

import java.util.ResourceBundle;

import org.mozilla.javascript.BabylTokenizer;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.TokenCharStream;
import org.mozilla.javascript.TokenStream;

public class BabylGenericTokenizer extends BabylTokenizer
{
    BabylGenericTokenizer(Parser p, TokenCharStream in, TokenStream ts, DecimalNumberReader numberReader, ResourceBundle keywords)
    {
        super(p, in, ts, numberReader);
        
        keywordLookup.put(keywords.getString("function"), Id_function);
        keywordLookup.put(keywords.getString("if"), Id_if);
        keywordLookup.put(keywords.getString("else"), Id_else);
        keywordLookup.put(keywords.getString("for"), Id_for);
        keywordLookup.put(keywords.getString("while"), Id_while);
        keywordLookup.put(keywords.getString("return"), Id_return);  
        keywordLookup.put(keywords.getString("do"), Id_do);
        keywordLookup.put(keywords.getString("true"), Id_true);
        keywordLookup.put(keywords.getString("false"), Id_false);
        keywordLookup.put(keywords.getString("var"), Id_var);
        keywordLookup.put(keywords.getString("break"), Id_break);
        keywordLookup.put(keywords.getString("try"), Id_try);
        keywordLookup.put(keywords.getString("switch"), Id_switch);
        keywordLookup.put(keywords.getString("case"), Id_case);
        keywordLookup.put(keywords.getString("null"), Id_null);

        keywordLookup.put(keywords.getString("in"), Id_in);
        keywordLookup.put(keywords.getString("int"), Id_int);
        keywordLookup.put(keywords.getString("let"), Id_let);
        keywordLookup.put(keywords.getString("new"), Id_new);
        keywordLookup.put(keywords.getString("byte"), Id_byte);
        keywordLookup.put(keywords.getString("char"), Id_char);
        keywordLookup.put(keywords.getString("enum"), Id_enum);
        keywordLookup.put(keywords.getString("goto"), Id_goto);
        keywordLookup.put(keywords.getString("long"), Id_long);
        keywordLookup.put(keywords.getString("this"), Id_this);
        keywordLookup.put(keywords.getString("void"), Id_void);
        keywordLookup.put(keywords.getString("with"), Id_with);
        keywordLookup.put(keywords.getString("class"), Id_class);
        keywordLookup.put(keywords.getString("yield"), Id_yield);
        keywordLookup.put(keywords.getString("const"), Id_const);
        keywordLookup.put(keywords.getString("final"), Id_final);
        keywordLookup.put(keywords.getString("float"), Id_float);
        keywordLookup.put(keywords.getString("short"), Id_short);
        keywordLookup.put(keywords.getString("super"), Id_super);
        keywordLookup.put(keywords.getString("throw"), Id_throw);
        keywordLookup.put(keywords.getString("catch"), Id_catch);
        keywordLookup.put(keywords.getString("native"), Id_native);
        keywordLookup.put(keywords.getString("delete"), Id_delete);
        keywordLookup.put(keywords.getString("throws"), Id_throws);
        keywordLookup.put(keywords.getString("import"), Id_import);
        keywordLookup.put(keywords.getString("double"), Id_double);
        keywordLookup.put(keywords.getString("static"), Id_static);
        keywordLookup.put(keywords.getString("public"), Id_public);
        keywordLookup.put(keywords.getString("export"), Id_export);
        keywordLookup.put(keywords.getString("typeof"), Id_typeof);
        keywordLookup.put(keywords.getString("package"), Id_package);
        keywordLookup.put(keywords.getString("default"), Id_default);
        keywordLookup.put(keywords.getString("finally"), Id_finally);
        keywordLookup.put(keywords.getString("boolean"), Id_boolean);
        keywordLookup.put(keywords.getString("private"), Id_private);
        keywordLookup.put(keywords.getString("extends"), Id_extends);
        keywordLookup.put(keywords.getString("abstract"), Id_abstract);
        keywordLookup.put(keywords.getString("continue"), Id_continue);
        keywordLookup.put(keywords.getString("debugger"), Id_debugger);
        keywordLookup.put(keywords.getString("volatile"), Id_volatile);
        keywordLookup.put(keywords.getString("interface"), Id_interface);
        keywordLookup.put(keywords.getString("protected"), Id_protected);
        keywordLookup.put(keywords.getString("transient"), Id_transient);
        keywordLookup.put(keywords.getString("implements"), Id_implements);
        keywordLookup.put(keywords.getString("instanceof"), Id_instanceof);
        keywordLookup.put(keywords.getString("synchronized"), Id_synchronized);    
    }
}
