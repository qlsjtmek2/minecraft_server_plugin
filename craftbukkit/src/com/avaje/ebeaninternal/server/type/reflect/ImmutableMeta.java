// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

public class ImmutableMeta
{
    private final Constructor<?> constructor;
    private final Method[] readers;
    
    public ImmutableMeta(final Constructor<?> constructor, final Method[] readers) {
        this.constructor = constructor;
        this.readers = readers;
    }
    
    public Constructor<?> getConstructor() {
        return this.constructor;
    }
    
    public Method[] getReaders() {
        return this.readers;
    }
    
    public boolean isCompoundType() {
        return this.readers.length > 1;
    }
}
