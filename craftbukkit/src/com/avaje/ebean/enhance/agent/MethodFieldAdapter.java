// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Opcodes;
import com.avaje.ebean.enhance.asm.MethodAdapter;

public class MethodFieldAdapter extends MethodAdapter implements Opcodes
{
    final ClassMeta meta;
    final String className;
    final String methodDescription;
    boolean transientAnnotation;
    
    public MethodFieldAdapter(final MethodVisitor mv, final ClassMeta meta, final String methodDescription) {
        super(mv);
        this.transientAnnotation = false;
        this.meta = meta;
        this.className = meta.getClassName();
        this.methodDescription = methodDescription;
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        if (desc.equals("Ljavax/persistence/Transient;")) {
            this.transientAnnotation = true;
        }
        return super.visitAnnotation(desc, visible);
    }
    
    public void visitLocalVariable(final String name, final String desc, final String signature, final Label start, final Label end, final int index) {
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }
    
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
        super.visitMethodInsn(opcode, owner, name, desc);
    }
    
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        if (this.transientAnnotation) {
            super.visitFieldInsn(opcode, owner, name, desc);
            return;
        }
        if (opcode == 178 || opcode == 179) {
            if (this.meta.isLog(3)) {
                this.meta.log(" ... info: skip static field " + owner + " " + name + " in " + this.methodDescription);
            }
            super.visitFieldInsn(opcode, owner, name, desc);
            return;
        }
        if (!this.meta.isFieldPersistent(name)) {
            if (this.meta.isLog(2)) {
                this.meta.log(" ... info: non-persistent field " + owner + " " + name + " in " + this.methodDescription);
            }
            super.visitFieldInsn(opcode, owner, name, desc);
            return;
        }
        if (opcode == 180) {
            final String methodName = "_ebean_get_" + name;
            final String methodDesc = "()" + desc;
            if (this.meta.isLog(4)) {
                this.meta.log("GETFIELD method:" + this.methodDescription + " field:" + name + " > " + methodName + " " + methodDesc);
            }
            super.visitMethodInsn(182, this.className, methodName, methodDesc);
        }
        else if (opcode == 181) {
            final String methodName = "_ebean_set_" + name;
            final String methodDesc = "(" + desc + ")V";
            if (this.meta.isLog(4)) {
                this.meta.log("PUTFIELD method:" + this.methodDescription + " field:" + name + " > " + methodName + " " + methodDesc);
            }
            super.visitMethodInsn(182, this.className, methodName, methodDesc);
        }
        else {
            this.meta.log("Warning adapting method:" + this.methodDescription + "; unexpected static access to a persistent field?? " + name + " opCode not GETFIELD or PUTFIELD??  opCode:" + opcode + "");
            super.visitFieldInsn(opcode, owner, name, desc);
        }
    }
}
