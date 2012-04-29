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
 * Portions created by the Initial Developer are Copyright (C) 1997-2000
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
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

// API class

package org.mozilla.javascript;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Iterator;


public class VMBridge
{

    static final VMBridge instance = makeInstance();

    private static VMBridge makeInstance()
    {
        return new VMBridge();
    }

    //private ThreadLocal<Object[]> contextLocal = new ThreadLocal<Object[]>();
    private Object[] contextLocal;
    
    /**
     * Return a helper object to optimize {@link Context} access.
     * <p>
     * The runtime will pass the resulting helper object to the subsequent
     * calls to {@link #getContext(Object contextHelper)} and
     * {@link #setContext(Object contextHelper, Context cx)} methods.
     * In this way the implementation can use the helper to cache
     * information about current thread to make {@link Context} access faster.
     */
    protected Object getThreadContextHelper()
    {
        // To make subsequent batch calls to getContext/setContext faster
        // associate permanently one element array with contextLocal
        // so getContext/setContext would need just to read/write the first
        // array element.
        // Note that it is necessary to use Object[], not Context[] to allow
        // garbage collection of Rhino classes. For details see comments
        // by Attila Szegedi in
        // https://bugzilla.mozilla.org/show_bug.cgi?id=281067#c5

        Object[] storage = contextLocal;
        if (storage == null) {
            storage = new Object[1];
            contextLocal = storage;
        }
        return storage;
    }

    /**
     * Get {@link Context} instance associated with the current thread
     * or null if none.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     */
    protected Context getContext(Object contextHelper)
    {
        Object[] storage = (Object[])contextHelper;
        return (Context)storage[0];
    }

    /**
     * Associate {@link Context} instance with the current thread or remove
     * the current association if <tt>cx</tt> is null.
     *
     * @param contextHelper The result of {@link #getThreadContextHelper()}
     *                      called from the current thread.
     */
    protected void setContext(Object contextHelper, Context cx)
    {
        Object[] storage = (Object[])contextHelper;
        storage[0] = cx;
    }



    /**
     * Return the ClassLoader instance associated with the current thread.
     */
    protected ClassLoader getCurrentThreadClassLoader()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * In many JVMSs, public methods in private
     * classes are not accessible by default (Sun Bug #4071593).
     * VMBridge instance should try to workaround that via, for example,
     * calling method.setAccessible(true) when it is available.
     * The implementation is responsible to catch all possible exceptions
     * like SecurityException if the workaround is not available.
     *
     * @return true if it was possible to make method accessible
     *         or false otherwise.
     */
    protected boolean tryToMakeAccessible(Object accessibleObject)
    {
        if (!(accessibleObject instanceof AccessibleObject)) {
            return false;
        }
        AccessibleObject accessible = (AccessibleObject)accessibleObject;
        if (accessible.isAccessible()) {
            return true;
        }
        try {
            accessible.setAccessible(true);
        } catch (Exception ex) { }

        return accessible.isAccessible();
    }


    /**
     * Create helper object to create later proxies implementing the specified
     * interfaces later. Under JDK 1.3 the implementation can look like:
     * <pre>
     * return java.lang.reflect.Proxy.getProxyClass(..., interfaces).
     *     getConstructor(new Class[] {
     *         java.lang.reflect.InvocationHandler.class });
     * </pre>
     *
     * @param interfaces Array with one or more interface class objects.
     */
    protected Object getInterfaceProxyHelper(ContextFactory cf,
                                             Class<?>[] interfaces)
    {
        throw Context.reportRuntimeError(
            "VMBridge.getInterfaceProxyHelper is not supported");
    }
//  From Java 1.5 version
//================
//  @Override
//  protected Object getInterfaceProxyHelper(ContextFactory cf,
//                                           Class<?>[] interfaces)
//  {
//      // XXX: How to handle interfaces array withclasses from different
//      // class loaders? Using cf.getApplicationClassLoader() ?
//      ClassLoader loader = interfaces[0].getClassLoader();
//      Class<?> cl = Proxy.getProxyClass(loader, interfaces);
//      Constructor<?> c;
//      try {
//          c = cl.getConstructor(new Class[] { InvocationHandler.class });
//      } catch (NoSuchMethodException ex) {
//          // Should not happen
//          throw Kit.initCause(new IllegalStateException(), ex);
//      }
//      return c;
//  }

    /**
     * Create proxy object for {@link InterfaceAdapter}. The proxy should call
     * {@link InterfaceAdapter#invoke(ContextFactory cf,
     *                                Object target,
     *                                Scriptable topScope,
     *                                Method method,
     *                                Object[] args)}
     * as implementation of interface methods associated with
     * <tt>proxyHelper</tt>.
     *
     * @param proxyHelper The result of the previous call to
     *        {@link #getInterfaceProxyHelper(ContextFactory, Class[])}.
     */
    protected Object newInterfaceProxy(Object proxyHelper,
                                       ContextFactory cf,
                                       InterfaceAdapter adapter,
                                       Object target,
                                       Scriptable topScope)
    {
        throw Context.reportRuntimeError(
            "VMBridge.newInterfaceProxy is not supported");
    }
//  From Java 1.5 version
//================
//  @Override
//  protected Object newInterfaceProxy(Object proxyHelper,
//                                     final ContextFactory cf,
//                                     final InterfaceAdapter adapter,
//                                     final Object target,
//                                     final Scriptable topScope)
//  {
//      Constructor<?> c = (Constructor<?>)proxyHelper;
//
//      InvocationHandler handler = new InvocationHandler() {
//              public Object invoke(Object proxy,
//                                   Method method,
//                                   Object[] args)
//              {
//                  return adapter.invoke(cf, target, topScope, method, args);
//              }
//          };
//      Object proxy;
//      try {
//          proxy = c.newInstance(new Object[] { handler });
//      } catch (InvocationTargetException ex) {
//          throw Context.throwAsScriptRuntimeEx(ex);
//      } catch (IllegalAccessException ex) {
//          // Shouls not happen
//          throw Kit.initCause(new IllegalStateException(), ex);
//      } catch (InstantiationException ex) {
//          // Shouls not happen
//          throw Kit.initCause(new IllegalStateException(), ex);
//      }
//      return proxy;
//  }


    /**
     * If "obj" is a java.util.Iterator or a java.lang.Iterable, return a
     * wrapping as a JavaScript Iterator. Otherwise, return null.
     * This method is in VMBridge since Iterable is a JDK 1.5 addition.
     */
    public Iterator<?> getJavaIterator(Context cx, Scriptable scope, Object obj) {
        if (obj instanceof Wrapper) {
            Object unwrapped = ((Wrapper) obj).unwrap();
            Iterator<?> iterator = null;
            if (unwrapped instanceof Iterator)
                iterator = (Iterator<?>) unwrapped;
            if (unwrapped instanceof Iterable)
                iterator = ((Iterable<?>)unwrapped).iterator();
            return iterator;
        }
        return null;
    }
}
