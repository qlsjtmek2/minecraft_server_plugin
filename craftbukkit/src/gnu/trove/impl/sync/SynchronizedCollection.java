// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove.impl.sync;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.io.Serializable;
import java.util.Collection;

class SynchronizedCollection<E> implements Collection<E>, Serializable
{
    private static final long serialVersionUID = 3053995032091335093L;
    final Collection<E> c;
    final Object mutex;
    
    SynchronizedCollection(final Collection<E> c, final Object mutex) {
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
    
    public boolean contains(final Object o) {
        synchronized (this.mutex) {
            return this.c.contains(o);
        }
    }
    
    public Object[] toArray() {
        synchronized (this.mutex) {
            return this.c.toArray();
        }
    }
    
    public <T> T[] toArray(final T[] a) {
        synchronized (this.mutex) {
            return this.c.toArray(a);
        }
    }
    
    public Iterator<E> iterator() {
        return this.c.iterator();
    }
    
    public boolean add(final E e) {
        synchronized (this.mutex) {
            return this.c.add(e);
        }
    }
    
    public boolean remove(final Object o) {
        synchronized (this.mutex) {
            return this.c.remove(o);
        }
    }
    
    public boolean containsAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.containsAll(coll);
        }
    }
    
    public boolean addAll(final Collection<? extends E> coll) {
        synchronized (this.mutex) {
            return this.c.addAll(coll);
        }
    }
    
    public boolean removeAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.removeAll(coll);
        }
    }
    
    public boolean retainAll(final Collection<?> coll) {
        synchronized (this.mutex) {
            return this.c.retainAll(coll);
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
