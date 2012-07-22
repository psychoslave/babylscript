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
	   String result = cx.compileStringToJS(code, null, 0, true);
	   return result;
   }
   String compileToJSNoHeaders(String code) 
   {
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   String result = cx.compileStringToJS(code, null, 0, false);
	   return result;
   }
   String evalStringToString(String code)
   {
	   String js = compileToJS(code);
	   Context cx = Context.enter();
	   cx.setLocale(Locale.ENGLISH);
	   cx.setOptimizationLevel(-1);
	   try {
		   return Context.toString(cx.evaluateString(scope, code, "<test>", 0, null));
	   } finally {
		   Context.exit();
	   }
   }
   
   @Test
   public void basic1() 
   {
	   assertEquals("32", compileToJSNoHeaders("32"));
   }
   
//   @Test
//   public void basic2() 
//   {
//      assertEquals("32", evalStringToString("var a = { b: '3', c: '2'}; a.b + a.c;"));
//   }
   
}
