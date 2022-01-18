// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import java.util.HashSet;
import com.avaje.ebean.enhance.asm.Type;
import com.avaje.ebean.enhance.asm.Opcodes;

public class FieldMeta implements Opcodes, EnhanceConstants
{
    private static final Type BOOLEAN_OBJECT_TYPE;
    private final ClassMeta classMeta;
    private final String fieldClass;
    private final String fieldName;
    private final String fieldDesc;
    private final HashSet<String> annotations;
    private final Type asmType;
    private final boolean primativeType;
    private final boolean objectType;
    private final String getMethodName;
    private final String getMethodDesc;
    private final String setMethodName;
    private final String setMethodDesc;
    private final String getNoInterceptMethodName;
    private final String setNoInterceptMethodName;
    private final String publicSetterName;
    private final String publicGetterName;
    
    public FieldMeta(final ClassMeta classMeta, final String name, final String desc, final String fieldClass) {
        this.annotations = new HashSet<String>();
        this.classMeta = classMeta;
        this.fieldName = name;
        this.fieldDesc = desc;
        this.fieldClass = fieldClass;
        this.asmType = Type.getType(desc);
        final int sort = this.asmType.getSort();
        this.primativeType = (sort > 0 && sort <= 8);
        this.objectType = (sort == 10);
        this.getMethodName = "_ebean_get_" + name;
        this.getMethodDesc = "()" + desc;
        this.setMethodName = "_ebean_set_" + name;
        this.setMethodDesc = "(" + desc + ")V";
        this.getNoInterceptMethodName = "_ebean_getni_" + name;
        this.setNoInterceptMethodName = "_ebean_setni_" + name;
        if (classMeta != null && classMeta.hasScalaInterface()) {
            this.publicSetterName = name + "_$eq";
            this.publicGetterName = name;
        }
        else {
            final String publicFieldName = this.getFieldName(name, this.asmType);
            final String initCap = Character.toUpperCase(publicFieldName.charAt(0)) + publicFieldName.substring(1);
            this.publicSetterName = "set" + initCap;
            if (this.fieldDesc.equals("Z")) {
                this.publicGetterName = "is" + initCap;
            }
            else {
                this.publicGetterName = "get" + initCap;
            }
        }
        if (classMeta != null && classMeta.isLog(6)) {
            classMeta.log(" ... public getter [" + this.publicGetterName + "]");
            classMeta.log(" ... public setter [" + this.publicSetterName + "]");
        }
    }
    
    private String getFieldName(final String name, final Type asmType) {
        if ((FieldMeta.BOOLEAN_OBJECT_TYPE.equals(asmType) || Type.BOOLEAN_TYPE.equals(asmType)) && name.startsWith("is") && name.length() > 2) {
            final char c = name.charAt(2);
            if (Character.isUpperCase(c)) {
                if (this.classMeta.isLog(6)) {
                    this.classMeta.log("trimming off \"is\" from boolean field name " + name + "]");
                }
                return name.substring(2);
            }
        }
        return name;
    }
    
    public String toString() {
        return this.fieldName;
    }
    
    public String getFieldName() {
        return this.fieldName;
    }
    
    public boolean isPrimativeType() {
        return this.primativeType;
    }
    
    public String getPublicGetterName() {
        return this.publicGetterName;
    }
    
    public String getPublicSetterName() {
        return this.publicSetterName;
    }
    
    public boolean isPersistentSetter(final String methodDesc) {
        return this.setMethodDesc.equals(methodDesc) && this.isInterceptSet();
    }
    
    public boolean isPersistentGetter(final String methodDesc) {
        return this.getMethodDesc.equals(methodDesc) && this.isInterceptGet();
    }
    
    protected void addAnnotationDesc(final String desc) {
        this.annotations.add(desc);
    }
    
    public String getName() {
        return this.fieldName;
    }
    
    public String getDesc() {
        return this.fieldDesc;
    }
    
    private boolean isInterceptGet() {
        return !this.isId() && !this.isTransient() && (!this.isMany() || true);
    }
    
