// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeReflectHelper
{
    public static Class<?>[] getParams(final Class<?> cls, final Class<?> matchRawType) {
        final Type[] types = getParamType(cls, matchRawType);
        final Class<?>[] result = (Class<?>[])new Class[types.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = getClass(types[i]);
        }
        return result;
    }
    
    public static Class<?> getClass(final Type type) {
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType)type).getRawType());
        }
        return (Class<?>)type;
    }
    
    private static Type[] getParamType(final Class<?> cls, final Class<?> matchRawType) {
        final Type[] gis = cls.getGenericInterfaces();
        for (int i = 0; i < gis.length; ++i) {
            final Type type = gis[i];
            if (type instanceof ParameterizedType) {
                final ParameterizedType paramType = (ParameterizedType)type;
                final Type rawType = paramType.getRawType();
                if (rawType.equals(matchRawType)) {
                    return paramType.getActualTypeArguments();
                }
            }
        }
        return null;
    }
}
