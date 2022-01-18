// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatIntIterator;
import java.util.Map;
import gnu.trove.TIntCollection;
import gnu.trove.set.TFloatSet;
import java.io.Serializable;
import gnu.trove.map.TFloatIntMap;

public class TSynchronizedFloatIntMap implements TFloatIntMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TFloatIntMap m;
    final Object mutex;
    private transient TFloatSet keySet;
    private transient TIntCollection values;
    
    public TSynchronizedFloatIntMap(final TFloatIntMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedFloatIntMap(final TFloatIntMap m, final Object mutex) {
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
    
    public boolean containsKey(final float key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final int value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public int get(final float key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public int put(final float key, final int value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public int remove(final float key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Float, ? extends Integer> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TFloatIntMap map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TFloatSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedFloatSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public float[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public float[] keys(final float[] array) {
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
    
    public TFloatIntIterator iterator() {
        return this.m.iterator();
    }
    
    public float getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public int putIfAbsent(final float key, final int value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TFloatIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TIntFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TFloatIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final float key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final float key, final int amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public int adjustOrPutValue(final float key, final int adjust_amount, final int put_amount) {
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
