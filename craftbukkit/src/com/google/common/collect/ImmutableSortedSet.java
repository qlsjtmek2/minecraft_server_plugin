// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.util.SortedSet;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements SortedSet<E>, SortedIterable<E>
{
    private static final Comparator<Comparable> NATURAL_ORDER;
    private static final ImmutableSortedSet<Comparable> NATURAL_EMPTY_SET;
    final transient Comparator<? super E> comparator;
    
    private static <E> ImmutableSortedSet<E> emptySet() {
        return (ImmutableSortedSet<E>)ImmutableSortedSet.NATURAL_EMPTY_SET;
    }
    
    static <E> ImmutableSortedSet<E> emptySet(final Comparator<? super E> comparator) {
        if (ImmutableSortedSet.NATURAL_ORDER.equals(comparator)) {
            return emptySet();
        }
        return new EmptyImmutableSortedSet<E>(comparator);
    }
    
    public static <E> ImmutableSortedSet<E> of() {
        return (ImmutableSortedSet<E>)emptySet();
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E element) {
        return new RegularImmutableSortedSet<E>(ImmutableList.of(element), Ordering.natural());
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E e1, final E e2) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)Arrays.asList(e1, e2));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E e1, final E e2, final E e3) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)Arrays.asList(e1, e2, e3));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E e1, final E e2, final E e3, final E e4) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)Arrays.asList(e1, e2, e3, e4));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)Arrays.asList(e1, e2, e3, e4, e5));
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E... remaining) {
        final int size = remaining.length + 6;
        final List<E> all = new ArrayList<E>(size);
        Collections.addAll((Collection<? super Comparable>)all, (Comparable[])new Comparable[] { e1, e2, e3, e4, e5, e6 });
        Collections.addAll(all, remaining);
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)all);
    }
    
    @Deprecated
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(final E[] elements) {
        return (ImmutableSortedSet<E>)copyOf((Comparable[])elements);
    }
    
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(final E[] elements) {
        return copyOf((Comparator<? super E>)Ordering.natural(), (Collection<? extends E>)Arrays.asList(elements));
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Iterable<? extends E> elements) {
        final Ordering<E> naturalOrder = Ordering.natural();
        return copyOf((Comparator<? super E>)naturalOrder, elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Collection<? extends E> elements) {
        final Ordering<E> naturalOrder = Ordering.natural();
        return copyOf((Comparator<? super E>)naturalOrder, elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Iterator<? extends E> elements) {
        final Ordering<E> naturalOrder = Ordering.natural();
        return copyOfInternal((Comparator<? super E>)naturalOrder, elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Comparator<? super E> comparator, final Iterator<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return (ImmutableSortedSet<E>)copyOfInternal((Comparator<? super Object>)comparator, (Iterator<?>)elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Comparator<? super E> comparator, final Iterable<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return (ImmutableSortedSet<E>)copyOfInternal((Comparator<? super Object>)comparator, (Iterable<?>)elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOf(final Comparator<? super E> comparator, final Collection<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        return copyOfInternal(comparator, (Iterable<? extends E>)elements);
    }
    
    public static <E> ImmutableSortedSet<E> copyOfSorted(final SortedSet<E> sortedSet) {
        Comparator<? super E> comparator = sortedSet.comparator();
        if (comparator == null) {
            comparator = (Comparator<? super E>)ImmutableSortedSet.NATURAL_ORDER;
        }
        return copyOfInternal(comparator, (Iterable<? extends E>)sortedSet);
    }
    
    private static <E> ImmutableSortedSet<E> copyOfInternal(final Comparator<? super E> comparator, final Iterable<? extends E> elements) {
        final boolean hasSameComparator = SortedIterables.hasSameComparator(comparator, elements);
        if (hasSameComparator && elements instanceof ImmutableSortedSet) {
            final ImmutableSortedSet<E> original = (ImmutableSortedSet<E>)(ImmutableSortedSet)elements;
            if (!original.isPartialView()) {
                return original;
            }
        }
        final ImmutableList<E> list = ImmutableList.copyOf((Collection<? extends E>)SortedIterables.sortedUnique((Comparator<? super E>)comparator, (Iterable<? extends E>)elements));
        return list.isEmpty() ? emptySet(comparator) : new RegularImmutableSortedSet<E>(list, comparator);
    }
    
    private static <E> ImmutableSortedSet<E> copyOfInternal(final Comparator<? super E> comparator, final Iterator<? extends E> elements) {
        final ImmutableList<E> list = ImmutableList.copyOf((Collection<? extends E>)SortedIterables.sortedUnique((Comparator<? super E>)comparator, (Iterator<? extends E>)elements));
        return list.isEmpty() ? emptySet(comparator) : new RegularImmutableSortedSet<E>(list, comparator);
    }
    
    public static <E> Builder<E> orderedBy(final Comparator<E> comparator) {
        return new Builder<E>(comparator);
    }
    
    public static <E extends Comparable<E>> Builder<E> reverseOrder() {
        return new Builder<E>(Ordering.natural().reverse());
    }
    
    public static <E extends Comparable<E>> Builder<E> naturalOrder() {
        return new Builder<E>(Ordering.natural());
    }
    
    int unsafeCompare(final Object a, final Object b) {
        return unsafeCompare(this.comparator, a, b);
    }
    
    static int unsafeCompare(final Comparator<?> comparator, final Object a, final Object b) {
        return comparator.compare(a, b);
    }
    
    ImmutableSortedSet(final Comparator<? super E> comparator) {
        this.comparator = comparator;
    }
    
    public Comparator<? super E> comparator() {
        return this.comparator;
    }
    
    public abstract UnmodifiableIterator<E> iterator();
    
    public ImmutableSortedSet<E> headSet(final E toElement) {
        return this.headSet(toElement, false);
    }
    
    ImmutableSortedSet<E> headSet(final E toElement, final boolean inclusive) {
        return this.headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
    }
    
    public ImmutableSortedSet<E> subSet(final E fromElement, final E toElement) {
        return this.subSet(fromElement, true, toElement, false);
    }
    
    ImmutableSortedSet<E> subSet(final E fromElement, final boolean fromInclusive, final E toElement, final boolean toInclusive) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(this.comparator.compare((Object)fromElement, (Object)toElement) <= 0);
        return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
    }
    
    public ImmutableSortedSet<E> tailSet(final E fromElement) {
        return this.tailSet(fromElement, true);
    }
    
    ImmutableSortedSet<E> tailSet(final E fromElement, final boolean inclusive) {
        return this.tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
    }
    
    abstract ImmutableSortedSet<E> headSetImpl(final E p0, final boolean p1);
    
    abstract ImmutableSortedSet<E> subSetImpl(final E p0, final boolean p1, final E p2, final boolean p3);
    
    abstract ImmutableSortedSet<E> tailSetImpl(final E p0, final boolean p1);
    
    abstract int indexOf(@Nullable final Object p0);
    
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
    
    Object writeReplace() {
        return new SerializedForm((Comparator<? super Object>)this.comparator, this.toArray());
    }
    
    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_SET = new EmptyImmutableSortedSet<Comparable>(ImmutableSortedSet.NATURAL_ORDER);
    }
    
    public static final class Builder<E> extends ImmutableSet.Builder<E>
    {
        private final Comparator<? super E> comparator;
        
        public Builder(final Comparator<? super E> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }
        
        public Builder<E> add(final E element) {
            super.add(element);
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
        
        public ImmutableSortedSet<E> build() {
            return (ImmutableSortedSet<E>)copyOfInternal((Comparator<? super Object>)this.comparator, (Iterator<?>)this.contents.iterator());
        }
    }
    
    private static class SerializedForm<E> implements Serializable
    {
        final Comparator<? super E> comparator;
        final Object[] elements;
        private static final long serialVersionUID = 0L;
        
        public SerializedForm(final Comparator<? super E> comparator, final Object[] elements) {
            this.comparator = comparator;
            this.elements = elements;
        }
        
        Object readResolve() {
            return new Builder<Object>((Comparator<? super Object>)this.comparator).add((Object[])this.elements).build();
        }
    }
}
