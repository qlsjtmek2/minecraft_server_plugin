// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TCharObjectIterator;
import gnu.trove.procedure.TCharObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TCharProcedure;
import java.util.Collections;
import gnu.trove.TCollections;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharObjectMap;

public class TUnmodifiableCharObjectMap<V> implements TCharObjectMap<V>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TCharObjectMap<V> m;
    private transient TCharSet keySet;
    private transient Collection<V> values;
    
    public TUnmodifiableCharObjectMap(final TCharObjectMap<V> m) {
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
    
    public boolean containsKey(final char key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final Object val) {
        return this.m.containsValue(val);
    }
    
    public V get(final char key) {
        return this.m.get(key);
    }
    
    public V put(final char key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TCharObjectMap<? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Character, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TCharSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public char[] keys() {
        return this.m.keys();
    }
    
    public char[] keys(final char[] array) {
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
    
    public char getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TCharObjectProcedure<? super V> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TCharObjectIterator<V> iterator() {
        return new TCharObjectIterator<V>() {
            TCharObjectIterator<V> iter = TUnmodifiableCharObjectMap.this.m.iterator();
            
            public char key() {
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
    
    public V putIfAbsent(final char key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TCharObjectProcedure<? super V> procedure) {
        throw new UnsupportedOperationException();
    }
}
