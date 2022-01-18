// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class ForwardingListMultimap<K, V> extends ForwardingMultimap<K, V> implements ListMultimap<K, V>
{
    protected abstract ListMultimap<K, V> delegate();
    
    public List<V> get(@Nullable final K key) {
        return this.delegate().get(key);
    }
    
    public List<V> removeAll(@Nullable final Object key) {
        return this.delegate().removeAll(key);
    }
    
    public List<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return this.delegate().replaceValues(key, values);
    }
}
