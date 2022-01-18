// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type
{
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final Type VOID_TYPE;
    public static final Type BOOLEAN_TYPE;
    public static final Type CHAR_TYPE;
    public static final Type BYTE_TYPE;
    public static final Type SHORT_TYPE;
    public static final Type INT_TYPE;
    public static final Type FLOAT_TYPE;
    public static final Type LONG_TYPE;
    public static final Type DOUBLE_TYPE;
    private final int sort;
    private final char[] buf;
    private final int off;
    private final int len;
    
    private Type(final int sort) {
        this(sort, null, 0, 1);
    }
    
    private Type(final int sort, final char[] buf, final int off, final int len) {
        this.sort = sort;
        this.buf = buf;
        this.off = off;
        this.len = len;
    }
    
    public static Type getType(final String typeDescriptor) {
        return getType(typeDescriptor.toCharArray(), 0);
    }
    
    public static Type getObjectType(final String internalName) {
        final char[] buf = internalName.toCharArray();
        return new Type((buf[0] == '[') ? 9 : 10, buf, 0, buf.length);
    }
    
    public static Type getType(final Class c) {
        if (!c.isPrimitive()) {
            return getType(getDescriptor(c));
        }
        if (c == Integer.TYPE) {
            return Type.INT_TYPE;
        }
        if (c == Void.TYPE) {
            return Type.VOID_TYPE;
        }
        if (c == Boolean.TYPE) {
            return Type.BOOLEAN_TYPE;
        }
        if (c == Byte.TYPE) {
            return Type.BYTE_TYPE;
        }
        if (c == Character.TYPE) {
            return Type.CHAR_TYPE;
        }
        if (c == Short.TYPE) {
            return Type.SHORT_TYPE;
        }
        if (c == Double.TYPE) {
            return Type.DOUBLE_TYPE;
        }
        if (c == Float.TYPE) {
            return Type.FLOAT_TYPE;
        }
        return Type.LONG_TYPE;
    }
    
    public static Type[] getArgumentTypes(final String methodDescriptor) {
        final char[] buf = methodDescriptor.toCharArray();
        int off = 1;
        int size = 0;
        while (true) {
            final char car = buf[off++];
            if (car == ')') {
                break;
            }
            if (car == 'L') {
                while (buf[off++] != ';') {}
                ++size;
            }
            else {
                if (car == '[') {
                    continue;
                }
                ++size;
            }
        }
        Type[] args;
        for (args = new Type[size], off = 1, size = 0; buf[off] != ')'; off += args[size].len + ((args[size].sort == 10) ? 2 : 0), ++size) {
            args[size] = getType(buf, off);
        }
        return args;
    }
    
    public static Type[] getArgumentTypes(final Method method) {
        final Class[] classes = method.getParameterTypes();
        final Type[] types = new Type[classes.length];
        for (int i = classes.length - 1; i >= 0; --i) {
            types[i] = getType(classes[i]);
        }
        return types;
    }
    
    public static Type getReturnType(final String methodDescriptor) {
        final char[] buf = methodDescriptor.toCharArray();
        return getType(buf, methodDescriptor.indexOf(41) + 1);
    }
    
    public static Type getReturnType(final Method method) {
        return getType(method.getReturnType());
    }
    
    private static Type getType(final char[] buf, final int off) {
        switch (buf[off]) {
            case 'V': {
                return Type.VOID_TYPE;
            }
            case 'Z': {
                return Type.BOOLEAN_TYPE;
            }
            case 'C': {
                return Type.CHAR_TYPE;
            }
            case 'B': {
                return Type.BYTE_TYPE;
            }
            case 'S': {
                return Type.SHORT_TYPE;
            }
            case 'I': {
                return Type.INT_TYPE;
            }
            case 'F': {
                return Type.FLOAT_TYPE;
            }
            case 'J': {
                return Type.LONG_TYPE;
            }
            case 'D': {
                return Type.DOUBLE_TYPE;
            }
            case '[': {
                int len;
                for (len = 1; buf[off + len] == '['; ++len) {}
                if (buf[off + len] == 'L') {
                    ++len;
                    while (buf[off + len] != ';') {
                        ++len;
                    }
                }
                return new Type(9, buf, off, len + 1);
            }
            default: {
                int len;
                for (len = 1; buf[off + len] != ';'; ++len) {}
                return new Type(10, buf, off + 1, len - 1);
            }
        }
    }
    
    public int getSort() {
        return this.sort;
    }
    
    public int getDimensions() {
        int i;
        for (i = 1; this.buf[this.off + i] == '['; ++i) {}
        return i;
    }
    
    public Type getElementType() {
        return getType(this.buf, this.off + this.getDimensions());
    }
    
    public String getClassName() {
        switch (this.sort) {
            case 0: {
                return "void";
            }
            case 1: {
                return "boolean";
            }
            case 2: {
                return "char";
            }
            case 3: {
                return "byte";
            }
            case 4: {
                return "short";
            }
            case 5: {
                return "int";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "long";
            }
            case 8: {
                return "double";
            }
            case 9: {
                final StringBuffer b = new StringBuffer(this.getElementType().getClassName());
                for (int i = this.getDimensions(); i > 0; --i) {
                    b.append("[]");
                }
                return b.toString();
            }
            default: {
                return new String(this.buf, this.off, this.len).replace('/', '.');
            }
        }
    }
    
    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }
    
    public String getDescriptor() {
        final StringBuffer buf = new StringBuffer();
        this.getDescriptor(buf);
        return buf.toString();
    }
    
    public static String getMethodDescriptor(final Type returnType, final Type[] argumentTypes) {
        final StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < argumentTypes.length; ++i) {
            argumentTypes[i].getDescriptor(buf);
        }
        buf.append(')');
        returnType.getDescriptor(buf);
        return buf.toString();
    }
    
    private void getDescriptor(final StringBuffer buf) {
        switch (this.sort) {
            case 0: {
                buf.append('V');
            }
            case 1: {
                buf.append('Z');
            }
            case 2: {
                buf.append('C');
            }
            case 3: {
                buf.append('B');
            }
            case 4: {
                buf.append('S');
            }
            case 5: {
                buf.append('I');
            }
            case 6: {
                buf.append('F');
            }
            case 7: {
                buf.append('J');
            }
            case 8: {
                buf.append('D');
            }
            case 9: {
                buf.append(this.buf, this.off, this.len);
            }
            default: {
                buf.append('L');
                buf.append(this.buf, this.off, this.len);
                buf.append(';');
            }
        }
    }
    
    public static String getInternalName(final Class c) {
        return c.getName().replace('.', '/');
    }
    
    public static String getDescriptor(final Class c) {
        final StringBuffer buf = new StringBuffer();
        getDescriptor(buf, c);
        return buf.toString();
    }
    
    public static String getConstructorDescriptor(final Constructor c) {
        final Class[] parameters = c.getParameterTypes();
        final StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }
        return buf.append(")V").toString();
    }
    
    public static String getMethodDescriptor(final Method m) {
        final Class[] parameters = m.getParameterTypes();
        final StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            getDescriptor(buf, parameters[i]);
        }
        buf.append(')');
        getDescriptor(buf, m.getReturnType());
        return buf.toString();
    }
    
    private static void getDescriptor(final StringBuffer buf, final Class c) {
        Class d;
        for (d = c; !d.isPrimitive(); d = d.getComponentType()) {
            if (!d.isArray()) {
                buf.append('L');
                final String name = d.getName();
                for (int len = name.length(), i = 0; i < len; ++i) {
                    final char car = name.charAt(i);
                    buf.append((car == '.') ? '/' : car);
                }
                buf.append(';');
                return;
            }
            buf.append('[');
        }
        char car2;
        if (d == Integer.TYPE) {
            car2 = 'I';
        }
        else if (d == Void.TYPE) {
            car2 = 'V';
        }
        else if (d == Boolean.TYPE) {
            car2 = 'Z';
        }
        else if (d == Byte.TYPE) {
            car2 = 'B';
        }
        else if (d == Character.TYPE) {
            car2 = 'C';
        }
        else if (d == Short.TYPE) {
            car2 = 'S';
        }
        else if (d == Double.TYPE) {
            car2 = 'D';
        }
        else if (d == Float.TYPE) {
            car2 = 'F';
        }
        else {
            car2 = 'J';
        }
        buf.append(car2);
    }
    
    public int getSize() {
        return (this.sort == 7 || this.sort == 8) ? 2 : 1;
    }
    
    public int getOpcode(final int opcode) {
        if (opcode == 46 || opcode == 79) {
            switch (this.sort) {
                case 1:
                case 3: {
                    return opcode + 5;
                }
                case 2: {
                    return opcode + 6;
                }
                case 4: {
                    return opcode + 7;
                }
                case 5: {
                    return opcode;
                }
                case 6: {
                    return opcode + 2;
                }
                case 7: {
                    return opcode + 1;
                }
                case 8: {
                    return opcode + 3;
                }
                default: {
                    return opcode + 4;
                }
            }
        }
        else {
            switch (this.sort) {
                case 0: {
                    return opcode + 5;
                }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5: {
                    return opcode;
                }
                case 6: {
                    return opcode + 2;
                }
                case 7: {
                    return opcode + 1;
                }
                case 8: {
                    return opcode + 3;
                }
                default: {
                    return opcode + 4;
                }
            }
        }
    }
    
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        final Type t = (Type)o;
        if (this.sort != t.sort) {
            return false;
        }
        if (this.sort == 10 || this.sort == 9) {
            if (this.len != t.len) {
                return false;
            }
            for (int i = this.off, j = t.off, end = i + this.len; i < end; ++i, ++j) {
                if (this.buf[i] != t.buf[j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int hashCode() {
        int hc = 13 * this.sort;
        if (this.sort == 10 || this.sort == 9) {
            for (int i = this.off, end = i + this.len; i < end; ++i) {
                hc = 17 * (hc + this.buf[i]);
            }
        }
        return hc;
    }
    
    public String toString() {
        return this.getDescriptor();
    }
    
    static {
        VOID_TYPE = new Type(0);
        BOOLEAN_TYPE = new Type(1);
        CHAR_TYPE = new Type(2);
        BYTE_TYPE = new Type(3);
        SHORT_TYPE = new Type(4);
        INT_TYPE = new Type(5);
        FLOAT_TYPE = new Type(6);
        LONG_TYPE = new Type(7);
        DOUBLE_TYPE = new Type(8);
    }
}
