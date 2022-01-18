// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.agent.VisitMethodParams;
import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebean.enhance.asm.Opcodes;

public class SubClassConstructor implements Opcodes, EnhanceConstants
{
    public static void addDefault(final ClassVisitor cv, final ClassMeta meta) {
        final VisitMethodParams params = new VisitMethodParams(cv, 1, "<init>", "()V", null, null);
        add(params, meta);
    }
    
    public static void add(final VisitMethodParams params, final ClassMeta meta) {
        final String className = meta.getClassName();
        final String superClassName = meta.getSuperClassName();
        if (params.forcePublic() && meta.isLog(0)) {
            meta.log(" forcing ACC_PUBLIC ");
        }
        final MethodVisitor mv = params.visitMethod();
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(17, l0);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, superClassName, "<init>", "()V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(18, l2);
        mv.visitVarInsn(25, 0);
        mv.visitTypeInsn(187, "com/avaje/ebean/bean/EntityBeanIntercept");
        mv.visitInsn(89);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, "com/avaje/ebean/bean/EntityBeanIntercept", "<init>", "(Ljava/lang/Object;)V");
        mv.visitFieldInsn(181, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLineNumber(19, l3);
        mv.visitInsn(177);
        final Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l4, 0);
        mv.visitMaxs(4, 1);
        mv.visitEnd();
    }
}
