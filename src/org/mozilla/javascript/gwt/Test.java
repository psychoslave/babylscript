package org.mozilla.javascript.gwt;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Evaluator;
import org.mozilla.javascript.InterpretedFunction;
import org.mozilla.javascript.Interpreter;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptOrFnNode;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.debug.DebuggableScript;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class Test implements EntryPoint 
{

    public void onModuleLoad() 
    {
        Scriptable scope;
        Context cx = Context.enter();
        cx.setOptimizationLevel(-1);
        
        String result = "";
        try {
            scope = cx.initStandardObjects();
//            result = Context.toString(cx.evaluateString(scope, "22", "<test>", 0, null));

            CompilerEnvirons compilerEnv = new CompilerEnvirons(); 
            Parser p = new Parser(compilerEnv, null);
//            if (returnFunction) {
//                p.calledByCompileFunction = true;
//            }
//             tree;
            String sourceString = "22 + 23; ";
            int lineno = 0;
            String sourceName = "hey";
            if (sourceString != null) {
                ScriptOrFnNode tree = p.parse(sourceString, sourceName, lineno);
                Interpreter compiler = new Interpreter();

                String encodedSource = p.getEncodedSource();

                Object bytecode = compiler.compile(compilerEnv,
                                                   tree, encodedSource,
                                                   false);

                InterpretedFunction resultScript;
                resultScript = (InterpretedFunction) compiler.createScriptObject(bytecode, null);
                
                result = "" + resultScript.exec(cx, scope);

            } 


            
//public final Object evaluateString(Scriptable scope, String source,
//                    String sourceName, int lineno,
//                    Object securityDomain)
//{
//Script script = compileString(source, sourceName, lineno,
//                   securityDomain);
//if (script != null) {
//return script.exec(this, scope);
//} else {
//return null;
//}
//}
            
        } finally {
//            Context.exit();
        }

        
        Element el = Document.get().getElementById("fillin");
        el.setInnerHTML(result);
    }
}
