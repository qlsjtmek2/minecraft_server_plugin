// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TIntFunction;
import gnu.trove.iterator.TFloatIntIterator;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TFloatSet;
import java.io.Serializable;
import gnu.trove.map.TFloatIntMap;

public class TUnmodifiableFloatIntMap implements TFloatIntMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TFloatIntMap m;
    private transient TFloatSet keySet;
    private transient TIntCollection values;
    
    public TUnmodifiableFloatIntMap(final TFloatIntMap m) {
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
    
    public boolean containsKey(final float key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final int val) {
        return this.m.containsValue(val);
    }
    
    public int get(final float key) {
        return this.m.get(key);
    }
    
    public int put(final float key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public int remove(final float key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TFloatIntMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Float, ? extends Integer> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TFloatSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public float[] keys() {
        return this.m.keys();
    }
    
    public float[] keys(final float[] array) {
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
    
    public float getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TFloatIntProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TFloatIntIterator iterator() {
        return new TFloatIntIterator() {
            TFloatIntIterator iter = TUnmodifiableFloatIntMap.this.m.iterator();
            
            public float key() {
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
    
    public int putIfAbsent(final float key, final int value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TIntFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TFloatIntProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final float key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final float key, final int amount) {
        throw new UnsupportedOperationException();
    }
    
    public int adjustOrPutValue(final float key, final int adjust_amount, final int put_amount) {
        throw new UnsupportedOperationException();
    }
}
