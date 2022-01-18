// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TShortFunction;
import gnu.trove.iterator.TFloatShortIterator;
import gnu.trove.procedure.TFloatShortProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TShortCollection;
import gnu.trove.set.TFloatSet;
import java.io.Serializable;
import gnu.trove.map.TFloatShortMap;

public class TUnmodifiableFloatShortMap implements TFloatShortMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TFloatShortMap m;
    private transient TFloatSet keySet;
    private transient TShortCollection values;
    
    public TUnmodifiableFloatShortMap(final TFloatShortMap m) {
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
    
    public boolean containsValue(final short val) {
        return this.m.containsValue(val);
    }
    
    public short get(final float key) {
        return this.m.get(key);
    }
    
    public short put(final float key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public short remove(final float key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TFloatShortMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Float, ? extends Short> map) {
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
    
    public float getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public short getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TShortProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TFloatShortProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TFloatShortIterator iterator() {
        return new TFloatShortIterator() {
            TFloatShortIterator iter = TUnmodifiableFloatShortMap.this.m.iterator();
            
            public float key() {
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
    
    public short putIfAbsent(final float key, final short value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TShortFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TFloatShortProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final float key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final float key, final short amount) {
        throw new UnsupportedOperationException();
    }
    
    public short adjustOrPutValue(final float key, final short adjust_amount, final short put_amount) {
        throw new UnsupportedOperationException();
    }
}
