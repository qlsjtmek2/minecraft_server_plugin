// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.Iterator;
import com.google.common.annotations.GwtCompatible;
import java.util.Collection;

@GwtCompatible
public abstract class ForwardingCollection<E> extends ForwardingObject implements Collection<E>
{
    protected abstract Collection<E> delegate();
    
    public Iterator<E> iterator() {
        return this.delegate().iterator();
    }
    
    public int size() {
        return this.delegate().size();
    }
    
    public boolean removeAll(final Collection<?> collection) {
        return this.delegate().removeAll(collection);
    }
    
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    public boolean contains(final Object object) {
        return this.delegate().contains(object);
    }
    
    public boolean add(final E element) {
        return this.delegate().add(element);
    }
    
    public boolean remove(final Object object) {
        return this.delegate().remove(object);
    }
    
    public boolean containsAll(final Collection<?> collection) {
        return this.delegate().containsAll(collection);
    }
    
    public boolean addAll(final Collection<? extends E> collection) {
        return this.delegate().addAll(collection);
    }
    
    public boolean retainAll(final Collection<?> collection) {
        return this.delegate().retainAll(collection);
    }
    
    public void clear() {
        this.delegate().clear();
    }
    
    public Object[] toArray() {
        return this.delegate().toArray();
    }
    
    public <T> T[] toArray(final T[] array) {
        return this.delegate().toArray(array);
    }
    
    @Beta
    protected boolean standardContains(@Nullable final Object object) {
        return Iterators.contains(this.iterator(), object);
    }
    
    @Beta
    protected boolean standardContainsAll(final Collection<?> collection) {
        for (final Object o : collection) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }
    
    @Beta
    protected boolean standardAddAll(final Collection<? extends E> collection) {
        return Iterators.addAll((Collection<Object>)this, collection.iterator());
    }
    
    @Beta
    protected boolean standardRemove(@Nullable final Object object) {
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (Objects.equal(iterator.next(), object)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    @Beta
    protected boolean standardRemoveAll(final Collection<?> collection) {
        return Iterators.removeAll(this.iterator(), collection);
    }
    
    @Beta
    protected boolean standardRetainAll(final Collection<?> collection) {
        return Iterators.retainAll(this.iterator(), collection);
    }
    
    @Beta
    protected void standardClear() {
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
    
    @Beta
    protected boolean standardIsEmpty() {
        return !this.iterator().hasNext();
    }
    
    @Beta
    protected String standardToString() {
        return Collections2.toStringImpl(this);
    }
    
    @Beta
    protected Object[] standardToArray() {
        final Object[] newArray = new Object[this.size()];
        return this.toArray(newArray);
    }
    
    @Beta
    protected <T> T[] standardToArray(final T[] array) {
        return ObjectArrays.toArrayImpl(this, array);
    }
}
