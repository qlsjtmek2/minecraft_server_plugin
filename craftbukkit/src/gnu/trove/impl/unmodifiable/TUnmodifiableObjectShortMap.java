// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TObjectShortIterator;
import gnu.trove.procedure.TObjectShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.TCollections;
import java.util.Collections;
import java.util.Map;
import gnu.trove.TShortCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectShortMap;

public class TUnmodifiableObjectShortMap<K> implements TObjectShortMap<K>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TObjectShortMap<K> m;
    private transient Set<K> keySet;
    private transient TShortCollection values;
    
    public TUnmodifiableObjectShortMap(final TObjectShortMap<K> m) {
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
    
    public boolean containsValue(final short val) {
        return this.m.containsValue(val);
    }
    
    public short get(final Object key) {
        return this.m.get(key);
    }
    
    public short put(final K key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public short remove(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TObjectShortMap<? extends K> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends K, ? extends Short> map) {
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
    
    public TShortCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public short[] values() {
        return this.m.values();
    }
    
    public short[] values(final short[] array) {
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
    
    public short getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TObjectShortProcedure<? super K> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TObjectShortIterator<K> iterator() {
        return new TObjectShortIterator<K>() {
            TObjectShortIterator<K> iter = TUnmodifiableObjectShortMap.this.m.iterator();
            
            public K key() {
                return this.iter.key();
            }
            
            public short value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public short setValue(final short val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public short putIfAbsent(final K key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TObjectShortProcedure<? super K> procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final K key, final short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(final K key, final short adjust_amount, final short put_amount) {
        throw new UnsupportedOperationException();
    }
}
