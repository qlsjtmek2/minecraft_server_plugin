// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TByteLongIterator;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TLongCollection;
import gnu.trove.set.TByteSet;
import java.io.Serializable;
import gnu.trove.map.TByteLongMap;

public class TUnmodifiableByteLongMap implements TByteLongMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TByteLongMap m;
    private transient TByteSet keySet;
    private transient TLongCollection values;
    
    public TUnmodifiableByteLongMap(final TByteLongMap m) {
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
    
    public boolean containsKey(final byte key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final long val) {
        return this.m.containsValue(val);
    }
    
    public long get(final byte key) {
        return this.m.get(key);
    }
    
    public long put(final byte key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public long remove(final byte key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TByteLongMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Byte, ? extends Long> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TByteSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public byte[] keys() {
        return this.m.keys();
    }
    
    public byte[] keys(final byte[] array) {
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
    
    public byte getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public long getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TByteProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TByteLongProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TByteLongIterator iterator() {
        return new TByteLongIterator() {
            TByteLongIterator iter = TUnmodifiableByteLongMap.this.m.iterator();
            
            public byte key() {
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
    
    public long putIfAbsent(final byte key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TByteLongProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final byte key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final byte key, final long amount) {
        throw new UnsupportedOperationException();
    }
    
    public long adjustOrPutValue(final byte key, final long adjust_amount, final long put_amount) {
        throw new UnsupportedOperationException();
    }
}
