// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TFloatFunction;
import gnu.trove.iterator.TLongFloatIterator;
import gnu.trove.procedure.TLongFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TLongSet;
import java.io.Serializable;
import gnu.trove.map.TLongFloatMap;

public class TUnmodifiableLongFloatMap implements TLongFloatMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TLongFloatMap m;
    private transient TLongSet keySet;
    private transient TFloatCollection values;
    
    public TUnmodifiableLongFloatMap(final TLongFloatMap m) {
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
    
    public boolean containsKey(final long key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final float val) {
        return this.m.containsValue(val);
    }
    
    public float get(final long key) {
        return this.m.get(key);
    }
    
    public float put(final long key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public float remove(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TLongFloatMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Long, ? extends Float> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TLongSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public long[] keys() {
        return this.m.keys();
    }
    
    public long[] keys(final long[] array) {
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
    
    public long getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public float getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TLongProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TLongFloatProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TLongFloatIterator iterator() {
        return new TLongFloatIterator() {
            TLongFloatIterator iter = TUnmodifiableLongFloatMap.this.m.iterator();
            
            public long key() {
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
    
    public float putIfAbsent(final long key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TFloatFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TLongFloatProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final long key, final float amount) {
        throw new UnsupportedOperationException();
    }
    
    public float adjustOrPutValue(final long key, final float adjust_amount, final float put_amount) {
        throw new UnsupportedOperationException();
    }
}
