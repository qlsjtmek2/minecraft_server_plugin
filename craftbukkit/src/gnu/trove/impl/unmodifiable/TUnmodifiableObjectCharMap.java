// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TObjectCharIterator;
import gnu.trove.procedure.TObjectCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.TCollections;
import java.util.Collections;
import java.util.Map;
import gnu.trove.TCharCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectCharMap;

public class TUnmodifiableObjectCharMap<K> implements TObjectCharMap<K>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TObjectCharMap<K> m;
    private transient Set<K> keySet;
    private transient TCharCollection values;
    
    public TUnmodifiableObjectCharMap(final TObjectCharMap<K> m) {
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
    
    public boolean containsValue(final char val) {
        return this.m.containsValue(val);
    }
    
    public char get(final Object key) {
        return this.m.get(key);
    }
    
    public char put(final K key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public char remove(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TObjectCharMap<? extends K> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends K, ? extends Character> map) {
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
    
    public TCharCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public char[] values() {
        return this.m.values();
    }
    
    public char[] values(final char[] array) {
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
    
    public char getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TObjectCharProcedure<? super K> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TObjectCharIterator<K> iterator() {
        return new TObjectCharIterator<K>() {
            TObjectCharIterator<K> iter = TUnmodifiableObjectCharMap.this.m.iterator();
            
            public K key() {
                return this.iter.key();
            }
            
            public char value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public char setValue(final char val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public char putIfAbsent(final K key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TObjectCharProcedure<? super K> procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final K key, final char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(final K key, final char adjust_amount, final char put_amount) {
        throw new UnsupportedOperationException();
    }
}
