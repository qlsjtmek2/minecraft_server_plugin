// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import java.util.Iterator;
import java.util.SortedSet;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E>
{
    final Comparator<? super E> comparator;
    private transient SortedMultiset<E> descendingMultiset;
    
    AbstractSortedMultiset(final Comparator<? super E> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }
    
    public SortedSet<E> elementSet() {
        return (SortedSet<E>)(SortedSet)super.elementSet();
    }
    
    SortedSet<E> createElementSet() {
        return new SortedMultisets.ElementSet<E>() {
            SortedMultiset<E> multiset() {
                return (SortedMultiset<E>)AbstractSortedMultiset.this;
            }
        };
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    public Multiset.Entry<E> firstEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
        return entryIterator.hasNext() ? entryIterator.next() : null;
    }
    
    public Multiset.Entry<E> lastEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
        return entryIterator.hasNext() ? entryIterator.next() : null;
    }
    
    public Multiset.Entry<E> pollFirstEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
        if (entryIterator.hasNext()) {
            Multiset.Entry<E> result = entryIterator.next();
            result = Multisets.immutableEntry(result.getElement(), result.getCount());
            entryIterator.remove();
            return result;
        }
        return null;
    }
    
    public Multiset.Entry<E> pollLastEntry() {
        final Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
        if (entryIterator.hasNext()) {
            Multiset.Entry<E> result = entryIterator.next();
            result = Multisets.immutableEntry(result.getElement(), result.getCount());
            entryIterator.remove();
            return result;
        }
        return null;
    }
    
    public SortedMultiset<E> subMultiset(final E fromElement, final BoundType fromBoundType, final E toElement, final BoundType toBoundType) {
        return this.tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
    }
    
    abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();
    
    Iterator<E> descendingIterator() {
        return Multisets.iteratorImpl(this.descendingMultiset());
    }
    
    public SortedMultiset<E> descendingMultiset() {
        final SortedMultiset<E> result = this.descendingMultiset;
        return (result == null) ? (this.descendingMultiset = this.createDescendingMultiset()) : result;
    }
    
    SortedMultiset<E> createDescendingMultiset() {
        return new SortedMultisets.DescendingMultiset<E>() {
            SortedMultiset<E> forwardMultiset() {
                return (SortedMultiset<E>)AbstractSortedMultiset.this;
            }
            
            Iterator<Multiset.Entry<E>> entryIterator() {
                return AbstractSortedMultiset.this.descendingEntryIterator();
            }
            
            public Iterator<E> iterator() {
                return AbstractSortedMultiset.this.descendingIterator();
            }
        };
    }
}
