// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TIntFunction;
import gnu.trove.iterator.TShortIntIterator;
import gnu.trove.procedure.TShortIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TShortSet;
import java.io.Serializable;
import gnu.trove.map.TShortIntMap;

public class TUnmodifiableShortIntMap implements TShortIntMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TShortIntMap m;
    private transient TShortSet keySet;
    private transient TIntCollection values;
    
    public TUnmodifiableShortIntMap(final TShortIntMap m) {
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
    
    public boolean containsValue(final int val) {
        return this.m.containsValue(val);
    }
    
    public int get(final short key) {
        return this.m.get(key);
    }
    
    public int put(final short key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public int remove(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TShortIntMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Short, ? extends Integer> map) {
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
    
    public TIntCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public int[] values() {
        return this.m.values();
    }
    
    public int[] values(final int[] array) {
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
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TShortIntProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TShortIntIterator iterator() {
        return new TShortIntIterator() {
            TShortIntIterator iter = TUnmodifiableShortIntMap.this.m.iterator();
            
            public short key() {
                return this.iter.key();
            }
            
            public int value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public int setValue(final int val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public int putIfAbsent(final short key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TIntFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TShortIntProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final short key, final int amount) {
        throw new UnsupportedOperationException();
    }
    
    public int adjustOrPutValue(final short key, final int adjust_amount, final int put_amount) {
        throw new UnsupportedOperationException();
    }
}
