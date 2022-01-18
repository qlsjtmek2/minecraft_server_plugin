// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TByteCharIterator;
import gnu.trove.procedure.TByteCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TCharCollection;
import gnu.trove.set.TByteSet;
import java.io.Serializable;
import gnu.trove.map.TByteCharMap;

public class TUnmodifiableByteCharMap implements TByteCharMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TByteCharMap m;
    private transient TByteSet keySet;
    private transient TCharCollection values;
    
    public TUnmodifiableByteCharMap(final TByteCharMap m) {
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
    
    public boolean containsValue(final char val) {
        return this.m.containsValue(val);
    }
    
    public char get(final byte key) {
        return this.m.get(key);
    }
    
    public char put(final byte key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public char remove(final byte key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TByteCharMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Byte, ? extends Character> map) {
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
    
    public TCharCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public char[] values() {
        return this.m.values();
    }
    
    public char[] values(final char[] array) {
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
    
    public char getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TByteProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TByteCharProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TByteCharIterator iterator() {
        return new TByteCharIterator() {
            TByteCharIterator iter = TUnmodifiableByteCharMap.this.m.iterator();
            
            public byte key() {
                return this.iter.key();
            }
            
            public char value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public char setValue(final char val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public char putIfAbsent(final byte key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TByteCharProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final byte key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final byte key, final char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(final byte key, final char adjust_amount, final char put_amount) {
        throw new UnsupportedOperationException();
    }
}
