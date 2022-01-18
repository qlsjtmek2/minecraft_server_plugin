// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.util.List;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;

public class MethodSetEmbeddedLoaded implements Opcodes, EnhanceConstants
{
    public static void addMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final String className = classMeta.getClassName();
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_setEmbeddedLoaded", "()V", null, null);
        mv.visitCode();
        Label labelBegin = null;
        final List<FieldMeta> allFields = classMeta.getAllFields();
        for (int i = 0; i < allFields.size(); ++i) {
            final FieldMeta fieldMeta = allFields.get(i);
            if (fieldMeta.isEmbedded()) {
                final Label l0 = new Label();
                if (labelBegin == null) {
                    labelBegin = l0;
                }
                mv.visitLabel(l0);
                mv.visitLineNumber(0, l0);
                mv.visitVarInsn(25, 0);
                mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
                mv.visitVarInsn(25, 0);
                fieldMeta.appendSwitchGet(mv, classMeta, false);
                mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "setEmbeddedLoaded", "(Ljava/lang/Object;)V");
            }
        }
        final Label l2 = new Label();
        if (labelBegin == null) {
            labelBegin = l2;
        }
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, labelBegin, l3, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
}
