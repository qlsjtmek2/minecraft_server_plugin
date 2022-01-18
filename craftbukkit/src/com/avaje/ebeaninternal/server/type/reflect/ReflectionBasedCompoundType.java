// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import com.avaje.ebean.config.CompoundTypeProperty;
import java.util.Arrays;
import java.lang.reflect.Constructor;
import com.avaje.ebean.config.CompoundType;

public class ReflectionBasedCompoundType implements CompoundType
{
    private final Constructor<?> constructor;
    private final ReflectionBasedCompoundTypeProperty[] props;
    
    public ReflectionBasedCompoundType(final Constructor<?> constructor, final ReflectionBasedCompoundTypeProperty[] props) {
        this.constructor = constructor;
        this.props = props;
    }
    
    public String toString() {
        return "ReflectionBasedCompoundType " + this.constructor + " " + Arrays.toString(this.props);
    }
    
    public Object create(final Object[] propertyValues) {
        try {
            return this.constructor.newInstance(propertyValues);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public CompoundTypeProperty[] getProperties() {
        return this.props;
    }
    
    public Class<?> getPropertyType(final int i) {
        return this.props[i].getPropertyType();
    }
    
    public Class<?> getCompoundType() {
        return this.constructor.getDeclaringClass();
    }
}
