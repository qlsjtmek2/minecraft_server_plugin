// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.EmptyVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.io.PrintStream;
import java.util.logging.Logger;

public class ClassMeta
{
    private static final Logger logger;
    private static final String OBJECT_CLASS;
    private final PrintStream logout;
    private final int logLevel;
    private final boolean subclassing;
    private String className;
    private String superClassName;
    private ClassMeta superMeta;
    private boolean hasGroovyInterface;
    private boolean hasScalaInterface;
    private boolean hasEntityBeanInterface;
    private boolean alreadyEnhanced;
    private boolean hasEqualsOrHashcode;
    private boolean hasDefaultConstructor;
    private HashSet<String> existingMethods;
    private HashSet<String> existingSuperMethods;
    private LinkedHashMap<String, FieldMeta> fields;
    private HashSet<String> classAnnotation;
    private AnnotationInfo annotationInfo;
    private ArrayList<MethodMeta> methodMetaList;
    private final EnhanceContext enhanceContext;
    
    public ClassMeta(final EnhanceContext enhanceContext, final boolean subclassing, final int logLevel, final PrintStream logout) {
        this.existingMethods = new HashSet<String>();
        this.existingSuperMethods = new HashSet<String>();
        this.fields = new LinkedHashMap<String, FieldMeta>();
        this.classAnnotation = new HashSet<String>();
        this.annotationInfo = new AnnotationInfo(null);
        this.methodMetaList = new ArrayList<MethodMeta>();
        this.enhanceContext = enhanceContext;
        this.subclassing = subclassing;
        this.logLevel = logLevel;
        this.logout = logout;
    }
    
    public EnhanceContext getEnhanceContext() {
        return this.enhanceContext;
    }
    
    public Set<String> getClassAnnotations() {
        return this.classAnnotation;
    }
    
    public AnnotationInfo getAnnotationInfo() {
        return this.annotationInfo;
    }
    
    public AnnotationInfo getInterfaceTransactionalInfo(final String methodName, final String methodDesc) {
        AnnotationInfo annotationInfo = null;
        for (int i = 0; i < this.methodMetaList.size(); ++i) {
            final MethodMeta meta = this.methodMetaList.get(i);
            if (meta.isMatch(methodName, methodDesc)) {
                if (annotationInfo != null) {
                    final String msg = "Error in [" + this.className + "] searching the transactional methods[" + this.methodMetaList + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
                    ClassMeta.logger.log(Level.SEVERE, msg);
                    this.log(msg);
                }
                else {
                    annotationInfo = meta.getAnnotationInfo();
                    if (this.isLog(9)) {
                        this.log("... found transactional info from interface " + this.className + " " + methodName + " " + methodDesc);
                    }
                }
            }
        }
        return annotationInfo;
    }
    
    public boolean isCheckSuperClassForEntity() {
        return this.isEntity() && !this.superClassName.equals(ClassMeta.OBJECT_CLASS);
    }
    
    public String toString() {
        return this.className;
    }
    
    public boolean isTransactional() {
        return this.classAnnotation.contains("Lcom/avaje/ebean/annotation/Transactional;");
    }
    
    public ArrayList<MethodMeta> getMethodMeta() {
        return this.methodMetaList;
    }
    
    public void setClassName(final String className, final String superClassName) {
        this.className = className;
        this.superClassName = superClassName;
    }
    
    public String getSuperClassName() {
        return this.superClassName;
    }
    
    public boolean isSubclassing() {
        return this.subclassing;
    }
    
    public boolean isLog(final int level) {
        return level <= this.logLevel;
    }
    
    public void log(String msg) {
        if (this.className != null) {
            msg = "cls: " + this.className + "  msg: " + msg;
        }
        this.logout.println("transform> " + msg);
    }
    
    public void logEnhanced() {
        String m = "enhanced ";
        if (this.hasScalaInterface()) {
            m += " (scala)";
        }
        if (this.hasGroovyInterface()) {
            m += " (groovy)";
        }
        this.log(m);
    }
    
    public boolean isInheritEqualsFromSuper() {
        return !this.subclassing && this.isSuperClassEntity();
    }
    
    public ClassMeta getSuperMeta() {
        return this.superMeta;
    }
    
    public void setSuperMeta(final ClassMeta superMeta) {
        this.superMeta = superMeta;
    }
    
    public void setHasEqualsOrHashcode(final boolean hasEqualsOrHashcode) {
        this.hasEqualsOrHashcode = hasEqualsOrHashcode;
    }
    
    public boolean hasEqualsOrHashCode() {
        return this.hasEqualsOrHashcode;
    }
    
    public boolean isFieldPersistent(final String fieldName) {
        final FieldMeta f = this.fields.get(fieldName);
        if (f != null) {
            return f.isPersistent();
        }
        return this.superMeta != null && this.superMeta.isFieldPersistent(fieldName);
    }
    
    public List<FieldMeta> getLocalFields() {
        final ArrayList<FieldMeta> list = new ArrayList<FieldMeta>();
        for (final FieldMeta fm : this.fields.values()) {
            if (!fm.isObjectArray()) {
                list.add(fm);
            }
        }
        return list;
    }
    
    public List<FieldMeta> getInheritedFields() {
        return this.getInheritedFields(new ArrayList<FieldMeta>());
    }
    
