// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TCharIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharIntIterator;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharIntMap;

public class TSynchronizedCharIntMap implements TCharIntMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TCharIntMap m;
    final Object mutex;
    private transient TCharSet keySet;
    private transient TIntCollection values;
    
    public TSynchronizedCharIntMap(final TCharIntMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedCharIntMap(final TCharIntMap m, final Object mutex) {
        this.keySet = null;
        this.values = null;
        this.m = m;
        this.mutex = mutex;
    }
    
    public int size() {
        synchronized (this.mutex) {
            return this.m.size();
        }
    }
    
    public boolean isEmpty() {
        synchronized (this.mutex) {
            return this.m.isEmpty();
        }
    }
    
    public boolean containsKey(final char key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final int value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public int get(final char key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public int put(final char key, final int value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public int remove(final char key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Character, ? extends Integer> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TCharIntMap map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TCharSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedCharSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public char[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public char[] keys(final char[] array) {
        synchronized (this.mutex) {
            return this.m.keys(array);
        }
    }
    
    public TIntCollection valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new TSynchronizedIntCollection(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public int[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public int[] values(final int[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TCharIntIterator iterator() {
        return this.m.iterator();
    }
    
    public char getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public int putIfAbsent(final char key, final int value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TCharIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TIntFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TCharIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final char key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final char key, final int amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public int adjustOrPutValue(final char key, final int adjust_amount, final int put_amount) {
        synchronized (this.mutex) {
            return this.m.adjustOrPutValue(key, adjust_amount, put_amount);
        }
    }
    
    public boolean equals(final Object o) {
        synchronized (this.mutex) {
            return this.m.equals(o);
        }
    }
    
    public int hashCode() {
        synchronized (this.mutex) {
            return this.m.hashCode();
        }
    }
    
    public String toString() {
        synchronized (this.mutex) {
            return this.m.toString();
        }
    }
    
    private void writeObject(final ObjectOutputStream s) throws IOException {
        synchronized (this.mutex) {
            s.defaultWriteObject();
        }
    }
}
