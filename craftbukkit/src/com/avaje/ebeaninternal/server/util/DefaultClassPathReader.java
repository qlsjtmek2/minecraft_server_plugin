// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

import java.lang.reflect.Method;
import java.io.File;
import java.net.URLClassLoader;
import java.util.logging.Logger;

public class DefaultClassPathReader implements ClassPathReader
{
    private static final Logger logger;
    
    public Object[] readPath(final ClassLoader classLoader) {
        if (classLoader instanceof URLClassLoader) {
            final URLClassLoader ucl = (URLClassLoader)classLoader;
            return ucl.getURLs();
        }
        try {
            final Method method = classLoader.getClass().getMethod("getClassPath", (Class<?>[])new Class[0]);
            if (method != null) {
                DefaultClassPathReader.logger.info("Using getClassPath() method on classLoader[" + classLoader.getClass() + "]");
                final String s = method.invoke(classLoader, new Object[0]).toString();
                return s.split(File.pathSeparator);
            }
        }
        catch (NoSuchMethodException e2) {}
        catch (Exception e) {
            throw new RuntimeException("Unexpected Error trying to read classpath from classloader", e);
        }
        try {
            final Method method = classLoader.getClass().getMethod("getClasspath", (Class<?>[])new Class[0]);
            if (method != null) {
                DefaultClassPathReader.logger.info("Using getClasspath() method on classLoader[" + classLoader.getClass() + "]");
                final String s = method.invoke(classLoader, new Object[0]).toString();
                return s.split(File.pathSeparator);
            }
        }
        catch (NoSuchMethodException e2) {}
        catch (Exception e) {
            throw new RuntimeException("Unexpected Error trying to read classpath from classloader", e);
        }
        final String imsg = "Unsure how to read classpath from classLoader [" + classLoader.getClass() + "]";
        DefaultClassPathReader.logger.info(imsg);
        final String msg = "Using java.class.path system property to search for entity beans";
        DefaultClassPathReader.logger.warning(msg);
        return System.getProperty("java.class.path", "").split(File.pathSeparator);
    }
    
    static {
        logger = Logger.getLogger(DefaultClassPathReader.class.getName());
    }
}
