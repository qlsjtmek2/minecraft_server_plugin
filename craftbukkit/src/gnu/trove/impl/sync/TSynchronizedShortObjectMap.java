// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TShortObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.iterator.TShortObjectIterator;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TShortSet;
import java.io.Serializable;
import gnu.trove.map.TShortObjectMap;

public class TSynchronizedShortObjectMap<V> implements TShortObjectMap<V>, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TShortObjectMap<V> m;
    final Object mutex;
    private transient TShortSet keySet;
    private transient Collection<V> values;
    
    public TSynchronizedShortObjectMap(final TShortObjectMap<V> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedShortObjectMap(final TShortObjectMap<V> m, final Object mutex) {
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
    
    public boolean containsKey(final short key) {
        synchronized (this.mutex) {
            return this.m.containsKey(key);
        }
    }
    
    public boolean containsValue(final Object value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public V get(final short key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public V put(final short key, final V value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public V remove(final short key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Short, ? extends V> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TShortObjectMap<? extends V> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.m.clear();
        }
    }
    
    public TShortSet keySet() {
        synchronized (this.mutex) {
            if (this.keySet == null) {
                this.keySet = new TSynchronizedShortSet(this.m.keySet(), this.mutex);
            }
            return this.keySet;
        }
    }
    
    public short[] keys() {
        synchronized (this.mutex) {
            return this.m.keys();
        }
    }
    
    public short[] keys(final short[] array) {
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
    
    public TShortObjectIterator<V> iterator() {
        return this.m.iterator();
    }
    
    public short getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public V putIfAbsent(final short key, final V value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TShortProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TShortObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TShortObjectProcedure<? super V> procedure) {
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
