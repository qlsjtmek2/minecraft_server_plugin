// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.Attribute;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.EmptyVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;

public class LocalFieldVisitor implements FieldVisitor
{
    private static final EmptyVisitor emptyVisitor;
    private final FieldVisitor fv;
    private final FieldMeta fieldMeta;
    
    public LocalFieldVisitor(final FieldMeta fieldMeta) {
        this.fv = null;
        this.fieldMeta = fieldMeta;
    }
    
    public LocalFieldVisitor(final ClassVisitor cv, final FieldVisitor fv, final FieldMeta fieldMeta) {
        this.fv = fv;
        this.fieldMeta = fieldMeta;
    }
    
    public boolean isPersistentSetter(final String methodDesc) {
        return this.fieldMeta.isPersistentSetter(methodDesc);
    }
    
    public boolean isPersistentGetter(final String methodDesc) {
        return this.fieldMeta.isPersistentGetter(methodDesc);
    }
    
    public String getName() {
        return this.fieldMeta.getFieldName();
    }
    
    public FieldMeta getFieldMeta() {
        return this.fieldMeta;
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        this.fieldMeta.addAnnotationDesc(desc);
        if (this.fv != null) {
            return this.fv.visitAnnotation(desc, visible);
        }
        return LocalFieldVisitor.emptyVisitor;
    }
    
    public void visitAttribute(final Attribute attr) {
        if (this.fv != null) {
            this.fv.visitAttribute(attr);
        }
    }
    
    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
    
    static {
        emptyVisitor = new EmptyVisitor();
    }
}
