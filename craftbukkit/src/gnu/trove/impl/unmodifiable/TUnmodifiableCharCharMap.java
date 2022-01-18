// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.unmodifiable;

import gnu.trove.function.TCharFunction;
import gnu.trove.iterator.TCharCharIterator;
import gnu.trove.procedure.TCharCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.TCollections;
import java.util.Map;
import gnu.trove.TCharCollection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharCharMap;

public class TUnmodifiableCharCharMap implements TCharCharMap, Serializable
{
    private static final long serialVersionUID = -1034234728574286014L;
    private final TCharCharMap m;
    private transient TCharSet keySet;
    private transient TCharCollection values;
    
    public TUnmodifiableCharCharMap(final TCharCharMap m) {
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
    
    public boolean containsValue(final char val) {
        return this.m.containsValue(val);
    }
    
    public char get(final char key) {
        return this.m.get(key);
    }
    
    public char put(final char key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public char remove(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final TCharCharMap m) {
        throw new UnsupportedOperationException();
    }
    
    public void putAll(final Map<? extends Character, ? extends Character> map) {
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
    
    public char getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public char getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        return this.m.forEachKey(procedure);
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        return this.m.forEachValue(procedure);
    }
    
    public boolean forEachEntry(final TCharCharProcedure procedure) {
        return this.m.forEachEntry(procedure);
    }
    
    public TCharCharIterator iterator() {
        return new TCharCharIterator() {
            TCharCharIterator iter = TUnmodifiableCharCharMap.this.m.iterator();
            
            public char key() {
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
    
    public char putIfAbsent(final char key, final char value) {
        throw new UnsupportedOperationException();
    }
    
    public void transformValues(final TCharFunction function) {
        throw new UnsupportedOperationException();
    }
    
    public boolean retainEntries(final TCharCharProcedure procedure) {
        throw new UnsupportedOperationException();
    }
    
    public boolean increment(final char key) {
        throw new UnsupportedOperationException();
    }
    
    public boolean adjustValue(final char key, final char amount) {
        throw new UnsupportedOperationException();
    }
    
    public char adjustOrPutValue(final char key, final char adjust_amount, final char put_amount) {
        throw new UnsupportedOperationException();
    }
}
