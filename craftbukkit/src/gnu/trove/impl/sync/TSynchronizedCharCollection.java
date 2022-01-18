// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.procedure.TCharProcedure;
import java.util.Collection;
import gnu.trove.iterator.TCharIterator;
import java.io.Serializable;
import gnu.trove.TCharCollection;

public class TSynchronizedCharCollection implements TCharCollection, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final TCharCollection c;
    final Object mutex;
    
    public TSynchronizedCharCollection(final TCharCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.mutex = this;
    }
    
    public TSynchronizedCharCollection(final TCharCollection c, final Object mutex) {
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
    
    public boolean contains(final char o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public char[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public char[] toArray(final char[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public TCharIterator iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final char e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final char o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final TCharCollection coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final char[] array) {
        synchronized (this.mutex) {
            return this.c.containsAll(array);
        }
    }
    
    public boolean addAll(final Collection<? extends Character> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final TCharCollection coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final char[] array) {
        synchronized (this.mutex) {
            return this.c.addAll(array);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final TCharCollection coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final char[] array) {
        synchronized (this.mutex) {
            return this.c.removeAll(array);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final TCharCollection coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final char[] array) {
        synchronized (this.mutex) {
            return this.c.retainAll(array);
        }
    }
    
    public char getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TCharProcedure procedure) {
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
