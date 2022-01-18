// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import com.avaje.ebean.enhance.asm.Opcodes;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import com.avaje.ebean.enhance.asm.Type;
import com.avaje.ebean.enhance.asm.MethodAdapter;

public class LocalVariablesSorter extends MethodAdapter
{
    private static final Type OBJECT_TYPE;
    private int[] mapping;
    private Object[] newLocals;
    protected final int firstLocal;
    protected int nextLocal;
    private boolean changed;
    
    public LocalVariablesSorter(final int access, final String desc, final MethodVisitor mv) {
        super(mv);
        this.mapping = new int[40];
        this.newLocals = new Object[20];
        final Type[] args = Type.getArgumentTypes(desc);
        this.nextLocal = (((0x8 & access) == 0x0) ? 1 : 0);
        for (int i = 0; i < args.length; ++i) {
            this.nextLocal += args[i].getSize();
        }
        this.firstLocal = this.nextLocal;
    }
    
    public void visitVarInsn(final int opcode, final int var) {
        Type type = null;
        switch (opcode) {
            case 22:
            case 55: {
                type = Type.LONG_TYPE;
                break;
            }
            case 24:
            case 57: {
                type = Type.DOUBLE_TYPE;
                break;
            }
            case 23:
            case 56: {
                type = Type.FLOAT_TYPE;
                break;
            }
            case 21:
            case 54: {
                type = Type.INT_TYPE;
                break;
            }
            case 25:
            case 58: {
                type = LocalVariablesSorter.OBJECT_TYPE;
                break;
            }
            default: {
                type = Type.VOID_TYPE;
                break;
            }
        }
        this.mv.visitVarInsn(opcode, this.remap(var, type));
    }
    
    public void visitIincInsn(final int var, final int increment) {
        this.mv.visitIincInsn(this.remap(var, Type.INT_TYPE), increment);
    }
    
    public void visitMaxs(final int maxStack, final int maxLocals) {
        this.mv.visitMaxs(maxStack, this.nextLocal);
    }
    
    public void visitLocalVariable(final String name, final String desc, final String signature, final Label start, final Label end, final int index) {
        final int newIndex = this.remap(index, Type.getType(desc));
        this.mv.visitLocalVariable(name, desc, signature, start, end, newIndex);
    }
    
    public void visitFrame(final int type, final int nLocal, final Object[] local, final int nStack, final Object[] stack) {
        if (type != -1) {
            throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
        }
        if (!this.changed) {
            this.mv.visitFrame(type, nLocal, local, nStack, stack);
            return;
        }
        final Object[] oldLocals = new Object[this.newLocals.length];
        System.arraycopy(this.newLocals, 0, oldLocals, 0, oldLocals.length);
        int index = 0;
        for (final Object t : local) {
            final int size = (t == Opcodes.LONG || t == Opcodes.DOUBLE) ? 2 : 1;
            if (t != Opcodes.TOP) {
                this.setFrameLocal(this.remap(index, size), t);
            }
            index += size;
        }
        index = 0;
        int number = 0;
        int i = 0;
        while (index < this.newLocals.length) {
            final Object t2 = this.newLocals[index++];
            if (t2 != null && t2 != Opcodes.TOP) {
                this.newLocals[i] = t2;
                number = i + 1;
                if (t2 == Opcodes.LONG || t2 == Opcodes.DOUBLE) {
                    ++index;
                }
            }
            else {
                this.newLocals[i] = Opcodes.TOP;
            }
            ++i;
        }
        this.mv.visitFrame(type, number, this.newLocals, nStack, stack);
        this.newLocals = oldLocals;
    }
    
    public int newLocal(final Type type) {
        Object t = null;
        switch (type.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                t = Opcodes.INTEGER;
                break;
            }
            case 6: {
                t = Opcodes.FLOAT;
                break;
            }
            case 7: {
                t = Opcodes.LONG;
                break;
            }
            case 8: {
                t = Opcodes.DOUBLE;
                break;
            }
            case 9: {
                t = type.getDescriptor();
                break;
            }
            default: {
                t = type.getInternalName();
                break;
            }
        }
        final int local = this.nextLocal;
        this.nextLocal += type.getSize();
        this.setLocalType(local, type);
        this.setFrameLocal(local, t);
        return local;
    }
    
    protected void setLocalType(final int local, final Type type) {
    }
    
    private void setFrameLocal(final int local, final Object type) {
        final int l = this.newLocals.length;
        if (local >= l) {
            final Object[] a = new Object[Math.max(2 * l, local + 1)];
            System.arraycopy(this.newLocals, 0, a, 0, l);
            this.newLocals = a;
        }
        this.newLocals[local] = type;
    }
    
    private int remap(final int var, final Type type) {
        if (var < this.firstLocal) {
            return var;
        }
        final int key = 2 * var + type.getSize() - 1;
        final int size = this.mapping.length;
        if (key >= size) {
            final int[] newMapping = new int[Math.max(2 * size, key + 1)];
            System.arraycopy(this.mapping, 0, newMapping, 0, size);
            this.mapping = newMapping;
        }
        int value = this.mapping[key];
        if (value == 0) {
            value = this.newLocalMapping(type);
            this.setLocalType(value, type);
            this.mapping[key] = value + 1;
        }
        else {
            --value;
        }
        if (value != var) {
            this.changed = true;
        }
        return value;
    }
    
    protected int newLocalMapping(final Type type) {
        final int local = this.nextLocal;
        this.nextLocal += type.getSize();
        return local;
    }
    
    private int remap(final int var, final int size) {
        if (var < this.firstLocal || !this.changed) {
            return var;
        }
        final int key = 2 * var + size - 1;
        final int value = (key < this.mapping.length) ? this.mapping[key] : 0;
        if (value == 0) {
            throw new IllegalStateException("Unknown local variable " + var);
        }
        return value - 1;
    }
    
    static {
        OBJECT_TYPE = Type.getObjectType("java/lang/Object");
    }
}
