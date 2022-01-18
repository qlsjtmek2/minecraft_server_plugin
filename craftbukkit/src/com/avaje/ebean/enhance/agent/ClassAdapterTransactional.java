// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import java.util.logging.Level;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import java.util.ArrayList;
import java.util.logging.Logger;
import com.avaje.ebean.enhance.asm.ClassAdapter;

public class ClassAdapterTransactional extends ClassAdapter
{
    static final Logger logger;
    final ArrayList<String> transactionalMethods;
    final EnhanceContext enhanceContext;
    final ClassLoader classLoader;
    ArrayList<ClassMeta> transactionalInterfaces;
    AnnotationInfo classAnnotationInfo;
    String className;
    
    public ClassAdapterTransactional(final ClassVisitor cv, final ClassLoader classLoader, final EnhanceContext context) {
        super(cv);
        this.transactionalMethods = new ArrayList<String>();
        this.transactionalInterfaces = new ArrayList<ClassMeta>();
        this.classLoader = classLoader;
        this.enhanceContext = context;
    }
    
    public boolean isLog(final int level) {
        return this.enhanceContext.isLog(level);
    }
    
    public void log(final String msg) {
        this.enhanceContext.log(this.className, msg);
    }
    
    public AnnotationInfo getInterfaceTransactionalInfo(final String methodName, final String methodDesc) {
        AnnotationInfo interfaceAnnotationInfo = null;
        for (int i = 0; i < this.transactionalInterfaces.size(); ++i) {
            final ClassMeta interfaceMeta = this.transactionalInterfaces.get(i);
            final AnnotationInfo ai = interfaceMeta.getInterfaceTransactionalInfo(methodName, methodDesc);
            if (ai != null) {
                if (interfaceAnnotationInfo != null) {
                    final String msg = "Error in [" + this.className + "] searching the transactional interfaces [" + this.transactionalInterfaces + "] found more than one match for the transactional method:" + methodName + " " + methodDesc;
                    ClassAdapterTransactional.logger.log(Level.SEVERE, msg);
                }
                else {
                    interfaceAnnotationInfo = ai;
                    if (this.isLog(2)) {
                        this.log("inherit transactional from interface [" + interfaceMeta + "] method[" + methodName + " " + methodDesc + "]");
                    }
                }
            }
        }
        return interfaceAnnotationInfo;
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.className = name;
        final int n = 1 + interfaces.length;
        final String[] newInterfaces = new String[n];
        for (int i = 0; i < interfaces.length; ++i) {
            newInterfaces[i] = interfaces[i];
            if (newInterfaces[i].equals("com/avaje/ebean/enhance/agent/EnhancedTransactional")) {
                throw new AlreadyEnhancedException(name);
            }
            final ClassMeta interfaceMeta = this.enhanceContext.getInterfaceMeta(newInterfaces[i], this.classLoader);
            if (interfaceMeta != null && interfaceMeta.isTransactional()) {
                this.transactionalInterfaces.add(interfaceMeta);
                if (this.isLog(6)) {
                    this.log(" implements tranactional interface " + interfaceMeta.getDescription());
                }
            }
        }
        newInterfaces[newInterfaces.length - 1] = "com/avaje/ebean/enhance/agent/EnhancedTransactional";
        super.visit(version, access, name, signature, superName, newInterfaces);
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        final AnnotationVisitor av = super.visitAnnotation(desc, visible);
        if (desc.equals("Lcom/avaje/ebean/annotation/Transactional;")) {
            this.classAnnotationInfo = new AnnotationInfo(null);
            return new AnnotationInfoVisitor(null, this.classAnnotationInfo, av);
        }
        return av;
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("<init>")) {
            return mv;
        }
        return new ScopeTransAdapter(this, mv, access, name, desc);
    }
    
    public void visitEnd() {
        if (!this.isLog(3)) {
            if (this.isLog(2)) {
                this.log("methods:" + this.transactionalMethods);
            }
        }
        super.visitEnd();
    }
    
    void transactionalMethod(final String methodName, final String methodDesc, final AnnotationInfo annoInfo) {
        this.transactionalMethods.add(methodName);
        if (this.isLog(4)) {
            this.log("method:" + methodName + " " + methodDesc + " transactional " + annoInfo);
        }
        else if (this.isLog(3)) {
            this.log("method:" + methodName);
        }
    }
    
    static {
        logger = Logger.getLogger(ClassAdapterTransactional.class.getName());
    }
}
