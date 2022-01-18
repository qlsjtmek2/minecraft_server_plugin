// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParamTypeUtil
{
    public static Class<?> findParamType(final Class<?> cls, final Class<?> matchType) {
        Type paramType = matchByInterfaces(cls, matchType);
        if (paramType == null) {
            final Type genericSuperclass = cls.getGenericSuperclass();
            if (genericSuperclass != null) {
                paramType = matchParamType(genericSuperclass, matchType);
            }
        }
        if (paramType instanceof Class) {
            return (Class<?>)paramType;
        }
        return null;
    }
    
    private static Type matchParamType(final Type type, final Class<?> matchType) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType pt = (ParameterizedType)type;
            final Type rawType = pt.getRawType();
            final boolean isAssignable = matchType.isAssignableFrom((Class<?>)rawType);
            if (isAssignable) {
                final Type[] typeArguments = pt.getActualTypeArguments();
                if (typeArguments.length != 1) {
                    final String m = "Expecting only 1 generic paramater but got " + typeArguments.length + " for " + type;
                    throw new RuntimeException(m);
                }
                return typeArguments[0];
            }
        }
        return null;
    }
    
    private static Type matchByInterfaces(final Class<?> cls, final Class<?> matchType) {
        final Type[] gis = cls.getGenericInterfaces();
        for (int i = 0; i < gis.length; ++i) {
            final Type match = matchParamType(gis[i], matchType);
            if (match != null) {
                return match;
            }
        }
        return null;
    }
}
