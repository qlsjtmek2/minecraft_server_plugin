// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Map;
import com.google.common.annotations.Beta;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.util.SortedMap;

@GwtCompatible
public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V>
{
    protected abstract SortedMap<K, V> delegate();
    
    public Comparator<? super K> comparator() {
        return this.delegate().comparator();
    }
    
    public K firstKey() {
        return this.delegate().firstKey();
    }
    
    public SortedMap<K, V> headMap(final K toKey) {
        return this.delegate().headMap(toKey);
    }
    
    public K lastKey() {
        return this.delegate().lastKey();
    }
    
    public SortedMap<K, V> subMap(final K fromKey, final K toKey) {
        return this.delegate().subMap(fromKey, toKey);
    }
    
    public SortedMap<K, V> tailMap(final K fromKey) {
        return this.delegate().tailMap(fromKey);
    }
    
    private int unsafeCompare(final Object k1, final Object k2) {
        final Comparator<? super K> comparator = this.comparator();
        if (comparator == null) {
            return ((Comparable)k1).compareTo(k2);
        }
        return comparator.compare((Object)k1, (Object)k2);
    }
    
    @Beta
    protected boolean standardContainsKey(@Nullable final Object key) {
        try {
            final Object ceilingKey = super.tailMap((K)key).firstKey();
            return this.unsafeCompare(ceilingKey, key) == 0;
        }
        catch (ClassCastException e) {
            return false;
        }
        catch (NoSuchElementException e2) {
            return false;
        }
        catch (NullPointerException e3) {
            return false;
        }
    }
    
    @Beta
    protected V standardRemove(@Nullable final Object key) {
        try {
            final Iterator<Map.Entry<Object, V>> entryIterator = (Iterator<Map.Entry<Object, V>>)super.tailMap((K)key).entrySet().iterator();
            if (entryIterator.hasNext()) {
                final Map.Entry<Object, V> ceilingEntry = entryIterator.next();
                if (this.unsafeCompare(ceilingEntry.getKey(), key) == 0) {
                    final V value = ceilingEntry.getValue();
                    entryIterator.remove();
                    return value;
                }
            }
        }
        catch (ClassCastException e) {
            return null;
        }
        catch (NullPointerException e2) {
            return null;
        }
        return null;
    }
    
    @Beta
    protected SortedMap<K, V> standardSubMap(final K fromKey, final K toKey) {
        return this.tailMap(fromKey).headMap(toKey);
    }
}
