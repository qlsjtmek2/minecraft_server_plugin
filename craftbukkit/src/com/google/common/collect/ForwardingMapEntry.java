// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import java.util.Map;

@GwtCompatible
public abstract class ForwardingMapEntry<K, V> extends ForwardingObject implements Map.Entry<K, V>
{
    protected abstract Map.Entry<K, V> delegate();
    
    public K getKey() {
        return this.delegate().getKey();
    }
    
    public V getValue() {
        return this.delegate().getValue();
    }
    
    public V setValue(final V value) {
        return this.delegate().setValue(value);
    }
    
    public boolean equals(@Nullable final Object object) {
        return this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object object) {
        if (object instanceof Map.Entry) {
            final Map.Entry<?, ?> that = (Map.Entry<?, ?>)object;
            return Objects.equal(this.getKey(), that.getKey()) && Objects.equal(this.getValue(), that.getValue());
        }
        return false;
    }
    
    @Beta
    protected int standardHashCode() {
        final K k = this.getKey();
        final V v = this.getValue();
        return ((k == null) ? 0 : k.hashCode()) ^ ((v == null) ? 0 : v.hashCode());
    }
    
    @Beta
    protected String standardToString() {
        return this.getKey() + "=" + this.getValue();
    }
}
