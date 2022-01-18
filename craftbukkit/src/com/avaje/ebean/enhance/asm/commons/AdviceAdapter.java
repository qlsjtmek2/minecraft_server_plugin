// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import java.util.Collection;
import com.avaje.ebean.enhance.asm.Type;
import com.avaje.ebean.enhance.asm.Label;
import java.util.HashMap;
import java.util.ArrayList;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import java.util.Map;
import java.util.List;
import com.avaje.ebean.enhance.asm.Opcodes;

public abstract class AdviceAdapter extends GeneratorAdapter implements Opcodes
{
    private static final Object THIS;
    private static final Object OTHER;
    protected int methodAccess;
    protected String methodDesc;
    private boolean constructor;
    private boolean superInitialized;
    private List stackFrame;
    private Map branches;
    
    protected AdviceAdapter(final MethodVisitor mv, final int access, final String name, final String desc) {
        super(mv, access, name, desc);
        this.methodAccess = access;
        this.methodDesc = desc;
        this.constructor = "<init>".equals(name);
    }
    
    public void visitCode() {
        this.mv.visitCode();
        if (this.constructor) {
            this.stackFrame = new ArrayList();
            this.branches = new HashMap();
        }
        else {
            this.superInitialized = true;
            this.onMethodEnter();
        }
    }
    
    public void visitLabel(final Label label) {
        this.mv.visitLabel(label);
        if (this.constructor && this.branches != null) {
            final List frame = this.branches.get(label);
            if (frame != null) {
                this.stackFrame = frame;
                this.branches.remove(label);
            }
        }
    }
    
