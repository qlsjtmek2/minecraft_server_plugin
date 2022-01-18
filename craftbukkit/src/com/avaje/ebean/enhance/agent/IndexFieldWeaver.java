// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;
import java.util.List;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;

public class IndexFieldWeaver implements Opcodes
{
    public static void addMethods(final ClassVisitor cv, final ClassMeta classMeta) {
        final List<FieldMeta> fields = classMeta.getAllFields();
        if (fields.size() == 0) {
            classMeta.log("Has no fields?");
            return;
        }
        if (classMeta.isLog(3)) {
            classMeta.log("fields size:" + fields.size() + " " + fields.toString());
        }
        generateCreateCopy(cv, classMeta, fields);
        generateGetField(cv, classMeta, fields, false);
        generateGetField(cv, classMeta, fields, true);
        generateSetField(cv, classMeta, fields, false);
        generateSetField(cv, classMeta, fields, true);
        generateGetDesc(cv, classMeta, fields);
        if (classMeta.hasEqualsOrHashCode()) {
            if (classMeta.isLog(1)) {
                classMeta.log("... skipping add equals() ... already has equals() hashcode() methods");
            }
            return;
        }
        if (classMeta.isInheritEqualsFromSuper()) {
            if (classMeta.isLog(1)) {
                classMeta.log("... skipping add equals() ... will inherit this from super class");
            }
            return;
        }
        int idIndex = -1;
        FieldMeta idFieldMeta = null;
        for (int i = 0; i < fields.size(); ++i) {
            final FieldMeta fieldMeta = fields.get(i);
            if (fieldMeta.isId()) {
                if (idIndex == -1) {
                    idIndex = i;
                    idFieldMeta = fieldMeta;
                }
                else {
                    idIndex = -2;
                }
            }
        }
        if (idIndex == -2) {
            if (classMeta.isLog(1)) {
                classMeta.log("has 2 or more id fields. Not adding equals() method.");
            }
        }
        else if (idIndex == -1) {
            if (classMeta.isLog(1)) {
                classMeta.log("has no id fields. Not adding equals() method.");
            }
        }
        else {
            MethodEquals.addMethods(cv, classMeta, idIndex, idFieldMeta);
        }
    }
    