    private boolean isInterceptSet() {
        return !this.isId() && !this.isTransient() && !this.isMany();
    }
    
    public boolean isObjectArray() {
        if (this.fieldDesc.charAt(0) == '[' && this.fieldDesc.length() > 2) {
            if (!this.isTransient()) {
                System.err.println("ERROR: We can not support Object Arrays... for field: " + this.fieldName);
            }
            return true;
        }
        return false;
    }
    
    public boolean isPersistent() {
        return !this.isTransient();
    }
    
    public boolean isTransient() {
        return this.annotations.contains("Ljavax/persistence/Transient;");
    }
    
    public boolean isId() {
        final boolean idField = this.annotations.contains("Ljavax/persistence/Id;") || this.annotations.contains("Ljavax/persistence/EmbeddedId;");
        return idField;
    }
    
    public boolean isMany() {
        return this.annotations.contains("Ljavax/persistence/OneToMany;") || this.annotations.contains("Ljavax/persistence/ManyToMany;");
    }
    
    public boolean isManyToMany() {
        return this.annotations.contains("Ljavax/persistence/ManyToMany;");
    }
    
    public boolean isEmbedded() {
        return this.annotations.contains("Ljavax/persistence/Embedded;") || this.annotations.contains("Lcom/avaje/ebean/annotation/EmbeddedColumns;");
    }
    
    private boolean isLocalField(final ClassMeta classMeta) {
        return this.fieldClass.equals(classMeta.getClassName());
    }
    
    public void appendGetPrimitiveIdValue(final MethodVisitor mv, final ClassMeta classMeta) {
        if (classMeta.isSubclassing()) {
            mv.visitMethodInsn(182, classMeta.getSuperClassName(), this.publicGetterName, this.getMethodDesc);
        }
        else {
            mv.visitMethodInsn(182, classMeta.getClassName(), this.getMethodName, this.getMethodDesc);
        }
    }
    
    public void appendCompare(final MethodVisitor mv, final ClassMeta classMeta) {
        if (this.primativeType) {
            if (classMeta.isLog(4)) {
                classMeta.log(" ... getIdentity compare primitive field[" + this.fieldName + "] type[" + this.fieldDesc + "]");
            }
            if (this.fieldDesc.equals("J")) {
                mv.visitInsn(9);
                mv.visitInsn(148);
            }
            else if (this.fieldDesc.equals("D")) {
                mv.visitInsn(14);
                mv.visitInsn(151);
            }
            else if (this.fieldDesc.equals("F")) {
                mv.visitInsn(11);
                mv.visitInsn(149);
            }
        }
    }
    
    public void appendValueOf(final MethodVisitor mv, final ClassMeta classMeta) {
        if (this.primativeType) {
            final Type objectWrapperType = PrimitiveHelper.getObjectWrapper(this.asmType);
            final String objDesc = objectWrapperType.getInternalName();
            final String primDesc = this.asmType.getDescriptor();
            mv.visitMethodInsn(184, objDesc, "valueOf", "(" + primDesc + ")L" + objDesc + ";");
        }
    }
    
    public void addFieldCopy(final MethodVisitor mv, final ClassMeta classMeta) {
        if (classMeta.isSubclassing()) {
            final String copyClassName = classMeta.getSuperClassName();
            mv.visitMethodInsn(183, copyClassName, this.publicGetterName, this.getMethodDesc);
            mv.visitMethodInsn(182, copyClassName, this.publicSetterName, this.setMethodDesc);
        }
        else if (this.isLocalField(classMeta)) {
            mv.visitFieldInsn(180, this.fieldClass, this.fieldName, this.fieldDesc);
            mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
        }
        else {
            if (classMeta.isLog(4)) {
                classMeta.log(" ... addFieldCopy on non-local field [" + this.fieldName + "] type[" + this.fieldDesc + "]");
            }
            mv.visitMethodInsn(182, classMeta.getClassName(), this.getNoInterceptMethodName, this.getMethodDesc);
            mv.visitMethodInsn(182, classMeta.getClassName(), this.setNoInterceptMethodName, this.setMethodDesc);
        }
    }
    
