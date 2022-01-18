// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TFloatFunction;
import gnu.trove.procedure.TFloatFloatProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.iterator.TFloatFloatIterator;
import java.util.Map;
import gnu.trove.TFloatCollection;
import gnu.trove.set.TFloatSet;
import java.io.Serializable;
import gnu.trove.map.TFloatFloatMap;

public class TSynchronizedFloatFloatMap implements TFloatFloatMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TFloatFloatMap m;
    final Object mutex;
    private transient TFloatSet keySet;
    private transient TFloatCollection values;
    
    public TSynchronizedFloatFloatMap(final TFloatFloatMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedFloatFloatMap(final TFloatFloatMap m, final Object mutex) {
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
    
    public boolean containsValue(final float value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public float get(final float key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public float put(final float key, final float value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public float remove(final float key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Float, ? extends Float> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TFloatFloatMap map) {
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
    
    public TFloatCollection valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new TSynchronizedFloatCollection(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public float[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public float[] values(final float[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TFloatFloatIterator iterator() {
        return this.m.iterator();
    }
    
    public float getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public float getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public float putIfAbsent(final float key, final float value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TFloatProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TFloatProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TFloatFloatProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TFloatFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TFloatFloatProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final float key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final float key, final float amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public float adjustOrPutValue(final float key, final float adjust_amount, final float put_amount) {
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
