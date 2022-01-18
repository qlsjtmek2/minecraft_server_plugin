// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.logging.Logger;

public class ClassUtil
{
    private static final Logger logger;
    private static boolean preferContext;
    
    public static Class<?> forName(final String name) throws ClassNotFoundException {
        return forName(name, null);
    }
    
    public static Class<?> forName(final String name, Class<?> caller) throws ClassNotFoundException {
        if (caller == null) {
            caller = ClassUtil.class;
        }
        final ClassLoadContext ctx = ClassLoadContext.of(caller, ClassUtil.preferContext);
        return ctx.forName(name);
    }
    
    public static ClassLoader getClassLoader(Class<?> caller, final boolean preferContext) {
        if (caller == null) {
            caller = ClassUtil.class;
        }
        final ClassLoadContext ctx = ClassLoadContext.of(caller, preferContext);
        final ClassLoader classLoader = ctx.getDefault(preferContext);
        if (ctx.isAmbiguous()) {
            ClassUtil.logger.info("Ambigous ClassLoader (Context vs Caller) chosen " + classLoader);
        }
        return classLoader;
    }
    
    public static boolean isPresent(final String className) {
        return isPresent(className, null);
    }
    
    public static boolean isPresent(final String className, final Class<?> caller) {
        try {
            forName(className, caller);
            return true;
        }
        catch (Throwable ex) {
            return false;
        }
    }
    
    public static Object newInstance(final String className) {
        return newInstance(className, null);
    }
    
    public static Object newInstance(final String className, final Class<?> caller) {
        try {
            final Class<?> cls = forName(className, caller);
            return cls.newInstance();
        }
        catch (Exception e) {
            final String msg = "Error constructing " + className;
            throw new IllegalArgumentException(msg, e);
        }
    }
    
    static {
        logger = Logger.getLogger(ClassUtil.class.getName());
        ClassUtil.preferContext = true;
    }
}
