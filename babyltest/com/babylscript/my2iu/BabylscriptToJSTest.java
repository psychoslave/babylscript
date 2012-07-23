package com.babylscript.my2iu;


import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class BabylscriptToJSTest
{
   Scriptable scope;

   @Before
   public void setUp() throws Exception
   {
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   try {
		   scope = cx.initStandardObjects();
	   } finally {
		   Context.exit();
	   }
   }

   @After
   public void tearDown() throws Exception
   {
   }

   String compileToJS(String code) 
   {
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   String result = "";
	   try {
		   result = cx.compileStringToJS(code, null, 0, true);
	   } finally {
		   Context.exit();
	   }
	   return result;
   }
   String compileToJSNoHeaders(String code) 
   {
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   String result = "";
	   try {
		   result = cx.compileStringToJS(code, null, 0, false);
	   } finally {
		   Context.exit();
	   }
	   return result;
   }
   String evalStringToString(String code)
   {
	   String js = compileToJS(code);
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   try {
		   return Context.toString(cx.evaluateString(scope, js, "<test>", 0, null));
	   } finally {
		   Context.exit();
	   }
   }
   
   @Test
   public void basic1() 
   {
	   assertEquals("babylwrap(32);\n", compileToJSNoHeaders("32"));
   }

   @Test
   public void basic2() 
   {
	   assertEquals("32", evalStringToString("32"));
   }

   @Test
   public void basic3() 
   {
	   assertEquals("babylwrap('hello');\n", compileToJSNoHeaders("'hello'"));
   }
   
   @Test
   public void simpleOperators1() 
   {
	   assertEquals("24", evalStringToString("16+8"));
   }

   @Test
   public void simpleOperators2() 
   {
	   assertEquals("5", evalStringToString("15%10"));
   }

   @Test
   public void simpleOperators3() 
   {
	   assertEquals("15", evalStringToString("'1' + 5"));
   }

   @Test
   public void simpleOperators4() 
   {
	   assertEquals("-5", evalStringToString("- 5"));
   }

   @Test
   public void simpleOperators5() 
   {
	   assertEquals("-6", evalStringToString("~5"));
   }

   @Test
   public void variables1() 
   {
	   assertEquals("babylroot[babyllookup(babylroot,'en','a')]", compileToJSNoHeaders("a"));
   }

   @Test
   public void variables2() 
   {
	   assertEquals("2", evalStringToString("a = 2"));
   }

   @Test
   public void variables3() 
   {
	   assertEquals("5", evalStringToString("a = 2; a += 3; a;"));
   }

   @Test
   public void statements1() 
   {
	   assertEquals("5", evalStringToString("a = 2\n b=3\n a+b"));
   }

   // 
   // Some corner cases
   //
   
   @Test
   public void lookupNonConstantWithIncrement1() 
   {
	   assertEquals("6", evalStringToString("var str = ''; function a() {str += 'a'; return str;} var b {a:5, aa:10}; b[a()]++; b.a;"));
// SOLUTION INVOLVES SOMETHING LIKE THIS:	   
//   var tmp0;
//
//   d = {
//   a : 'a'
//   };
//   function readA(k)
//   {
//    return k.a; 
//   }
//   var a = (tmp0=d)[readA(tmp0)];
//   alert(a);
   }
   
   @Test
   public void lookupNonConstantWithIncrement2() 
   {
	   assertEquals("6", evalStringToString("var str = ''; function a() {str += 'a'; return str;} var next = false; function c() { next = !next; return next ? b: {a:20,aa:30}} var b {a:5, aa:10};  c()[a()]++; b.a;"));
   }

   @Test
   public void assignAddWithTypeChange() 
   {
	   assertEquals("3", evalStringToString("var str = 2; str += 'hi';  ---fr--- str.longueur"));
   }

   @Test
   public void scoping1() 
   {
	   assertEquals("6", evalStringToString("var c = 1; function a() {  var c = 5; function b() {c++; return c;} return b; } var result = a()();"));
   }

   @Test
   public void scoping2() 
   {
	   assertEquals("3", evalStringToString("var c = 1; function a() { if (!c) { var c = 1000; return 3;} return 5; } var result = a();"));
   }

}
