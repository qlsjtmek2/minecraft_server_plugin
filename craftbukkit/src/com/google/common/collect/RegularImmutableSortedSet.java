// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E>
{
    private final transient ImmutableList<E> elements;
    
    RegularImmutableSortedSet(final ImmutableList<E> elements, final Comparator<? super E> comparator) {
        super(comparator);
        this.elements = elements;
        Preconditions.checkArgument(!elements.isEmpty());
    }
    
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public int size() {
        return this.elements.size();
    }
    
    public boolean contains(final Object o) {
        if (o == null) {
            return false;
        }
        try {
            return this.binarySearch(o) >= 0;
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    public boolean containsAll(final Collection<?> targets) {
        if (!SortedIterables.hasSameComparator(this.comparator(), targets) || targets.size() <= 1) {
            return super.containsAll(targets);
        }
        final Iterator<E> thisIterator = this.iterator();
        final Iterator<?> thatIterator = targets.iterator();
        Object target = thatIterator.next();
        try {
            while (thisIterator.hasNext()) {
                final int cmp = this.unsafeCompare(thisIterator.next(), target);
                if (cmp == 0) {
                    if (!thatIterator.hasNext()) {
                        return true;
                    }
                    target = thatIterator.next();
                }
                else {
                    if (cmp > 0) {
                        return false;
                    }
                    continue;
                }
            }
        }
        catch (NullPointerException e) {
            return false;
        }
        catch (ClassCastException e2) {
            return false;
        }
        return false;
    }
    
    private int binarySearch(final Object key) {
        final Comparator<Object> unsafeComparator = (Comparator<Object>)this.comparator;
        return Collections.binarySearch(this.elements, key, unsafeComparator);
    }
    
    boolean isPartialView() {
        return this.elements.isPartialView();
    }
    
    public Object[] toArray() {
        return this.elements.toArray();
    }
    
    public <T> T[] toArray(final T[] array) {
        return this.elements.toArray(array);
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        final Set<?> that = (Set<?>)object;
        if (this.size() != that.size()) {
            return false;
        }
        if (SortedIterables.hasSameComparator(this.comparator, that)) {
            final Iterator<?> otherIterator = that.iterator();
            try {
                for (final Object element : this) {
                    final Object otherElement = otherIterator.next();
                    if (otherElement == null || this.unsafeCompare(element, otherElement) != 0) {
                        return false;
                    }
                }
                return true;
            }
            catch (ClassCastException e) {
                return false;
            }
            catch (NoSuchElementException e2) {
                return false;
            }
        }
        return this.containsAll(that);
    }
    
    public E first() {
        return this.elements.get(0);
    }
    
    public E last() {
        return this.elements.get(this.size() - 1);
    }
    
    ImmutableSortedSet<E> headSetImpl(final E toElement, final boolean inclusive) {
        int index;
        if (inclusive) {
            index = SortedLists.binarySearch(this.elements, (Object)Preconditions.checkNotNull((E)toElement), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        else {
            index = SortedLists.binarySearch(this.elements, (Object)Preconditions.checkNotNull((E)toElement), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        return this.createSubset(0, index);
    }
    
    ImmutableSortedSet<E> subSetImpl(final E fromElement, final boolean fromInclusive, final E toElement, final boolean toInclusive) {
        return this.tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
    }
    
    ImmutableSortedSet<E> tailSetImpl(final E fromElement, final boolean inclusive) {
        int index;
        if (inclusive) {
            index = SortedLists.binarySearch(this.elements, (Object)Preconditions.checkNotNull((E)fromElement), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        else {
            index = SortedLists.binarySearch(this.elements, (Object)Preconditions.checkNotNull((E)fromElement), (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        return this.createSubset(index, this.size());
    }
    
    Comparator<Object> unsafeComparator() {
        return (Comparator<Object>)this.comparator;
    }
    
    private ImmutableSortedSet<E> createSubset(final int newFromIndex, final int newToIndex) {
        if (newFromIndex == 0 && newToIndex == this.size()) {
            return this;
        }
        if (newFromIndex < newToIndex) {
            return new RegularImmutableSortedSet((ImmutableList<Object>)this.elements.subList(newFromIndex, newToIndex), (Comparator<? super Object>)this.comparator);
        }
        return ImmutableSortedSet.emptySet(this.comparator);
    }
    
    int indexOf(@Nullable final Object target) {
        if (target == null) {
            return -1;
        }
        int position;
        try {
            position = SortedLists.binarySearch(this.elements, target, (Comparator<? super Object>)this.comparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
        }
        catch (ClassCastException e) {
            return -1;
        }
        return (position >= 0 && this.elements.get(position).equals(target)) ? position : -1;
    }
    
    ImmutableList<E> createAsList() {
        return new ImmutableSortedAsList<E>(this, this.elements);
    }
}
