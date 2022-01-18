// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TObjectFunction;
import gnu.trove.iterator.TByteObjectIterator;
import gnu.trove.procedure.TByteObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TByteProcedure;
import java.util.Collections;
import gnu.trove.TCollections;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TByteSet;
import java.io.Serializable;
import gnu.trove.map.TByteObjectMap;

public class TUnmodifiableByteObjectMap<V> implements TByteObjectMap<V>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TByteObjectMap<V> m;
    private transient TByteSet keySet;
    private transient Collection<V> values;
    
    public TUnmodifiableByteObjectMap(final TByteObjectMap<V> m) {
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
    
    public boolean containsKey(final byte key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final Object val) {
        return this.m.containsValue(val);
    }
    
    public V get(final byte key) {
        return this.m.get(key);
    }
    
    public V put(final byte key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public V remove(final byte key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TByteObjectMap<? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Byte, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TByteSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public byte[] keys() {
        return this.m.keys();
    }
    
    public byte[] keys(final byte[] array) {
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
    
    public byte getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public boolean forEachKey(final TByteProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TByteObjectProcedure<? super V> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TByteObjectIterator<V> iterator() {
        return new TByteObjectIterator<V>() {
            TByteObjectIterator<V> iter = TUnmodifiableByteObjectMap.this.m.iterator();
            
            public byte key() {
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
    
    public V putIfAbsent(final byte key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TByteObjectProcedure<? super V> procedure) {
        throw new UnsupportedOperationException();
    }
}
