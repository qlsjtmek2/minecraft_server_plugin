// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;

public abstract class MethodAdviceAdapter extends GeneratorAdapter implements Opcodes
{
    protected int methodAccess;
    protected String methodName;
    protected String methodDesc;
    
    protected MethodAdviceAdapter(final MethodVisitor mv, final int access, final String name, final String desc) {
        super(mv, access, name, desc);
        this.methodAccess = access;
        this.methodDesc = desc;
        this.methodName = name;
    }
    
    public void visitCode() {
        this.mv.visitCode();
        this.onMethodEnter();
    }
    
    public void visitLabel(final Label label) {
        this.mv.visitLabel(label);
    }
    
    public void visitInsn(final int opcode) {
        switch (opcode) {
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 191: {
                this.onMethodExit(opcode);
                break;
            }
        }
        this.mv.visitInsn(opcode);
    }
    
    public void visitVarInsn(final int opcode, final int var) {
        super.visitVarInsn(opcode, var);
    }
    
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        this.mv.visitFieldInsn(opcode, owner, name, desc);
    }
    
    public void visitIntInsn(final int opcode, final int operand) {
        this.mv.visitIntInsn(opcode, operand);
    }
    
    public void visitLdcInsn(final Object cst) {
        this.mv.visitLdcInsn(cst);
    }
    
    public void visitMultiANewArrayInsn(final String desc, final int dims) {
        this.mv.visitMultiANewArrayInsn(desc, dims);
    }
    
    public void visitTypeInsn(final int opcode, final String type) {
        this.mv.visitTypeInsn(opcode, type);
    }
    
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
        this.mv.visitMethodInsn(opcode, owner, name, desc);
    }
    
    public void visitJumpInsn(final int opcode, final Label label) {
        this.mv.visitJumpInsn(opcode, label);
    }
    
    public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
        this.mv.visitLookupSwitchInsn(dflt, keys, labels);
    }
    
    public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label[] labels) {
        this.mv.visitTableSwitchInsn(min, max, dflt, labels);
    }
    
    protected void onMethodEnter() {
    }
    
    protected void onMethodExit(final int opcode) {
    }
}
