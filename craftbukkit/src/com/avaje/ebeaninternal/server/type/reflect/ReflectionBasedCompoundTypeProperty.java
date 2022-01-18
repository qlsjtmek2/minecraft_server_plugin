// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Method;
import com.avaje.ebean.config.CompoundTypeProperty;

public class ReflectionBasedCompoundTypeProperty implements CompoundTypeProperty
{
    private static final Object[] NO_ARGS;
    private final Method reader;
    private final String name;
    private final Class<?> propertyType;
    
    public ReflectionBasedCompoundTypeProperty(final String name, final Method reader, final Class<?> propertyType) {
        this.name = name;
        this.reader = reader;
        this.propertyType = propertyType;
    }
    
    public String toString() {
        return this.name;
    }
    
    public int getDbType() {
        return 0;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Object getValue(final Object valueObject) {
        try {
            return this.reader.invoke(valueObject, ReflectionBasedCompoundTypeProperty.NO_ARGS);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public Class<?> getPropertyType() {
        return this.propertyType;
    }
    
    static {
        NO_ARGS = new Object[0];
    }
}
