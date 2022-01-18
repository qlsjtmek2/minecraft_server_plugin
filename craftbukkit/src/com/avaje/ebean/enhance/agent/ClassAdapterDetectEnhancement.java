// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodAdapter;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.EmptyVisitor;
import java.util.ArrayList;
import java.util.HashSet;
import com.avaje.ebean.enhance.asm.ClassAdapter;

public class ClassAdapterDetectEnhancement extends ClassAdapter
{
    private final ClassLoader classLoader;
    private final EnhanceContext enhanceContext;
    private final HashSet<String> classAnnotation;
    private final ArrayList<DetectMethod> methods;
    private String className;
    private boolean entity;
    private boolean entityInterface;
    private boolean entityField;
    private boolean transactional;
    private boolean enhancedTransactional;
    
    public ClassAdapterDetectEnhancement(final ClassLoader classLoader, final EnhanceContext context) {
        super(new EmptyVisitor());
        this.classAnnotation = new HashSet<String>();
        this.methods = new ArrayList<DetectMethod>();
        this.classLoader = classLoader;
        this.enhanceContext = context;
    }
    
    public boolean isEntityOrTransactional() {
        return this.entity || this.isTransactional();
    }
    
    public String getStatus() {
        String s = "class: " + this.className;
        if (this.isEntity()) {
            s = s + " entity:true  enhanced:" + this.entityField;
            s = "*" + s;
        }
        else if (this.isTransactional()) {
            s = s + " transactional:true  enhanced:" + this.enhancedTransactional;
            s = "*" + s;
        }
        else {
            s = " " + s;
        }
        return s;
    }
    
    public boolean isLog(final int level) {
        return this.enhanceContext.isLog(level);
    }
    
    public void log(final String msg) {
        this.enhanceContext.log(this.className, msg);
    }
    
    public void log(final int level, final String msg) {
        if (this.isLog(level)) {
            this.log(msg);
        }
    }
    
    public boolean isEnhancedEntity() {
        return this.entityField;
    }
    
    public boolean isEnhancedTransactional() {
        return this.enhancedTransactional;
    }
    
    public boolean isEntity() {
        return this.entity;
    }
    
    public boolean isTransactional() {
        if (this.transactional) {
            return this.transactional;
        }
        for (int i = 0; i < this.methods.size(); ++i) {
            final DetectMethod m = this.methods.get(i);
            if (m.isTransactional()) {
                return true;
            }
        }
        return false;
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        if ((access & 0x200) != 0x0) {
            throw new NoEnhancementRequiredException(name + " is an Interface");
        }
        this.className = name;
        for (int i = 0; i < interfaces.length; ++i) {
            if (interfaces[i].equals("com/avaje/ebean/bean/EntityBean")) {
                this.entityInterface = true;
                this.entity = true;
            }
            else if (interfaces[i].equals("com/avaje/ebean/enhance/agent/EnhancedTransactional")) {
                this.enhancedTransactional = true;
            }
            else {
                final ClassMeta intefaceMeta = this.enhanceContext.getInterfaceMeta(interfaces[i], this.classLoader);
                if (intefaceMeta != null && intefaceMeta.isTransactional()) {
                    this.transactional = true;
                    if (this.isLog(9)) {
                        this.log("detected implements tranactional interface " + intefaceMeta);
                    }
                }
            }
        }
        if (this.isLog(2)) {
            this.log("interfaces:  entityInterface[" + this.entityInterface + "] transactional[" + this.enhancedTransactional + "]");
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        if (this.isLog(8)) {
            this.log("visitAnnotation " + desc);
        }
        this.classAnnotation.add(desc);
        if (this.isEntityAnnotation(desc)) {
            if (this.isLog(5)) {
                this.log("found entity annotation " + desc);
            }
            this.entity = true;
        }
        else if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
            if (this.isLog(5)) {
                this.log("found transactional annotation " + desc);
            }
            this.transactional = true;
        }
        return super.visitAnnotation(desc, visible);
    }
    
    private boolean isEntityAnnotation(final String desc) {
        return desc.equals("Ljavax/persistence/Entity;") || desc.equals("Ljavax/persistence/Embeddable;") || desc.equals("Ljavax/persistence/MappedSuperclass;");
    }
    
    private boolean isEbeanFieldMarker(final String name, final String desc, final String signature) {
        if (name.equals("_EBEAN_MARKER")) {
            if (!desc.equals("Ljava/lang/String;")) {
                final String m = "Error: _EBEAN_MARKER field of wrong type? " + desc;
                this.log(m);
            }
            return true;
        }
        return false;
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        if (this.isLog(8)) {
            this.log("visitField " + name + " " + value);
        }
        if ((access & 0x8) != 0x0 && this.isEbeanFieldMarker(name, desc, signature)) {
            this.entityField = true;
            if (this.isLog(1)) {
                this.log("Found ebean marker field " + name + " " + value);
            }
        }
        return super.visitField(access, name, desc, signature, value);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        if (this.isLog(9)) {
            this.log("visitMethod " + name + " " + desc);
        }
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        final DetectMethod dmv = new DetectMethod(mv);
        this.methods.add(dmv);
        return dmv;
    }
    
    private static class DetectMethod extends MethodAdapter
    {
        boolean transactional;
        
        public DetectMethod(final MethodVisitor mv) {
            super(mv);
        }
        
        public boolean isTransactional() {
            return this.transactional;
        }
        
        public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
            if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
                this.transactional = true;
            }
            return super.visitAnnotation(desc, visible);
        }
    }
}
