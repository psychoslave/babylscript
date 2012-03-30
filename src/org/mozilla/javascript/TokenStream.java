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

package org.mozilla.javascript;

import java.io.*;

/**
 * This class implements the JavaScript scanner.
 *
 * It is based on the C source files jsscan.c and jsscan.h
 * in the jsref package.
 *
 * @see org.mozilla.javascript.Parser
 *
 * @author Mike McCabe
 * @author Brendan Eich
 */

class TokenStream
{
    /*
     * For chars - because we need something out-of-range
     * to check.  (And checking EOF by exception is annoying.)
     * Note distinction from EOF token type!
     */
    private final static int
        EOF_CHAR = -1;

    TokenStream(Parser parser, Reader sourceReader, String sourceString,
                int lineno)
    {
        this.parser = parser;
        this.in = new TokenCharStream(sourceReader, sourceString, lineno);
        xmlTokenizer = new XMLTokenizer(parser, in, this);
    }

    /* This function uses the cached op, string and number fields in
     * TokenStream; if getToken has been called since the passed token
     * was scanned, the op or string printed may be incorrect.
     */
    String tokenToString(int token)
    {
        if (Token.printTrees) {
            String name = Token.name(token);

            switch (token) {
            case Token.STRING:
            case Token.REGEXP:
            case Token.NAME:
                return name + " `" + this.string + "'";

            case Token.NUMBER:
                return "NUMBER " + this.number;
            }

            return name;
        }
        return "";
    }

    static boolean isKeyword(String s)
    {
        return Token.EOF != stringToKeyword(s);
    }

