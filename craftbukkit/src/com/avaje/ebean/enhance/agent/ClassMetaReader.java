// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.ClassReader;
import java.util.HashMap;
import java.util.Map;

public class ClassMetaReader
{
    private Map<String, ClassMeta> cache;
    private final EnhanceContext enhanceContext;
    
    public ClassMetaReader(final EnhanceContext enhanceContext) {
        this.cache = new HashMap<String, ClassMeta>();
        this.enhanceContext = enhanceContext;
    }
    
    public ClassMeta get(final boolean readMethodAnnotations, final String name, final ClassLoader classLoader) throws ClassNotFoundException {
        return this.getWithCache(readMethodAnnotations, name, classLoader);
    }
    
    private ClassMeta getWithCache(final boolean readMethodAnnotations, final String name, final ClassLoader classLoader) throws ClassNotFoundException {
        synchronized (this.cache) {
            ClassMeta meta = this.cache.get(name);
            if (meta == null) {
                meta = this.readFromResource(readMethodAnnotations, name, classLoader);
                if (meta != null) {
                    if (meta.isCheckSuperClassForEntity()) {
                        final ClassMeta superMeta = this.getWithCache(readMethodAnnotations, meta.getSuperClassName(), classLoader);
                        if (superMeta != null && superMeta.isEntity()) {
                            meta.setSuperMeta(superMeta);
                        }
                    }
                    this.cache.put(name, meta);
                }
            }
            return meta;
        }
    }
    
    private ClassMeta readFromResource(final boolean readMethodAnnotations, final String className, final ClassLoader classLoader) throws ClassNotFoundException {
        final byte[] classBytes = this.enhanceContext.getClassBytes(className, classLoader);
        if (classBytes == null) {
            this.enhanceContext.log(1, "Class [" + className + "] not found.");
            return null;
        }
        if (this.enhanceContext.isLog(3)) {
            this.enhanceContext.log(className, "read ClassMeta");
        }
        final ClassReader cr = new ClassReader(classBytes);
        final ClassMetaReaderVisitor ca = new ClassMetaReaderVisitor(readMethodAnnotations, this.enhanceContext);
        cr.accept(ca, 0);
        return ca.getClassMeta();
    }
}
