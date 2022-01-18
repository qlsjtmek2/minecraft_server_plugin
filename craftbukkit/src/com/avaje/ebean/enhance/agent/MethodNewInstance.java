// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;

public class MethodNewInstance
{
    public static void addMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_newInstance", "()Ljava/lang/Object;", null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(10, l0);
        mv.visitTypeInsn(187, classMeta.getClassName());
        mv.visitInsn(89);
        mv.visitMethodInsn(183, classMeta.getClassName(), "<init>", "()V");
        mv.visitInsn(176);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l2, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
}
