// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.io.InputStream;
import java.io.IOException;
import java.net.URLClassLoader;
import java.net.URL;

public class ClassPathClassBytesReader implements ClassBytesReader
{
    private final URL[] urls;
    
    public ClassPathClassBytesReader(final URL[] urls) {
        this.urls = ((urls == null) ? new URL[0] : urls);
    }
    
    public byte[] getClassBytes(final String className, final ClassLoader classLoader) {
        final URLClassLoader cl = new URLClassLoader(this.urls, classLoader);
        final String resource = className.replace('.', '/') + ".class";
        InputStream is = null;
        try {
            final URL url = cl.getResource(resource);
            if (url == null) {
                throw new RuntimeException("Class Resource not found for " + resource);
            }
            is = url.openStream();
            final byte[] classBytes = InputStreamTransform.readBytes(is);
            return classBytes;
        }
        catch (IOException e) {
            throw new RuntimeException("IOException reading bytes for " + className, e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e2) {
                    throw new RuntimeException("Error closing InputStream for " + className, e2);
                }
            }
        }
    }
}