    public void visitInsn(final int opcode) {
        if (this.constructor) {
            switch (opcode) {
                case 177: {
                    this.onMethodExit(opcode);
                    break;
                }
                case 172:
                case 174:
                case 176:
                case 191: {
                    this.popValue();
                    this.onMethodExit(opcode);
                    break;
                }
                case 173:
                case 175: {
                    this.popValue();
                    this.popValue();
                    this.onMethodExit(opcode);
                }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 11:
                case 12:
                case 13:
                case 133:
                case 135:
                case 140:
                case 141: {
                    this.pushValue(AdviceAdapter.OTHER);
                    break;
                }
                case 9:
                case 10:
                case 14:
                case 15: {
                    this.pushValue(AdviceAdapter.OTHER);
                    this.pushValue(AdviceAdapter.OTHER);
                    break;
                }
                case 46:
                case 48:
                case 50:
                case 51:
                case 52:
                case 53:
                case 87:
                case 96:
                case 98:
                case 100:
                case 102:
                case 104:
                case 106:
                case 108:
                case 110:
                case 112:
                case 114:
                case 120:
                case 121:
                case 122:
                case 123:
                case 124:
                case 125:
                case 126:
                case 128:
                case 130:
                case 136:
                case 137:
                case 142:
                case 144:
                case 149:
                case 150:
                case 194:
                case 195: {
                    this.popValue();
                    break;
                }
                case 88:
                case 97:
                case 99:
                case 101:
                case 103:
                case 105:
                case 107:
                case 109:
                case 111:
                case 113:
                case 115:
                case 127:
                case 129:
                case 131: {
                    this.popValue();
                    this.popValue();
                    break;
                }
                case 79:
                case 81:
                case 83:
                case 84:
                case 85:
                case 86:
                case 148:
                case 151:
                case 152: {
                    this.popValue();
                    this.popValue();
                    this.popValue();
                    break;
                }
                case 80:
                case 82: {
                    this.popValue();
                    this.popValue();
                    this.popValue();
                    this.popValue();
                    break;
                }
                case 89: {
                    this.pushValue(this.peekValue());
                    break;
                }
                case 90: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
                    break;
                }
                case 91: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
                    break;
                }
                case 92: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
                    this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
                    break;
                }
                case 93: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
                    this.stackFrame.add(s - 3, this.stackFrame.get(s - 1));
                    break;
                }
                case 94: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 4, this.stackFrame.get(s - 1));
                    this.stackFrame.add(s - 4, this.stackFrame.get(s - 1));
                    break;
                }
                case 95: {
                    final int s = this.stackFrame.size();
                    this.stackFrame.add(s - 2, this.stackFrame.get(s - 1));
                    this.stackFrame.remove(s);
                    break;
                }
            }
        }
        else {
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
        }
        this.mv.visitInsn(opcode);
    }
    
    public void visitVarInsn(final int opcode, final int var) {
        super.visitVarInsn(opcode, var);
        if (this.constructor) {
            switch (opcode) {
                case 21:
                case 23: {
                    this.pushValue(AdviceAdapter.OTHER);
                    break;
                }
                case 22:
                case 24: {
                    this.pushValue(AdviceAdapter.OTHER);
                    this.pushValue(AdviceAdapter.OTHER);
                    break;
                }
                case 25: {
                    this.pushValue((var == 0) ? AdviceAdapter.THIS : AdviceAdapter.OTHER);
                    break;
                }
                case 54:
                case 56:
                case 58: {
                    this.popValue();
                    break;
                }
                case 55:
                case 57: {
                    this.popValue();
                    this.popValue();
                    break;
                }
            }
        }
    }
    
    public void visitFieldInsn(final int opcode, final String owner, final String name, final String desc) {
        this.mv.visitFieldInsn(opcode, owner, name, desc);
        if (this.constructor) {
            final char c = desc.charAt(0);
            final boolean longOrDouble = c == 'J' || c == 'D';
            switch (opcode) {
                case 178: {
                    this.pushValue(AdviceAdapter.OTHER);
                    if (longOrDouble) {
                        this.pushValue(AdviceAdapter.OTHER);
                        break;
                    }
                    break;
                }
                case 179: {
                    this.popValue();
                    if (longOrDouble) {
                        this.popValue();
                        break;
                    }
                    break;
                }
                case 181: {
                    this.popValue();
                    if (longOrDouble) {
                        this.popValue();
                        this.popValue();
                        break;
                    }
                    break;
                }
                default: {
                    if (longOrDouble) {
                        this.pushValue(AdviceAdapter.OTHER);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void visitIntInsn(final int opcode, final int operand) {
        this.mv.visitIntInsn(opcode, operand);
        if (this.constructor && opcode != 188) {
            this.pushValue(AdviceAdapter.OTHER);
        }
    }
    
    public void visitLdcInsn(final Object cst) {
        this.mv.visitLdcInsn(cst);
        if (this.constructor) {
            this.pushValue(AdviceAdapter.OTHER);
            if (cst instanceof Double || cst instanceof Long) {
                this.pushValue(AdviceAdapter.OTHER);
            }
        }
    }
    
    public void visitMultiANewArrayInsn(final String desc, final int dims) {
        this.mv.visitMultiANewArrayInsn(desc, dims);
        if (this.constructor) {
            for (int i = 0; i < dims; ++i) {
                this.popValue();
            }
            this.pushValue(AdviceAdapter.OTHER);
        }
    }
    
    public void visitTypeInsn(final int opcode, final String type) {
        this.mv.visitTypeInsn(opcode, type);
        if (this.constructor && opcode == 187) {
            this.pushValue(AdviceAdapter.OTHER);
        }
    }
    
    public void visitMethodInsn(final int opcode, final String owner, final String name, final String desc) {
        this.mv.visitMethodInsn(opcode, owner, name, desc);
        if (this.constructor) {
            final Type[] types = Type.getArgumentTypes(desc);
            for (int i = 0; i < types.length; ++i) {
                this.popValue();
                if (types[i].getSize() == 2) {
                    this.popValue();
                }
            }
            switch (opcode) {
                case 182:
                case 185: {
                    this.popValue();
                    break;
                }
                case 183: {
                    final Object type = this.popValue();
                    if (type == AdviceAdapter.THIS && !this.superInitialized) {
                        this.onMethodEnter();
                        this.superInitialized = true;
                        this.constructor = false;
                        break;
                    }
                    break;
                }
            }
            final Type returnType = Type.getReturnType(desc);
            if (returnType != Type.VOID_TYPE) {
                this.pushValue(AdviceAdapter.OTHER);
                if (returnType.getSize() == 2) {
                    this.pushValue(AdviceAdapter.OTHER);
                }
            }
        }
    }
    
    public void visitJumpInsn(final int opcode, final Label label) {
        this.mv.visitJumpInsn(opcode, label);
        if (this.constructor) {
            switch (opcode) {
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 198:
                case 199: {
                    this.popValue();
                    break;
                }
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166: {
                    this.popValue();
                    this.popValue();
                    break;
                }
                case 168: {
                    this.pushValue(AdviceAdapter.OTHER);
                    break;
                }
            }
            this.addBranch(label);
        }
    }
    
    public void visitLookupSwitchInsn(final Label dflt, final int[] keys, final Label[] labels) {
        this.mv.visitLookupSwitchInsn(dflt, keys, labels);
        if (this.constructor) {
            this.popValue();
            this.addBranches(dflt, labels);
        }
    }
    
    public void visitTableSwitchInsn(final int min, final int max, final Label dflt, final Label[] labels) {
        this.mv.visitTableSwitchInsn(min, max, dflt, labels);
        if (this.constructor) {
            this.popValue();
            this.addBranches(dflt, labels);
        }
    }
    
    private void addBranches(final Label dflt, final Label[] labels) {
        this.addBranch(dflt);
        for (int i = 0; i < labels.length; ++i) {
            this.addBranch(labels[i]);
        }
    }
    
    private void addBranch(final Label label) {
        if (this.branches.containsKey(label)) {
            return;
        }
        this.branches.put(label, new ArrayList(this.stackFrame));
    }
    
    private Object popValue() {
        return this.stackFrame.remove(this.stackFrame.size() - 1);
    }
    
    private Object peekValue() {
        return this.stackFrame.get(this.stackFrame.size() - 1);
    }
    
    private void pushValue(final Object o) {
        this.stackFrame.add(o);
    }
    
    protected void onMethodEnter() {
    }
    
    protected void onMethodExit(final int opcode) {
    }
    
    static {
        THIS = new Object();
        OTHER = new Object();
    }
}
