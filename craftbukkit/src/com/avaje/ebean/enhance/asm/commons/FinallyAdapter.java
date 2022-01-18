// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Label;

public class FinallyAdapter extends AdviceAdapter
{
    private String name;
    private Label startFinally;
    
    public FinallyAdapter(final MethodVisitor mv, final int acc, final String name, final String desc) {
        super(mv, acc, name, desc);
        this.startFinally = new Label();
        this.name = name;
    }
    
    public void visitCode() {
        super.visitCode();
        this.mv.visitLabel(this.startFinally);
    }
    
    public void visitMaxs(final int maxStack, final int maxLocals) {
        final Label endFinally = new Label();
        this.mv.visitTryCatchBlock(this.startFinally, endFinally, endFinally, null);
        this.mv.visitLabel(endFinally);
        this.onFinally(191);
        this.mv.visitInsn(191);
        this.mv.visitMaxs(maxStack, maxLocals);
    }
    
    protected void onMethodExit(final int opcode) {
        if (opcode != 191) {
            this.onFinally(opcode);
        }
    }
    
    private void onFinally(final int opcode) {
        this.mv.visitFieldInsn(178, "java/lang/System", "err", "Ljava/io/PrintStream;");
        this.mv.visitLdcInsn("Exiting " + this.name);
        this.mv.visitMethodInsn(182, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    }
}
