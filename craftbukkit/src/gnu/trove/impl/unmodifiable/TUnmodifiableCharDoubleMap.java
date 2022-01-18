// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TDoubleFunction;
import gnu.trove.iterator.TCharDoubleIterator;
import gnu.trove.procedure.TCharDoubleProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TDoubleCollection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharDoubleMap;

public class TUnmodifiableCharDoubleMap implements TCharDoubleMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TCharDoubleMap m;
    private transient TCharSet keySet;
    private transient TDoubleCollection values;
    
    public TUnmodifiableCharDoubleMap(final TCharDoubleMap m) {
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
    
    public boolean containsValue(final double val) {
        return this.m.containsValue(val);
    }
    
    public double get(final char key) {
        return this.m.get(key);
    }
    
    public double put(final char key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public double remove(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TCharDoubleMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Character, ? extends Double> map) {
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
    
    public TDoubleCollection valueCollection() {
        if (this.values == null) {
            this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
        }
        return this.values;
    }
    
    public double[] values() {
        return this.m.values();
    }
    
    public double[] values(final double[] array) {
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
    
    public double getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TDoubleProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TCharDoubleProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TCharDoubleIterator iterator() {
        return new TCharDoubleIterator() {
            TCharDoubleIterator iter = TUnmodifiableCharDoubleMap.this.m.iterator();
            
            public char key() {
                return this.iter.key();
            }
            
            public double value() {
                return this.iter.value();
            }
            
            public void advance() {
                this.iter.advance();
            }
            
            public boolean hasNext() {
                return this.iter.hasNext();
            }
            
            public double setValue(final double val) {
                throw new UnsupportedOperationException();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public double putIfAbsent(final char key, final double value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TDoubleFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TCharDoubleProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final char key, final double amount) {
        throw new UnsupportedOperationException();
    }
    
    public double adjustOrPutValue(final char key, final double adjust_amount, final double put_amount) {
        throw new UnsupportedOperationException();
    }
}
