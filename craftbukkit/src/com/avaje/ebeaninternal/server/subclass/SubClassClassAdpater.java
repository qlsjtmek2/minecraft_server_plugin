// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import com.avaje.ebean.enhance.agent.MethodNewInstance;
import com.avaje.ebean.enhance.agent.MethodIsEmbeddedNewOrDirty;
import com.avaje.ebean.enhance.agent.MethodSetEmbeddedLoaded;
import com.avaje.ebean.enhance.agent.IndexFieldWeaver;
import com.avaje.ebean.enhance.agent.MethodPropertyChangeListener;
import com.avaje.ebean.enhance.agent.VisitMethodParams;
import com.avaje.ebean.enhance.agent.MethodEquals;
import com.avaje.ebean.enhance.agent.InterceptField;
import com.avaje.ebean.enhance.agent.MarkerField;
import com.avaje.ebean.enhance.agent.NoEnhancementRequiredException;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.agent.AlreadyEnhancedException;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.agent.ClassMeta;
import com.avaje.ebean.enhance.agent.EnhanceContext;
import java.util.logging.Logger;
import com.avaje.ebean.enhance.agent.EnhanceConstants;
import com.avaje.ebean.enhance.asm.ClassAdapter;

public class SubClassClassAdpater extends ClassAdapter implements EnhanceConstants
{
    static final Logger logger;
    final EnhanceContext enhanceContext;
    final ClassLoader classLoader;
    final ClassMeta classMeta;
    final String subClassSuffix;
    boolean firstMethod;
    
    public SubClassClassAdpater(final String subClassSuffix, final ClassVisitor cv, final ClassLoader classLoader, final EnhanceContext context) {
        super(cv);
        this.firstMethod = true;
        this.subClassSuffix = subClassSuffix;
        this.classLoader = classLoader;
        this.enhanceContext = context;
        this.classMeta = context.createClassMeta();
    }
    
    public boolean isLog(final int level) {
        return this.classMeta.isLog(level);
    }
    
    public void log(final String msg) {
        this.classMeta.log(msg);
    }
    
    public void visit(final int version, final int access, String name, final String signature, String superName, final String[] interfaces) {
        final int n = 1 + interfaces.length;
        final String[] c = new String[n];
        for (int i = 0; i < interfaces.length; ++i) {
            c[i] = interfaces[i];
            if (c[i].equals("com/avaje/ebean/bean/EntityBean")) {
                throw new AlreadyEnhancedException(name);
            }
            if (c[i].equals("scala/ScalaObject")) {
                this.classMeta.setScalaInterface(true);
            }
            if (c[i].equals("groovy/lang/GroovyObject")) {
                this.classMeta.setGroovyInterface(true);
            }
        }
        c[c.length - 1] = "com/avaje/ebean/bean/EntityBean";
        if (!superName.equals("java/lang/Object")) {
            final ClassMeta superMeta = this.enhanceContext.getSuperMeta(superName, this.classLoader);
            if (superMeta != null) {
                this.classMeta.setSuperMeta(superMeta);
                if (this.classMeta.isLog(2)) {
                    this.classMeta.log("entity inheritance " + superMeta.getDescription());
                }
            }
        }
        superName = name;
        name += this.subClassSuffix;
        this.classMeta.setClassName(name, superName);
        super.visit(version, access, name, signature, superName, c);
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        this.classMeta.addClassAnnotation(desc);
        return super.visitAnnotation(desc, visible);
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        if ((access & 0x8) != 0x0) {
            if (this.isLog(2)) {
                this.log("Skip intercepting static field " + name);
            }
            return null;
        }
        if ((access & 0x80) != 0x0) {
            if (this.classMeta.isLog(2)) {
                this.classMeta.log("Skip intercepting transient field " + name);
            }
            return null;
        }
        if (this.classMeta.isLog(5)) {
            this.classMeta.log(" ... reading field:" + name + " desc:" + desc);
        }
        return this.classMeta.createLocalFieldVisitor(name, desc);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        if (this.firstMethod) {
            if (!this.classMeta.isEntityEnhancementRequired()) {
                throw new NoEnhancementRequiredException();
            }
            final String marker = MarkerField.addField(this.cv, this.classMeta.getClassName());
            if (this.isLog(4)) {
                this.log("... add marker field \"" + marker + "\"");
                this.log("... add intercept and identity fields");
            }
            InterceptField.addField(this.cv, this.enhanceContext.isTransientInternalFields());
            MethodEquals.addIdentityField(this.cv);
            this.firstMethod = false;
        }
        final VisitMethodParams params = new VisitMethodParams(this.cv, access, name, desc, signature, exceptions);
        if (this.isDefaultConstructor(access, name, desc, signature, exceptions)) {
            SubClassConstructor.add(params, this.classMeta);
            return null;
        }
        if (this.isSpecialMethod(access, name, desc)) {
            return null;
        }
        this.classMeta.addExistingSuperMethod(name, desc);
        return null;
    }
    
    public void visitEnd() {
        if (!this.classMeta.isEntityEnhancementRequired()) {
            throw new NoEnhancementRequiredException();
        }
        if (!this.classMeta.hasDefaultConstructor()) {
            if (this.isLog(2)) {
                this.log("... adding default constructor");
            }
            SubClassConstructor.addDefault(this.cv, this.classMeta);
        }
        MarkerField.addGetMarker(this.cv, this.classMeta.getClassName());
        InterceptField.addGetterSetter(this.cv, this.classMeta.getClassName());
        MethodPropertyChangeListener.addMethod(this.cv, this.classMeta);
        GetterSetterMethods.add(this.cv, this.classMeta);
        IndexFieldWeaver.addMethods(this.cv, this.classMeta);
        MethodSetEmbeddedLoaded.addMethod(this.cv, this.classMeta);
        MethodIsEmbeddedNewOrDirty.addMethod(this.cv, this.classMeta);
        MethodNewInstance.addMethod(this.cv, this.classMeta);
        MethodWriteReplace.add(this.cv, this.classMeta);
        this.enhanceContext.addClassMeta(this.classMeta);
        super.visitEnd();
    }
    
    private boolean isDefaultConstructor(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        if (name.equals("<init>") && desc.equals("()V")) {
            this.classMeta.setHasDefaultConstructor(true);
            return true;
        }
        return false;
    }
    
    private boolean isSpecialMethod(final int access, final String name, final String desc) {
        if (name.equals("hashCode") && desc.equals("()I")) {
            this.classMeta.setHasEqualsOrHashcode(true);
            return true;
        }
        if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
            this.classMeta.setHasEqualsOrHashcode(true);
            return true;
        }
        return false;
    }
    
    static {
        logger = Logger.getLogger(SubClassClassAdpater.class.getName());
    }
}
