// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.ClassReader;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.io.PrintStream;
import com.avaje.ebean.enhance.asm.ClassWriter;
import java.net.URL;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;

public class Transformer implements ClassFileTransformer
{
    private static final int CLASS_WRITER_COMPUTEFLAGS = 3;
    private final EnhanceContext enhanceContext;
    private boolean performDetect;
    private boolean transformTransactional;
    private boolean transformEntityBeans;
    
    public static void premain(final String agentArgs, final Instrumentation inst) {
        final Transformer t = new Transformer("", agentArgs);
        inst.addTransformer(t);
        if (t.getLogLevel() > 0) {
            System.out.println("premain loading Transformer args:" + agentArgs);
        }
    }
    
    public Transformer(final String extraClassPath, final String agentArgs) {
        this(parseClassPaths(extraClassPath), agentArgs);
    }
    
    public Transformer(final URL[] extraClassPath, final String agentArgs) {
        this(new ClassPathClassBytesReader(extraClassPath), agentArgs);
    }
    
    public Transformer(final ClassBytesReader r, final String agentArgs) {
        this.enhanceContext = new EnhanceContext(r, false, agentArgs);
        this.performDetect = this.enhanceContext.getPropertyBoolean("detect", true);
        this.transformTransactional = this.enhanceContext.getPropertyBoolean("transactional", true);
        this.transformEntityBeans = this.enhanceContext.getPropertyBoolean("entity", true);
    }
    
    protected ClassWriter createClassWriter() {
        return new ClassWriter(3);
    }
    
    public void setLogout(final PrintStream logout) {
        this.enhanceContext.setLogout(logout);
    }
    
    public void log(final int level, final String msg) {
        this.enhanceContext.log(level, msg);
    }
    
    public int getLogLevel() {
        return this.enhanceContext.getLogLevel();
    }
    
    public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined, final ProtectionDomain protectionDomain, final byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (this.enhanceContext.isIgnoreClass(className)) {
                return null;
            }
            ClassAdapterDetectEnhancement detect = null;
            if (this.performDetect) {
                this.enhanceContext.log(5, "performing detection on " + className);
                detect = this.detect(loader, classfileBuffer);
            }
            if (detect == null) {
                this.enhanceContext.log(1, "no detection so enhancing entity " + className);
                return this.entityEnhancement(loader, classfileBuffer);
            }
            if (this.transformEntityBeans && detect.isEntity()) {
                if (!detect.isEnhancedEntity()) {
                    detect.log(2, "performing entity transform");
                    return this.entityEnhancement(loader, classfileBuffer);
                }
                detect.log(1, "already enhanced entity");
            }
            if (this.transformTransactional && detect.isTransactional()) {
                if (!detect.isEnhancedTransactional()) {
                    detect.log(2, "performing transactional transform");
                    return this.transactionalEnhancement(loader, classfileBuffer);
                }
                detect.log(1, "already enhanced transactional");
            }
            return null;
        }
        catch (NoEnhancementRequiredException e) {
            this.log(8, "No Enhancement required " + e.getMessage());
            return null;
        }
        catch (Exception e2) {
            this.enhanceContext.log(e2);
            return null;
        }
    }
    
    private byte[] entityEnhancement(final ClassLoader loader, final byte[] classfileBuffer) {
        final ClassReader cr = new ClassReader(classfileBuffer);
        final ClassWriter cw = this.createClassWriter();
        final ClassAdpaterEntity ca = new ClassAdpaterEntity(cw, loader, this.enhanceContext);
        try {
            cr.accept(ca, 0);
            if (ca.isLog(1)) {
                ca.logEnhanced();
            }
            if (this.enhanceContext.isReadOnly()) {
                return null;
            }
            return cw.toByteArray();
        }
        catch (AlreadyEnhancedException e) {
            if (ca.isLog(1)) {
                ca.log("already enhanced entity");
            }
            return null;
        }
        catch (NoEnhancementRequiredException e2) {
            if (ca.isLog(2)) {
                ca.log("skipping... no enhancement required");
            }
            return null;
        }
    }
    
    private byte[] transactionalEnhancement(final ClassLoader loader, final byte[] classfileBuffer) {
        final ClassReader cr = new ClassReader(classfileBuffer);
        final ClassWriter cw = this.createClassWriter();
        final ClassAdapterTransactional ca = new ClassAdapterTransactional(cw, loader, this.enhanceContext);
        try {
            cr.accept(ca, 0);
            if (ca.isLog(1)) {
                ca.log("enhanced");
            }
            if (this.enhanceContext.isReadOnly()) {
                return null;
            }
            return cw.toByteArray();
        }
        catch (AlreadyEnhancedException e) {
            if (ca.isLog(1)) {
                ca.log("already enhanced");
            }
            return null;
        }
        catch (NoEnhancementRequiredException e2) {
            if (ca.isLog(0)) {
                ca.log("skipping... no enhancement required");
            }
            return null;
        }
    }
    
    public static URL[] parseClassPaths(final String extraClassPath) {
        if (extraClassPath == null) {
            return new URL[0];
        }
        final String[] stringPaths = extraClassPath.split(";");
        return UrlPathHelper.convertToUrl(stringPaths);
    }
    
    private ClassAdapterDetectEnhancement detect(final ClassLoader classLoader, final byte[] classfileBuffer) {
        final ClassAdapterDetectEnhancement detect = new ClassAdapterDetectEnhancement(classLoader, this.enhanceContext);
        final ClassReader cr = new ClassReader(classfileBuffer);
        cr.accept(detect, 7);
        return detect;
    }
}
