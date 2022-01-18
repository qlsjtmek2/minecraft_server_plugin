// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class EmptyImmutableMap extends ImmutableMap<Object, Object>
{
    static final EmptyImmutableMap INSTANCE;
    private static final long serialVersionUID = 0L;
    
    public Object get(@Nullable final Object key) {
        return null;
    }
    
    public int size() {
        return 0;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return false;
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return false;
    }
    
    public ImmutableSet<Map.Entry<Object, Object>> entrySet() {
        return ImmutableSet.of();
    }
    
    public ImmutableSet<Object> keySet() {
        return ImmutableSet.of();
    }
    
    public ImmutableCollection<Object> values() {
        return ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION;
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Map) {
            final Map<?, ?> that = (Map<?, ?>)object;
            return that.isEmpty();
        }
        return false;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public int hashCode() {
        return 0;
    }
    
    public String toString() {
        return "{}";
    }
    
    Object readResolve() {
        return EmptyImmutableMap.INSTANCE;
    }
    
    static {
        INSTANCE = new EmptyImmutableMap();
    }
}
