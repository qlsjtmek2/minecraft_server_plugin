// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;

public class AnnotationInfoVisitor implements AnnotationVisitor
{
    final AnnotationVisitor av;
    final AnnotationInfo info;
    final String prefix;
    
    public AnnotationInfoVisitor(final String prefix, final AnnotationInfo info, final AnnotationVisitor av) {
        this.av = av;
        this.info = info;
        this.prefix = prefix;
    }
    
    public void visit(final String name, final Object value) {
        this.info.add(this.prefix, name, value);
    }
    
    public AnnotationVisitor visitAnnotation(final String name, final String desc) {
        return this.create(name);
    }
    
    public AnnotationVisitor visitArray(final String name) {
        return this.create(name);
    }
    
    private AnnotationInfoVisitor create(final String name) {
        final String newPrefix = (this.prefix == null) ? name : (this.prefix + "." + name);
        return new AnnotationInfoVisitor(newPrefix, this.info, this.av);
    }
    
    public void visitEnd() {
        this.av.visitEnd();
    }
    
    public void visitEnum(final String name, final String desc, final String value) {
        this.info.addEnum(this.prefix, name, desc, value);
        this.av.visitEnum(name, desc, value);
    }
}