    private static int stringToKeyword(String name)
    {
// #string_id_map#
// The following assumes that Token.EOF == 0
        final int
            Id_break         = Token.BREAK,
            Id_case          = Token.CASE,
            Id_continue      = Token.CONTINUE,
            Id_default       = Token.DEFAULT,
            Id_delete        = Token.DELPROP,
            Id_do            = Token.DO,
            Id_else          = Token.ELSE,
            Id_export        = Token.EXPORT,
            Id_false         = Token.FALSE,
            Id_for           = Token.FOR,
            Id_function      = Token.FUNCTION,
            Id_if            = Token.IF,
            Id_in            = Token.IN,
            Id_let           = Token.LET,
            Id_new           = Token.NEW,
            Id_null          = Token.NULL,
            Id_return        = Token.RETURN,
            Id_switch        = Token.SWITCH,
            Id_this          = Token.THIS,
            Id_true          = Token.TRUE,
            Id_typeof        = Token.TYPEOF,
            Id_var           = Token.VAR,
            Id_void          = Token.VOID,
            Id_while         = Token.WHILE,
            Id_with          = Token.WITH,
            Id_yield         = Token.YIELD,

            // the following are #ifdef RESERVE_JAVA_KEYWORDS in jsscan.c
            Id_abstract      = Token.RESERVED,
            Id_boolean       = Token.RESERVED,
            Id_byte          = Token.RESERVED,
            Id_catch         = Token.CATCH,
            Id_char          = Token.RESERVED,
            Id_class         = Token.RESERVED,
            Id_const         = Token.CONST,
            Id_debugger      = Token.DEBUGGER,
            Id_double        = Token.RESERVED,
            Id_enum          = Token.RESERVED,
            Id_extends       = Token.RESERVED,
            Id_final         = Token.RESERVED,
            Id_finally       = Token.FINALLY,
            Id_float         = Token.RESERVED,
            Id_goto          = Token.RESERVED,
            Id_implements    = Token.RESERVED,
            Id_import        = Token.IMPORT,
            Id_instanceof    = Token.INSTANCEOF,
            Id_int           = Token.RESERVED,
            Id_interface     = Token.RESERVED,
            Id_long          = Token.RESERVED,
            Id_native        = Token.RESERVED,
            Id_package       = Token.RESERVED,
            Id_private       = Token.RESERVED,
            Id_protected     = Token.RESERVED,
            Id_public        = Token.RESERVED,
            Id_short         = Token.RESERVED,
            Id_static        = Token.RESERVED,
            Id_super         = Token.RESERVED,
            Id_synchronized  = Token.RESERVED,
            Id_throw         = Token.THROW,
            Id_throws        = Token.RESERVED,
            Id_transient     = Token.RESERVED,
            Id_try           = Token.TRY,
            Id_volatile      = Token.RESERVED;

        int id;
        String s = name;
// #generated# Last update: 2007-04-18 13:53:30 PDT
        L0: { id = 0; String X = null; int c;
            L: switch (s.length()) {
            case 2: c=s.charAt(1);
                if (c=='f') { if (s.charAt(0)=='i') {id=Id_if; break L0;} }
                else if (c=='n') { if (s.charAt(0)=='i') {id=Id_in; break L0;} }
                else if (c=='o') { if (s.charAt(0)=='d') {id=Id_do; break L0;} }
                break L;
            case 3: switch (s.charAt(0)) {
                case 'f': if (s.charAt(2)=='r' && s.charAt(1)=='o') {id=Id_for; break L0;} break L;
                case 'i': if (s.charAt(2)=='t' && s.charAt(1)=='n') {id=Id_int; break L0;} break L;
                case 'l': if (s.charAt(2)=='t' && s.charAt(1)=='e') {id=Id_let; break L0;} break L;
                case 'n': if (s.charAt(2)=='w' && s.charAt(1)=='e') {id=Id_new; break L0;} break L;
                case 't': if (s.charAt(2)=='y' && s.charAt(1)=='r') {id=Id_try; break L0;} break L;
                case 'v': if (s.charAt(2)=='r' && s.charAt(1)=='a') {id=Id_var; break L0;} break L;
                } break L;
            case 4: switch (s.charAt(0)) {
                case 'b': X="byte";id=Id_byte; break L;
                case 'c': c=s.charAt(3);
                    if (c=='e') { if (s.charAt(2)=='s' && s.charAt(1)=='a') {id=Id_case; break L0;} }
                    else if (c=='r') { if (s.charAt(2)=='a' && s.charAt(1)=='h') {id=Id_char; break L0;} }
                    break L;
                case 'e': c=s.charAt(3);
                    if (c=='e') { if (s.charAt(2)=='s' && s.charAt(1)=='l') {id=Id_else; break L0;} }
                    else if (c=='m') { if (s.charAt(2)=='u' && s.charAt(1)=='n') {id=Id_enum; break L0;} }
                    break L;
                case 'g': X="goto";id=Id_goto; break L;
                case 'l': X="long";id=Id_long; break L;
                case 'n': X="null";id=Id_null; break L;
                case 't': c=s.charAt(3);
                    if (c=='e') { if (s.charAt(2)=='u' && s.charAt(1)=='r') {id=Id_true; break L0;} }
                    else if (c=='s') { if (s.charAt(2)=='i' && s.charAt(1)=='h') {id=Id_this; break L0;} }
                    break L;
                case 'v': X="void";id=Id_void; break L;
                case 'w': X="with";id=Id_with; break L;
                } break L;
            case 5: switch (s.charAt(2)) {
                case 'a': X="class";id=Id_class; break L;
                case 'e': c=s.charAt(0);
                    if (c=='b') { X="break";id=Id_break; }
                    else if (c=='y') { X="yield";id=Id_yield; }
                    break L;
                case 'i': X="while";id=Id_while; break L;
                case 'l': X="false";id=Id_false; break L;
                case 'n': c=s.charAt(0);
                    if (c=='c') { X="const";id=Id_const; }
                    else if (c=='f') { X="final";id=Id_final; }
                    break L;
                case 'o': c=s.charAt(0);
                    if (c=='f') { X="float";id=Id_float; }
                    else if (c=='s') { X="short";id=Id_short; }
                    break L;
                case 'p': X="super";id=Id_super; break L;
                case 'r': X="throw";id=Id_throw; break L;
                case 't': X="catch";id=Id_catch; break L;
                } break L;
            case 6: switch (s.charAt(1)) {
                case 'a': X="native";id=Id_native; break L;
                case 'e': c=s.charAt(0);
                    if (c=='d') { X="delete";id=Id_delete; }
                    else if (c=='r') { X="return";id=Id_return; }
                    break L;
                case 'h': X="throws";id=Id_throws; break L;
                case 'm': X="import";id=Id_import; break L;
                case 'o': X="double";id=Id_double; break L;
                case 't': X="static";id=Id_static; break L;
                case 'u': X="public";id=Id_public; break L;
                case 'w': X="switch";id=Id_switch; break L;
                case 'x': X="export";id=Id_export; break L;
                case 'y': X="typeof";id=Id_typeof; break L;
                } break L;
            case 7: switch (s.charAt(1)) {
                case 'a': X="package";id=Id_package; break L;
                case 'e': X="default";id=Id_default; break L;
                case 'i': X="finally";id=Id_finally; break L;
                case 'o': X="boolean";id=Id_boolean; break L;
                case 'r': X="private";id=Id_private; break L;
                case 'x': X="extends";id=Id_extends; break L;
                } break L;
            case 8: switch (s.charAt(0)) {
                case 'a': X="abstract";id=Id_abstract; break L;
                case 'c': X="continue";id=Id_continue; break L;
                case 'd': X="debugger";id=Id_debugger; break L;
                case 'f': X="function";id=Id_function; break L;
                case 'v': X="volatile";id=Id_volatile; break L;
                } break L;
            case 9: c=s.charAt(0);
                if (c=='i') { X="interface";id=Id_interface; }
                else if (c=='p') { X="protected";id=Id_protected; }
                else if (c=='t') { X="transient";id=Id_transient; }
                break L;
            case 10: c=s.charAt(1);
                if (c=='m') { X="implements";id=Id_implements; }
                else if (c=='n') { X="instanceof";id=Id_instanceof; }
                break L;
            case 12: X="synchronized";id=Id_synchronized; break L;
            }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
        }
// #/generated#
// #/string_id_map#
        if (id == 0) { return Token.EOF; }
        return id & 0xff;
    }

