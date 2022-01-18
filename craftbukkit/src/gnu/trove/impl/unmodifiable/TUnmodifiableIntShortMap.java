// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TIntShortIterator;
import gnu.trove.procedure.TIntShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TShortCollection;
import gnu.trove.set.TIntSet;
import java.io.Serializable;
import gnu.trove.map.TIntShortMap;

public class TUnmodifiableIntShortMap implements TIntShortMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TIntShortMap m;
    private transient TIntSet keySet;
    private transient TShortCollection values;
    
    public TUnmodifiableIntShortMap(final TIntShortMap m) {
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
    
    public boolean containsValue(final short val) {
        return this.m.containsValue(val);
    }
    
    public short get(final int key) {
        return this.m.get(key);
    }
    
    public short put(final int key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public short remove(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TIntShortMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Integer, ? extends Short> map) {
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
    
    public int getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public short getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TIntShortProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TIntShortIterator iterator() {
        return new TIntShortIterator() {
            TIntShortIterator iter = TUnmodifiableIntShortMap.this.m.iterator();
            
            public int key() {
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
    
    public short putIfAbsent(final int key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TIntShortProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final int key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final int key, final short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(final int key, final short adjust_amount, final short put_amount) {
        throw new UnsupportedOperationException();
    }
}
