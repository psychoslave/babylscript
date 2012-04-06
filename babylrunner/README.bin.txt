The code for Babylscript is based on Mozilla's Rhino JavaScript interpreter, 
version 1.7R2. Since the Babylscript implementation is merely a modification
of Rhino's source code, the Babylscript code has the same license terms (a mix 
of MPL1.1 and GPL2).

This package a jar file with the Babylscript engine in it wrapped in simple
harness that lets you run Babylscript/Rhino/JavaScript code from the 
command-line. You need Java (version 1.5 or later) to run this jar file.


QUICK-START GUIDE
=================

A sample HelloWorld Babylscript program is provided called "sample.js". 
Run this program using

   java -jar babylscriptrun.jar sample.js


ADDITIONAL INFO
===============

You can also run multiple files by listing each one in turn. This is useful
for including libraries.

   java -jar babylscriptrun.jar lib.js sample.js
   
The programs run using this Babylscript harness must be encoded in UTF-8 
(e.g. in Windows Notepad, when you choose the "Save As..." option, there is an
option that lets you change the encoding). 