    final int getLineno() { return in.lineno; }

    final String getString() { return string; }

    final double getNumber() { return number; }

    final boolean eof() { return in.hitEOF; }

    final int getOffset() { return in.getOffset(); }

    final String getLine() { return in.getLine(); }

    
    final int getToken() throws IOException
    {
        int c;

    retry:
        for (;;) {
            // Eat whitespace, possibly sensitive to newlines.
            for (;;) {
                c = in.getChar();
                if (c == EOF_CHAR) {
                    return Token.EOF;
                } else if (c == '\n') {
                    in.setDirtyLine(false);
                    return Token.EOL;
                } else if (!isJSSpace(c)) {
                    if (c != '-') {
                        in.setDirtyLine(true);
                    }
                    break;
                }
            }

            if (c == '@') return Token.XMLATTR;

            // identifier/keyword/instanceof?
            // watch out for starting with a <backslash>
            boolean identifierStart;
            boolean isUnicodeEscapeStart = false;
            if (c == '\\') {
                c = in.getChar();
                if (c == 'u') {
                    identifierStart = true;
                    isUnicodeEscapeStart = true;
                    stringBufferTop = 0;
                } else {
                    identifierStart = false;
                    in.ungetChar(c);
                    c = '\\';
                }
            } else {
                identifierStart = Character.isJavaIdentifierStart((char)c);
                if (identifierStart) {
                    stringBufferTop = 0;
                    addToString(c);
                }
            }

            if (identifierStart) {
                boolean containsEscape = isUnicodeEscapeStart;
                for (;;) {
                    if (isUnicodeEscapeStart) {
                        // strictly speaking we should probably push-back
                        // all the bad characters if the <backslash>uXXXX
                        // sequence is malformed. But since there isn't a
                        // correct context(is there?) for a bad Unicode
                        // escape sequence in an identifier, we can report
                        // an error here.
                        int escapeVal = 0;
                        for (int i = 0; i != 4; ++i) {
                            c = in.getChar();
                            escapeVal = Kit.xDigitToInt(c, escapeVal);
                            // Next check takes care about c < 0 and bad escape
                            if (escapeVal < 0) { break; }
                        }
                        if (escapeVal < 0) {
                            parser.addError("msg.invalid.escape");
                            return Token.ERROR;
                        }
                        addToString(escapeVal);
                        isUnicodeEscapeStart = false;
                    } else {
                        c = in.getChar();
                        if (c == '\\') {
                            c = in.getChar();
                            if (c == 'u') {
                                isUnicodeEscapeStart = true;
                                containsEscape = true;
                            } else {
                                parser.addError("msg.illegal.character");
                                return Token.ERROR;
                            }
                        } else {
                            if (c == EOF_CHAR
                                || !Character.isJavaIdentifierPart((char)c))
                            {
                                break;
                            }
                            addToString(c);
                        }
                    }
                }
                in.ungetChar(c);

                String str = getStringFromBuffer();
                if (!containsEscape) {
                    // OPT we shouldn't have to make a string (object!) to
                    // check if it's a keyword.

                    // Return the corresponding token if it's a keyword
                    int result = stringToKeyword(str);
                    if (result != Token.EOF) {
                        if ((result == Token.LET || result == Token.YIELD) && 
                            parser.compilerEnv.getLanguageVersion() 
                               < Context.VERSION_1_7)
                        {
                            // LET and YIELD are tokens only in 1.7 and later
                            string = result == Token.LET ? "let" : "yield";
                            result = Token.NAME;
                        }
                        if (result != Token.RESERVED) {
                            return result;
                        } else if (!parser.compilerEnv.
                                        isReservedKeywordAsIdentifier())
                        {
                            return result;
                        } else {
                            // If implementation permits to use future reserved
                            // keywords in violation with the EcmaScript,
                            // treat it as name but issue warning
                            parser.addWarning("msg.reserved.keyword", str);
                        }
                    }
                }
                this.string = (String)allStrings.intern(str);
                return Token.NAME;
            }

            // is it a number?
            if (isDigit(c) || (c == '.' && isDigit(in.peekChar()))) {

                stringBufferTop = 0;
                int base = 10;

                if (c == '0') {
                    c = in.getChar();
                    if (c == 'x' || c == 'X') {
                        base = 16;
                        c = in.getChar();
                    } else if (isDigit(c)) {
                        base = 8;
                    } else {
                        addToString('0');
                    }
                }

                if (base == 16) {
                    while (0 <= Kit.xDigitToInt(c, 0)) {
                        addToString(c);
                        c = in.getChar();
                    }
                } else {
                    while ('0' <= c && c <= '9') {
                        /*
                         * We permit 08 and 09 as decimal numbers, which
                         * makes our behavior a superset of the ECMA
                         * numeric grammar.  We might not always be so
                         * permissive, so we warn about it.
                         */
                        if (base == 8 && c >= '8') {
                            parser.addWarning("msg.bad.octal.literal",
                                              c == '8' ? "8" : "9");
                            base = 10;
                        }
                        addToString(c);
                        c = in.getChar();
                    }
                }

                boolean isInteger = true;

                if (base == 10 && (c == '.' || c == 'e' || c == 'E')) {
                    isInteger = false;
                    if (c == '.') {
                        do {
                            addToString(c);
                            c = in.getChar();
                        } while (isDigit(c));
                    }
                    if (c == 'e' || c == 'E') {
                        addToString(c);
                        c = in.getChar();
                        if (c == '+' || c == '-') {
                            addToString(c);
                            c = in.getChar();
                        }
                        if (!isDigit(c)) {
                            parser.addError("msg.missing.exponent");
                            return Token.ERROR;
                        }
                        do {
                            addToString(c);
                            c = in.getChar();
                        } while (isDigit(c));
                    }
                }
                in.ungetChar(c);
                String numString = getStringFromBuffer();

                double dval;
                if (base == 10 && !isInteger) {
                    try {
                        // Use Java conversion to number from string...
                        dval = Double.valueOf(numString).doubleValue();
                    }
                    catch (NumberFormatException ex) {
                        parser.addError("msg.caught.nfe");
                        return Token.ERROR;
                    }
                } else {
                    dval = ScriptRuntime.stringToNumber(numString, 0, base);
                }

                this.number = dval;
                return Token.NUMBER;
            }

            // is it a string?
            if (c == '"' || c == '\'') {
                // We attempt to accumulate a string the fast way, by
                // building it directly out of the reader.  But if there
                // are any escaped characters in the string, we revert to
                // building it out of a StringBuffer.

                int quoteChar = c;
                stringBufferTop = 0;

                c = in.getChar();
            strLoop: while (c != quoteChar) {
                    if (c == '\n' || c == EOF_CHAR) {
                        in.ungetChar(c);
                        parser.addError("msg.unterminated.string.lit");
                        return Token.ERROR;
                    }

                    if (c == '\\') {
                        // We've hit an escaped character
                        int escapeVal;

                        c = in.getChar();
                        switch (c) {
                        case 'b': c = '\b'; break;
                        case 'f': c = '\f'; break;
                        case 'n': c = '\n'; break;
                        case 'r': c = '\r'; break;
                        case 't': c = '\t'; break;

                        // \v a late addition to the ECMA spec,
                        // it is not in Java, so use 0xb
                        case 'v': c = 0xb; break;

                        case 'u':
                            // Get 4 hex digits; if the u escape is not
                            // followed by 4 hex digits, use 'u' + the
                            // literal character sequence that follows.
                            int escapeStart = stringBufferTop;
                            addToString('u');
                            escapeVal = 0;
                            for (int i = 0; i != 4; ++i) {
                                c = in.getChar();
                                escapeVal = Kit.xDigitToInt(c, escapeVal);
                                if (escapeVal < 0) {
                                    continue strLoop;
                                }
                                addToString(c);
                            }
                            // prepare for replace of stored 'u' sequence
                            // by escape value
                            stringBufferTop = escapeStart;
                            c = escapeVal;
                            break;
                        case 'x':
                            // Get 2 hex digits, defaulting to 'x'+literal
                            // sequence, as above.
                            c = in.getChar();
                            escapeVal = Kit.xDigitToInt(c, 0);
                            if (escapeVal < 0) {
                                addToString('x');
                                continue strLoop;
                            } else {
                                int c1 = c;
                                c = in.getChar();
                                escapeVal = Kit.xDigitToInt(c, escapeVal);
                                if (escapeVal < 0) {
                                    addToString('x');
                                    addToString(c1);
                                    continue strLoop;
                                } else {
                                    // got 2 hex digits
                                    c = escapeVal;
                                }
                            }
                            break;

                        case '\n':
                            // Remove line terminator after escape to follow
                            // SpiderMonkey and C/C++
                            c = in.getChar();
                            continue strLoop;

                        default:
                            if ('0' <= c && c < '8') {
                                int val = c - '0';
                                c = in.getChar();
                                if ('0' <= c && c < '8') {
                                    val = 8 * val + c - '0';
                                    c = in.getChar();
                                    if ('0' <= c && c < '8' && val <= 037) {
                                        // c is 3rd char of octal sequence only
                                        // if the resulting val <= 0377
                                        val = 8 * val + c - '0';
                                        c = in.getChar();
                                    }
                                }
                                in.ungetChar(c);
                                c = val;
                            }
                        }
                    }
                    addToString(c);
                    c = in.getChar();
                }

                String str = getStringFromBuffer();
                this.string = (String)allStrings.intern(str);
                return Token.STRING;
            }

            switch (c) {
            case ';': return Token.SEMI;
            case '[': return Token.LB;
            case ']': return Token.RB;
            case '{': return Token.LC;
            case '}': return Token.RC;
            case '(': return Token.LP;
            case ')': return Token.RP;
            case ',': return Token.COMMA;
            case '?': return Token.HOOK;
            case ':':
                if (in.matchChar(':')) {
                    return Token.COLONCOLON;
                } else {
                    return Token.COLON;
                }
            case '.':
                if (in.matchChar('.')) {
                    return Token.DOTDOT;
                } else if (in.matchChar('(')) {
                    return Token.DOTQUERY;
                } else {
                    return Token.DOT;
                }

            case '|':
                if (in.matchChar('|')) {
                    return Token.OR;
                } else if (in.matchChar('=')) {
                    return Token.ASSIGN_BITOR;
                } else {
                    return Token.BITOR;
                }

            case '^':
                if (in.matchChar('=')) {
                    return Token.ASSIGN_BITXOR;
                } else {
                    return Token.BITXOR;
                }

            case '&':
                if (in.matchChar('&')) {
                    return Token.AND;
                } else if (in.matchChar('=')) {
                    return Token.ASSIGN_BITAND;
                } else {
                    return Token.BITAND;
                }

            case '=':
                if (in.matchChar('=')) {
                    if (in.matchChar('='))
                        return Token.SHEQ;
                    else
                        return Token.EQ;
                } else {
                    return Token.ASSIGN;
                }

            case '!':
                if (in.matchChar('=')) {
                    if (in.matchChar('='))
                        return Token.SHNE;
                    else
                        return Token.NE;
                } else {
                    return Token.NOT;
                }

            case '<':
                /* NB:treat HTML begin-comment as comment-till-eol */
                if (in.matchChar('!')) {
                    if (in.matchChar('-')) {
                        if (in.matchChar('-')) {
                            in.skipLine();
                            continue retry;
                        }
                        in.ungetCharIgnoreLineEnd('-');
                    }
                    in.ungetCharIgnoreLineEnd('!');
                }
                if (in.matchChar('<')) {
                    if (in.matchChar('=')) {
                        return Token.ASSIGN_LSH;
                    } else {
                        return Token.LSH;
                    }
                } else {
                    if (in.matchChar('=')) {
                        return Token.LE;
                    } else {
                        return Token.LT;
                    }
                }

            case '>':
                if (in.matchChar('>')) {
                    if (in.matchChar('>')) {
                        if (in.matchChar('=')) {
                            return Token.ASSIGN_URSH;
                        } else {
                            return Token.URSH;
                        }
                    } else {
                        if (in.matchChar('=')) {
                            return Token.ASSIGN_RSH;
                        } else {
                            return Token.RSH;
                        }
                    }
                } else {
                    if (in.matchChar('=')) {
                        return Token.GE;
                    } else {
                        return Token.GT;
                    }
                }

            case '*':
                if (in.matchChar('=')) {
                    return Token.ASSIGN_MUL;
                } else {
                    return Token.MUL;
                }

            case '/':
                // is it a // comment?
                if (in.matchChar('/')) {
                    in.skipLine();
                    continue retry;
                }
                if (in.matchChar('*')) {
                    boolean lookForSlash = false;
                    for (;;) {
                        c = in.getChar();
                        if (c == EOF_CHAR) {
                            parser.addError("msg.unterminated.comment");
                            return Token.ERROR;
                        } else if (c == '*') {
                            lookForSlash = true;
                        } else if (c == '/') {
                            if (lookForSlash) {
                                continue retry;
                            }
                        } else {
                            lookForSlash = false;
                        }
                    }
                }

                if (in.matchChar('=')) {
                    return Token.ASSIGN_DIV;
                } else {
                    return Token.DIV;
                }

            case '%':
                if (in.matchChar('=')) {
                    return Token.ASSIGN_MOD;
                } else {
                    return Token.MOD;
                }

            case '~':
                return Token.BITNOT;

            case '+':
                if (in.matchChar('=')) {
                    return Token.ASSIGN_ADD;
                } else if (in.matchChar('+')) {
                    return Token.INC;
                } else {
                    return Token.ADD;
                }

            case '-':
                if (in.matchChar('=')) {
                    c = Token.ASSIGN_SUB;
                } else if (in.matchChar('-')) {
                    if (!in.getDirtyLine()) {
                        // treat HTML end-comment after possible whitespace
                        // after line start as comment-utill-eol
                        if (in.matchChar('>')) {
                            in.skipLine();
                            continue retry;
                        }
                    }
                    c = Token.DEC;
                } else {
                    c = Token.SUB;
                }
                in.setDirtyLine(true);
                return c;

            default:
                parser.addError("msg.illegal.character");
                return Token.ERROR;
            }
        }
    }

