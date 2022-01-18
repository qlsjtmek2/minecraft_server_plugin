// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.iterator.TShortDoubleIterator;
import gnu.trove.procedure.TShortDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TShortSet;
import java.io.Serializable;
import gnu.trove.map.TShortDoubleMap;

public class TUnmodifiableShortDoubleMap implements TShortDoubleMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TShortDoubleMap m;
    private transient TShortSet keySet;
    private transient TDoubleCollection values;
    
    public TUnmodifiableShortDoubleMap(final TShortDoubleMap m) {
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
    
    public boolean containsKey(final short key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final double val) {
        return this.m.containsValue(val);
    }
    
    public double get(final short key) {
        return this.m.get(key);
    }
    
    public double put(final short key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public double remove(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TShortDoubleMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Short, ? extends Double> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TShortSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public short[] keys() {
        return this.m.keys();
    }
    
    public short[] keys(final short[] array) {
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
    
    public short getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public double getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TDoubleProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TShortDoubleProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TShortDoubleIterator iterator() {
        return new TShortDoubleIterator() {
            TShortDoubleIterator iter = TUnmodifiableShortDoubleMap.this.m.iterator();
            
            public short key() {
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
    
    public double putIfAbsent(final short key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TDoubleFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TShortDoubleProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final short key, final double amount) {
        throw new UnsupportedOperationException();
    }
    
    public double adjustOrPutValue(final short key, final double adjust_amount, final double put_amount) {
        throw new UnsupportedOperationException();
    }
}
