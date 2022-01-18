// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import com.google.common.base.Objects;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Set;
import com.google.common.annotations.GwtCompatible;
import java.util.AbstractCollection;

@GwtCompatible
abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E>
{
    private transient Set<E> elementSet;
    private transient Set<Entry<E>> entrySet;
    
    public int size() {
        return Multisets.sizeImpl(this);
    }
    
    public boolean isEmpty() {
        return this.entrySet().isEmpty();
    }
    
    public boolean contains(@Nullable final Object element) {
        return this.count(element) > 0;
    }
    
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl((Multiset<E>)this);
    }
    
    public int count(final Object element) {
        for (final Entry<E> entry : this.entrySet()) {
            if (Objects.equal(entry.getElement(), element)) {
                return entry.getCount();
            }
        }
        return 0;
    }
    
    public boolean add(@Nullable final E element) {
        this.add(element, 1);
        return true;
    }
    
    public int add(final E element, final int occurrences) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final Object element) {
        return this.remove(element, 1) > 0;
    }
    
    public int remove(final Object element, final int occurrences) {
        throw new UnsupportedOperationException();
    }
    
    public int setCount(final E element, final int count) {
        return Multisets.setCountImpl(this, element, count);
    }
    
    public boolean setCount(final E element, final int oldCount, final int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }
    
    public boolean addAll(final Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl((Multiset<Object>)this, elementsToAdd);
    }
    
    public boolean removeAll(final Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }
    
    public boolean retainAll(final Collection<?> elementsToRetain) {
        return Multisets.retainAllImpl(this, elementsToRetain);
    }
    
    public void clear() {
        Iterators.clear(this.entryIterator());
    }
    
    public Set<E> elementSet() {
        Set<E> result = this.elementSet;
        if (result == null) {
            result = (this.elementSet = this.createElementSet());
        }
        return result;
    }
    
    Set<E> createElementSet() {
        return new ElementSet();
    }
    
    abstract Iterator<Entry<E>> entryIterator();
    
    abstract int distinctElements();
    
    public Set<Entry<E>> entrySet() {
        final Set<Entry<E>> result = this.entrySet;
        return (result == null) ? (this.entrySet = this.createEntrySet()) : result;
    }
    
    Set<Entry<E>> createEntrySet() {
        return (Set<Entry<E>>)new EntrySet();
    }
    
    public boolean equals(@Nullable final Object object) {
        return Multisets.equalsImpl(this, object);
    }
    
    public int hashCode() {
        return this.entrySet().hashCode();
    }
    
    public String toString() {
        return this.entrySet().toString();
    }
    
    class ElementSet extends Multisets.ElementSet<E>
    {
        Multiset<E> multiset() {
            return (Multiset<E>)AbstractMultiset.this;
        }
    }
    
    class EntrySet extends Multisets.EntrySet<E>
    {
        Multiset<E> multiset() {
            return (Multiset<E>)AbstractMultiset.this;
        }
        
        public Iterator<Entry<E>> iterator() {
            return AbstractMultiset.this.entryIterator();
        }
        
        public int size() {
            return AbstractMultiset.this.distinctElements();
        }
    }
}
