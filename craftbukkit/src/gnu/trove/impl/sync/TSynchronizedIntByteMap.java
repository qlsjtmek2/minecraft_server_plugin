// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TByteFunction;
import gnu.trove.procedure.TIntByteProcedure;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.iterator.TIntByteIterator;
import java.util.Map;
import gnu.trove.TByteCollection;
import gnu.trove.set.TIntSet;
import java.io.Serializable;
import gnu.trove.map.TIntByteMap;

public class TSynchronizedIntByteMap implements TIntByteMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TIntByteMap m;
    final Object mutex;
    private transient TIntSet keySet;
    private transient TByteCollection values;
    
    public TSynchronizedIntByteMap(final TIntByteMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedIntByteMap(final TIntByteMap m, final Object mutex) {
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
    
    public boolean containsKey(final int key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final byte value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public byte get(final int key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public byte put(final int key, final byte value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public byte remove(final int key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Integer, ? extends Byte> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TIntByteMap map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TIntSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedIntSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public int[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public int[] keys(final int[] array) {
        synchronized (this.mutex) {
            return this.m.keys(array);
        }
    }
    
    public TByteCollection valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new TSynchronizedByteCollection(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public byte[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public byte[] values(final byte[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TIntByteIterator iterator() {
        return this.m.iterator();
    }
    
    public int getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public byte getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public byte putIfAbsent(final int key, final byte value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TByteProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TIntByteProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TByteFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TIntByteProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final int key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final int key, final byte amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public byte adjustOrPutValue(final int key, final byte adjust_amount, final byte put_amount) {
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
