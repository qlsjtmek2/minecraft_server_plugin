// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.ListIterator;
import java.util.Collection;
import com.google.common.annotations.GwtCompatible;
import java.util.List;

@GwtCompatible
public abstract class ForwardingList<E> extends ForwardingCollection<E> implements List<E>
{
    protected abstract List<E> delegate();
    
    public void add(final int index, final E element) {
        this.delegate().add(index, element);
    }
    
    public boolean addAll(final int index, final Collection<? extends E> elements) {
        return this.delegate().addAll(index, elements);
    }
    
    public E get(final int index) {
        return this.delegate().get(index);
    }
    
    public int indexOf(final Object element) {
        return this.delegate().indexOf(element);
    }
    
    public int lastIndexOf(final Object element) {
        return this.delegate().lastIndexOf(element);
    }
    
    public ListIterator<E> listIterator() {
        return this.delegate().listIterator();
    }
    
    public ListIterator<E> listIterator(final int index) {
        return this.delegate().listIterator(index);
    }
    
    public E remove(final int index) {
        return this.delegate().remove(index);
    }
    
    public E set(final int index, final E element) {
        return this.delegate().set(index, element);
    }
    
    public List<E> subList(final int fromIndex, final int toIndex) {
        return this.delegate().subList(fromIndex, toIndex);
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Beta
    protected boolean standardAdd(final E element) {
        this.add(this.size(), element);
        return true;
    }
    
    @Beta
    protected boolean standardAddAll(final int index, final Iterable<? extends E> elements) {
        return Lists.addAllImpl((List<Object>)this, index, elements);
    }
    
    @Beta
    protected int standardIndexOf(@Nullable final Object element) {
        return Lists.indexOfImpl(this, element);
    }
    
    @Beta
    protected int standardLastIndexOf(@Nullable final Object element) {
        return Lists.lastIndexOfImpl(this, element);
    }
    
    @Beta
    protected Iterator<E> standardIterator() {
        return this.listIterator();
    }
    
    @Beta
    protected ListIterator<E> standardListIterator() {
        return this.listIterator(0);
    }
    
    @Beta
    protected ListIterator<E> standardListIterator(final int start) {
        return Lists.listIteratorImpl((List<E>)this, start);
    }
    
    @Beta
    protected List<E> standardSubList(final int fromIndex, final int toIndex) {
        return Lists.subListImpl((List<E>)this, fromIndex, toIndex);
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object object) {
        return Lists.equalsImpl(this, object);
    }
    
    @Beta
    protected int standardHashCode() {
        return Lists.hashCodeImpl(this);
    }
}
