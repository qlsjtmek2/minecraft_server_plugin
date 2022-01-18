// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TObjectLongIterator;
import gnu.trove.procedure.TObjectLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.TCollections;
import java.util.Collections;
import java.util.Map;
import gnu.trove.TLongCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectLongMap;

public class TUnmodifiableObjectLongMap<K> implements TObjectLongMap<K>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TObjectLongMap<K> m;
    private transient Set<K> keySet;
    private transient TLongCollection values;
    
    public TUnmodifiableObjectLongMap(final TObjectLongMap<K> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
    }
    
    public int size() {
        return this.m.size();
    }
    
    public boolean isEmpty() {
        return this.m.isEmpty();
    }
    
    public boolean containsKey(final Object key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final long val) {
        return this.m.containsValue(val);
    }
    
    public long get(final Object key) {
        return this.m.get(key);
    }
    
    public long put(final K key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public long remove(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TObjectLongMap<? extends K> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends K, ? extends Long> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public Set<K> keySet() {
        if (this.keySet == null) {
            this.keySet = Collections.unmodifiableSet((Set<? extends K>)this.m.keySet());
        }
        return this.keySet;
    }
    
    public Object[] keys() {
        return this.m.keys();
    }
    
    public K[] keys(final K[] array) {
        return this.m.keys(array);
    }
    
    public TLongCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public long[] values() {
        return this.m.values();
    }
    
    public long[] values(final long[] array) {
        return this.m.values(array);
    }
    
    public boolean equals(final Object o) {
        return o == this || this.m.equals(o);
    }
    
    public int hashCode() {
        return this.m.hashCode();
    }
    
    public String toString() {
        return this.m.toString();
    }
    
    public long getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TObjectLongProcedure<? super K> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TObjectLongIterator<K> iterator() {
        return new TObjectLongIterator<K>() {
            TObjectLongIterator<K> iter = TUnmodifiableObjectLongMap.this.m.iterator();
            
            public K key() {
                return this.iter.key();
            }
            
            public long value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public long setValue(final long val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public long putIfAbsent(final K key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TObjectLongProcedure<? super K> procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final K key, final long amount) {
        throw new UnsupportedOperationException();
    }
    
    public long adjustOrPutValue(final K key, final long adjust_amount, final long put_amount) {
        throw new UnsupportedOperationException();
    }
}
