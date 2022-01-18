// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;

public class InterceptField implements Opcodes, EnhanceConstants
{
    public static void addField(final ClassVisitor cv, final boolean transientInternalFields) {
        final int access = 4 + (transientInternalFields ? 128 : 0);
        final FieldVisitor f1 = cv.visitField(access, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
        f1.visitEnd();
    }
    
    public static void addGetterSetter(final ClassVisitor cv, final String className) {
        final String lClassName = "L" + className + ";";
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_getIntercept", "()Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitInsn(176);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", lClassName, null, l0, l2, 0);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        addInitInterceptMethod(cv, className);
    }
    
    private static void addInitInterceptMethod(final ClassVisitor cv, final String className) {
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_intercept", "()Lcom/avaje/ebean/bean/EntityBeanIntercept;", null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        final Label l2 = new Label();
        mv.visitJumpInsn(199, l2);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLineNumber(2, l3);
        mv.visitVarInsn(25, 0);
        mv.visitTypeInsn(187, "com/avaje/ebean/bean/EntityBeanIntercept");
        mv.visitInsn(89);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "com/avaje/ebean/bean/EntityBeanIntercept", "<init>", "(Ljava/lang/Object;)V");
        mv.visitFieldInsn(181, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitLabel(l2);
        mv.visitLineNumber(3, l2);
        mv.visitFrame(3, 0, null, 0, null);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitInsn(176);
        final Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l4, 0);
        mv.visitMaxs(4, 1);
        mv.visitEnd();
    }
}
