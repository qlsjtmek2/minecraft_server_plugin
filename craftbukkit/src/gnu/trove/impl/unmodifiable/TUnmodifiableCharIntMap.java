// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TIntFunction;
import gnu.trove.iterator.TCharIntIterator;
import gnu.trove.procedure.TCharIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharIntMap;

public class TUnmodifiableCharIntMap implements TCharIntMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TCharIntMap m;
    private transient TCharSet keySet;
    private transient TIntCollection values;
    
    public TUnmodifiableCharIntMap(final TCharIntMap m) {
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
    
    public boolean containsKey(final char key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final int val) {
        return this.m.containsValue(val);
    }
    
    public int get(final char key) {
        return this.m.get(key);
    }
    
    public int put(final char key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public int remove(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TCharIntMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Character, ? extends Integer> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TCharSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public char[] keys() {
        return this.m.keys();
    }
    
    public char[] keys(final char[] array) {
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
    
    public char getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TCharIntProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TCharIntIterator iterator() {
        return new TCharIntIterator() {
            TCharIntIterator iter = TUnmodifiableCharIntMap.this.m.iterator();
            
            public char key() {
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
    
    public int putIfAbsent(final char key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TIntFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TCharIntProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final char key, final int amount) {
        throw new UnsupportedOperationException();
    }
    
    public int adjustOrPutValue(final char key, final int adjust_amount, final int put_amount) {
        throw new UnsupportedOperationException();
    }
}
