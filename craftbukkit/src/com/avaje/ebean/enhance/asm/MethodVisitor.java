// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

public interface MethodVisitor
{
    AnnotationVisitor visitAnnotationDefault();
    
    AnnotationVisitor visitAnnotation(final String p0, final boolean p1);
    
    AnnotationVisitor visitParameterAnnotation(final int p0, final String p1, final boolean p2);
    
    void visitAttribute(final Attribute p0);
    
    void visitCode();
    
    void visitFrame(final int p0, final int p1, final Object[] p2, final int p3, final Object[] p4);
    
    void visitInsn(final int p0);
    
    void visitIntInsn(final int p0, final int p1);
    
    void visitVarInsn(final int p0, final int p1);
    
    void visitTypeInsn(final int p0, final String p1);
    
    void visitFieldInsn(final int p0, final String p1, final String p2, final String p3);
    
    void visitMethodInsn(final int p0, final String p1, final String p2, final String p3);
    
    void visitJumpInsn(final int p0, final Label p1);
    
    void visitLabel(final Label p0);
    
    void visitLdcInsn(final Object p0);
    
    void visitIincInsn(final int p0, final int p1);
    
    void visitTableSwitchInsn(final int p0, final int p1, final Label p2, final Label[] p3);
    
    void visitLookupSwitchInsn(final Label p0, final int[] p1, final Label[] p2);
    
    void visitMultiANewArrayInsn(final String p0, final int p1);
    
    void visitTryCatchBlock(final Label p0, final Label p1, final Label p2, final String p3);
    
    void visitLocalVariable(final String p0, final String p1, final String p2, final Label p3, final Label p4, final int p5);
    
    void visitLineNumber(final int p0, final Label p1);
    
    void visitMaxs(final int p0, final int p1);
    
    void visitEnd();
}
