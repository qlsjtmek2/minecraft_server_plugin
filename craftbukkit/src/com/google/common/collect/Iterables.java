// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Queue;
import java.util.SortedSet;
import java.util.NoSuchElementException;
import com.google.common.base.Function;
import java.util.Arrays;
import java.util.Set;
import java.util.Iterator;
import com.google.common.annotations.GwtIncompatible;
import java.util.List;
import java.util.RandomAccess;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import java.util.Collection;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class Iterables
{
    public static <T> Iterable<T> unmodifiableIterable(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof UnmodifiableIterable || iterable instanceof ImmutableCollection) {
            return iterable;
        }
        return new UnmodifiableIterable<T>((Iterable)iterable);
    }
    
    @Deprecated
    public static <E> Iterable<E> unmodifiableIterable(final ImmutableCollection<E> iterable) {
        return Preconditions.checkNotNull(iterable);
    }
    
    public static int size(final Iterable<?> iterable) {
        return (iterable instanceof Collection) ? ((Collection)iterable).size() : Iterators.size(iterable.iterator());
    }
    
    public static boolean contains(final Iterable<?> iterable, @Nullable final Object element) {
        if (iterable instanceof Collection) {
            final Collection<?> collection = (Collection<?>)(Collection)iterable;
            try {
                return collection.contains(element);
            }
            catch (NullPointerException e) {
                return false;
            }
            catch (ClassCastException e2) {
                return false;
            }
        }
        return Iterators.contains(iterable.iterator(), element);
    }
    
    public static boolean removeAll(final Iterable<?> removeFrom, final Collection<?> elementsToRemove) {
        return (removeFrom instanceof Collection) ? ((Collection)removeFrom).removeAll(Preconditions.checkNotNull(elementsToRemove)) : Iterators.removeAll(removeFrom.iterator(), elementsToRemove);
    }
    
    public static boolean retainAll(final Iterable<?> removeFrom, final Collection<?> elementsToRetain) {
        return (removeFrom instanceof Collection) ? ((Collection)removeFrom).retainAll(Preconditions.checkNotNull(elementsToRetain)) : Iterators.retainAll(removeFrom.iterator(), elementsToRetain);
    }
    
    public static <T> boolean removeIf(final Iterable<T> removeFrom, final Predicate<? super T> predicate) {
        if (removeFrom instanceof RandomAccess && removeFrom instanceof List) {
            return removeIfFromRandomAccessList((List<Object>)(List)removeFrom, Preconditions.checkNotNull(predicate));
        }
        return Iterators.removeIf(removeFrom.iterator(), predicate);
    }
    
    private static <T> boolean removeIfFromRandomAccessList(final List<T> list, final Predicate<? super T> predicate) {
        int from = 0;
        int to = 0;
        while (from < list.size()) {
            final T element = list.get(from);
            if (!predicate.apply((Object)element)) {
                if (from > to) {
                    try {
                        list.set(to, element);
                    }
                    catch (UnsupportedOperationException e) {
                        slowRemoveIfForRemainingElements(list, predicate, to, from);
                        return true;
                    }
                }
                ++to;
            }
            ++from;
        }
        list.subList(to, list.size()).clear();
        return from != to;
    }
    
    private static <T> void slowRemoveIfForRemainingElements(final List<T> list, final Predicate<? super T> predicate, final int to, final int from) {
        for (int n = list.size() - 1; n > from; --n) {
            if (predicate.apply((Object)list.get(n))) {
                list.remove(n);
            }
        }
        for (int n = from - 1; n >= to; --n) {
            list.remove(n);
        }
    }
    
    public static boolean elementsEqual(final Iterable<?> iterable1, final Iterable<?> iterable2) {
        return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator());
    }
    
    public static String toString(final Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator());
    }
    
    public static <T> T getOnlyElement(final Iterable<T> iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }
    
    public static <T> T getOnlyElement(final Iterable<T> iterable, @Nullable final T defaultValue) {
        return Iterators.getOnlyElement(iterable.iterator(), defaultValue);
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] toArray(final Iterable<? extends T> iterable, final Class<T> type) {
        final Collection<? extends T> collection = toCollection(iterable);
        final T[] array = ObjectArrays.newArray(type, collection.size());
        return collection.toArray(array);
    }
    
    static Object[] toArray(final Iterable<?> iterable) {
        return toCollection(iterable).toArray();
    }
    
    private static <E> Collection<E> toCollection(final Iterable<E> iterable) {
        return (Collection<E>)((iterable instanceof Collection) ? ((Collection)iterable) : Lists.newArrayList((Iterator<?>)iterable.iterator()));
    }
    
    public static <T> boolean addAll(final Collection<T> addTo, final Iterable<? extends T> elementsToAdd) {
        if (elementsToAdd instanceof Collection) {
            final Collection<? extends T> c = Collections2.cast(elementsToAdd);
            return addTo.addAll(c);
        }
        return Iterators.addAll(addTo, elementsToAdd.iterator());
    }
    
    public static int frequency(final Iterable<?> iterable, @Nullable final Object element) {
        if (iterable instanceof Multiset) {
            return ((Multiset)iterable).count(element);
        }
        if (iterable instanceof Set) {
            return ((Set)iterable).contains(element) ? 1 : 0;
        }
        return Iterators.frequency(iterable.iterator(), element);
    }
    
    public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.cycle(iterable);
            }
            
            public String toString() {
                return iterable.toString() + " (cycled)";
            }
        };
    }
    
    public static <T> Iterable<T> cycle(final T... elements) {
        return cycle(Lists.newArrayList(elements));
    }
    
    public static <T> Iterable<T> concat(final Iterable<? extends T> a, final Iterable<? extends T> b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        return concat((Iterable<? extends Iterable<? extends T>>)Arrays.asList(a, b));
    }
    
    public static <T> Iterable<T> concat(final Iterable<? extends T> a, final Iterable<? extends T> b, final Iterable<? extends T> c) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        return concat((Iterable<? extends Iterable<? extends T>>)Arrays.asList(a, b, c));
    }
    
    public static <T> Iterable<T> concat(final Iterable<? extends T> a, final Iterable<? extends T> b, final Iterable<? extends T> c, final Iterable<? extends T> d) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(d);
        return concat((Iterable<? extends Iterable<? extends T>>)Arrays.asList(a, b, c, d));
    }
    
    public static <T> Iterable<T> concat(final Iterable<? extends T>... inputs) {
        return concat((Iterable<? extends Iterable<? extends T>>)ImmutableList.copyOf(inputs));
    }
    
    public static <T> Iterable<T> concat(final Iterable<? extends Iterable<? extends T>> inputs) {
        Preconditions.checkNotNull(inputs);
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat((Iterator<? extends Iterator<? extends T>>)iterators((Iterable<? extends Iterable<?>>)inputs));
            }
        };
    }
    
    private static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(final Iterable<? extends Iterable<? extends T>> iterables) {
        final Iterator<? extends Iterable<? extends T>> iterableIterator = iterables.iterator();
        return new UnmodifiableIterator<Iterator<? extends T>>() {
            public boolean hasNext() {
                return iterableIterator.hasNext();
            }
            
            public Iterator<? extends T> next() {
                return iterableIterator.next().iterator();
            }
        };
    }
    
    public static <T> Iterable<List<T>> partition(final Iterable<T> iterable, final int size) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(size > 0);
        return new IterableWithToString<List<T>>() {
            public Iterator<List<T>> iterator() {
                return (Iterator<List<T>>)Iterators.partition(iterable.iterator(), size);
            }
        };
    }
    
    public static <T> Iterable<List<T>> paddedPartition(final Iterable<T> iterable, final int size) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(size > 0);
        return new IterableWithToString<List<T>>() {
            public Iterator<List<T>> iterator() {
                return (Iterator<List<T>>)Iterators.paddedPartition(iterable.iterator(), size);
            }
        };
    }
    
    public static <T> Iterable<T> filter(final Iterable<T> unfiltered, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(predicate);
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                return (Iterator<T>)Iterators.filter(unfiltered.iterator(), predicate);
            }
        };
    }
    
    @GwtIncompatible("Class.isInstance")
    public static <T> Iterable<T> filter(final Iterable<?> unfiltered, final Class<T> type) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(type);
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                return (Iterator<T>)Iterators.filter(unfiltered.iterator(), (Class<Object>)type);
            }
        };
    }
    
    public static <T> boolean any(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }
    
    public static <T> boolean all(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return Iterators.all(iterable.iterator(), predicate);
    }
    
    public static <T> T find(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return Iterators.find(iterable.iterator(), predicate);
    }
    
    public static <T> T find(final Iterable<T> iterable, final Predicate<? super T> predicate, @Nullable final T defaultValue) {
        return Iterators.find(iterable.iterator(), predicate, defaultValue);
    }
    
    public static <T> int indexOf(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return Iterators.indexOf(iterable.iterator(), predicate);
    }
    
    public static <F, T> Iterable<T> transform(final Iterable<F> fromIterable, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(fromIterable);
        Preconditions.checkNotNull(function);
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                return Iterators.transform(fromIterable.iterator(), (Function<? super Object, ? extends T>)function);
            }
        };
    }
    
    public static <T> T get(final Iterable<T> iterable, final int position) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof List) {
            return ((List)iterable).get(position);
        }
        if (iterable instanceof Collection) {
            final Collection<T> collection = (Collection<T>)(Collection)iterable;
            Preconditions.checkElementIndex(position, collection.size());
        }
        else {
            checkNonnegativeIndex(position);
        }
        return Iterators.get(iterable.iterator(), position);
    }
    
    private static void checkNonnegativeIndex(final int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("position cannot be negative: " + position);
        }
    }
    
    public static <T> T get(final Iterable<T> iterable, final int position, @Nullable final T defaultValue) {
        Preconditions.checkNotNull(iterable);
        checkNonnegativeIndex(position);
        try {
            return get(iterable, position);
        }
        catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }
    
    public static <T> T getFirst(final Iterable<T> iterable, @Nullable final T defaultValue) {
        return Iterators.getNext(iterable.iterator(), defaultValue);
    }
    
    public static <T> T getLast(final Iterable<T> iterable) {
        if (iterable instanceof List) {
            final List<T> list = (List<T>)(List)iterable;
            if (list.isEmpty()) {
                throw new NoSuchElementException();
            }
            return getLastInNonemptyList(list);
        }
        else {
            if (iterable instanceof SortedSet) {
                final SortedSet<T> sortedSet = (SortedSet<T>)(SortedSet)iterable;
                return sortedSet.last();
            }
            return Iterators.getLast(iterable.iterator());
        }
    }
    
    public static <T> T getLast(final Iterable<T> iterable, @Nullable final T defaultValue) {
        if (iterable instanceof Collection) {
            final Collection<T> collection = (Collection<T>)(Collection)iterable;
            if (collection.isEmpty()) {
                return defaultValue;
            }
        }
        if (iterable instanceof List) {
            final List<T> list = (List<T>)(List)iterable;
            return getLastInNonemptyList(list);
        }
        if (iterable instanceof SortedSet) {
            final SortedSet<T> sortedSet = (SortedSet<T>)(SortedSet)iterable;
            return sortedSet.last();
        }
        return Iterators.getLast(iterable.iterator(), defaultValue);
    }
    
    private static <T> T getLastInNonemptyList(final List<T> list) {
        return list.get(list.size() - 1);
    }
    
    public static <T> Iterable<T> skip(final Iterable<T> iterable, final int numberToSkip) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(numberToSkip >= 0, (Object)"number to skip cannot be negative");
        if (iterable instanceof List) {
            final List<T> list = (List<T>)(List)iterable;
            return new IterableWithToString<T>() {
                public Iterator<T> iterator() {
                    return (Iterator<T>)((numberToSkip >= list.size()) ? Iterators.emptyIterator() : list.subList(numberToSkip, list.size()).iterator());
                }
            };
        }
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                final Iterator<T> iterator = iterable.iterator();
                Iterators.skip(iterator, numberToSkip);
                return new Iterator<T>() {
                    boolean atStart = true;
                    
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }
                    
                    public T next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        try {
                            return iterator.next();
                        }
                        finally {
                            this.atStart = false;
                        }
                    }
                    
                    public void remove() {
                        if (this.atStart) {
                            throw new IllegalStateException();
                        }
                        iterator.remove();
                    }
                };
            }
        };
    }
    
    public static <T> Iterable<T> limit(final Iterable<T> iterable, final int limitSize) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(limitSize >= 0, (Object)"limit is negative");
        return new IterableWithToString<T>() {
            public Iterator<T> iterator() {
                return Iterators.limit(iterable.iterator(), limitSize);
            }
        };
    }
    
    public static <T> Iterable<T> consumingIterable(final Iterable<T> iterable) {
        if (iterable instanceof Queue) {
            return new Iterable<T>() {
                public Iterator<T> iterator() {
                    return new ConsumingQueueIterator<T>((Queue)iterable);
                }
            };
        }
        Preconditions.checkNotNull(iterable);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.consumingIterator(iterable.iterator());
            }
        };
    }
    
    @Deprecated
    public static <T> Iterable<T> reverse(final List<T> list) {
        return Lists.reverse(list);
    }
    
    public static <T> boolean isEmpty(final Iterable<T> iterable) {
        return !iterable.iterator().hasNext();
    }
    
    static boolean remove(final Iterable<?> iterable, @Nullable final Object o) {
        final Iterator<?> i = iterable.iterator();
        while (i.hasNext()) {
            if (Objects.equal(i.next(), o)) {
                i.remove();
                return true;
            }
        }
        return false;
    }
    
    private static final class UnmodifiableIterable<T> implements Iterable<T>
    {
        private final Iterable<T> iterable;
        
        private UnmodifiableIterable(final Iterable<T> iterable) {
            this.iterable = iterable;
        }
        
        public Iterator<T> iterator() {
            return Iterators.unmodifiableIterator(this.iterable.iterator());
        }
        
        public String toString() {
            return this.iterable.toString();
        }
    }
    
    private static class ConsumingQueueIterator<T> extends AbstractIterator<T>
    {
        private final Queue<T> queue;
        
        private ConsumingQueueIterator(final Queue<T> queue) {
            this.queue = queue;
        }
        
        public T computeNext() {
            try {
                return this.queue.remove();
            }
            catch (NoSuchElementException e) {
                return this.endOfData();
            }
        }
    }
    
    abstract static class IterableWithToString<E> implements Iterable<E>
    {
        public String toString() {
            return Iterables.toString(this);
        }
    }
}
