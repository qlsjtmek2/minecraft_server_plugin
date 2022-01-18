// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.SortedMap;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class SortedIterables
{
    public static boolean hasSameComparator(final Comparator<?> comparator, final Iterable<?> elements) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(elements);
        Comparator<?> comparator2;
        if (elements instanceof SortedSet) {
            final SortedSet<?> sortedSet = (SortedSet<?>)(SortedSet)elements;
            comparator2 = sortedSet.comparator();
            if (comparator2 == null) {
                comparator2 = Ordering.natural();
            }
        }
        else if (elements instanceof SortedIterable) {
            comparator2 = (Comparator<?>)((SortedIterable)elements).comparator();
        }
        else {
            comparator2 = null;
        }
        return comparator.equals(comparator2);
    }
    
    public static <E> Collection<E> sortedUnique(final Comparator<? super E> comparator, final Iterator<E> elements) {
        final SortedSet<E> sortedSet = (SortedSet<E>)Sets.newTreeSet((Comparator<? super Object>)comparator);
        Iterators.addAll(sortedSet, (Iterator<? extends E>)elements);
        return sortedSet;
    }
    
    public static <E> Collection<E> sortedUnique(final Comparator<? super E> comparator, Iterable<E> elements) {
        if (elements instanceof Multiset) {
            elements = (Iterable<E>)((Multiset)elements).elementSet();
        }
        if (!(elements instanceof Set)) {
            final E[] array = (E[])Iterables.toArray(elements);
            if (!hasSameComparator(comparator, elements)) {
                Arrays.sort(array, comparator);
            }
            return uniquifySortedArray(comparator, array);
        }
        if (hasSameComparator(comparator, elements)) {
            return (Collection<E>)(Set)elements;
        }
        final List<E> list = (List<E>)Lists.newArrayList((Iterable<?>)elements);
        Collections.sort(list, comparator);
        return list;
    }
    
    private static <E> Collection<E> uniquifySortedArray(final Comparator<? super E> comparator, E[] array) {
        if (array.length == 0) {
            return (Collection<E>)Collections.emptySet();
        }
        int length = 1;
        for (int i = 1; i < array.length; ++i) {
            final int cmp = comparator.compare((Object)array[i], (Object)array[length - 1]);
            if (cmp != 0) {
                array[length++] = array[i];
            }
        }
        if (length < array.length) {
            array = ObjectArrays.arraysCopyOf(array, length);
        }
        return Arrays.asList(array);
    }
    
    public static <E> Collection<Multiset.Entry<E>> sortedCounts(final Comparator<? super E> comparator, final Iterator<E> elements) {
        final TreeMultiset<E> multiset = TreeMultiset.create(comparator);
        Iterators.addAll(multiset, (Iterator<? extends E>)elements);
        return (Collection<Multiset.Entry<E>>)multiset.entrySet();
    }
    
    public static <E> Collection<Multiset.Entry<E>> sortedCounts(final Comparator<? super E> comparator, final Iterable<E> elements) {
        if (elements instanceof Multiset) {
            final Multiset<E> multiset = (Multiset<E>)(Multiset)elements;
            if (hasSameComparator(comparator, elements)) {
                return multiset.entrySet();
            }
            final List<Multiset.Entry<E>> entries = (List<Multiset.Entry<E>>)Lists.newArrayList((Iterable<?>)multiset.entrySet());
            Collections.sort(entries, Ordering.from(comparator).onResultOf((Function<Object, ? extends E>)new Function<Multiset.Entry<E>, E>() {
                public E apply(final Multiset.Entry<E> entry) {
                    return entry.getElement();
                }
            }));
            return entries;
        }
        else {
            if (elements instanceof Set) {
                Collection<E> sortedElements;
                if (hasSameComparator(comparator, elements)) {
                    sortedElements = (Collection<E>)(Collection)elements;
                }
                else {
                    final List<E> list = (List<E>)Lists.newArrayList((Iterable<?>)elements);
                    Collections.sort(list, comparator);
                    sortedElements = list;
                }
                return singletonEntries(sortedElements);
            }
            if (hasSameComparator(comparator, elements)) {
                E current = null;
                int currentCount = 0;
                final List<Multiset.Entry<E>> sortedEntries = (List<Multiset.Entry<E>>)Lists.newArrayList();
                for (final E e : elements) {
                    if (currentCount > 0) {
                        if (comparator.compare((Object)current, (Object)e) == 0) {
                            ++currentCount;
                        }
                        else {
                            sortedEntries.add(Multisets.immutableEntry(current, currentCount));
                            current = e;
                            currentCount = 1;
                        }
                    }
                    else {
                        current = e;
                        currentCount = 1;
                    }
                }
                if (currentCount > 0) {
                    sortedEntries.add(Multisets.immutableEntry(current, currentCount));
                }
                return sortedEntries;
            }
            final TreeMultiset<E> multiset2 = TreeMultiset.create(comparator);
            Iterables.addAll(multiset2, (Iterable<? extends E>)elements);
            return (Collection<Multiset.Entry<E>>)multiset2.entrySet();
        }
    }
    
    static <E> Collection<Multiset.Entry<E>> singletonEntries(final Collection<E> set) {
        return Collections2.transform(set, (Function<? super E, Multiset.Entry<E>>)new Function<E, Multiset.Entry<E>>() {
            public Multiset.Entry<E> apply(final E elem) {
                return Multisets.immutableEntry(elem, 1);
            }
        });
    }
}
