// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TLongIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.iterator.TLongIntIterator;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TLongSet;
import java.io.Serializable;
import gnu.trove.map.TLongIntMap;

public class TSynchronizedLongIntMap implements TLongIntMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TLongIntMap m;
    final Object mutex;
    private transient TLongSet keySet;
    private transient TIntCollection values;
    
    public TSynchronizedLongIntMap(final TLongIntMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedLongIntMap(final TLongIntMap m, final Object mutex) {
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
    
    public boolean containsKey(final long key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final int value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public int get(final long key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public int put(final long key, final int value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public int remove(final long key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Long, ? extends Integer> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TLongIntMap map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TLongSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedLongSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public long[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public long[] keys(final long[] array) {
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
    
    public TLongIntIterator iterator() {
        return this.m.iterator();
    }
    
    public long getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public int putIfAbsent(final long key, final int value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TLongProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TLongIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TIntFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TLongIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final long key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final long key, final int amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public int adjustOrPutValue(final long key, final int adjust_amount, final int put_amount) {
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
