// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TFloatFunction;
import gnu.trove.iterator.TIntFloatIterator;
import gnu.trove.procedure.TIntFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TIntSet;
import java.io.Serializable;
import gnu.trove.map.TIntFloatMap;

public class TUnmodifiableIntFloatMap implements TIntFloatMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TIntFloatMap m;
    private transient TIntSet keySet;
    private transient TFloatCollection values;
    
    public TUnmodifiableIntFloatMap(final TIntFloatMap m) {
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
    
    public boolean containsKey(final int key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final float val) {
        return this.m.containsValue(val);
    }
    
    public float get(final int key) {
        return this.m.get(key);
    }
    
    public float put(final int key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public float remove(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TIntFloatMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Integer, ? extends Float> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TIntSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public int[] keys() {
        return this.m.keys();
    }
    
    public int[] keys(final int[] array) {
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
    
    public int getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public float getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TIntFloatProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TIntFloatIterator iterator() {
        return new TIntFloatIterator() {
            TIntFloatIterator iter = TUnmodifiableIntFloatMap.this.m.iterator();
            
            public int key() {
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
    
    public float putIfAbsent(final int key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TFloatFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TIntFloatProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final int key, final float amount) {
        throw new UnsupportedOperationException();
    }
    
    public float adjustOrPutValue(final int key, final float adjust_amount, final float put_amount) {
        throw new UnsupportedOperationException();
    }
}