    private static boolean isAlpha(int c)
    {
        // Use 'Z' < 'a'
        if (c <= 'Z') {
            return 'A' <= c;
        } else {
            return 'a' <= c && c <= 'z';
        }
    }

    static boolean isDigit(int c)
    {
        return '0' <= c && c <= '9';
    }

    /* As defined in ECMA.  jsscan.c uses C isspace() (which allows
     * \v, I think.)  note that code in getChar() implicitly accepts
     * '\r' == \u000D as well.
     */
    static boolean isJSSpace(int c)
    {
        if (c <= 127) {
            return c == 0x20 || c == 0x9 || c == 0xC || c == 0xB;
        } else {
            return c == 0xA0
                || Character.getType((char)c) == Character.SPACE_SEPARATOR;
        }
    }

    private static boolean isJSFormatChar(int c)
    {
        return c > 127 && Character.getType((char)c) == Character.FORMAT;
    }

    /**
     * Parser calls the method when it gets / or /= in literal context.
     */
    void readRegExp(int startToken)
        throws IOException
    {
        stringBufferTop = 0;
        if (startToken == Token.ASSIGN_DIV) {
            // Miss-scanned /=
            addToString('=');
        } else {
            if (startToken != Token.DIV) Kit.codeBug();
        }

        boolean inCharSet = false; // true if inside a '['..']' pair
        int c;
        while ((c = in.getChar()) != '/' || inCharSet) {
            if (c == '\n' || c == EOF_CHAR) {
                in.ungetChar(c);
                throw parser.reportError("msg.unterminated.re.lit");
            }
            if (c == '\\') {
                addToString(c);
                c = in.getChar();
            } else if (c == '[') {
                inCharSet = true;
            } else if (c == ']') {
                inCharSet = false;
            }
            addToString(c);
        }
        int reEnd = stringBufferTop;

        while (true) {
            if (in.matchChar('g'))
                addToString('g');
            else if (in.matchChar('i'))
                addToString('i');
            else if (in.matchChar('m'))
                addToString('m');
            else
                break;
        }

        if (isAlpha(in.peekChar())) {
            throw parser.reportError("msg.invalid.re.flag");
        }

        this.string = new String(stringBuffer, 0, reEnd);
        this.regExpFlags = new String(stringBuffer, reEnd,
                                      stringBufferTop - reEnd);
    }

