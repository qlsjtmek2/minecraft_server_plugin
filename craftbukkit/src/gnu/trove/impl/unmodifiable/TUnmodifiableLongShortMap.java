// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TLongShortIterator;
import gnu.trove.procedure.TLongShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TShortCollection;
import gnu.trove.set.TLongSet;
import java.io.Serializable;
import gnu.trove.map.TLongShortMap;

public class TUnmodifiableLongShortMap implements TLongShortMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TLongShortMap m;
    private transient TLongSet keySet;
    private transient TShortCollection values;
    
    public TUnmodifiableLongShortMap(final TLongShortMap m) {
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
    
    public boolean containsValue(final short val) {
        return this.m.containsValue(val);
    }
    
    public short get(final long key) {
        return this.m.get(key);
    }
    
    public short put(final long key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public short remove(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TLongShortMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Long, ? extends Short> map) {
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
    
    public long getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public short getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TLongProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TLongShortProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TLongShortIterator iterator() {
        return new TLongShortIterator() {
            TLongShortIterator iter = TUnmodifiableLongShortMap.this.m.iterator();
            
            public long key() {
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
    
    public short putIfAbsent(final long key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TLongShortProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final long key, final short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(final long key, final short adjust_amount, final short put_amount) {
        throw new UnsupportedOperationException();
    }
}
