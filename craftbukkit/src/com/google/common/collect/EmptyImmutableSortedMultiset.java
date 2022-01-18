// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.Comparator;

final class EmptyImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E>
{
    EmptyImmutableSortedMultiset(final Comparator<? super E> comparator) {
        super(comparator);
    }
    
    public Multiset.Entry<E> firstEntry() {
        return null;
    }
    
    public Multiset.Entry<E> lastEntry() {
        return null;
    }
    
    public int count(@Nullable final Object element) {
        return 0;
    }
    
    public int size() {
        return 0;
    }
    
    ImmutableSortedSet<E> createElementSet() {
        return ImmutableSortedSet.emptySet(this.comparator());
    }
    
    ImmutableSortedSet<E> createDescendingElementSet() {
        return ImmutableSortedSet.emptySet(this.reverseComparator());
    }
    
    UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator() {
        return Iterators.emptyIterator();
    }
    
    UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
        return Iterators.emptyIterator();
    }
    
    public ImmutableSortedMultiset<E> headMultiset(final E upperBound, final BoundType boundType) {
        Preconditions.checkNotNull(upperBound);
        Preconditions.checkNotNull(boundType);
        return this;
    }
    
    public ImmutableSortedMultiset<E> tailMultiset(final E lowerBound, final BoundType boundType) {
        Preconditions.checkNotNull(lowerBound);
        Preconditions.checkNotNull(boundType);
        return this;
    }
    
    int distinctElements() {
        return 0;
    }
    
    boolean isPartialView() {
        return false;
    }
}
