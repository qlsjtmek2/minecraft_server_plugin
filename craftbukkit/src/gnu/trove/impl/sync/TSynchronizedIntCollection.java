// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.procedure.TIntProcedure;
import java.util.Collection;
import gnu.trove.iterator.TIntIterator;
import java.io.Serializable;
import gnu.trove.TIntCollection;

public class TSynchronizedIntCollection implements TIntCollection, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final TIntCollection c;
    final Object mutex;
    
    public TSynchronizedIntCollection(final TIntCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.mutex = this;
    }
    
    public TSynchronizedIntCollection(final TIntCollection c, final Object mutex) {
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
    
    public boolean contains(final int o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public int[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public int[] toArray(final int[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public TIntIterator iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final int e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final int o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final TIntCollection coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final int[] array) {
        synchronized (this.mutex) {
            return this.c.containsAll(array);
        }
    }
    
    public boolean addAll(final Collection<? extends Integer> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final TIntCollection coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final int[] array) {
        synchronized (this.mutex) {
            return this.c.addAll(array);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final TIntCollection coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final int[] array) {
        synchronized (this.mutex) {
            return this.c.removeAll(array);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final TIntCollection coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final int[] array) {
        synchronized (this.mutex) {
            return this.c.retainAll(array);
        }
    }
    
    public int getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TIntProcedure procedure) {
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
