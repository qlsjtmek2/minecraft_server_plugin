// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class LazyHashSet<E> implements Set<E>
{
    Set<E> reference;
    
    public LazyHashSet() {
        this.reference = null;
    }
    
    public int size() {
        return this.getReference().size();
    }
    
    public boolean isEmpty() {
        return this.getReference().isEmpty();
    }
    
    public boolean contains(final Object o) {
        return this.getReference().contains(o);
    }
    
    public Iterator<E> iterator() {
        return this.getReference().iterator();
    }
    
    public Object[] toArray() {
        return this.getReference().toArray();
    }
    
    public <T> T[] toArray(final T[] a) {
        return this.getReference().toArray(a);
    }
    
    public boolean add(final E o) {
        return this.getReference().add(o);
    }
    
    public boolean remove(final Object o) {
        return this.getReference().remove(o);
    }
    
    public boolean containsAll(final Collection<?> c) {
        return this.getReference().containsAll(c);
    }
    
    public boolean addAll(final Collection<? extends E> c) {
        return this.getReference().addAll(c);
    }
    
    public boolean retainAll(final Collection<?> c) {
        return this.getReference().retainAll(c);
    }
    
    public boolean removeAll(final Collection<?> c) {
        return this.getReference().removeAll(c);
    }
    
    public void clear() {
        this.getReference().clear();
    }
    
    public Set<E> getReference() {
        final Set<E> reference = this.reference;
        if (reference != null) {
            return reference;
        }
        return this.reference = this.makeReference();
    }
    
    abstract Set<E> makeReference();
    
    public boolean isLazy() {
        return this.reference == null;
    }
    
    public int hashCode() {
        return 157 * this.getReference().hashCode();
    }
    
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final LazyHashSet<?> that = (LazyHashSet<?>)obj;
        return (this.isLazy() && that.isLazy()) || this.getReference().equals(that.getReference());
    }
    
    public String toString() {
        return this.getReference().toString();
    }
}
