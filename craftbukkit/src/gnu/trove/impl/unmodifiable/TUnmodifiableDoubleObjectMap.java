// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TDoubleObjectIterator;
import gnu.trove.procedure.TDoubleObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import java.util.Collections;
import gnu.trove.TCollections;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleObjectMap;

public class TUnmodifiableDoubleObjectMap<V> implements TDoubleObjectMap<V>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TDoubleObjectMap<V> m;
    private transient TDoubleSet keySet;
    private transient Collection<V> values;
    
    public TUnmodifiableDoubleObjectMap(final TDoubleObjectMap<V> m) {
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
    
    public boolean containsKey(final double key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final Object val) {
        return this.m.containsValue(val);
    }
    
    public V get(final double key) {
        return this.m.get(key);
    }
    
    public V put(final double key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TDoubleObjectMap<? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Double, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TDoubleSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public double[] keys() {
        return this.m.keys();
    }
    
    public double[] keys(final double[] array) {
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
    
    public double getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TDoubleObjectProcedure<? super V> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TDoubleObjectIterator<V> iterator() {
        return new TDoubleObjectIterator<V>() {
            TDoubleObjectIterator<V> iter = TUnmodifiableDoubleObjectMap.this.m.iterator();
            
            public double key() {
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
    
    public V putIfAbsent(final double key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TDoubleObjectProcedure<? super V> procedure) {
        throw new UnsupportedOperationException();
    }
}
