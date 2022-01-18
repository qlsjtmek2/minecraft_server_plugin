// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Objects;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import java.util.Map;

@GwtCompatible
abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V>
{
    public abstract K getKey();
    
    public abstract V getValue();
    
    public V setValue(final V value) {
        throw new UnsupportedOperationException();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Map.Entry) {
            final Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
            return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
        }
        return false;
    }
    
    public int hashCode() {
        final K k = this.getKey();
        final V v = this.getValue();
        return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
    }
    
    public String toString() {
        return this.getKey() + "=" + this.getValue();
    }
}
