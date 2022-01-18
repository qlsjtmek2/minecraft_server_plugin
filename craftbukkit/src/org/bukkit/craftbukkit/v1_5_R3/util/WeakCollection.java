// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.NoSuchElementException;
import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import java.lang.ref.WeakReference;
import java.util.Collection;

public final class WeakCollection<T> implements Collection<T>
{
    static final Object NO_VALUE;
    private final Collection<WeakReference<T>> collection;
    
    public WeakCollection() {
        this.collection = new ArrayList<WeakReference<T>>();
    }
    
    public boolean add(final T value) {
        Validate.notNull(value, "Cannot add null value");
        return this.collection.add(new WeakReference<T>(value));
    }
    
    public boolean addAll(final Collection<? extends T> collection) {
        final Collection<WeakReference<T>> values = this.collection;
        boolean ret = false;
        for (final T value : collection) {
            Validate.notNull(value, "Cannot add null value");
            ret |= values.add(new WeakReference<T>(value));
        }
        return ret;
    }
    
    public void clear() {
        this.collection.clear();
    }
    
    public boolean contains(final Object object) {
        if (object == null) {
            return false;
        }
        for (final T compare : this) {
            if (object.equals(compare)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsAll(final Collection<?> collection) {
        return this.toCollection().containsAll(collection);
    }
    
    public boolean isEmpty() {
        return !this.iterator().hasNext();
    }
    
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<WeakReference<T>> it = WeakCollection.this.collection.iterator();
            Object value = WeakCollection.NO_VALUE;
            
            public boolean hasNext() {
                Object value = this.value;
                if (value != null && value != WeakCollection.NO_VALUE) {
                    return true;
                }
                final Iterator<WeakReference<T>> it = this.it;
                value = null;
                while (it.hasNext()) {
                    final WeakReference<T> ref = it.next();
                    value = ref.get();
                    if (value != null) {
                        this.value = value;
                        return true;
                    }
                    it.remove();
                }
                return false;
            }
            
            public T next() throws NoSuchElementException {
                if (!this.hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }
                final T value = (T)this.value;
                this.value = WeakCollection.NO_VALUE;
                return value;
            }
            
            public void remove() throws IllegalStateException {
                if (this.value != WeakCollection.NO_VALUE) {
                    throw new IllegalStateException("No last element");
                }
                this.value = null;
                this.it.remove();
            }
        };
    }
    
    public boolean remove(final Object object) {
        if (object == null) {
            return false;
        }
        final Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            if (object.equals(it.next())) {
                it.remove();
                return true;
            }
        }
        return false;
    }
    
    public boolean removeAll(final Collection<?> collection) {
        final Iterator<T> it = this.iterator();
        boolean ret = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                ret = true;
                it.remove();
            }
        }
        return ret;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        final Iterator<T> it = this.iterator();
        boolean ret = false;
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                ret = true;
                it.remove();
            }
        }
        return ret;
    }
    
    public int size() {
        int s = 0;
        for (final T value : this) {
            ++s;
        }
        return s;
    }
    
    public Object[] toArray() {
        return this.toArray(new Object[0]);
    }
    
    public <T> T[] toArray(final T[] array) {
        return this.toCollection().toArray(array);
    }
    
    private Collection<T> toCollection() {
        final ArrayList<T> collection = new ArrayList<T>();
        for (final T value : this) {
            collection.add(value);
        }
        return collection;
    }
    
    static {
        NO_VALUE = new Object();
    }
}
