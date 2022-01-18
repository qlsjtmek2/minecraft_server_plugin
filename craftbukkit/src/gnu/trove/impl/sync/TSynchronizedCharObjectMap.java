// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TObjectFunction;
import gnu.trove.procedure.TCharObjectProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.iterator.TCharObjectIterator;
import java.util.Map;
import java.util.Collection;
import gnu.trove.set.TCharSet;
import java.io.Serializable;
import gnu.trove.map.TCharObjectMap;

public class TSynchronizedCharObjectMap<V> implements TCharObjectMap<V>, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TCharObjectMap<V> m;
    final Object mutex;
    private transient TCharSet keySet;
    private transient Collection<V> values;
    
    public TSynchronizedCharObjectMap(final TCharObjectMap<V> m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedCharObjectMap(final TCharObjectMap<V> m, final Object mutex) {
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
    
    public boolean containsValue(final Object value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public V get(final char key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public V put(final char key, final V value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public V remove(final char key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Character, ? extends V> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TCharObjectMap<? extends V> map) {
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
    
    public TCharObjectIterator<V> iterator() {
        return this.m.iterator();
    }
    
    public char getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public V putIfAbsent(final char key, final V value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TCharProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TCharObjectProcedure<? super V> procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TObjectFunction<V, V> function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TCharObjectProcedure<? super V> procedure) {
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
