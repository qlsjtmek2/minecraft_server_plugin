// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TDoubleShortIterator;
import gnu.trove.procedure.TDoubleShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TShortCollection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleShortMap;

public class TUnmodifiableDoubleShortMap implements TDoubleShortMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TDoubleShortMap m;
    private transient TDoubleSet keySet;
    private transient TShortCollection values;
    
    public TUnmodifiableDoubleShortMap(final TDoubleShortMap m) {
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
    
    public boolean containsValue(final short val) {
        return this.m.containsValue(val);
    }
    
    public short get(final double key) {
        return this.m.get(key);
    }
    
    public short put(final double key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public short remove(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TDoubleShortMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Double, ? extends Short> map) {
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
    
    public TShortCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public short[] values() {
        return this.m.values();
    }
    
    public short[] values(final short[] array) {
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
    
    public short getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TDoubleShortProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TDoubleShortIterator iterator() {
        return new TDoubleShortIterator() {
            TDoubleShortIterator iter = TUnmodifiableDoubleShortMap.this.m.iterator();
            
            public double key() {
                return this.iter.key();
            }
            
            public short value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public short setValue(final short val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public short putIfAbsent(final double key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TDoubleShortProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final double key, final short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(final double key, final short adjust_amount, final short put_amount) {
        throw new UnsupportedOperationException();
    }
}