    boolean isXMLAttribute() 
    {
        return xmlTokenizer.isXMLAttribute();
    }

    int getFirstXMLToken() throws IOException
    {
        return xmlTokenizer.getFirstXMLToken();
    }

    int getNextXMLToken() throws IOException
    {
        return xmlTokenizer.getNextXMLToken();
    }

    private String getStringFromBuffer()
    {
        return new String(stringBuffer, 0, stringBufferTop);
    }

    private void addToString(int c)
    {
        int N = stringBufferTop;
        if (N == stringBuffer.length) {
            char[] tmp = new char[stringBuffer.length * 2];
            System.arraycopy(stringBuffer, 0, tmp, 0, N);
            stringBuffer = tmp;
        }
        stringBuffer[N] = (char)c;
        stringBufferTop = N + 1;
    }
    
    String regExpFlags;

    // Set this to an initial non-null value so that the Parser has
    // something to retrieve even if an error has occurred and no
    // string is found.  Fosters one class of error, but saves lots of
    // code.
    String string = "";
    double number;

    private char[] stringBuffer = new char[128];
    private int stringBufferTop;
    private ObjToIntMap allStrings = new ObjToIntMap(50);

    private TokenCharStream in;

    private XMLTokenizer xmlTokenizer;
    
    private Parser parser;
}
