// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

public interface ClassVisitor
{
    void visit(final int p0, final int p1, final String p2, final String p3, final String p4, final String[] p5);
    
    void visitSource(final String p0, final String p1);
    
    void visitOuterClass(final String p0, final String p1, final String p2);
    
    AnnotationVisitor visitAnnotation(final String p0, final boolean p1);
    
    void visitAttribute(final Attribute p0);
    
    void visitInnerClass(final String p0, final String p1, final String p2, final int p3);
    
    FieldVisitor visitField(final int p0, final String p1, final String p2, final String p3, final Object p4);
    
    MethodVisitor visitMethod(final int p0, final String p1, final String p2, final String p3, final String[] p4);
    
    void visitEnd();
}
