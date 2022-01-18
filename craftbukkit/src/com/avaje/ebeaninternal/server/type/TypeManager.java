// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;

public interface TypeManager
{
    CheckImmutableResponse checkImmutable(final Class<?> p0);
    
    ScalarDataReader<?> recursiveCreateScalarDataReader(final Class<?> p0);
    
    ScalarType<?> recursiveCreateScalarTypes(final Class<?> p0);
    
    void add(final ScalarType<?> p0);
    
    CtCompoundType<?> getCompoundType(final Class<?> p0);
    
    ScalarType<?> getScalarType(final int p0);
    
     <T> ScalarType<T> getScalarType(final Class<T> p0);
    
     <T> ScalarType<T> getScalarType(final Class<T> p0, final int p1);
    
    ScalarType<?> createEnumScalarType(final Class<?> p0);
}
