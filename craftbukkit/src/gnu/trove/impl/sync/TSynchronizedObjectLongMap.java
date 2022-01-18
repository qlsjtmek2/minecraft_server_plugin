// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TLongFunction;
import gnu.trove.procedure.TObjectLongProcedure;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectLongIterator;
import java.util.Map;
import gnu.trove.TLongCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectLongMap;

public class TSynchronizedObjectLongMap<K> implements TObjectLongMap<K>, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TObjectLongMap<K> m;
    final Object mutex;
    private transient Set<K> keySet;
    private transient TLongCollection values;
    
    public TSynchronizedObjectLongMap(final TObjectLongMap<K> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedObjectLongMap(final TObjectLongMap<K> m, final Object mutex) {
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
    
    public boolean containsKey(final Object key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final long value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public long get(final Object key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public long put(final K key, final long value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public long remove(final Object key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends K, ? extends Long> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TObjectLongMap<? extends K> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public Set<K> keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new SynchronizedSet<K>(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public Object[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public K[] keys(final K[] array) {
        synchronized (this.mutex) {
            return this.m.keys(array);
        }
    }
    
    public TLongCollection valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new TSynchronizedLongCollection(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public long[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public long[] values(final long[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TObjectLongIterator<K> iterator() {
        return this.m.iterator();
    }
    
    public long getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public long putIfAbsent(final K key, final long value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TLongProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TObjectLongProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TLongFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TObjectLongProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final K key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final K key, final long amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public long adjustOrPutValue(final K key, final long adjust_amount, final long put_amount) {
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
