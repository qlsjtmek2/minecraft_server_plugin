// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParamTypeHelper
{
    public static TypeInfo getTypeInfo(final Type genericType) {
        if (genericType instanceof ParameterizedType) {
            return getParamTypeInfo((ParameterizedType)genericType);
        }
        final Class<?> entityType = getBeanType(genericType);
        if (entityType != null) {
            return new TypeInfo(ManyType.NONE, (Class)entityType);
        }
        return null;
    }
    
    private static TypeInfo getParamTypeInfo(final ParameterizedType paramType) {
        final Type rawType = paramType.getRawType();
        final ManyType manyType = getManyType(rawType);
        if (ManyType.NONE.equals(manyType)) {
            return null;
        }
        final Type[] typeArguments = paramType.getActualTypeArguments();
        if (typeArguments.length == 1) {
            final Type argType = typeArguments[0];
            final Class<?> beanType = getBeanType(argType);
            if (beanType != null) {
                return new TypeInfo(manyType, (Class)beanType);
            }
        }
        return null;
    }
    
    private static Class<?> getBeanType(final Type argType) {
        if (argType instanceof Class) {
            return (Class<?>)argType;
        }
        return null;
    }
    
    private static ManyType getManyType(final Type rawType) {
        if (List.class.equals(rawType)) {
            return ManyType.LIST;
        }
        if (Set.class.equals(rawType)) {
            return ManyType.SET;
        }
        if (Map.class.equals(rawType)) {
            return ManyType.MAP;
        }
        return ManyType.NONE;
    }
    
    public enum ManyType
    {
        LIST, 
        SET, 
        MAP, 
        NONE;
    }
    
    public static class TypeInfo
    {
        private final ManyType manyType;
        private final Class<?> beanType;
        
        private TypeInfo(final ManyType manyType, final Class<?> beanType) {
            this.manyType = manyType;
            this.beanType = beanType;
        }
        
        public boolean isManyType() {
            return !ManyType.NONE.equals(this.manyType);
        }
        
        public ManyType getManyType() {
            return this.manyType;
        }
        
        public Class<?> getBeanType() {
            return this.beanType;
        }
        
        public String toString() {
            if (this.isManyType()) {
                return this.manyType + " " + this.beanType;
            }
            return this.beanType.toString();
        }
    }
}
