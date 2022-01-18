// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TDoubleObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleObjectIterator;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleObjectMap;

public class TSynchronizedDoubleObjectMap<V> implements TDoubleObjectMap<V>, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TDoubleObjectMap<V> m;
    final Object mutex;
    private transient TDoubleSet keySet;
    private transient Collection<V> values;
    
    public TSynchronizedDoubleObjectMap(final TDoubleObjectMap<V> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedDoubleObjectMap(final TDoubleObjectMap<V> m, final Object mutex) {
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
    
    public boolean containsKey(final double key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final Object value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public V get(final double key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public V put(final double key, final V value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public V remove(final double key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Double, ? extends V> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TDoubleObjectMap<? extends V> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TDoubleSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedDoubleSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public double[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public double[] keys(final double[] array) {
        synchronized (this.mutex) {
            return this.m.keys(array);
        }
    }
    
    public Collection<V> valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new SynchronizedCollection<V>(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public V[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public V[] values(final V[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TDoubleObjectIterator<V> iterator() {
        return this.m.iterator();
    }
    
    public double getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public V putIfAbsent(final double key, final V value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TDoubleObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TDoubleObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
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
