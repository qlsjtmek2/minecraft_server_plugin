// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import com.avaje.ebean.enhance.asm.Type;

public class PrimitiveHelper
{
    private static Type INTEGER_OBJECT;
    private static Type SHORT_OBJECT;
    private static Type CHARACTER_OBJECT;
    private static Type LONG_OBJECT;
    private static Type DOUBLE_OBJECT;
    private static Type FLOAT_OBJECT;
    private static Type BYTE_OBJECT;
    private static Type BOOLEAN_OBJECT;
    
    public static Type getObjectWrapper(final Type primativeAsmType) {
        final int sort = primativeAsmType.getSort();
        switch (sort) {
            case 5: {
                return PrimitiveHelper.INTEGER_OBJECT;
            }
            case 4: {
                return PrimitiveHelper.SHORT_OBJECT;
            }
            case 2: {
                return PrimitiveHelper.CHARACTER_OBJECT;
            }
            case 7: {
                return PrimitiveHelper.LONG_OBJECT;
            }
            case 8: {
                return PrimitiveHelper.DOUBLE_OBJECT;
            }
            case 6: {
                return PrimitiveHelper.FLOAT_OBJECT;
            }
            case 3: {
                return PrimitiveHelper.BYTE_OBJECT;
            }
            case 1: {
                return PrimitiveHelper.BOOLEAN_OBJECT;
            }
            default: {
                throw new RuntimeException("Expected primative? " + primativeAsmType);
            }
        }
    }
    
    static {
        PrimitiveHelper.INTEGER_OBJECT = Type.getType(Integer.class);
        PrimitiveHelper.SHORT_OBJECT = Type.getType(Short.class);
        PrimitiveHelper.CHARACTER_OBJECT = Type.getType(Character.class);
        PrimitiveHelper.LONG_OBJECT = Type.getType(Long.class);
        PrimitiveHelper.DOUBLE_OBJECT = Type.getType(Double.class);
        PrimitiveHelper.FLOAT_OBJECT = Type.getType(Float.class);
        PrimitiveHelper.BYTE_OBJECT = Type.getType(Byte.class);
        PrimitiveHelper.BOOLEAN_OBJECT = Type.getType(Boolean.class);
    }
}
