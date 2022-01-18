// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class LocalClassLoader extends URLClassLoader
{
    public LocalClassLoader(final URL[] urls, final ClassLoader loader) {
        super(urls, loader);
    }
    
    protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }
        final Class<?> c = super.findLoadedClass(name);
        if (c != null) {
            return c;
        }
        final String resource = name.replace('.', '/') + ".class";
        try {
            final URL url = super.getResource(resource);
            if (url == null) {
                throw new ClassNotFoundException(name);
            }
            final File f = new File("build/bin/" + resource);
            System.out.println("FileLen:" + f.length() + "  " + f.getName());
            final InputStream is = url.openStream();
            try {
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                final byte[] b = new byte[2048];
                int count;
                while ((count = is.read(b, 0, 2048)) != -1) {
                    os.write(b, 0, count);
                }
                final byte[] bytes = os.toByteArray();
                System.err.println("bytes: " + bytes.length + " " + resource);
                return this.defineClass(name, bytes, 0, bytes.length);
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
        catch (SecurityException e2) {
            return super.loadClass(name, resolve);
        }
        catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
    }
}
