// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.EmptyVisitor;

public class ClassMetaReaderVisitor extends EmptyVisitor implements EnhanceConstants
{
    private final ClassMeta classMeta;
    private final boolean readMethodMeta;
    
    public ClassMetaReaderVisitor(final boolean readMethodMeta, final EnhanceContext context) {
        this.readMethodMeta = readMethodMeta;
        this.classMeta = context.createClassMeta();
    }
    
    public ClassMeta getClassMeta() {
        return this.classMeta;
    }
    
    public boolean isLog(final int level) {
        return this.classMeta.isLog(level);
    }
    
    public void log(final String msg) {
        this.classMeta.log(msg);
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.classMeta.setClassName(name, superName);
        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        this.classMeta.addClassAnnotation(desc);
        final AnnotationVisitor av = super.visitAnnotation(desc, visible);
        if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
            return new AnnotationInfoVisitor(null, this.classMeta.getAnnotationInfo(), av);
        }
        return av;
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        if ((access & 0x8) != 0x0) {
            if (this.isLog(2)) {
                this.log("Skip static field " + name);
            }
            return super.visitField(access, name, desc, signature, value);
        }
        if ((access & 0x80) != 0x0) {
            if (this.isLog(2)) {
                this.log("Skip transient field " + name);
            }
            return super.visitField(access, name, desc, signature, value);
        }
        return this.classMeta.createLocalFieldVisitor(name, desc);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final boolean staticAccess = (access & 0x8) != 0x0;
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!staticAccess && this.readMethodMeta) {
            return this.classMeta.createMethodVisitor(mv, access, name, desc);
        }
        return mv;
    }
}
