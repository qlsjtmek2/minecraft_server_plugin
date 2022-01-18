// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebean.enhance.asm.Opcodes;

public class MethodWriteReplace implements Opcodes, EnhanceConstants
{
    public static void add(final ClassVisitor cv, final ClassMeta classMeta) {
        final MethodVisitor mv = cv.visitMethod(2, "writeReplace", "()Ljava/lang/Object;", null, new String[] { "java/io/ObjectStreamException" });
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, classMeta.getClassName(), "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "writeReplaceIntercept", "()Ljava/lang/Object;");
        mv.visitInsn(176);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l2, 0);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }
}
