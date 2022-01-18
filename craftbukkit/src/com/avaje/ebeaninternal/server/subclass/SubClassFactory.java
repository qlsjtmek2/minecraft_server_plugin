// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import java.io.InputStream;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.ClassWriter;
import com.avaje.ebean.enhance.asm.ClassReader;
import java.io.IOException;
import java.util.logging.Level;
import com.avaje.ebean.enhance.agent.ClassBytesReader;
import java.net.URL;
import com.avaje.ebean.enhance.agent.ClassPathClassBytesReader;
import com.avaje.ebean.enhance.agent.EnhanceContext;
import java.util.logging.Logger;
import com.avaje.ebean.enhance.agent.EnhanceConstants;

public class SubClassFactory extends ClassLoader implements EnhanceConstants, GenSuffix
{
    private static final Logger logger;
    private static final int CLASS_WRITER_FLAGS = 3;
    private final EnhanceContext enhanceContext;
    private final ClassLoader parentClassLoader;
    
    public SubClassFactory(final ClassLoader parent, final int logLevel) {
        super(parent);
        this.parentClassLoader = parent;
        final ClassPathClassBytesReader reader = new ClassPathClassBytesReader(null);
        this.enhanceContext = new EnhanceContext(reader, true, "debug=" + logLevel);
    }
    
    public Class<?> create(final Class<?> normalClass, final String serverName) throws IOException {
        String subClassSuffix = "$$EntityBean";
        if (serverName != null) {
            subClassSuffix = subClassSuffix + "$" + serverName;
        }
        final String clsName = normalClass.getName();
        final String subClsName = clsName + subClassSuffix;
        try {
            final byte[] newClsBytes = this.subclassBytes(clsName, subClassSuffix);
            final Class<?> newCls = this.defineClass(subClsName, newClsBytes, 0, newClsBytes.length);
            return newCls;
        }
        catch (IOException ex) {
            final String m = "Error creating subclass for [" + clsName + "]";
            SubClassFactory.logger.log(Level.SEVERE, m, ex);
            throw ex;
        }
        catch (Throwable ex2) {
            final String m = "Error creating subclass for [" + clsName + "]";
            SubClassFactory.logger.log(Level.SEVERE, m, ex2);
            throw new RuntimeException(ex2);
        }
    }
    
    private byte[] subclassBytes(final String className, final String subClassSuffix) throws IOException {
        final String resName = className.replace('.', '/') + ".class";
        final InputStream is = this.getResourceAsStream(resName);
        final ClassReader cr = new ClassReader(is);
        final ClassWriter cw = new ClassWriter(3);
        final SubClassClassAdpater ca = new SubClassClassAdpater(subClassSuffix, cw, this.parentClassLoader, this.enhanceContext);
        if (ca.isLog(1)) {
            ca.log(" enhancing " + className + subClassSuffix);
        }
        cr.accept(ca, 0);
        return cw.toByteArray();
    }
    
    static {
        logger = Logger.getLogger(SubClassFactory.class.getName());
    }
}
