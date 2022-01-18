// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import com.google.common.base.Objects;
import java.util.Iterator;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.Set;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E>
{
    protected abstract Multiset<E> delegate();
    
    public int count(final Object element) {
        return this.delegate().count(element);
    }
    
    public int add(final E element, final int occurrences) {
        return this.delegate().add(element, occurrences);
    }
    
    public int remove(final Object element, final int occurrences) {
        return this.delegate().remove(element, occurrences);
    }
    
    public Set<E> elementSet() {
        return this.delegate().elementSet();
    }
    
    public Set<Entry<E>> entrySet() {
        return this.delegate().entrySet();
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    public int setCount(final E element, final int count) {
        return this.delegate().setCount(element, count);
    }
    
    public boolean setCount(final E element, final int oldCount, final int newCount) {
        return this.delegate().setCount(element, oldCount, newCount);
    }
    
    @Beta
    protected boolean standardContains(@Nullable final Object object) {
        return this.count(object) > 0;
    }
    
    @Beta
    protected void standardClear() {
        final Iterator<Entry<E>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            entryIterator.next();
            entryIterator.remove();
        }
    }
    
    @Beta
    protected int standardCount(@Nullable final Object object) {
        for (final Entry<?> entry : this.entrySet()) {
            if (Objects.equal(entry.getElement(), object)) {
                return entry.getCount();
            }
        }
        return 0;
    }
    
    @Beta
    protected boolean standardAdd(final E element) {
        this.add(element, 1);
        return true;
    }
    
    @Beta
    protected boolean standardAddAll(final Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl((Multiset<Object>)this, elementsToAdd);
    }
    
    @Beta
    protected boolean standardRemove(final Object element) {
        return this.remove(element, 1) > 0;
    }
    
    @Beta
    protected boolean standardRemoveAll(final Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }
    
    @Beta
    protected boolean standardRetainAll(final Collection<?> elementsToRetain) {
        return Multisets.retainAllImpl(this, elementsToRetain);
    }
    
    @Beta
    protected int standardSetCount(final E element, final int count) {
        return Multisets.setCountImpl(this, element, count);
    }
    
    @Beta
    protected boolean standardSetCount(final E element, final int oldCount, final int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }
    
    @Deprecated
    @Beta
    protected Set<E> standardElementSet() {
        return new StandardElementSet();
    }
    
    @Beta
    protected Iterator<E> standardIterator() {
        return Multisets.iteratorImpl((Multiset<E>)this);
    }
    
    @Beta
    protected int standardSize() {
        return Multisets.sizeImpl(this);
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object object) {
        return Multisets.equalsImpl(this, object);
    }
    
    @Beta
    protected int standardHashCode() {
        return this.entrySet().hashCode();
    }
    
    @Beta
    protected String standardToString() {
        return this.entrySet().toString();
    }
    
    @Beta
    protected class StandardElementSet extends Multisets.ElementSet<E>
    {
        Multiset<E> multiset() {
            return (Multiset<E>)ForwardingMultiset.this;
        }
    }
}
