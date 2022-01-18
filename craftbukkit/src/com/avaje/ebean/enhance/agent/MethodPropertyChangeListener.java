// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;

public class MethodPropertyChangeListener implements Opcodes, EnhanceConstants
{
    public static void addMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        addAddListenerMethod(cv, classMeta);
        addAddPropertyListenerMethod(cv, classMeta);
        addRemoveListenerMethod(cv, classMeta);
        addRemovePropertyListenerMethod(cv, classMeta);
    }
    
    private static boolean alreadyExisting(final ClassMeta classMeta, final String method, final String desc) {
        if (classMeta.isExistingMethod(method, desc)) {
            if (classMeta.isLog(1)) {
                classMeta.log("Existing method... " + method + desc + "  - not adding Ebean's implementation");
            }
            return true;
        }
        return false;
    }
    
    private static void addAddListenerMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final String desc = "(Ljava/beans/PropertyChangeListener;)V";
        if (alreadyExisting(classMeta, "addPropertyChangeListener", desc)) {
            return;
        }
        final String className = classMeta.getClassName();
        final MethodVisitor mv = cv.visitMethod(1, "addPropertyChangeListener", desc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "addPropertyChangeListener", "(Ljava/beans/PropertyChangeListener;)V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
        mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l3, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }
    
    private static void addAddPropertyListenerMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final String desc = "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V";
        if (alreadyExisting(classMeta, "addPropertyChangeListener", desc)) {
            return;
        }
        final String className = classMeta.getClassName();
        final MethodVisitor mv = cv.visitMethod(1, "addPropertyChangeListener", desc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1);
        mv.visitVarInsn(25, 2);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "addPropertyChangeListener", "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
        mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l3, 1);
        mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l3, 2);
        mv.visitMaxs(3, 3);
        mv.visitEnd();
    }
    
    private static void addRemoveListenerMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final String desc = "(Ljava/beans/PropertyChangeListener;)V";
        if (alreadyExisting(classMeta, "removePropertyChangeListener", desc)) {
            return;
        }
        final String className = classMeta.getClassName();
        final MethodVisitor mv = cv.visitMethod(1, "removePropertyChangeListener", desc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "removePropertyChangeListener", "(Ljava/beans/PropertyChangeListener;)V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
        mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l3, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }
    
    private static void addRemovePropertyListenerMethod(final ClassVisitor cv, final ClassMeta classMeta) {
        final String desc = "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V";
        if (alreadyExisting(classMeta, "removePropertyChangeListener", desc)) {
            return;
        }
        final String className = classMeta.getClassName();
        final MethodVisitor mv = cv.visitMethod(1, "removePropertyChangeListener", desc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1);
        mv.visitVarInsn(25, 2);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "removePropertyChangeListener", "(Ljava/lang/String;Ljava/beans/PropertyChangeListener;)V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
        mv.visitLocalVariable("name", "Ljava/lang/String;", null, l0, l3, 1);
        mv.visitLocalVariable("listener", "Ljava/beans/PropertyChangeListener;", null, l0, l3, 2);
        mv.visitMaxs(3, 3);
        mv.visitEnd();
    }
}
