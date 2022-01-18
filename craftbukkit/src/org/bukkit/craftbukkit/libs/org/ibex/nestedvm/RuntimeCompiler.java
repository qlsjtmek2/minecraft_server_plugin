// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;

public class RuntimeCompiler
{
    public static Class compile(final Seekable seekable) throws IOException, Compiler.Exn {
        return compile(seekable, null);
    }
    
    public static Class compile(final Seekable seekable, final String s) throws IOException, Compiler.Exn {
        return compile(seekable, s, null);
    }
    
    public static Class compile(final Seekable seekable, final String s, final String s2) throws IOException, Compiler.Exn {
        final String s3 = "nestedvm.runtimecompiled";
        byte[] array;
        try {
            array = runCompiler(seekable, s3, s, s2, null);
        }
        catch (Compiler.Exn exn) {
            if (exn.getMessage() == null && exn.getMessage().indexOf("constant pool full") == -1) {
                throw exn;
            }
            array = runCompiler(seekable, s3, s, s2, "lessconstants");
        }
        return new SingleClassLoader().fromBytes(s3, array);
    }
    
    private static byte[] runCompiler(final Seekable seekable, final String s, final String s2, final String source, final String s3) throws IOException, Compiler.Exn {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            final ClassFileCompiler classFileCompiler = new ClassFileCompiler(seekable, s, byteArrayOutputStream);
            classFileCompiler.parseOptions("nosupportcall,maxinsnpermethod=256");
            classFileCompiler.setSource(source);
            if (s2 != null) {
                classFileCompiler.parseOptions(s2);
            }
            if (s3 != null) {
                classFileCompiler.parseOptions(s3);
            }
            classFileCompiler.go();
        }
        finally {
            seekable.seek(0);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public static void main(final String[] array) throws Exception {
        if (array.length == 0) {
            System.err.println("Usage: RuntimeCompiler mipsbinary");
            System.exit(1);
        }
        final UnixRuntime unixRuntime = compile(new Seekable.File(array[0]), "unixruntime").newInstance();
        System.err.println("Instansiated: " + unixRuntime);
        System.exit(UnixRuntime.runAndExec(unixRuntime, array));
    }
    
    private static class SingleClassLoader extends ClassLoader
    {
        public Class loadClass(final String s, final boolean b) throws ClassNotFoundException {
            return super.loadClass(s, b);
        }
        
        public Class fromBytes(final String s, final byte[] array) {
            return this.fromBytes(s, array, 0, array.length);
        }
        
        public Class fromBytes(final String s, final byte[] array, final int n, final int n2) {
            final Class<?> defineClass = super.defineClass(s, array, n, n2);
            this.resolveClass(defineClass);
            return defineClass;
        }
    }
}
