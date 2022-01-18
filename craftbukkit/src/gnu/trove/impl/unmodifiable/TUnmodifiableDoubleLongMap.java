// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TDoubleLongIterator;
import gnu.trove.procedure.TDoubleLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TLongCollection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleLongMap;

public class TUnmodifiableDoubleLongMap implements TDoubleLongMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TDoubleLongMap m;
    private transient TDoubleSet keySet;
    private transient TLongCollection values;
    
    public TUnmodifiableDoubleLongMap(final TDoubleLongMap m) {
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
    
    public boolean containsValue(final long val) {
        return this.m.containsValue(val);
    }
    
    public long get(final double key) {
        return this.m.get(key);
    }
    
    public long put(final double key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public long remove(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TDoubleLongMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Double, ? extends Long> map) {
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
    
    public TLongCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public long[] values() {
        return this.m.values();
    }
    
    public long[] values(final long[] array) {
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
    
    public long getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TDoubleLongProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TDoubleLongIterator iterator() {
        return new TDoubleLongIterator() {
            TDoubleLongIterator iter = TUnmodifiableDoubleLongMap.this.m.iterator();
            
            public double key() {
                return this.iter.key();
            }
            
            public long value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public long setValue(final long val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public long putIfAbsent(final double key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TDoubleLongProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final double key, final long amount) {
        throw new UnsupportedOperationException();
    }
    
    public long adjustOrPutValue(final double key, final long adjust_amount, final long put_amount) {
        throw new UnsupportedOperationException();
    }
}
