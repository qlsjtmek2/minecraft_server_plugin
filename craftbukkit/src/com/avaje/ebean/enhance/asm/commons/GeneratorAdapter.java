// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import java.util.Arrays;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import java.util.ArrayList;
import com.avaje.ebean.enhance.asm.MethodVisitor;
import java.util.List;
import com.avaje.ebean.enhance.asm.Type;

public class GeneratorAdapter extends LocalVariablesSorter
{
    private static final String CLDESC = "Ljava/lang/Class;";
    private static final Type BYTE_TYPE;
    private static final Type BOOLEAN_TYPE;
    private static final Type SHORT_TYPE;
    private static final Type CHARACTER_TYPE;
    private static final Type INTEGER_TYPE;
    private static final Type FLOAT_TYPE;
    private static final Type LONG_TYPE;
    private static final Type DOUBLE_TYPE;
    private static final Type NUMBER_TYPE;
    private static final Type OBJECT_TYPE;
    private static final Method BOOLEAN_VALUE;
    private static final Method CHAR_VALUE;
    private static final Method INT_VALUE;
    private static final Method FLOAT_VALUE;
    private static final Method LONG_VALUE;
    private static final Method DOUBLE_VALUE;
    public static final int ADD = 96;
    public static final int SUB = 100;
    public static final int MUL = 104;
    public static final int DIV = 108;
    public static final int REM = 112;
    public static final int NEG = 116;
    public static final int SHL = 120;
    public static final int SHR = 122;
    public static final int USHR = 124;
    public static final int AND = 126;
    public static final int OR = 128;
    public static final int XOR = 130;
    public static final int EQ = 153;
    public static final int NE = 154;
    public static final int LT = 155;
    public static final int GE = 156;
    public static final int GT = 157;
    public static final int LE = 158;
    private final int access;
    private final Type returnType;
    private final Type[] argumentTypes;
    private final List localTypes;
    
    public GeneratorAdapter(final MethodVisitor mv, final int access, final String name, final String desc) {
        super(access, desc, mv);
        this.localTypes = new ArrayList();
        this.access = access;
        this.returnType = Type.getReturnType(desc);
        this.argumentTypes = Type.getArgumentTypes(desc);
    }
    
    public GeneratorAdapter(final int access, final Method method, final MethodVisitor mv) {
        super(access, method.getDescriptor(), mv);
        this.localTypes = new ArrayList();
        this.access = access;
        this.returnType = method.getReturnType();
        this.argumentTypes = method.getArgumentTypes();
    }
    
    public GeneratorAdapter(final int access, final Method method, final String signature, final Type[] exceptions, final ClassVisitor cv) {
        this(access, method, cv.visitMethod(access, method.getName(), method.getDescriptor(), signature, getInternalNames(exceptions)));
    }
    
    private static String[] getInternalNames(final Type[] types) {
        if (types == null) {
            return null;
        }
        final String[] names = new String[types.length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = types[i].getInternalName();
        }
        return names;
    }
    
    public void push(final boolean value) {
        this.push(value ? 1 : 0);
    }
    
    public void push(final int value) {
        if (value >= -1 && value <= 5) {
            this.mv.visitInsn(3 + value);
        }
        else if (value >= -128 && value <= 127) {
            this.mv.visitIntInsn(16, value);
        }
        else if (value >= -32768 && value <= 32767) {
            this.mv.visitIntInsn(17, value);
        }
        else {
            this.mv.visitLdcInsn(value);
        }
    }
    
    public void push(final long value) {
        if (value == 0L || value == 1L) {
            this.mv.visitInsn(9 + (int)value);
        }
        else {
            this.mv.visitLdcInsn(value);
        }
    }
    
    public void push(final float value) {
        final int bits = Float.floatToIntBits(value);
        if (bits == 0L || bits == 1065353216 || bits == 1073741824) {
            this.mv.visitInsn(11 + (int)value);
        }
        else {
            this.mv.visitLdcInsn(new Float(value));
        }
    }
    
    public void push(final double value) {
        final long bits = Double.doubleToLongBits(value);
        if (bits == 0L || bits == 4607182418800017408L) {
            this.mv.visitInsn(14 + (int)value);
        }
        else {
            this.mv.visitLdcInsn(new Double(value));
        }
    }
    
    public void push(final String value) {
        if (value == null) {
            this.mv.visitInsn(1);
        }
        else {
            this.mv.visitLdcInsn(value);
        }
    }
    