    private List<FieldMeta> getInheritedFields(List<FieldMeta> list) {
        if (list == null) {
            list = new ArrayList<FieldMeta>();
        }
        if (this.superMeta != null) {
            this.superMeta.addFieldsForInheritance(list);
        }
        return list;
    }
    
    private void addFieldsForInheritance(final List<FieldMeta> list) {
        if (this.isEntity()) {
            list.addAll(0, this.fields.values());
            if (this.superMeta != null) {
                this.superMeta.addFieldsForInheritance(list);
            }
        }
    }
    
    public List<FieldMeta> getAllFields() {
        final List<FieldMeta> list = this.getLocalFields();
        this.getInheritedFields(list);
        return list;
    }
    
    public void addFieldGetSetMethods(final ClassVisitor cv) {
        if (this.isEntityEnhancementRequired()) {
            for (final FieldMeta fm : this.fields.values()) {
                fm.addGetSetMethods(cv, this);
            }
        }
    }
    
    public boolean isEntity() {
        return this.classAnnotation.contains("Ljavax/persistence/Entity;") || this.classAnnotation.contains("Ljavax/persistence/Embeddable;") || this.classAnnotation.contains("Ljavax/persistence/MappedSuperclass;") || this.classAnnotation.contains("Lcom/avaje/ebean/annotation/LdapDomain;");
    }
    
    public boolean isEntityEnhancementRequired() {
        return !this.alreadyEnhanced && this.isEntity();
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public boolean isSuperClassEntity() {
        return this.superMeta != null && this.superMeta.isEntity();
    }
    
    public void addClassAnnotation(final String desc) {
        this.classAnnotation.add(desc);
    }
    
    public void addExistingSuperMethod(final String methodName, final String methodDesc) {
        this.existingSuperMethods.add(methodName + methodDesc);
    }
    
    public void addExistingMethod(final String methodName, final String methodDesc) {
        this.existingMethods.add(methodName + methodDesc);
    }
    
    public boolean isExistingMethod(final String methodName, final String methodDesc) {
        return this.existingMethods.contains(methodName + methodDesc);
    }
    
    public boolean isExistingSuperMethod(final String methodName, final String methodDesc) {
        return this.existingSuperMethods.contains(methodName + methodDesc);
    }
    
    public MethodVisitor createMethodVisitor(final MethodVisitor mv, final int access, final String name, final String desc) {
        final MethodMeta methodMeta = new MethodMeta(this.annotationInfo, access, name, desc);
        this.methodMetaList.add(methodMeta);
        return new MethodReader(mv, methodMeta);
    }
    
    public FieldVisitor createLocalFieldVisitor(final String name, final String desc) {
        return this.createLocalFieldVisitor(null, null, name, desc);
    }
    
    public FieldVisitor createLocalFieldVisitor(final ClassVisitor cv, final FieldVisitor fv, final String name, final String desc) {
        final String fieldClass = this.subclassing ? this.superClassName : this.className;
        final FieldMeta fieldMeta = new FieldMeta(this, name, desc, fieldClass);
        final LocalFieldVisitor localField = new LocalFieldVisitor(cv, fv, fieldMeta);
        if (name.startsWith("_ebean")) {
            if (this.isLog(0)) {
                this.log("... ignore field " + name);
            }
        }
        else {
            this.fields.put(localField.getName(), fieldMeta);
        }
        return localField;
    }
    
    public boolean isAlreadyEnhanced() {
        return this.alreadyEnhanced;
    }
    
    public void setAlreadyEnhanced(final boolean alreadyEnhanced) {
        this.alreadyEnhanced = alreadyEnhanced;
    }
    
    public boolean hasDefaultConstructor() {
        return this.hasDefaultConstructor;
    }
    
    public void setHasDefaultConstructor(final boolean hasDefaultConstructor) {
        this.hasDefaultConstructor = hasDefaultConstructor;
    }
    
    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        this.appendDescription(sb);
        return sb.toString();
    }
    
    private void appendDescription(final StringBuilder sb) {
        sb.append(this.className);
        if (this.superMeta != null) {
            sb.append(" : ");
            this.superMeta.appendDescription(sb);
        }
    }
    
    public boolean hasScalaInterface() {
        return this.hasScalaInterface;
    }
    
    public void setScalaInterface(final boolean hasScalaInterface) {
        this.hasScalaInterface = hasScalaInterface;
    }
    
    public boolean hasEntityBeanInterface() {
        return this.hasEntityBeanInterface;
    }
    
    public void setEntityBeanInterface(final boolean hasEntityBeanInterface) {
        this.hasEntityBeanInterface = hasEntityBeanInterface;
    }
    
    public boolean hasGroovyInterface() {
        return this.hasGroovyInterface;
    }
    
    public void setGroovyInterface(final boolean hasGroovyInterface) {
        this.hasGroovyInterface = hasGroovyInterface;
    }
    
    static {
        logger = Logger.getLogger(ClassMeta.class.getName());
        OBJECT_CLASS = Object.class.getName().replace('.', '/');
    }
    
    private static final class MethodReader extends EmptyVisitor
    {
        final MethodVisitor mv;
        final MethodMeta methodMeta;
        
        MethodReader(final MethodVisitor mv, final MethodMeta methodMeta) {
            this.mv = mv;
            this.methodMeta = methodMeta;
        }
        
        public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
            final AnnotationVisitor av = this.mv.visitAnnotation(desc, visible);
            return new AnnotationInfoVisitor(null, this.methodMeta.annotationInfo, av);
        }
    }
}
