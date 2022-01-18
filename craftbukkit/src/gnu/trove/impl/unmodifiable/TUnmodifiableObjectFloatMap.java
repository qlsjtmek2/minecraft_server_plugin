// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TFloatFunction;
import gnu.trove.iterator.TObjectFloatIterator;
import gnu.trove.procedure.TObjectFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.TCollections;
import java.util.Collections;
import java.util.Map;
import gnu.trove.TFloatCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectFloatMap;

public class TUnmodifiableObjectFloatMap<K> implements TObjectFloatMap<K>, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TObjectFloatMap<K> m;
    private transient Set<K> keySet;
    private transient TFloatCollection values;
    
    public TUnmodifiableObjectFloatMap(final TObjectFloatMap<K> m) {
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
    
    public boolean containsValue(final float val) {
        return this.m.containsValue(val);
    }
    
    public float get(final Object key) {
        return this.m.get(key);
    }
    
    public float put(final K key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public float remove(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TObjectFloatMap<? extends K> m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends K, ? extends Float> map) {
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
    
    public TFloatCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public float[] values() {
        return this.m.values();
    }
    
    public float[] values(final float[] array) {
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
    
    public float getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TObjectFloatProcedure<? super K> procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TObjectFloatIterator<K> iterator() {
        return new TObjectFloatIterator<K>() {
            TObjectFloatIterator<K> iter = TUnmodifiableObjectFloatMap.this.m.iterator();
            
            public K key() {
                return this.iter.key();
            }
            
            public float value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public float setValue(final float val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public float putIfAbsent(final K key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TFloatFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TObjectFloatProcedure<? super K> procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final K key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final K key, final float amount) {
        throw new UnsupportedOperationException();
    }
    
    public float adjustOrPutValue(final K key, final float adjust_amount, final float put_amount) {
        throw new UnsupportedOperationException();
    }
}
