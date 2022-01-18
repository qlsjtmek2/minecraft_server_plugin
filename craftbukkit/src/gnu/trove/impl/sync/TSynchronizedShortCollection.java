// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.procedure.TShortProcedure;
import java.util.Collection;
import gnu.trove.iterator.TShortIterator;
import java.io.Serializable;
import gnu.trove.TShortCollection;

public class TSynchronizedShortCollection implements TShortCollection, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final TShortCollection c;
    final Object mutex;
    
    public TSynchronizedShortCollection(final TShortCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.mutex = this;
    }
    
    public TSynchronizedShortCollection(final TShortCollection c, final Object mutex) {
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
    
    public boolean contains(final short o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public short[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public short[] toArray(final short[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public TShortIterator iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final short e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final short o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final TShortCollection coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final short[] array) {
        synchronized (this.mutex) {
            return this.c.containsAll(array);
        }
    }
    
    public boolean addAll(final Collection<? extends Short> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final TShortCollection coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final short[] array) {
        synchronized (this.mutex) {
            return this.c.addAll(array);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final TShortCollection coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final short[] array) {
        synchronized (this.mutex) {
            return this.c.removeAll(array);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final TShortCollection coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final short[] array) {
        synchronized (this.mutex) {
            return this.c.retainAll(array);
        }
    }
    
    public short getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TShortProcedure procedure) {
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
