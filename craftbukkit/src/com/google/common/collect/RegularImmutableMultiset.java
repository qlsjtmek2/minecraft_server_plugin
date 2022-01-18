// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import java.util.Map;
import java.util.Iterator;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E>
{
    private final transient ImmutableMap<E, Integer> map;
    private final transient int size;
    
    RegularImmutableMultiset(final ImmutableMap<E, Integer> map, final int size) {
        this.map = map;
        this.size = size;
    }
    
    boolean isPartialView() {
        return this.map.isPartialView();
    }
    
    public int count(@Nullable final Object element) {
        final Integer value = this.map.get(element);
        return (value == null) ? 0 : value;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean contains(@Nullable final Object element) {
        return this.map.containsKey(element);
    }
    
    public ImmutableSet<E> elementSet() {
        return this.map.keySet();
    }
    
    UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
        final Iterator<Map.Entry<E, Integer>> mapIterator = this.map.entrySet().iterator();
        return new UnmodifiableIterator<Multiset.Entry<E>>() {
            public boolean hasNext() {
                return mapIterator.hasNext();
            }
            
            public Multiset.Entry<E> next() {
                final Map.Entry<E, Integer> mapEntry = mapIterator.next();
                return Multisets.immutableEntry(mapEntry.getKey(), mapEntry.getValue());
            }
        };
    }
    
    public int hashCode() {
        return this.map.hashCode();
    }
    
    int distinctElements() {
        return this.map.size();
    }
}
