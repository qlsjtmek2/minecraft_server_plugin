// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;

public class DefaultConstructor
{
    public static void add(final ClassVisitor cw, final ClassMeta classMeta) {
        if (classMeta.isLog(3)) {
            classMeta.log("... adding default constructor, super class: " + classMeta.getSuperClassName());
        }
        final MethodVisitor underlyingMV = cw.visitMethod(1, "<init>", "()V", null, null);
        final ConstructorAdapter mv = new ConstructorAdapter(underlyingMV, classMeta, "()V");
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, classMeta.getSuperClassName(), "<init>", "()V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l3, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }
}