    public void appendSwitchGet(final MethodVisitor mv, final ClassMeta classMeta, final boolean intercept) {
        if (classMeta.isSubclassing()) {
            if (intercept) {
                mv.visitMethodInsn(182, classMeta.getClassName(), this.publicGetterName, this.getMethodDesc);
            }
            else {
                mv.visitMethodInsn(183, classMeta.getSuperClassName(), this.publicGetterName, this.getMethodDesc);
            }
        }
        else if (intercept) {
            mv.visitMethodInsn(182, classMeta.getClassName(), this.getMethodName, this.getMethodDesc);
        }
        else if (this.isLocalField(classMeta)) {
            mv.visitFieldInsn(180, classMeta.getClassName(), this.fieldName, this.fieldDesc);
        }
        else {
            mv.visitMethodInsn(182, classMeta.getClassName(), this.getNoInterceptMethodName, this.getMethodDesc);
        }
        if (this.primativeType) {
            this.appendValueOf(mv, classMeta);
        }
    }
    
    public void appendSwitchSet(final MethodVisitor mv, final ClassMeta classMeta, final boolean intercept) {
        if (this.primativeType) {
            final Type objectWrapperType = PrimitiveHelper.getObjectWrapper(this.asmType);
            final String primDesc = this.asmType.getDescriptor();
            final String primType = this.asmType.getClassName();
            final String objInt = objectWrapperType.getInternalName();
            mv.visitTypeInsn(192, objInt);
            mv.visitMethodInsn(182, objInt, primType + "Value", "()" + primDesc);
        }
        else {
            mv.visitTypeInsn(192, this.asmType.getInternalName());
        }
        if (classMeta.isSubclassing()) {
            if (intercept) {
                mv.visitMethodInsn(182, classMeta.getClassName(), this.publicSetterName, this.setMethodDesc);
            }
            else {
                mv.visitMethodInsn(183, classMeta.getSuperClassName(), this.publicSetterName, this.setMethodDesc);
            }
        }
        else if (intercept) {
            mv.visitMethodInsn(182, classMeta.getClassName(), this.setMethodName, this.setMethodDesc);
        }
        else if (this.isLocalField(classMeta)) {
            mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
        }
        else {
            mv.visitMethodInsn(182, classMeta.getClassName(), this.setNoInterceptMethodName, this.setMethodDesc);
        }
    }
    
    public void addPublicGetSetMethods(final ClassVisitor cv, final ClassMeta classMeta, final boolean checkExisting) {
        if (this.isPersistent()) {
            if (this.isId()) {
                this.addPublicSetMethod(cv, classMeta, checkExisting);
            }
            else {
                this.addPublicGetMethod(cv, classMeta, checkExisting);
                this.addPublicSetMethod(cv, classMeta, checkExisting);
            }
        }
    }
    
    private void addPublicGetMethod(final ClassVisitor cv, final ClassMeta classMeta, final boolean checkExisting) {
        if (checkExisting && !classMeta.isExistingSuperMethod(this.publicGetterName, this.getMethodDesc)) {
            if (classMeta.isLog(1)) {
                classMeta.log("excluding " + this.publicGetterName + " as not on super object");
            }
            return;
        }
        this.addPublicGetMethod(new VisitMethodParams(cv, 1, this.publicGetterName, this.getMethodDesc, null, null), classMeta);
    }
    
