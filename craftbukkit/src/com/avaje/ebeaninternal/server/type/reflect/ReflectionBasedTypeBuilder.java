// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarTypeWrapper;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.type.TypeManager;

public class ReflectionBasedTypeBuilder
{
    private final TypeManager typeManager;
    
    public ReflectionBasedTypeBuilder(final TypeManager typeManager) {
        this.typeManager = typeManager;
    }
    
    public ScalarType<?> buildScalarType(final ImmutableMeta meta) {
        if (meta.isCompoundType()) {
            throw new RuntimeException("Must be scalar");
        }
        final Constructor<?> constructor = meta.getConstructor();
        final Class<?> logicalType = constructor.getDeclaringClass();
        final Method[] readers = meta.getReaders();
        final Class<?> returnType = readers[0].getReturnType();
        final ScalarType<?> scalarType = this.typeManager.recursiveCreateScalarTypes(returnType);
        final ReflectionBasedScalarTypeConverter r = new ReflectionBasedScalarTypeConverter(constructor, readers[0]);
        final ScalarTypeWrapper st = new ScalarTypeWrapper((Class<B>)logicalType, (ScalarType<S>)scalarType, r);
        return (ScalarType<?>)st;
    }
    
    public ReflectionBasedCompoundType buildCompound(final ImmutableMeta meta) {
        final Constructor<?> constructor = meta.getConstructor();
        final Method[] readers = meta.getReaders();
        final ReflectionBasedCompoundTypeProperty[] props = new ReflectionBasedCompoundTypeProperty[readers.length];
        for (int i = 0; i < readers.length; ++i) {
            final Class<?> returnType = readers[i].getReturnType();
            this.typeManager.recursiveCreateScalarDataReader(returnType);
            final String name = this.getPropertyName(readers[i]);
            props[i] = new ReflectionBasedCompoundTypeProperty(name, readers[i], returnType);
        }
        return new ReflectionBasedCompoundType(constructor, props);
    }
    
    private String getPropertyName(final Method method) {
        final String name = method.getName();
        if (name.startsWith("is")) {
            return this.lowerFirstChar(name.substring(2));
        }
        if (name.startsWith("get")) {
            return this.lowerFirstChar(name.substring(3));
        }
        final String msg = "Expecting method " + name + " to start with is or get " + " so as to follow bean specification?";
        throw new RuntimeException(msg);
    }
    
    private String lowerFirstChar(final String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
