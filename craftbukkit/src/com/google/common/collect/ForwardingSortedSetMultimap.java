// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import java.util.Comparator;
import java.util.SortedSet;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class ForwardingSortedSetMultimap<K, V> extends ForwardingSetMultimap<K, V> implements SortedSetMultimap<K, V>
{
    protected abstract SortedSetMultimap<K, V> delegate();
    
    public SortedSet<V> get(@Nullable final K key) {
        return this.delegate().get(key);
    }
    
    public SortedSet<V> removeAll(@Nullable final Object key) {
        return this.delegate().removeAll(key);
    }
    
    public SortedSet<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return this.delegate().replaceValues(key, values);
    }
    
    public Comparator<? super V> valueComparator() {
        return this.delegate().valueComparator();
    }
}
