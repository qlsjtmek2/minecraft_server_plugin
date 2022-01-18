// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TShortObjectIterator;
import gnu.trove.procedure.TShortObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TShortProcedure;
import java.util.Collections;
import gnu.trove.TCollections;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TShortSet;
import java.io.Serializable;
import gnu.trove.map.TShortObjectMap;

public class TUnmodifiableShortObjectMap<V> implements TShortObjectMap<V>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TShortObjectMap<V> m;
    private transient TShortSet keySet;
    private transient Collection<V> values;
    
    public TUnmodifiableShortObjectMap(final TShortObjectMap<V> m) {
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
    
    public boolean containsKey(final short key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final Object val) {
        return this.m.containsValue(val);
    }
    
    public V get(final short key) {
        return this.m.get(key);
    }
    
    public V put(final short key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TShortObjectMap<? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Short, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TShortSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public short[] keys() {
        return this.m.keys();
    }
    
    public short[] keys(final short[] array) {
        return this.m.keys(array);
    }
    
    public Collection<V> valueCollection() {
        if (this.values == null) {
            this.values = Collections.unmodifiableCollection((Collection<? extends V>)this.m.valueCollection());
        }
        return this.values;
    }
    
    public V[] values() {
        return this.m.values();
    }
    
    public V[] values(final V[] array) {
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
    
    public short getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TShortObjectProcedure<? super V> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TShortObjectIterator<V> iterator() {
        return new TShortObjectIterator<V>() {
            TShortObjectIterator<V> iter = TUnmodifiableShortObjectMap.this.m.iterator();
            
            public short key() {
                return this.iter.key();
            }
            
            public V value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public V setValue(final V val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public V putIfAbsent(final short key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TShortObjectProcedure<? super V> procedure) {
        throw new UnsupportedOperationException();
    }
}
