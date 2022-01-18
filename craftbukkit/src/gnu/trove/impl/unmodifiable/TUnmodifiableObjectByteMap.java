// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TByteFunction;
import gnu.trove.iterator.TObjectByteIterator;
import gnu.trove.procedure.TObjectByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.TCollections;
import java.util.Collections;
import java.util.Map;
import gnu.trove.TByteCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectByteMap;

public class TUnmodifiableObjectByteMap<K> implements TObjectByteMap<K>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TObjectByteMap<K> m;
    private transient Set<K> keySet;
    private transient TByteCollection values;
    
    public TUnmodifiableObjectByteMap(final TObjectByteMap<K> m) {
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
    
    public boolean containsValue(final byte val) {
        return this.m.containsValue(val);
    }
    
    public byte get(final Object key) {
        return this.m.get(key);
    }
    
    public byte put(final K key, final byte value) {
        throw new UnsupportedOperationException();
    }
    
    public byte remove(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TObjectByteMap<? extends K> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends K, ? extends Byte> map) {
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
    
    public TByteCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public byte[] values() {
        return this.m.values();
    }
    
    public byte[] values(final byte[] array) {
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
    
    public byte getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TByteProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TObjectByteProcedure<? super K> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TObjectByteIterator<K> iterator() {
        return new TObjectByteIterator<K>() {
            TObjectByteIterator<K> iter = TUnmodifiableObjectByteMap.this.m.iterator();
            
            public K key() {
                return this.iter.key();
            }
            
            public byte value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public byte setValue(final byte val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public byte putIfAbsent(final K key, final byte value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TByteFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TObjectByteProcedure<? super K> procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final K key, final byte amount) {
        throw new UnsupportedOperationException();
    }
    
    public byte adjustOrPutValue(final K key, final byte adjust_amount, final byte put_amount) {
        throw new UnsupportedOperationException();
    }
}
