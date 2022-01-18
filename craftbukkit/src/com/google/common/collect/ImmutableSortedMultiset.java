// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtIncompatible;

@GwtIncompatible("hasn't been tested yet")
abstract class ImmutableSortedMultiset<E> extends ImmutableSortedMultisetFauxverideShim<E> implements SortedMultiset<E>
{
    private static final Comparator<Comparable> NATURAL_ORDER;
    private static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET;
    private final transient Comparator<? super E> comparator;
    private transient Comparator<? super E> reverseComparator;
    private transient ImmutableSortedSet<E> elementSet;
    transient ImmutableSortedMultiset<E> descendingMultiset;
    
    public static <E> ImmutableSortedMultiset<E> of() {
        return (ImmutableSortedMultiset<E>)ImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E element) {
        return (ImmutableSortedMultiset<E>)RegularImmutableSortedMultiset.createFromSorted((Comparator<? super Object>)ImmutableSortedMultiset.NATURAL_ORDER, (List<? extends Multiset.Entry<Object>>)ImmutableList.of(Multisets.immutableEntry((Object)Preconditions.checkNotNull((E)element), 1)));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E e1, final E e2) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)Arrays.asList(e1, e2));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E e1, final E e2, final E e3) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)Arrays.asList(e1, e2, e3));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E e1, final E e2, final E e3, final E e4) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)Arrays.asList(e1, e2, e3, e4));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)Arrays.asList(e1, e2, e3, e4, e5));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E... remaining) {
        final int size = remaining.length + 6;
        final List<E> all = new ArrayList<E>(size);
        Collections.addAll((Collection<? super Comparable>)all, (Comparable[])new Comparable[] { e1, e2, e3, e4, e5, e6 });
        Collections.addAll(all, remaining);
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)all);
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedMultiset<E> copyOf(final E[] elements) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Iterable<? extends E>)Arrays.asList(elements));
    }
    
    public static <E> ImmutableSortedMultiset<E> copyOf(final Iterable<? extends E> elements) {
        final Ordering<E> naturalOrder = Ordering.natural();
        return copyOf((Comparator<? super E>)naturalOrder, elements);
    }
    
    public static <E> ImmutableSortedMultiset<E> copyOf(final Iterator<? extends E> elements) {
        final Ordering<E> naturalOrder = Ordering.natural();
        return copyOfInternal((Comparator<? super E>)naturalOrder, elements);
    }
    
    public static <E> ImmutableSortedMultiset<E> copyOf(final Comparator<? super E> comparator, final Iterator<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return (ImmutableSortedMultiset<E>)copyOfInternal((Comparator<? super Object>)comparator, (Iterator<?>)elements);
    }
    
    public static <E> ImmutableSortedMultiset<E> copyOf(final Comparator<? super E> comparator, final Iterable<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return (ImmutableSortedMultiset<E>)copyOfInternal((Comparator<? super Object>)comparator, (Iterable<?>)elements);
    }
    
    public static <E> ImmutableSortedMultiset<E> copyOfSorted(final SortedMultiset<E> sortedMultiset) {
        Comparator<? super E> comparator = sortedMultiset.comparator();
        if (comparator == null) {
            comparator = (Comparator<? super E>)ImmutableSortedMultiset.NATURAL_ORDER;
        }
        return copyOfInternal(comparator, (Iterable<? extends E>)sortedMultiset);
    }
    
    private static <E> ImmutableSortedMultiset<E> copyOfInternal(final Comparator<? super E> comparator, final Iterable<? extends E> iterable) {
        if (SortedIterables.hasSameComparator(comparator, iterable) && iterable instanceof ImmutableSortedMultiset) {
            final ImmutableSortedMultiset<E> multiset = (ImmutableSortedMultiset<E>)(ImmutableSortedMultiset)iterable;
            if (!multiset.isPartialView()) {
                return (ImmutableSortedMultiset<E>)(ImmutableSortedMultiset)iterable;
            }
        }
        final ImmutableList<Multiset.Entry<E>> entries = ImmutableList.copyOf((Collection<? extends Multiset.Entry<E>>)SortedIterables.sortedCounts(comparator, iterable));
        if (entries.isEmpty()) {
            return emptyMultiset(comparator);
        }
        verifyEntries(entries);
        return (ImmutableSortedMultiset<E>)RegularImmutableSortedMultiset.createFromSorted((Comparator<? super Object>)comparator, (List<? extends Multiset.Entry<Object>>)entries);
    }
    
    private static <E> ImmutableSortedMultiset<E> copyOfInternal(final Comparator<? super E> comparator, final Iterator<? extends E> iterator) {
        final ImmutableList<Multiset.Entry<E>> entries = ImmutableList.copyOf((Collection<? extends Multiset.Entry<E>>)SortedIterables.sortedCounts(comparator, iterator));
        if (entries.isEmpty()) {
            return emptyMultiset(comparator);
        }
        verifyEntries(entries);
        return (ImmutableSortedMultiset<E>)RegularImmutableSortedMultiset.createFromSorted((Comparator<? super Object>)comparator, (List<? extends Multiset.Entry<Object>>)entries);
    }
    
    private static <E> void verifyEntries(final Collection<Multiset.Entry<E>> entries) {
        for (final Multiset.Entry<E> entry : entries) {
            Preconditions.checkNotNull(entry.getElement());
        }
    }
    
    static <E> ImmutableSortedMultiset<E> emptyMultiset(final Comparator<? super E> comparator) {
        if (ImmutableSortedMultiset.NATURAL_ORDER.equals(comparator)) {
            return (ImmutableSortedMultiset<E>)ImmutableSortedMultiset.NATURAL_EMPTY_MULTISET;
        }
        return new EmptyImmutableSortedMultiset<E>(comparator);
    }
    
    ImmutableSortedMultiset(final Comparator<? super E> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    Comparator<Object> unsafeComparator() {
        return (Comparator<Object>)this.comparator;
    }
    
    Comparator<? super E> reverseComparator() {
        final Comparator<? super E> result = this.reverseComparator;
        if (result == null) {
            return this.reverseComparator = Ordering.from(this.comparator).reverse();
        }
        return result;
    }
    
    public ImmutableSortedSet<E> elementSet() {
        final ImmutableSortedSet<E> result = this.elementSet;
        if (result == null) {
            return this.elementSet = this.createElementSet();
        }
        return result;
    }
    
    abstract ImmutableSortedSet<E> createElementSet();
    
    abstract ImmutableSortedSet<E> createDescendingElementSet();
    
    public ImmutableSortedMultiset<E> descendingMultiset() {
        final ImmutableSortedMultiset<E> result = this.descendingMultiset;
        if (result == null) {
            return this.descendingMultiset = new DescendingImmutableSortedMultiset<E>(this);
        }
        return result;
    }
    
    abstract UnmodifiableIterator<Multiset.Entry<E>> descendingEntryIterator();
    
    public final Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }
    
    public Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }
    
    public abstract ImmutableSortedMultiset<E> headMultiset(final E p0, final BoundType p1);
    
    public ImmutableSortedMultiset<E> subMultiset(final E lowerBound, final BoundType lowerBoundType, final E upperBound, final BoundType upperBoundType) {
        return this.tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
    }
    
    public abstract ImmutableSortedMultiset<E> tailMultiset(final E p0, final BoundType p1);
    
    public static <E> Builder<E> orderedBy(final Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }
    
    public static <E extends Comparable<E>> Builder<E> reverseOrder() {
        return new Builder<E>(Ordering.natural().reverse());
    }
    
    public static <E extends Comparable<E>> Builder<E> naturalOrder() {
        return new Builder<E>(Ordering.natural());
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_MULTISET = new EmptyImmutableSortedMultiset<Comparable>(ImmutableSortedMultiset.NATURAL_ORDER);
    }
    
    public static class Builder<E> extends ImmutableMultiset.Builder<E>
    {
        private final Comparator<? super E> comparator;
        
        public Builder(final Comparator<? super E> comparator) {
            super(TreeMultiset.create((Comparator<? super Object>)comparator));
            this.comparator = Preconditions.checkNotNull(comparator);
        }
        
        public Builder<E> add(final E element) {
            super.add(element);
            return this;
        }
        
        public Builder<E> addCopies(final E element, final int occurrences) {
            super.addCopies(element, occurrences);
            return this;
        }
        
        public Builder<E> setCount(final E element, final int count) {
            super.setCount(element, count);
            return this;
        }
        
        public Builder<E> add(final E... elements) {
            super.add(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterable<? extends E> elements) {
            super.addAll(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }
        
        public ImmutableSortedMultiset<E> build() {
            return ImmutableSortedMultiset.copyOf(this.comparator, (Iterable<? extends E>)this.contents);
        }
    }
    
    private static final class SerializedForm implements Serializable
    {
        Comparator comparator;
        Object[] elements;
        int[] counts;
        
        SerializedForm(final SortedMultiset<?> multiset) {
            this.comparator = multiset.comparator();
            final int n = multiset.entrySet().size();
            this.elements = new Object[n];
            this.counts = new int[n];
            int i = 0;
            for (final Multiset.Entry<?> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                ++i;
            }
        }
        
        Object readResolve() {
            final int n = this.elements.length;
            final Builder<Object> builder = ImmutableSortedMultiset.orderedBy((Comparator<Object>)this.comparator);
            for (int i = 0; i < n; ++i) {
                builder.addCopies(this.elements[i], this.counts[i]);
            }
            return builder.build();
        }
    }
}
