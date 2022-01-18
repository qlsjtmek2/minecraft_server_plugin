// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V>
{
    final transient ImmutableMap<K, V> delegate;
    final transient ImmutableBiMap<V, K> inverse;
    
    RegularImmutableBiMap(final ImmutableMap<K, V> delegate) {
        this.delegate = delegate;
        final ImmutableMap.Builder<V, K> builder = ImmutableMap.builder();
        for (final Map.Entry<K, V> entry : delegate.entrySet()) {
            builder.put(entry.getValue(), entry.getKey());
        }
        final ImmutableMap<V, K> backwardMap = builder.build();
        this.inverse = (ImmutableBiMap<V, K>)new RegularImmutableBiMap((ImmutableMap<Object, Object>)backwardMap, (ImmutableBiMap<Object, Object>)this);
    }
    
    RegularImmutableBiMap(final ImmutableMap<K, V> delegate, final ImmutableBiMap<V, K> inverse) {
        this.delegate = delegate;
        this.inverse = inverse;
    }
    
    ImmutableMap<K, V> delegate() {
        return this.delegate;
    }
    
    public ImmutableBiMap<V, K> inverse() {
        return this.inverse;
    }
    
    boolean isPartialView() {
        return this.delegate.isPartialView() || this.inverse.delegate().isPartialView();
    }
}
