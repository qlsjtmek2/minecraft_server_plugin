// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TLongLongIterator;
import gnu.trove.procedure.TLongLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TLongCollection;
import gnu.trove.set.TLongSet;
import java.io.Serializable;
import gnu.trove.map.TLongLongMap;

public class TUnmodifiableLongLongMap implements TLongLongMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TLongLongMap m;
    private transient TLongSet keySet;
    private transient TLongCollection values;
    
    public TUnmodifiableLongLongMap(final TLongLongMap m) {
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
    
    public boolean containsValue(final long val) {
        return this.m.containsValue(val);
    }
    
    public long get(final long key) {
        return this.m.get(key);
    }
    
    public long put(final long key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public long remove(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TLongLongMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Long, ? extends Long> map) {
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
    
    public long getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public long getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TLongProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TLongLongProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TLongLongIterator iterator() {
        return new TLongLongIterator() {
            TLongLongIterator iter = TUnmodifiableLongLongMap.this.m.iterator();
            
            public long key() {
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
    
    public long putIfAbsent(final long key, final long value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TLongFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TLongLongProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final long key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final long key, final long amount) {
        throw new UnsupportedOperationException();
    }
    
    public long adjustOrPutValue(final long key, final long adjust_amount, final long put_amount) {
        throw new UnsupportedOperationException();
    }
}
