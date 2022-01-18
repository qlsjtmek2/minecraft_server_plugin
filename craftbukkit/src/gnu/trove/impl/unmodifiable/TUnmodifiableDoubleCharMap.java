// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TDoubleCharIterator;
import gnu.trove.procedure.TDoubleCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TCharCollection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleCharMap;

public class TUnmodifiableDoubleCharMap implements TDoubleCharMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TDoubleCharMap m;
    private transient TDoubleSet keySet;
    private transient TCharCollection values;
    
    public TUnmodifiableDoubleCharMap(final TDoubleCharMap m) {
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
    
    public boolean containsKey(final double key) {
        return this.m.containsKey(key);
    }
    
    public boolean containsValue(final char val) {
        return this.m.containsValue(val);
    }
    
    public char get(final double key) {
        return this.m.get(key);
    }
    
    public char put(final double key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public char remove(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TDoubleCharMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Double, ? extends Character> map) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public TDoubleSet keySet() {
        if (this.keySet == null) {
            this.keySet = TCollections.unmodifiableSet(this.m.keySet());
        }
        return this.keySet;
    }
    
    public double[] keys() {
        return this.m.keys();
    }
    
    public double[] keys(final double[] array) {
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
    
    public double getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public char getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TDoubleCharProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TDoubleCharIterator iterator() {
        return new TDoubleCharIterator() {
            TDoubleCharIterator iter = TUnmodifiableDoubleCharMap.this.m.iterator();
            
            public double key() {
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
    
    public char putIfAbsent(final double key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TDoubleCharProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final double key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final double key, final char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(final double key, final char adjust_amount, final char put_amount) {
        throw new UnsupportedOperationException();
    }
}
