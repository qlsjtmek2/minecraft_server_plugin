// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TFloatFunction;
import gnu.trove.iterator.TDoubleFloatIterator;
import gnu.trove.procedure.TDoubleFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleFloatMap;

public class TUnmodifiableDoubleFloatMap implements TDoubleFloatMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TDoubleFloatMap m;
    private transient TDoubleSet keySet;
    private transient TFloatCollection values;
    
    public TUnmodifiableDoubleFloatMap(final TDoubleFloatMap m) {
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
    
    public boolean containsValue(final float val) {
        return this.m.containsValue(val);
    }
    
    public float get(final double key) {
        return this.m.get(key);
    }
    
    public float put(final double key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public float remove(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TDoubleFloatMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Double, ? extends Float> map) {
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
    
    public double getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public float getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TDoubleFloatProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TDoubleFloatIterator iterator() {
        return new TDoubleFloatIterator() {
            TDoubleFloatIterator iter = TUnmodifiableDoubleFloatMap.this.m.iterator();
            
            public double key() {
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
    
    public float putIfAbsent(final double key, final float value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TFloatFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TDoubleFloatProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final double key, final float amount) {
        throw new UnsupportedOperationException();
    }
    
    public float adjustOrPutValue(final double key, final float adjust_amount, final float put_amount) {
        throw new UnsupportedOperationException();
    }
}
