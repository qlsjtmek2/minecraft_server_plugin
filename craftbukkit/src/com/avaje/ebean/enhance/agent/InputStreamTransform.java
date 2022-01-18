// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.security.ProtectionDomain;
import java.lang.instrument.IllegalClassFormatException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

public class InputStreamTransform
{
    final Transformer transformer;
    final ClassLoader classLoader;
    
    public InputStreamTransform(final Transformer transformer, final ClassLoader classLoader) {
        this.transformer = transformer;
        this.classLoader = classLoader;
    }
    
    public void log(final int level, final String msg) {
        this.transformer.log(level, msg);
    }
    
    public byte[] transform(final String className, final File file) throws IOException, IllegalClassFormatException {
        try {
            return this.transform(className, new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public byte[] transform(final String className, final InputStream is) throws IOException, IllegalClassFormatException {
        try {
            final byte[] classBytes = readBytes(is);
            return this.transformer.transform(this.classLoader, className, null, null, classBytes);
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    public static void writeBytes(final byte[] bytes, final File file) throws IOException {
        writeBytes(bytes, new FileOutputStream(file));
    }
    
    public static void writeBytes(final byte[] bytes, final OutputStream os) throws IOException {
        final BufferedOutputStream bos = new BufferedOutputStream(os);
        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        final byte[] buf = new byte[1028];
        int len = 0;
        while ((len = bis.read(buf, 0, buf.length)) > -1) {
            bos.write(buf, 0, len);
        }
        bos.flush();
        bos.close();
        bis.close();
    }
    
    public static byte[] readBytes(final InputStream is) throws IOException {
        final BufferedInputStream bis = new BufferedInputStream(is);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
        final byte[] buf = new byte[1028];
        int len = 0;
        while ((len = bis.read(buf, 0, buf.length)) > -1) {
            baos.write(buf, 0, len);
        }
        baos.flush();
        baos.close();
        return baos.toByteArray();
    }
}
