// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import gnu.trove.procedure.TByteProcedure;
import java.util.Collection;
import gnu.trove.iterator.TByteIterator;
import java.io.Serializable;
import gnu.trove.TByteCollection;

public class TSynchronizedByteCollection implements TByteCollection, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final TByteCollection c;
    final Object mutex;
    
    public TSynchronizedByteCollection(final TByteCollection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        this.c = c;
        this.mutex = this;
    }
    
    public TSynchronizedByteCollection(final TByteCollection c, final Object mutex) {
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
    
    public boolean contains(final byte o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public byte[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public byte[] toArray(final byte[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public TByteIterator iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final byte e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final byte o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final TByteCollection coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean containsAll(final byte[] array) {
        synchronized (this.mutex) {
            return this.c.containsAll(array);
        }
    }
    
    public boolean addAll(final Collection<? extends Byte> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final TByteCollection coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean addAll(final byte[] array) {
        synchronized (this.mutex) {
            return this.c.addAll(array);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final TByteCollection coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean removeAll(final byte[] array) {
        synchronized (this.mutex) {
            return this.c.removeAll(array);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final TByteCollection coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
        }
    }
    
    public boolean retainAll(final byte[] array) {
        synchronized (this.mutex) {
            return this.c.retainAll(array);
        }
    }
    
    public byte getNoEntryValue() {
        return this.c.getNoEntryValue();
    }
    
    public boolean forEach(final TByteProcedure procedure) {
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
