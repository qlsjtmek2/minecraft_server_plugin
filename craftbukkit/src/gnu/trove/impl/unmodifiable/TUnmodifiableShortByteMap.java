// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TByteFunction;
import gnu.trove.iterator.TShortByteIterator;
import gnu.trove.procedure.TShortByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TByteCollection;
import gnu.trove.set.TShortSet;
import java.io.Serializable;
import gnu.trove.map.TShortByteMap;

public class TUnmodifiableShortByteMap implements TShortByteMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TShortByteMap m;
    private transient TShortSet keySet;
    private transient TByteCollection values;
    
    public TUnmodifiableShortByteMap(final TShortByteMap m) {
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
    
    public boolean containsValue(final byte val) {
        return this.m.containsValue(val);
    }
    
    public byte get(final short key) {
        return this.m.get(key);
    }
    
    public byte put(final short key, final byte value) {
        throw new UnsupportedOperationException();
    }
    
    public byte remove(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TShortByteMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Short, ? extends Byte> map) {
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
    
    public TByteCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public byte[] values() {
        return this.m.values();
    }
    
    public byte[] values(final byte[] array) {
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
    
    public byte getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TByteProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TShortByteProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TShortByteIterator iterator() {
        return new TShortByteIterator() {
            TShortByteIterator iter = TUnmodifiableShortByteMap.this.m.iterator();
            
            public short key() {
                return this.iter.key();
            }
            
            public byte value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public byte setValue(final byte val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public byte putIfAbsent(final short key, final byte value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TByteFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TShortByteProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final short key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final short key, final byte amount) {
        throw new UnsupportedOperationException();
    }
    
    public byte adjustOrPutValue(final short key, final byte adjust_amount, final byte put_amount) {
        throw new UnsupportedOperationException();
    }
}
