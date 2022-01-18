// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.function.TCharFunction;
import gnu.trove.procedure.TDoubleCharProcedure;
import gnu.trove.procedure.TCharProcedure;
import gnu.trove.procedure.TDoubleProcedure;
import gnu.trove.iterator.TDoubleCharIterator;
import java.util.Map;
import gnu.trove.TCharCollection;
import gnu.trove.set.TDoubleSet;
import java.io.Serializable;
import gnu.trove.map.TDoubleCharMap;

public class TSynchronizedDoubleCharMap implements TDoubleCharMap, Serializable
{
    private static final long serialVersionUID = 1978198479659022715L;
    private final TDoubleCharMap m;
    final Object mutex;
    private transient TDoubleSet keySet;
    private transient TCharCollection values;
    
    public TSynchronizedDoubleCharMap(final TDoubleCharMap m) {
        this.keySet = null;
        this.values = null;
        if (m == null) {
            throw new NullPointerException();
        }
        this.m = m;
        this.mutex = this;
    }
    
    public TSynchronizedDoubleCharMap(final TDoubleCharMap m, final Object mutex) {
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
    
    public boolean containsValue(final char value) {
        synchronized (this.mutex) {
            return this.m.containsValue(value);
        }
    }
    
    public char get(final double key) {
        synchronized (this.mutex) {
            return this.m.get(key);
        }
    }
    
    public char put(final double key, final char value) {
        synchronized (this.mutex) {
            return this.m.put(key, value);
        }
    }
    
    public char remove(final double key) {
        synchronized (this.mutex) {
            return this.m.remove(key);
        }
    }
    
    public void putAll(final Map<? extends Double, ? extends Character> map) {
        synchronized (this.mutex) {
            this.m.putAll(map);
        }
    }
    
    public void putAll(final TDoubleCharMap map) {
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
    
    public TCharCollection valueCollection() {
        synchronized (this.mutex) {
            if (this.values == null) {
                this.values = new TSynchronizedCharCollection(this.m.valueCollection(), this.mutex);
            }
            return this.values;
        }
    }
    
    public char[] values() {
        synchronized (this.mutex) {
            return this.m.values();
        }
    }
    
    public char[] values(final char[] array) {
        synchronized (this.mutex) {
            return this.m.values(array);
        }
    }
    
    public TDoubleCharIterator iterator() {
        return this.m.iterator();
    }
    
    public double getNoEntryKey() {
        return this.m.getNoEntryKey();
    }
    
    public char getNoEntryValue() {
        return this.m.getNoEntryValue();
    }
    
    public char putIfAbsent(final double key, final char value) {
        synchronized (this.mutex) {
            return this.m.putIfAbsent(key, value);
        }
    }
    
    public boolean forEachKey(final TDoubleProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachKey(procedure);
        }
    }
    
    public boolean forEachValue(final TCharProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachValue(procedure);
        }
    }
    
    public boolean forEachEntry(final TDoubleCharProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.forEachEntry(procedure);
        }
    }
    
    public void transformValues(final TCharFunction function) {
        synchronized (this.mutex) {
            this.m.transformValues(function);
        }
    }
    
    public boolean retainEntries(final TDoubleCharProcedure procedure) {
        synchronized (this.mutex) {
            return this.m.retainEntries(procedure);
        }
    }
    
    public boolean increment(final double key) {
        synchronized (this.mutex) {
            return this.m.increment(key);
        }
    }
    
    public boolean adjustValue(final double key, final char amount) {
        synchronized (this.mutex) {
            return this.m.adjustValue(key, amount);
        }
    }
    
    public char adjustOrPutValue(final double key, final char adjust_amount, final char put_amount) {
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