    private void addPublicGetMethod(final VisitMethodParams params, final ClassMeta classMeta) {
        final MethodVisitor mv = params.visitMethod();
        final int iReturnOpcode = this.asmType.getOpcode(172);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, classMeta.getClassName(), "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitLdcInsn(this.fieldName);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, classMeta.getSuperClassName(), params.getName(), params.getDesc());
        mv.visitInsn(iReturnOpcode);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + classMeta.getClassName() + ";", null, l0, l3, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
    
    private void addPublicSetMethod(final ClassVisitor cv, final ClassMeta classMeta, final boolean checkExisting) {
        if (checkExisting && !classMeta.isExistingSuperMethod(this.publicSetterName, this.setMethodDesc)) {
            if (classMeta.isLog(1)) {
                classMeta.log("excluding " + this.publicSetterName + " as not on super object");
            }
            return;
        }
        this.addPublicSetMethod(new VisitMethodParams(cv, 1, this.publicSetterName, this.setMethodDesc, null, null), classMeta);
    }
    
    private void addPublicSetMethod(final VisitMethodParams params, final ClassMeta classMeta) {
        final MethodVisitor mv = params.visitMethod();
        final String publicGetterName = this.getPublicGetterName();
        String preSetterArgTypes = "Ljava/lang/Object;Ljava/lang/Object;";
        if (this.primativeType) {
            preSetterArgTypes = this.fieldDesc + this.fieldDesc;
        }
        final int iLoadOpcode = this.asmType.getOpcode(21);
        final int iPosition = this.asmType.getSize();
        final String className = classMeta.getClassName();
        final String superClassName = classMeta.getSuperClassName();
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        if (this.isInterceptSet()) {
            mv.visitInsn(4);
        }
        else {
            mv.visitInsn(3);
        }
        String preSetterMethod = "preSetter";
        if (this.isMany()) {
            preSetterMethod = "preSetterMany";
        }
        mv.visitLdcInsn(this.fieldName);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(182, className, publicGetterName, this.getMethodDesc);
        mv.visitVarInsn(iLoadOpcode, 1);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", preSetterMethod, "(ZLjava/lang/String;" + preSetterArgTypes + ")Ljava/beans/PropertyChangeEvent;");
        mv.visitVarInsn(58, 1 + iPosition);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(iLoadOpcode, 1);
        mv.visitMethodInsn(183, superClassName, params.getName(), params.getDesc());
        final Label levt = new Label();
        mv.visitLabel(levt);
        mv.visitLineNumber(3, levt);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1 + iPosition);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(183, superClassName, publicGetterName, this.getMethodDesc);
        if (this.primativeType) {
            this.appendValueOf(mv, classMeta);
        }
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "postSetter", "(Ljava/beans/PropertyChangeEvent;Ljava/lang/Object;)V");
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLineNumber(1, l3);
        mv.visitInsn(177);
        final Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l4, 0);
        mv.visitLocalVariable("newValue", this.fieldDesc, null, l0, l4, 1);
        mv.visitLocalVariable("evt", "Ljava/beans/PropertyChangeEvent;", null, l2, l4, 2);
        mv.visitMaxs(5, 3);
        mv.visitEnd();
    }
    
    public void addGetSetMethods(final ClassVisitor cv, final ClassMeta classMeta) {
        if (!this.isLocalField(classMeta)) {
            final String msg = "ERROR: " + this.fieldClass + " != " + classMeta.getClassName() + " for field " + this.fieldName + " " + this.fieldDesc;
            throw new RuntimeException(msg);
        }
        this.addGet(cv, classMeta);
        this.addSet(cv, classMeta);
        this.addGetNoIntercept(cv, classMeta);
        this.addSetNoIntercept(cv, classMeta);
    }
    
    private String getEbeanCollectionClass() {
        if (this.fieldDesc.equals("Ljava/util/List;")) {
            return "com/avaje/ebean/common/BeanList";
        }
        if (this.fieldDesc.equals("Ljava/util/Set;")) {
            return "com/avaje/ebean/common/BeanSet";
        }
        if (this.fieldDesc.equals("Ljava/util/Map;")) {
            return "com/avaje/ebean/common/BeanMap";
        }
        return null;
    }
    
    private boolean isInterceptMany() {
        if (this.isMany() && !this.isTransient()) {
            final String ebCollection = this.getEbeanCollectionClass();
            if (ebCollection != null) {
                return true;
            }
            this.classMeta.log("Error unepxected many type " + this.fieldDesc);
        }
        return false;
    }
    
    private void addGet(final ClassVisitor cw, final ClassMeta classMeta) {
        if (classMeta.isLog(3)) {
            classMeta.log(this.getMethodName + " " + this.getMethodDesc + " intercept:" + this.isInterceptGet() + " " + this.annotations);
        }
        final MethodVisitor mv = cw.visitMethod(4, this.getMethodName, this.getMethodDesc, null, null);
        mv.visitCode();
        if (this.isInterceptMany()) {
            this.addGetForMany(mv);
            return;
        }
        final int iReturnOpcode = this.asmType.getOpcode(172);
        final String className = classMeta.getClassName();
        final Label labelEnd = new Label();
        Label labelStart = null;
        if (this.isInterceptGet()) {
            labelStart = new Label();
            mv.visitLabel(labelStart);
            mv.visitLineNumber(4, labelStart);
            mv.visitFrame(3, 0, null, 0, null);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
            mv.visitLdcInsn(this.fieldName);
            mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
        }
        if (labelStart == null) {
            labelStart = labelEnd;
        }
        mv.visitLabel(labelEnd);
        mv.visitLineNumber(5, labelEnd);
        mv.visitFrame(3, 0, null, 0, null);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
        mv.visitInsn(iReturnOpcode);
        final Label labelEnd2 = new Label();
        mv.visitLabel(labelEnd2);
        mv.visitLocalVariable("this", "L" + className + ";", null, labelStart, labelEnd2, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
    
    private void addGetForMany(final MethodVisitor mv) {
        final String className = this.classMeta.getClassName();
        final String ebCollection = this.getEbeanCollectionClass();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitLdcInsn(this.fieldName);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "preGetter", "(Ljava/lang/String;)V");
        final Label l2 = new Label();
        if (this.classMeta.getEnhanceContext().isCheckNullManyFields()) {
            if (this.classMeta.isLog(3)) {
                this.classMeta.log("... add Many null check on " + this.fieldName + " ebtype:" + ebCollection);
            }
            final Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(2, l3);
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
            mv.visitJumpInsn(199, l2);
            final Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(3, l4);
            mv.visitVarInsn(25, 0);
            mv.visitTypeInsn(187, ebCollection);
            mv.visitInsn(89);
            mv.visitMethodInsn(183, ebCollection, "<init>", "()V");
            mv.visitFieldInsn(181, className, this.fieldName, this.fieldDesc);
            if (this.isManyToMany()) {
                if (this.classMeta.isLog(3)) {
                    this.classMeta.log("... add ManyToMany modify listening to " + this.fieldName);
                }
                final Label l5 = new Label();
                mv.visitLabel(l5);
                mv.visitLineNumber(4, l5);
                mv.visitVarInsn(25, 0);
                mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
                mv.visitTypeInsn(192, "com/avaje/ebean/bean/BeanCollection");
                mv.visitFieldInsn(178, "com/avaje/ebean/bean/BeanCollection$ModifyListenMode", "ALL", "Lcom/avaje/ebean/bean/BeanCollection$ModifyListenMode;");
                mv.visitMethodInsn(185, "com/avaje/ebean/bean/BeanCollection", "setModifyListening", "(Lcom/avaje/ebean/bean/BeanCollection$ModifyListenMode;)V");
            }
        }
        mv.visitLabel(l2);
        mv.visitLineNumber(5, l2);
        mv.visitFrame(3, 0, null, 0, null);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, className, this.fieldName, this.fieldDesc);
        mv.visitInsn(176);
        final Label l6 = new Label();
        mv.visitLabel(l6);
        mv.visitLocalVariable("this", "L" + className + ";", null, l0, l6, 0);
        mv.visitMaxs(3, 1);
        mv.visitEnd();
    }
    
    private void addGetNoIntercept(final ClassVisitor cw, final ClassMeta classMeta) {
        final int iReturnOpcode = this.asmType.getOpcode(172);
        if (classMeta.isLog(3)) {
            classMeta.log(this.getNoInterceptMethodName + " " + this.getMethodDesc);
        }
        final MethodVisitor mv = cw.visitMethod(4, this.getNoInterceptMethodName, this.getMethodDesc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, this.fieldClass, this.fieldName, this.fieldDesc);
        mv.visitInsn(iReturnOpcode);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l2, 0);
        mv.visitMaxs(2, 1);
        mv.visitEnd();
    }
    
    private void addSet(final ClassVisitor cw, final ClassMeta classMeta) {
        String preSetterArgTypes = "Ljava/lang/Object;Ljava/lang/Object;";
        if (!this.objectType) {
            preSetterArgTypes = this.fieldDesc + this.fieldDesc;
        }
        final int iLoadOpcode = this.asmType.getOpcode(21);
        final int iPosition = this.asmType.getSize();
        if (classMeta.isLog(3)) {
            classMeta.log(this.setMethodName + " " + this.setMethodDesc + " intercept:" + this.isInterceptSet() + " opCode:" + iLoadOpcode + "," + iPosition + " preSetterArgTypes" + preSetterArgTypes);
        }
        final MethodVisitor mv = cw.visitMethod(4, this.setMethodName, this.setMethodDesc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, this.fieldClass, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        if (this.isInterceptSet()) {
            mv.visitInsn(4);
        }
        else {
            mv.visitInsn(3);
        }
        mv.visitLdcInsn(this.fieldName);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(182, this.fieldClass, this.getMethodName, this.getMethodDesc);
        mv.visitVarInsn(iLoadOpcode, 1);
        String preSetterMethod = "preSetter";
        if (this.isMany()) {
            preSetterMethod = "preSetterMany";
        }
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", preSetterMethod, "(ZLjava/lang/String;" + preSetterArgTypes + ")Ljava/beans/PropertyChangeEvent;");
        mv.visitVarInsn(58, 1 + iPosition);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(2, l2);
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(iLoadOpcode, 1);
        mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLineNumber(3, l3);
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, this.fieldClass, "_ebean_intercept", "Lcom/avaje/ebean/bean/EntityBeanIntercept;");
        mv.visitVarInsn(25, 1 + iPosition);
        mv.visitMethodInsn(182, "com/avaje/ebean/bean/EntityBeanIntercept", "postSetter", "(Ljava/beans/PropertyChangeEvent;)V");
        final Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitLineNumber(4, l4);
        mv.visitInsn(177);
        final Label l5 = new Label();
        mv.visitLabel(l5);
        mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l5, 0);
        mv.visitLocalVariable("newValue", this.fieldDesc, null, l0, l5, 1);
        mv.visitLocalVariable("evt", "Ljava/beans/PropertyChangeEvent;", null, l2, l5, 2);
        mv.visitMaxs(5, 3);
        mv.visitEnd();
    }
    
    private void addSetNoIntercept(final ClassVisitor cw, final ClassMeta classMeta) {
        final int iLoadOpcode = this.asmType.getOpcode(21);
        final int iPosition = this.asmType.getSize();
        if (classMeta.isLog(3)) {
            classMeta.log(this.setNoInterceptMethodName + " " + this.setMethodDesc + " opCode:" + iLoadOpcode + "," + iPosition);
        }
        final MethodVisitor mv = cw.visitMethod(4, this.setNoInterceptMethodName, this.setMethodDesc, null, null);
        mv.visitCode();
        final Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitLineNumber(1, l0);
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(iLoadOpcode, 1);
        mv.visitFieldInsn(181, this.fieldClass, this.fieldName, this.fieldDesc);
        final Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitLineNumber(1, l2);
        mv.visitInsn(177);
        final Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + this.fieldClass + ";", null, l0, l3, 0);
        mv.visitLocalVariable("_newValue", this.fieldDesc, null, l0, l3, 1);
        mv.visitMaxs(4, 2);
        mv.visitEnd();
    }
    
    static {
        BOOLEAN_OBJECT_TYPE = Type.getType(Boolean.class);
    }
}
