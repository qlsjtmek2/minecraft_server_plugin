// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.asm.commons;

import java.util.HashMap;
import com.avaje.ebean.enhance.asm.Type;
import java.util.Map;

public class Method
{
    private final String name;
    private final String desc;
    private static final Map DESCRIPTORS;
    
    public Method(final String name, final String desc) {
        this.name = name;
        this.desc = desc;
    }
    
    public Method(final String name, final Type returnType, final Type[] argumentTypes) {
        this(name, Type.getMethodDescriptor(returnType, argumentTypes));
    }
    
    public static Method getMethod(final String method) throws IllegalArgumentException {
        return getMethod(method, false);
    }
    
    public static Method getMethod(final String method, final boolean defaultPackage) throws IllegalArgumentException {
        final int space = method.indexOf(32);
        int start = method.indexOf(40, space) + 1;
        final int end = method.indexOf(41, start);
        if (space == -1 || start == -1 || end == -1) {
            throw new IllegalArgumentException();
        }
        final String returnType = method.substring(0, space);
        final String methodName = method.substring(space + 1, start - 1).trim();
        final StringBuffer sb = new StringBuffer();
        sb.append('(');
        int p;
        do {
            p = method.indexOf(44, start);
            String s;
            if (p == -1) {
                s = map(method.substring(start, end).trim(), defaultPackage);
            }
            else {
                s = map(method.substring(start, p).trim(), defaultPackage);
                start = p + 1;
            }
            sb.append(s);
        } while (p != -1);
        sb.append(')');
        sb.append(map(returnType, defaultPackage));
        return new Method(methodName, sb.toString());
    }
    
    private static String map(final String type, final boolean defaultPackage) {
        if ("".equals(type)) {
            return type;
        }
        final StringBuffer sb = new StringBuffer();
        int index = 0;
        while ((index = type.indexOf("[]", index) + 1) > 0) {
            sb.append('[');
        }
        final String t = type.substring(0, type.length() - sb.length() * 2);
        final String desc = Method.DESCRIPTORS.get(t);
        if (desc != null) {
            sb.append(desc);
        }
        else {
            sb.append('L');
            if (t.indexOf(46) < 0) {
                if (!defaultPackage) {
                    sb.append("java/lang/");
                }
                sb.append(t);
            }
            else {
                sb.append(t.replace('.', '/'));
            }
            sb.append(';');
        }
        return sb.toString();
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescriptor() {
        return this.desc;
    }
    
    public Type getReturnType() {
        return Type.getReturnType(this.desc);
    }
    
    public Type[] getArgumentTypes() {
        return Type.getArgumentTypes(this.desc);
    }
    
    public String toString() {
        return this.name + this.desc;
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof Method)) {
            return false;
        }
        final Method other = (Method)o;
        return this.name.equals(other.name) && this.desc.equals(other.desc);
    }
    
    public int hashCode() {
        return this.name.hashCode() ^ this.desc.hashCode();
    }
    
    static {
        (DESCRIPTORS = new HashMap()).put("void", "V");
        Method.DESCRIPTORS.put("byte", "B");
        Method.DESCRIPTORS.put("char", "C");
        Method.DESCRIPTORS.put("double", "D");
        Method.DESCRIPTORS.put("float", "F");
        Method.DESCRIPTORS.put("int", "I");
        Method.DESCRIPTORS.put("long", "J");
        Method.DESCRIPTORS.put("short", "S");
        Method.DESCRIPTORS.put("boolean", "Z");
    }
}
