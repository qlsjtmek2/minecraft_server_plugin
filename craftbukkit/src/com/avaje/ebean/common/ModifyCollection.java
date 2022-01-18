// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import java.util.Iterator;
import com.avaje.ebean.bean.BeanCollection;
import java.util.Collection;

class ModifyCollection<E> implements Collection<E>
{
    protected final BeanCollection<E> owner;
    protected final Collection<E> c;
    
    public ModifyCollection(final BeanCollection<E> owner, final Collection<E> c) {
        this.owner = owner;
        this.c = c;
    }
    
    public boolean add(final E o) {
        if (this.c.add(o)) {
            this.owner.modifyAddition(o);
            return true;
        }
        return false;
    }
    
    public boolean addAll(final Collection<? extends E> collection) {
        boolean changed = false;
        for (final E o : collection) {
            if (this.c.add(o)) {
                this.owner.modifyAddition(o);
                changed = true;
            }
        }
        return changed;
    }
    
    public void clear() {
        this.c.clear();
    }
    
    public boolean contains(final Object o) {
        return this.c.contains(o);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        return this.c.containsAll(collection);
    }
    
    public boolean isEmpty() {
        return this.c.isEmpty();
    }
    
    public Iterator<E> iterator() {
        final Iterator<E> it = this.c.iterator();
        return new ModifyIterator<E>(this.owner, it);
    }
    
    public boolean remove(final Object o) {
        if (this.c.remove(o)) {
            this.owner.modifyRemoval(o);
            return true;
        }
        return false;
    }
    
    public boolean removeAll(final Collection<?> collection) {
        boolean changed = false;
        for (final Object o : collection) {
            if (this.c.remove(o)) {
                this.owner.modifyRemoval(o);
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean retainAll(final Collection<?> collection) {
        boolean changed = false;
        final Iterator<?> it = this.c.iterator();
        while (it.hasNext()) {
            final Object o = it.next();
            if (!collection.contains(o)) {
                it.remove();
                this.owner.modifyRemoval(o);
                changed = true;
            }
        }
        return changed;
    }
    
    public int size() {
        return this.c.size();
    }
    
    public Object[] toArray() {
        return this.c.toArray();
    }
    
    public <T> T[] toArray(final T[] a) {
        return this.c.toArray(a);
    }
}
