// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TIntFunction;
import gnu.trove.procedure.TObjectIntProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.iterator.TObjectIntIterator;
import java.util.Map;
import gnu.trove.TIntCollection;
import java.util.Set;
import java.io.Serializable;
import gnu.trove.map.TObjectIntMap;

public class TSynchronizedObjectIntMap<K> implements TObjectIntMap<K>, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TObjectIntMap<K> m;
    final Object mutex;
    private transient Set<K> keySet;
    private transient TIntCollection values;
    
    public TSynchronizedObjectIntMap(final TObjectIntMap<K> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedObjectIntMap(final TObjectIntMap<K> m, final Object mutex) {
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
    
    public boolean containsValue(final int value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public int get(final Object key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public int put(final K key, final int value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public int remove(final Object key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends K, ? extends Integer> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TObjectIntMap<? extends K> map) {
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
    
    public TObjectIntIterator<K> iterator() {
        return this.m.iterator();
    }
    
    public int getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public int putIfAbsent(final K key, final int value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TObjectProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TIntProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TObjectIntProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TIntFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TObjectIntProcedure<? super K> procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final K key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final K key, final int amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public int adjustOrPutValue(final K key, final int adjust_amount, final int put_amount) {
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
