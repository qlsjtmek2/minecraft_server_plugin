// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import com.avaje.ebean.config.ScalarTypeConverter;

public class ReflectionBasedScalarTypeConverter implements ScalarTypeConverter
{
    private static final Object[] NO_ARGS;
    private final Constructor<?> constructor;
    private final Method reader;
    
    public ReflectionBasedScalarTypeConverter(final Constructor<?> constructor, final Method reader) {
        this.constructor = constructor;
        this.reader = reader;
    }
    
    public Object getNullValue() {
        return null;
    }
    
    public Object unwrapValue(final Object beanType) {
        if (beanType == null) {
            return null;
        }
        try {
            return this.reader.invoke(beanType, ReflectionBasedScalarTypeConverter.NO_ARGS);
        }
        catch (Exception e) {
            final String msg = "Error invoking read method " + this.reader.getName() + " on " + beanType.getClass().getName();
            throw new RuntimeException(msg);
        }
    }
    
    public Object wrapValue(final Object scalarType) {
        try {
            return this.constructor.newInstance(scalarType);
        }
        catch (Exception e) {
            final String msg = "Error invoking constructor " + this.constructor + " with " + scalarType;
            throw new RuntimeException(msg);
        }
    }
    
    static {
        NO_ARGS = new Object[0];
    }
}
