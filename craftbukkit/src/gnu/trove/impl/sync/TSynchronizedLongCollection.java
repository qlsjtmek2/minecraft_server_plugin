// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.procedure.TLongProcedure;
import java.util.Collection;
import gnu.trove.iterator.TLongIterator;
import java.io.Serializable;
import gnu.trove.TLongCollection;

public class TSynchronizedLongCollection implements TLongCollection, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final TLongCollection c;
    final Object mutex;
    
    public TSynchronizedLongCollection(final TLongCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.mutex = this;
    }
    
    public TSynchronizedLongCollection(final TLongCollection c, final Object mutex) {
        this.c = c;
        this.mutex = mutex;
    }
    
    public int size() {
        synchronized (this.mutex) {
            return this.c.size();
        }
    }
    
    public boolean isEmpty() {
        synchronized (this.mutex) {
            return this.c.isEmpty();
        }
    }
    
    public boolean contains(final long o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public long[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public long[] toArray(final long[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public TLongIterator iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final long e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final long o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final TLongCollection coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final long[] array) {
        synchronized (this.mutex) {
            return this.c.containsAll(array);
        }
    }
    
    public boolean addAll(final Collection<? extends Long> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final TLongCollection coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final long[] array) {
        synchronized (this.mutex) {
            return this.c.addAll(array);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final TLongCollection coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final long[] array) {
        synchronized (this.mutex) {
            return this.c.removeAll(array);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final TLongCollection coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final long[] array) {
        synchronized (this.mutex) {
            return this.c.retainAll(array);
        }
    }
    
    public long getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TLongProcedure procedure) {
        synchronized (this.mutex) {
            return this.c.forEach(procedure);
        }
    }
    
    public void clear() {
        synchronized (this.mutex) {
            this.c.clear();
        }
    }
    
    public String toString() {
        synchronized (this.mutex) {
            return this.c.toString();
        }
    }
    
    private void writeObject(final ObjectOutputStream s) throws IOException {
        synchronized (this.mutex) {
            s.defaultWriteObject();
        }
    }
}