    private static void generateGetField(final ClassVisitor cv, final ClassMeta classMeta, final List<FieldMeta> fields, final boolean intercept) {
        final String className = classMeta.getClassName();
        MethodVisitor mv = null;
        if (intercept) {
            mv = cv.visitMethod(1, "_ebean_getFieldIntercept", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
        }
        else {
            mv = cv.visitMethod(1, "_ebean_getField", "(ILjava/lang/Object;)Ljava/lang/Object;", null, null);
        }
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 2);
        mv.visitTypeInsn(192, className);
        mv.visitVarInsn(58, 3);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitVarInsn(21, 1);
        final Label[] switchLabels = new Label[fields.size()];
        for (int i = 0; i < switchLabels.length; ++i) {
            switchLabels[i] = new Label();
        }
        final int maxIndex = switchLabels.length - 1;
        final Label labelException = new Label();
        mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
        for (int j = 0; j < fields.size(); ++j) {
            final FieldMeta fieldMeta = fields.get(j);
            mv.visitLabel(switchLabels[j]);
            mv.visitLineNumber(1, switchLabels[j]);
            mv.visitVarInsn(25, 3);
            fieldMeta.appendSwitchGet(mv, classMeta, intercept);
            mv.visitInsn(176);
        }
        mv.visitLabel(labelException);
        mv.visitLineNumber(1, labelException);
        mv.visitTypeInsn(187, "java/lang/RuntimeException");
        mv.visitInsn(89);
        mv.visitTypeInsn(187, "java/lang/StringBuilder");
        mv.visitInsn(89);
        mv.visitLdcInsn("Invalid index ");
        mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        mv.visitVarInsn(21, 1);
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
        mv.visitInsn(191);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l3, 0);
        mv.visitLocalVariable("index", "I", null, l0, l3, 1);
        mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l3, 2);
        mv.visitLocalVariable("p", "L" + className + ";", null, l2, l3, 3);
        mv.visitMaxs(5, 4);
        mv.visitEnd();
    }
    
    private static void generateSetField(final ClassVisitor cv, final ClassMeta classMeta, final List<FieldMeta> fields, final boolean intercept) {
        final String className = classMeta.getClassName();
        MethodVisitor mv = null;
        if (intercept) {
            mv = cv.visitMethod(1, "_ebean_setFieldIntercept", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
        }
        else {
            mv = cv.visitMethod(1, "_ebean_setField", "(ILjava/lang/Object;Ljava/lang/Object;)V", null, null);
        }
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 2);
        mv.visitTypeInsn(192, className);
        mv.visitVarInsn(58, 4);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitVarInsn(21, 1);
        final Label[] switchLabels = new Label[fields.size()];
        for (int i = 0; i < switchLabels.length; ++i) {
            switchLabels[i] = new Label();
        }
        final Label labelException = new Label();
        final int maxIndex = switchLabels.length - 1;
        mv.visitTableSwitchInsn(0, maxIndex, labelException, switchLabels);
        for (int j = 0; j < fields.size(); ++j) {
            final FieldMeta fieldMeta = fields.get(j);
            mv.visitLabel(switchLabels[j]);
            mv.visitLineNumber(1, switchLabels[j]);
            mv.visitVarInsn(25, 4);
            mv.visitVarInsn(25, 3);
            fieldMeta.appendSwitchSet(mv, classMeta, intercept);
            final Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(1, l3);
            mv.visitInsn(177);
        }
        mv.visitLabel(labelException);
        mv.visitLineNumber(1, labelException);
        mv.visitTypeInsn(187, "java/lang/RuntimeException");
        mv.visitInsn(89);
        mv.visitTypeInsn(187, "java/lang/StringBuilder");
        mv.visitInsn(89);
        mv.visitLdcInsn("Invalid index ");
        mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
        mv.visitVarInsn(21, 1);
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
        mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
        mv.visitMethodInsn(183, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V");
        mv.visitInsn(191);
        final Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l4, 0);
        mv.visitLocalVariable("index", "I", null, l0, l4, 1);
        mv.visitLocalVariable("o", "Ljava/lang/Object;", null, l0, l4, 2);
        mv.visitLocalVariable("arg", "Ljava/lang/Object;", null, l0, l4, 3);
        mv.visitLocalVariable("p", "L" + className + ";", null, l2, l4, 4);
        mv.visitMaxs(5, 5);
        mv.visitEnd();
    }
    
    private static void generateCreateCopy(final ClassVisitor cv, final ClassMeta classMeta, final List<FieldMeta> fields) {
        String copyClassName;
        final String className = copyClassName = classMeta.getClassName();
        if (classMeta.isSubclassing()) {
            copyClassName = classMeta.getSuperClassName();
        }
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_createCopy", "()Ljava/lang/Object;", null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitTypeInsn(187, copyClassName);
        mv.visitInsn(89);
        mv.visitMethodInsn(183, copyClassName, "<init>", "()V");
        mv.visitVarInsn(58, 1);
        Label l2 = null;
        for (int i = 0; i < fields.size(); ++i) {
            final FieldMeta fieldMeta = fields.get(i);
            if (fieldMeta.isPersistent()) {
                final Label label = new Label();
                if (i == 0) {
                    l2 = label;
                }
                mv.visitLabel(label);
                mv.visitLineNumber(1, label);
                mv.visitVarInsn(25, 1);
                mv.visitVarInsn(25, 0);
                fieldMeta.addFieldCopy(mv, classMeta);
            }
        }
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLineNumber(1, l3);
        mv.visitVarInsn(25, 1);
        mv.visitInsn(176);
        final Label l4 = new Label();
        mv.visitLabel(l4);
        if (l2 == null) {
            l2 = l3;
        }
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l4, 0);
        mv.visitLocalVariable("p", "L" + copyClassName + ";", null, l2, l4, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
    }
    
    private static void generateGetDesc(final ClassVisitor cv, final ClassMeta classMeta, final List<FieldMeta> fields) {
        final String className = classMeta.getClassName();
        final int size = fields.size();
        final MethodVisitor mv = cv.visitMethod(1, "_ebean_getFieldNames", "()[Ljava/lang/String;", null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        visitIntInsn(mv, size);
        mv.visitTypeInsn(189, "java/lang/String");
        for (int i = 0; i < size; ++i) {
            final FieldMeta fieldMeta = fields.get(i);
            mv.visitInsn(89);
            visitIntInsn(mv, i);
            mv.visitLdcInsn(fieldMeta.getName());
            mv.visitInsn(83);
        }
        mv.visitInsn(176);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l2, 0);
        mv.visitMaxs(4, 1);
        mv.visitEnd();
    }
    
    public static void visitIntInsn(final MethodVisitor mv, final int value) {
        switch (value) {
            case 0: {
                mv.visitInsn(3);
                break;
            }
            case 1: {
                mv.visitInsn(4);
                break;
            }
            case 2: {
                mv.visitInsn(5);
                break;
            }
            case 3: {
                mv.visitInsn(6);
                break;
            }
            case 4: {
                mv.visitInsn(7);
                break;
            }
            case 5: {
                mv.visitInsn(8);
                break;
            }
            default: {
                if (value <= 127) {
                    mv.visitIntInsn(16, value);
                    break;
                }
                mv.visitIntInsn(17, value);
                break;
            }
        }
    }
}
