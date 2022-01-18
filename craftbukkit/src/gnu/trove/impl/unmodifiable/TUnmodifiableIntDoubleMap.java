// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.procedure.TIntDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TIntSet;
import java.io.Serializable;
import gnu.trove.map.TIntDoubleMap;

public class TUnmodifiableIntDoubleMap implements TIntDoubleMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TIntDoubleMap m;
    private transient TIntSet keySet;
    private transient TDoubleCollection values;
    
    public TUnmodifiableIntDoubleMap(final TIntDoubleMap m) {
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
    
    public boolean containsValue(final double val) {
        return this.m.containsValue(val);
    }
    
    public double get(final int key) {
        return this.m.get(key);
    }
    
    public double put(final int key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public double remove(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TIntDoubleMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Integer, ? extends Double> map) {
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
    
    public TDoubleCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public double[] values() {
        return this.m.values();
    }
    
    public double[] values(final double[] array) {
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
    
    public double getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TDoubleProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TIntDoubleProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TIntDoubleIterator iterator() {
        return new TIntDoubleIterator() {
            TIntDoubleIterator iter = TUnmodifiableIntDoubleMap.this.m.iterator();
            
            public int key() {
                return this.iter.key();
            }
            
            public double value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public double setValue(final double val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public double putIfAbsent(final int key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TDoubleFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TIntDoubleProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final int key, final double amount) {
        throw new UnsupportedOperationException();
    }
    
    public double adjustOrPutValue(final int key, final double adjust_amount, final double put_amount) {
        throw new UnsupportedOperationException();
    }
}