    public void push(final Type value) {
        if (value == null) {
            this.mv.visitInsn(1);
        }
        else {
            switch (value.getSort()) {
                case 1: {
                    this.mv.visitFieldInsn(178, "java/lang/Boolean", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 2: {
                    this.mv.visitFieldInsn(178, "java/lang/Char", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 3: {
                    this.mv.visitFieldInsn(178, "java/lang/Byte", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 4: {
                    this.mv.visitFieldInsn(178, "java/lang/Short", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 5: {
                    this.mv.visitFieldInsn(178, "java/lang/Integer", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 6: {
                    this.mv.visitFieldInsn(178, "java/lang/Float", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 7: {
                    this.mv.visitFieldInsn(178, "java/lang/Long", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                case 8: {
                    this.mv.visitFieldInsn(178, "java/lang/Double", "TYPE", "Ljava/lang/Class;");
                    break;
                }
                default: {
                    this.mv.visitLdcInsn(value);
                    break;
                }
            }
        }
    }
    
    private int getArgIndex(final int arg) {
        int index = ((this.access & 0x8) == 0x0) ? 1 : 0;
        for (int i = 0; i < arg; ++i) {
            index += this.argumentTypes[i].getSize();
        }
        return index;
    }
    
    private void loadInsn(final Type type, final int index) {
        this.mv.visitVarInsn(type.getOpcode(21), index);
    }
    
    private void storeInsn(final Type type, final int index) {
        this.mv.visitVarInsn(type.getOpcode(54), index);
    }
    
    public void loadThis() {
        if ((this.access & 0x8) != 0x0) {
            throw new IllegalStateException("no 'this' pointer within static method");
        }
        this.mv.visitVarInsn(25, 0);
    }
    
    public void loadArg(final int arg) {
        this.loadInsn(this.argumentTypes[arg], this.getArgIndex(arg));
    }
    
    public void loadArgs(final int arg, final int count) {
        int index = this.getArgIndex(arg);
        for (int i = 0; i < count; ++i) {
            final Type t = this.argumentTypes[arg + i];
            this.loadInsn(t, index);
            index += t.getSize();
        }
    }
    
    public void loadArgs() {
        this.loadArgs(0, this.argumentTypes.length);
    }
    
    public void loadArgArray() {
        this.push(this.argumentTypes.length);
        this.newArray(GeneratorAdapter.OBJECT_TYPE);
        for (int i = 0; i < this.argumentTypes.length; ++i) {
            this.dup();
            this.push(i);
            this.loadArg(i);
            this.box(this.argumentTypes[i]);
            this.arrayStore(GeneratorAdapter.OBJECT_TYPE);
        }
    }
    
    public void storeArg(final int arg) {
        this.storeInsn(this.argumentTypes[arg], this.getArgIndex(arg));
    }
    
    public Type getLocalType(final int local) {
        return this.localTypes.get(local - this.firstLocal);
    }
    
    protected void setLocalType(final int local, final Type type) {
        final int index = local - this.firstLocal;
        while (this.localTypes.size() < index + 1) {
            this.localTypes.add(null);
        }
        this.localTypes.set(index, type);
    }
    
    public void loadLocal(final int local) {
        this.loadInsn(this.getLocalType(local), local);
    }
    
    public void loadLocal(final int local, final Type type) {
        this.setLocalType(local, type);
        this.loadInsn(type, local);
    }
    
    public void storeLocal(final int local) {
        this.storeInsn(this.getLocalType(local), local);
    }
    
    public void storeLocal(final int local, final Type type) {
        this.setLocalType(local, type);
        this.storeInsn(type, local);
    }
    
    public void arrayLoad(final Type type) {
        this.mv.visitInsn(type.getOpcode(46));
    }
    
    public void arrayStore(final Type type) {
        this.mv.visitInsn(type.getOpcode(79));
    }
    
    public void pop() {
        this.mv.visitInsn(87);
    }
    
    public void pop2() {
        this.mv.visitInsn(88);
    }
    
    public void dup() {
        this.mv.visitInsn(89);
    }
    
    public void dup2() {
        this.mv.visitInsn(92);
    }
    
    public void dupX1() {
        this.mv.visitInsn(90);
    }
    
    public void dupX2() {
        this.mv.visitInsn(91);
    }
    
    public void dup2X1() {
        this.mv.visitInsn(93);
    }
    
    public void dup2X2() {
        this.mv.visitInsn(94);
    }
    
    public void swap() {
        this.mv.visitInsn(95);
    }
    
    public void swap(final Type prev, final Type type) {
        if (type.getSize() == 1) {
            if (prev.getSize() == 1) {
                this.swap();
            }
            else {
                this.dupX2();
                this.pop();
            }
        }
        else if (prev.getSize() == 1) {
            this.dup2X1();
            this.pop2();
        }
        else {
            this.dup2X2();
            this.pop2();
        }
    }
    
    public void math(final int op, final Type type) {
        this.mv.visitInsn(type.getOpcode(op));
    }
    
    public void not() {
        this.mv.visitInsn(4);
        this.mv.visitInsn(130);
    }
    
    public void iinc(final int local, final int amount) {
        this.mv.visitIincInsn(local, amount);
    }
    
    public void cast(final Type from, final Type to) {
        if (from != to) {
            if (from == Type.DOUBLE_TYPE) {
                if (to == Type.FLOAT_TYPE) {
                    this.mv.visitInsn(144);
                }
                else if (to == Type.LONG_TYPE) {
                    this.mv.visitInsn(143);
                }
                else {
                    this.mv.visitInsn(142);
                    this.cast(Type.INT_TYPE, to);
                }
            }
            else if (from == Type.FLOAT_TYPE) {
                if (to == Type.DOUBLE_TYPE) {
                    this.mv.visitInsn(141);
                }
                else if (to == Type.LONG_TYPE) {
                    this.mv.visitInsn(140);
                }
                else {
                    this.mv.visitInsn(139);
                    this.cast(Type.INT_TYPE, to);
                }
            }
            else if (from == Type.LONG_TYPE) {
                if (to == Type.DOUBLE_TYPE) {
                    this.mv.visitInsn(138);
                }
                else if (to == Type.FLOAT_TYPE) {
                    this.mv.visitInsn(137);
                }
                else {
                    this.mv.visitInsn(136);
                    this.cast(Type.INT_TYPE, to);
                }
            }
            else if (to == Type.BYTE_TYPE) {
                this.mv.visitInsn(145);
            }
            else if (to == Type.CHAR_TYPE) {
                this.mv.visitInsn(146);
            }
            else if (to == Type.DOUBLE_TYPE) {
                this.mv.visitInsn(135);
            }
            else if (to == Type.FLOAT_TYPE) {
                this.mv.visitInsn(134);
            }
            else if (to == Type.LONG_TYPE) {
                this.mv.visitInsn(133);
            }
            else if (to == Type.SHORT_TYPE) {
                this.mv.visitInsn(147);
            }
        }
    }
    
    public void box(final Type type) {
        if (type.getSort() == 10 || type.getSort() == 9) {
            return;
        }
        if (type == Type.VOID_TYPE) {
            this.push((String)null);
        }
        else {
            Type boxed = type;
            switch (type.getSort()) {
                case 3: {
                    boxed = GeneratorAdapter.BYTE_TYPE;
                    break;
                }
                case 1: {
                    boxed = GeneratorAdapter.BOOLEAN_TYPE;
                    break;
                }
                case 4: {
                    boxed = GeneratorAdapter.SHORT_TYPE;
                    break;
                }
                case 2: {
                    boxed = GeneratorAdapter.CHARACTER_TYPE;
                    break;
                }
                case 5: {
                    boxed = GeneratorAdapter.INTEGER_TYPE;
                    break;
                }
                case 6: {
                    boxed = GeneratorAdapter.FLOAT_TYPE;
                    break;
                }
                case 7: {
                    boxed = GeneratorAdapter.LONG_TYPE;
                    break;
                }
                case 8: {
                    boxed = GeneratorAdapter.DOUBLE_TYPE;
                    break;
                }
            }
            this.newInstance(boxed);
            if (type.getSize() == 2) {
                this.dupX2();
                this.dupX2();
                this.pop();
            }
            else {
                this.dupX1();
                this.swap();
            }
            this.invokeConstructor(boxed, new Method("<init>", Type.VOID_TYPE, new Type[] { type }));
        }
    }
    
    public void unbox(final Type type) {
        Type t = GeneratorAdapter.NUMBER_TYPE;
        Method sig = null;
        switch (type.getSort()) {
            case 0: {
                return;
            }
            case 2: {
                t = GeneratorAdapter.CHARACTER_TYPE;
                sig = GeneratorAdapter.CHAR_VALUE;
                break;
            }
            case 1: {
                t = GeneratorAdapter.BOOLEAN_TYPE;
                sig = GeneratorAdapter.BOOLEAN_VALUE;
                break;
            }
            case 8: {
                sig = GeneratorAdapter.DOUBLE_VALUE;
                break;
            }
            case 6: {
                sig = GeneratorAdapter.FLOAT_VALUE;
                break;
            }
            case 7: {
                sig = GeneratorAdapter.LONG_VALUE;
                break;
            }
            case 3:
            case 4:
            case 5: {
                sig = GeneratorAdapter.INT_VALUE;
                break;
            }
        }
        if (sig == null) {
            this.checkCast(type);
        }
        else {
            this.checkCast(t);
            this.invokeVirtual(t, sig);
        }
    }
    
    public Label newLabel() {
        return new Label();
    }
    
    public void mark(final Label label) {
        this.mv.visitLabel(label);
    }
    
    public Label mark() {
        final Label label = new Label();
        this.mv.visitLabel(label);
        return label;
    }
    
    public void ifCmp(final Type type, final int mode, final Label label) {
        int intOp = -1;
        switch (type.getSort()) {
            case 7: {
                this.mv.visitInsn(148);
                break;
            }
            case 8: {
                this.mv.visitInsn(152);
                break;
            }
            case 6: {
                this.mv.visitInsn(150);
                break;
            }
            case 9:
            case 10: {
                switch (mode) {
                    case 153: {
                        this.mv.visitJumpInsn(165, label);
                        return;
                    }
                    case 154: {
                        this.mv.visitJumpInsn(166, label);
                        return;
                    }
                    default: {
                        throw new IllegalArgumentException("Bad comparison for type " + type);
                    }
                }
                break;
            }
            default: {
                switch (mode) {
                    case 153: {
                        intOp = 159;
                        break;
                    }
                    case 154: {
                        intOp = 160;
                        break;
                    }
                    case 156: {
                        intOp = 162;
                        break;
                    }
                    case 155: {
                        intOp = 161;
                        break;
                    }
                    case 158: {
                        intOp = 164;
                        break;
                    }
                    case 157: {
                        intOp = 163;
                        break;
                    }
                }
                this.mv.visitJumpInsn(intOp, label);
                return;
            }
        }
        int jumpMode = mode;
        switch (mode) {
            case 156: {
                jumpMode = 155;
                break;
            }
            case 158: {
                jumpMode = 157;
                break;
            }
        }
        this.mv.visitJumpInsn(jumpMode, label);
    }
    
    public void ifICmp(final int mode, final Label label) {
        this.ifCmp(Type.INT_TYPE, mode, label);
    }
    
    public void ifZCmp(final int mode, final Label label) {
        this.mv.visitJumpInsn(mode, label);
    }
    
    public void ifNull(final Label label) {
        this.mv.visitJumpInsn(198, label);
    }
    
    public void ifNonNull(final Label label) {
        this.mv.visitJumpInsn(199, label);
    }
    
    public void goTo(final Label label) {
        this.mv.visitJumpInsn(167, label);
    }
    
    public void ret(final int local) {
        this.mv.visitVarInsn(169, local);
    }
    
    public void tableSwitch(final int[] keys, final TableSwitchGenerator generator) {
        float density;
        if (keys.length == 0) {
            density = 0.0f;
        }
        else {
            density = keys.length / (keys[keys.length - 1] - keys[0] + 1);
        }
        this.tableSwitch(keys, generator, density >= 0.5f);
    }
    
    public void tableSwitch(final int[] keys, final TableSwitchGenerator generator, final boolean useTable) {
        for (int i = 1; i < keys.length; ++i) {
            if (keys[i] < keys[i - 1]) {
                throw new IllegalArgumentException("keys must be sorted ascending");
            }
        }
        final Label def = this.newLabel();
        final Label end = this.newLabel();
        if (keys.length > 0) {
            final int len = keys.length;
            final int min = keys[0];
            final int max = keys[len - 1];
            final int range = max - min + 1;
            if (useTable) {
                final Label[] labels = new Label[range];
                Arrays.fill(labels, def);
                for (int j = 0; j < len; ++j) {
                    labels[keys[j] - min] = this.newLabel();
                }
                this.mv.visitTableSwitchInsn(min, max, def, labels);
                for (int j = 0; j < range; ++j) {
                    final Label label = labels[j];
                    if (label != def) {
                        this.mark(label);
                        generator.generateCase(j + min, end);
                    }
                }
            }
            else {
                final Label[] labels = new Label[len];
                for (int j = 0; j < len; ++j) {
                    labels[j] = this.newLabel();
                }
                this.mv.visitLookupSwitchInsn(def, keys, labels);
                for (int j = 0; j < len; ++j) {
                    this.mark(labels[j]);
                    generator.generateCase(keys[j], end);
                }
            }
        }
        this.mark(def);
        generator.generateDefault();
        this.mark(end);
    }
    
    public void returnValue() {
        this.mv.visitInsn(this.returnType.getOpcode(172));
    }
    
    private void fieldInsn(final int opcode, final Type ownerType, final String name, final Type fieldType) {
        this.mv.visitFieldInsn(opcode, ownerType.getInternalName(), name, fieldType.getDescriptor());
    }
    
    public void getStatic(final Type owner, final String name, final Type type) {
        this.fieldInsn(178, owner, name, type);
    }
    
    public void putStatic(final Type owner, final String name, final Type type) {
        this.fieldInsn(179, owner, name, type);
    }
    
    public void getField(final Type owner, final String name, final Type type) {
        this.fieldInsn(180, owner, name, type);
    }
    
    public void putField(final Type owner, final String name, final Type type) {
        this.fieldInsn(181, owner, name, type);
    }
    
    private void invokeInsn(final int opcode, final Type type, final Method method) {
        final String owner = (type.getSort() == 9) ? type.getDescriptor() : type.getInternalName();
        this.mv.visitMethodInsn(opcode, owner, method.getName(), method.getDescriptor());
    }
    
    public void invokeVirtual(final Type owner, final Method method) {
        this.invokeInsn(182, owner, method);
    }
    
    public void invokeConstructor(final Type type, final Method method) {
        this.invokeInsn(183, type, method);
    }
    
    public void invokeStatic(final Type owner, final Method method) {
        this.invokeInsn(184, owner, method);
    }
    
    public void invokeInterface(final Type owner, final Method method) {
        this.invokeInsn(185, owner, method);
    }
    
    private void typeInsn(final int opcode, final Type type) {
        this.mv.visitTypeInsn(opcode, type.getInternalName());
    }
    
    public void newInstance(final Type type) {
        this.typeInsn(187, type);
    }
    
    public void newArray(final Type type) {
        int typ = 0;
        switch (type.getSort()) {
            case 1: {
                typ = 4;
                break;
            }
            case 2: {
                typ = 5;
                break;
            }
            case 3: {
                typ = 8;
                break;
            }
            case 4: {
                typ = 9;
                break;
            }
            case 5: {
                typ = 10;
                break;
            }
            case 6: {
                typ = 6;
                break;
            }
            case 7: {
                typ = 11;
                break;
            }
            case 8: {
                typ = 7;
                break;
            }
            default: {
                this.typeInsn(189, type);
                return;
            }
        }
        this.mv.visitIntInsn(188, typ);
    }
    
    public void arrayLength() {
        this.mv.visitInsn(190);
    }
    
    public void throwException() {
        this.mv.visitInsn(191);
    }
    
    public void throwException(final Type type, final String msg) {
        this.newInstance(type);
        this.dup();
        this.push(msg);
        this.invokeConstructor(type, Method.getMethod("void <init> (String)"));
        this.throwException();
    }
    
    public void checkCast(final Type type) {
        if (!type.equals(GeneratorAdapter.OBJECT_TYPE)) {
            this.typeInsn(192, type);
        }
    }
    
    public void instanceOf(final Type type) {
        this.typeInsn(193, type);
    }
    
    public void monitorEnter() {
        this.mv.visitInsn(194);
    }
    
    public void monitorExit() {
        this.mv.visitInsn(195);
    }
    
    public void endMethod() {
        if ((this.access & 0x400) == 0x0) {
            this.mv.visitMaxs(0, 0);
        }
        this.mv.visitEnd();
    }
    
    public void catchException(final Label start, final Label end, final Type exception) {
        this.mv.visitTryCatchBlock(start, end, this.mark(), exception.getInternalName());
    }
    
    static {
        BYTE_TYPE = Type.getObjectType("java/lang/Byte");
        BOOLEAN_TYPE = Type.getObjectType("java/lang/Boolean");
        SHORT_TYPE = Type.getObjectType("java/lang/Short");
        CHARACTER_TYPE = Type.getObjectType("java/lang/Character");
        INTEGER_TYPE = Type.getObjectType("java/lang/Integer");
        FLOAT_TYPE = Type.getObjectType("java/lang/Float");
        LONG_TYPE = Type.getObjectType("java/lang/Long");
        DOUBLE_TYPE = Type.getObjectType("java/lang/Double");
        NUMBER_TYPE = Type.getObjectType("java/lang/Number");
        OBJECT_TYPE = Type.getObjectType("java/lang/Object");
        BOOLEAN_VALUE = Method.getMethod("boolean booleanValue()");
        CHAR_VALUE = Method.getMethod("char charValue()");
        INT_VALUE = Method.getMethod("int intValue()");
        FLOAT_VALUE = Method.getMethod("float floatValue()");
        LONG_VALUE = Method.getMethod("long longValue()");
        DOUBLE_VALUE = Method.getMethod("double doubleValue()");
    }
}
