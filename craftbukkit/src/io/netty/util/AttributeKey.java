// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.ConcurrentMap;

public final class AttributeKey<T> extends UniqueName
{
    private static final ConcurrentMap<String, Boolean> names;
    
    public AttributeKey(final String name) {
        super(AttributeKey.names, name, new Object[0]);
    }
    
    static {
        names = PlatformDependent.newConcurrentHashMap();
    }
}
