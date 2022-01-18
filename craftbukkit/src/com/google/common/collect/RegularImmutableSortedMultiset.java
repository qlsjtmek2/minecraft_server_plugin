// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;
import java.util.Comparator;

final class RegularImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E>
{
    final transient ImmutableList<CumulativeCountEntry<E>> entries;
    
    static <E> RegularImmutableSortedMultiset<E> createFromSorted(final Comparator<? super E> comparator, final List<? extends Multiset.Entry<E>> entries) {
        final List<CumulativeCountEntry<E>> newEntries = (List<CumulativeCountEntry<E>>)Lists.newArrayListWithCapacity(entries.size());
        CumulativeCountEntry<E> previous = null;
        for (final Multiset.Entry<E> entry : entries) {
            newEntries.add(previous = new CumulativeCountEntry<E>(entry.getElement(), entry.getCount(), previous));
        }
        return new RegularImmutableSortedMultiset<E>(comparator, ImmutableList.copyOf((Collection<? extends CumulativeCountEntry<E>>)newEntries));
    }
    
    RegularImmutableSortedMultiset(final Comparator<? super E> comparator, final ImmutableList<CumulativeCountEntry<E>> entries) {
        super(comparator);
        this.entries = entries;
        assert !entries.isEmpty();
    }
    
    ImmutableList<E> elementList() {
        return (ImmutableList<E>)new TransformedImmutableList<CumulativeCountEntry<E>, E>(this.entries) {
            E transform(final CumulativeCountEntry<E> entry) {
                return entry.getElement();
            }
        };
    }
    
    ImmutableSortedSet<E> createElementSet() {
        return new RegularImmutableSortedSet<E>(this.elementList(), this.comparator());
    }
    
    ImmutableSortedSet<E> createDescendingElementSet() {
        return new RegularImmutableSortedSet<E>(this.elementList().reverse(), this.reverseComparator());
    }
    
    UnmodifiableIterator<Multiset.Entry<E>> entryIterator() {
        return (UnmodifiableIterator<Multiset.Entry<E>>)this.entries.iterator();
    }
    
    UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator() {
        return (UnmodifiableIterator<Multiset.Entry<E>>)this.entries.reverse().iterator();
    }
    
    public CumulativeCountEntry<E> firstEntry() {
        return this.entries.get(0);
    }
    
    public CumulativeCountEntry<E> lastEntry() {
        return this.entries.get(this.entries.size() - 1);
    }
    
    public int size() {
        final CumulativeCountEntry<E> firstEntry = this.firstEntry();
        final CumulativeCountEntry<E> lastEntry = this.lastEntry();
        return Ints.saturatedCast(lastEntry.cumulativeCount - firstEntry.cumulativeCount + firstEntry.count);
    }
    
    int distinctElements() {
        return this.entries.size();
    }
    
    boolean isPartialView() {
        return this.entries.isPartialView();
    }
    
    public int count(@Nullable final Object element) {
        if (element == null) {
            return 0;
        }
        try {
            final int index = SortedLists.binarySearch(this.elementList(), element, (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
            return (index >= 0) ? this.entries.get(index).getCount() : 0;
        }
        catch (ClassCastException e) {
            return 0;
        }
    }
    
    public ImmutableSortedMultiset<E> headMultiset(final E upperBound, final BoundType boundType) {
        int index = 0;
        switch (boundType) {
            case OPEN: {
                index = SortedLists.binarySearch(this.elementList(), (Object)Preconditions.checkNotNull((E)upperBound), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
                break;
            }
            case CLOSED: {
                index = SortedLists.binarySearch(this.elementList(), (Object)Preconditions.checkNotNull((E)upperBound), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return this.createSubMultiset(0, index);
    }
    
    public ImmutableSortedMultiset<E> tailMultiset(final E lowerBound, final BoundType boundType) {
        int index = 0;
        switch (boundType) {
            case OPEN: {
                index = SortedLists.binarySearch(this.elementList(), (Object)Preconditions.checkNotNull((E)lowerBound), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
                break;
            }
            case CLOSED: {
                index = SortedLists.binarySearch(this.elementList(), (Object)Preconditions.checkNotNull((E)lowerBound), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        return this.createSubMultiset(index, this.distinctElements());
    }
    
    private ImmutableSortedMultiset<E> createSubMultiset(final int newFromIndex, final int newToIndex) {
        if (newFromIndex == 0 && newToIndex == this.entries.size()) {
            return this;
        }
        if (newFromIndex >= newToIndex) {
            return ImmutableSortedMultiset.emptyMultiset(this.comparator());
        }
        return new RegularImmutableSortedMultiset((Comparator<? super Object>)this.comparator(), (ImmutableList<CumulativeCountEntry<Object>>)this.entries.subList(newFromIndex, newToIndex));
    }
    
    private static final class CumulativeCountEntry<E> extends Multisets.AbstractEntry<E>
    {
        final E element;
        final int count;
        final long cumulativeCount;
        
        CumulativeCountEntry(final E element, final int count, @Nullable final CumulativeCountEntry<E> previous) {
            this.element = element;
            this.count = count;
            this.cumulativeCount = count + ((previous == null) ? 0L : previous.cumulativeCount);
        }
        
        public E getElement() {
            return this.element;
        }
        
        public int getCount() {
            return this.count;
        }
    }
}
