// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Enumeration;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Arrays;
import java.util.NoSuchElementException;
import com.google.common.annotations.GwtIncompatible;
import java.util.List;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import java.util.Collection;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class Iterators
{
    static final UnmodifiableIterator<Object> EMPTY_ITERATOR;
    private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR;
    
    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return (UnmodifiableIterator<T>)Iterators.EMPTY_ITERATOR;
    }
    
    static <T> Iterator<T> emptyModifiableIterator() {
        return (Iterator<T>)Iterators.EMPTY_MODIFIABLE_ITERATOR;
    }
    
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        if (iterator instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator<T>)(UnmodifiableIterator)iterator;
        }
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public T next() {
                return iterator.next();
            }
        };
    }
    
    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final UnmodifiableIterator<T> iterator) {
        return Preconditions.checkNotNull(iterator);
    }
    
    public static int size(final Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            ++count;
        }
        return count;
    }
    
    public static boolean contains(final Iterator<?> iterator, @Nullable final Object element) {
        if (element == null) {
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    return true;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (element.equals(iterator.next())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean removeAll(final Iterator<?> removeFrom, final Collection<?> elementsToRemove) {
        Preconditions.checkNotNull(elementsToRemove);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (elementsToRemove.contains(removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public static <T> boolean removeIf(final Iterator<T> removeFrom, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (predicate.apply((Object)removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public static boolean retainAll(final Iterator<?> removeFrom, final Collection<?> elementsToRetain) {
        Preconditions.checkNotNull(elementsToRetain);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (!elementsToRetain.contains(removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    public static boolean elementsEqual(final Iterator<?> iterator1, final Iterator<?> iterator2) {
        while (iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
                return false;
            }
            final Object o1 = iterator1.next();
            final Object o2 = iterator2.next();
            if (!Objects.equal(o1, o2)) {
                return false;
            }
        }
        return !iterator2.hasNext();
    }
    
    public static String toString(final Iterator<?> iterator) {
        if (!iterator.hasNext()) {
            return "[]";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append('[').append(iterator.next());
        while (iterator.hasNext()) {
            builder.append(", ").append(iterator.next());
        }
        return builder.append(']').toString();
    }
    
    public static <T> T getOnlyElement(final Iterator<T> iterator) {
        final T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <" + first);
        for (int i = 0; i < 4 && iterator.hasNext(); ++i) {
            sb.append(", " + iterator.next());
        }
        if (iterator.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }
    
    public static <T> T getOnlyElement(final Iterator<T> iterator, @Nullable final T defaultValue) {
        return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
    }
    
    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] toArray(final Iterator<? extends T> iterator, final Class<T> type) {
        final List<T> list = (List<T>)Lists.newArrayList((Iterator<?>)iterator);
        return Iterables.toArray((Iterable<? extends T>)list, type);
    }
    
    public static <T> boolean addAll(final Collection<T> addTo, final Iterator<? extends T> iterator) {
        Preconditions.checkNotNull(addTo);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add((T)iterator.next());
        }
        return wasModified;
    }
    
    public static int frequency(final Iterator<?> iterator, @Nullable final Object element) {
        int result = 0;
        if (element == null) {
            while (iterator.hasNext()) {
                if (iterator.next() == null) {
                    ++result;
                }
            }
        }
        else {
            while (iterator.hasNext()) {
                if (element.equals(iterator.next())) {
                    ++result;
                }
            }
        }
        return result;
    }
    
    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>() {
            Iterator<T> iterator = Iterators.emptyIterator();
            Iterator<T> removeFrom;
            
            public boolean hasNext() {
                if (!this.iterator.hasNext()) {
                    this.iterator = iterable.iterator();
                }
                return this.iterator.hasNext();
            }
            
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.iterator;
                return this.iterator.next();
            }
            
            public void remove() {
                Preconditions.checkState(this.removeFrom != null, (Object)"no calls to next() since last call to remove()");
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }
    
    public static <T> Iterator<T> cycle(final T... elements) {
        return cycle(Lists.newArrayList(elements));
    }
    
    public static <T> Iterator<T> concat(final Iterator<? extends T> a, final Iterator<? extends T> b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        return concat((Iterator<? extends Iterator<? extends T>>)Arrays.asList(a, b).iterator());
    }
    
    public static <T> Iterator<T> concat(final Iterator<? extends T> a, final Iterator<? extends T> b, final Iterator<? extends T> c) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        return concat((Iterator<? extends Iterator<? extends T>>)Arrays.asList(a, b, c).iterator());
    }
    
    public static <T> Iterator<T> concat(final Iterator<? extends T> a, final Iterator<? extends T> b, final Iterator<? extends T> c, final Iterator<? extends T> d) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(d);
        return concat((Iterator<? extends Iterator<? extends T>>)Arrays.asList(a, b, c, d).iterator());
    }
    
    public static <T> Iterator<T> concat(final Iterator<? extends T>... inputs) {
        return concat((Iterator<? extends Iterator<? extends T>>)ImmutableList.copyOf(inputs).iterator());
    }
    
    public static <T> Iterator<T> concat(final Iterator<? extends Iterator<? extends T>> inputs) {
        Preconditions.checkNotNull(inputs);
        return new Iterator<T>() {
            Iterator<? extends T> current = Iterators.emptyIterator();
            Iterator<? extends T> removeFrom;
            
            public boolean hasNext() {
                boolean currentHasNext;
                while (!(currentHasNext = Preconditions.checkNotNull(this.current).hasNext()) && inputs.hasNext()) {
                    this.current = inputs.next();
                }
                return currentHasNext;
            }
            
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.removeFrom = this.current;
                return (T)this.current.next();
            }
            
            public void remove() {
                Preconditions.checkState(this.removeFrom != null, (Object)"no calls to next() since last call to remove()");
                this.removeFrom.remove();
                this.removeFrom = null;
            }
        };
    }
    
    public static <T> UnmodifiableIterator<List<T>> partition(final Iterator<T> iterator, final int size) {
        return partitionImpl(iterator, size, false);
    }
    
    public static <T> UnmodifiableIterator<List<T>> paddedPartition(final Iterator<T> iterator, final int size) {
        return partitionImpl(iterator, size, true);
    }
    
    private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator, final int size, final boolean pad) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(size > 0);
        return new UnmodifiableIterator<List<T>>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public List<T> next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Object[] array = new Object[size];
                int count;
                for (count = 0; count < size && iterator.hasNext(); ++count) {
                    array[count] = iterator.next();
                }
                for (int i = count; i < size; ++i) {
                    array[i] = null;
                }
                final List<T> list = Collections.unmodifiableList((List<? extends T>)Arrays.asList((T[])array));
                return (pad || count == size) ? list : list.subList(0, count);
            }
        };
    }
    
    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>() {
            protected T computeNext() {
                while (unfiltered.hasNext()) {
                    final T element = unfiltered.next();
                    if (predicate.apply(element)) {
                        return element;
                    }
                }
                return this.endOfData();
            }
        };
    }
    
    @GwtIncompatible("Class.isInstance")
    public static <T> UnmodifiableIterator<T> filter(final Iterator<?> unfiltered, final Class<T> type) {
        return filter(unfiltered, Predicates.instanceOf(type));
    }
    
    public static <T> boolean any(final Iterator<T> iterator, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            final T element = iterator.next();
            if (predicate.apply((Object)element)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean all(final Iterator<T> iterator, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            final T element = iterator.next();
            if (!predicate.apply((Object)element)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> T find(final Iterator<T> iterator, final Predicate<? super T> predicate) {
        return filter(iterator, predicate).next();
    }
    
    public static <T> T find(final Iterator<T> iterator, final Predicate<? super T> predicate, @Nullable final T defaultValue) {
        final UnmodifiableIterator<T> filteredIterator = filter(iterator, predicate);
        return filteredIterator.hasNext() ? filteredIterator.next() : defaultValue;
    }
    
    public static <T> int indexOf(final Iterator<T> iterator, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, (Object)"predicate");
        int i = 0;
        while (iterator.hasNext()) {
            final T current = iterator.next();
            if (predicate.apply((Object)current)) {
                return i;
            }
            ++i;
        }
        return -1;
    }
    
    public static <F, T> Iterator<T> transform(final Iterator<F> fromIterator, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(fromIterator);
        Preconditions.checkNotNull(function);
        return new Iterator<T>() {
            public boolean hasNext() {
                return fromIterator.hasNext();
            }
            
            public T next() {
                final F from = fromIterator.next();
                return function.apply(from);
            }
            
            public void remove() {
                fromIterator.remove();
            }
        };
    }
    
    public static <T> T get(final Iterator<T> iterator, final int position) {
        checkNonnegative(position);
        int skipped = 0;
        while (iterator.hasNext()) {
            final T t = iterator.next();
            if (skipped++ == position) {
                return t;
            }
        }
        throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
    }
    
    private static void checkNonnegative(final int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
        }
    }
    
    public static <T> T get(final Iterator<T> iterator, final int position, @Nullable final T defaultValue) {
        checkNonnegative(position);
        try {
            return get(iterator, position);
        }
        catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }
    
    public static <T> T getNext(final Iterator<T> iterator, @Nullable final T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }
    
    public static <T> T getLast(final Iterator<T> iterator) {
        T current;
        do {
            current = iterator.next();
        } while (iterator.hasNext());
        return current;
    }
    
    public static <T> T getLast(final Iterator<T> iterator, @Nullable final T defaultValue) {
        return iterator.hasNext() ? getLast(iterator) : defaultValue;
    }
    
    @Beta
    public static <T> int skip(final Iterator<T> iterator, final int numberToSkip) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(numberToSkip >= 0, (Object)"number to skip cannot be negative");
        int i;
        for (i = 0; i < numberToSkip && iterator.hasNext(); ++i) {
            iterator.next();
        }
        return i;
    }
    
    public static <T> Iterator<T> limit(final Iterator<T> iterator, final int limitSize) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(limitSize >= 0, (Object)"limit is negative");
        return new Iterator<T>() {
            private int count;
            
            public boolean hasNext() {
                return this.count < limitSize && iterator.hasNext();
            }
            
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.count;
                return iterator.next();
            }
            
            public void remove() {
                iterator.remove();
            }
        };
    }
    
    public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }
            
            public T next() {
                final T next = iterator.next();
                iterator.remove();
                return next;
            }
        };
    }
    
    static void clear(final Iterator<?> iterator) {
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
    
    public static <T> UnmodifiableIterator<T> forArray(final T... array) {
        Preconditions.checkNotNull(array);
        return new AbstractIndexedListIterator<T>(array.length) {
            protected T get(final int index) {
                return array[index];
            }
        };
    }
    
    static <T> UnmodifiableIterator<T> forArray(final T[] array, final int offset, final int length) {
        Preconditions.checkArgument(length >= 0);
        final int end = offset + length;
        Preconditions.checkPositionIndexes(offset, end, array.length);
        return new AbstractIndexedListIterator<T>(length) {
            protected T get(final int index) {
                return array[offset + index];
            }
        };
    }
    
    public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable final T value) {
        return new UnmodifiableIterator<T>() {
            boolean done;
            
            public boolean hasNext() {
                return !this.done;
            }
            
            public T next() {
                if (this.done) {
                    throw new NoSuchElementException();
                }
                this.done = true;
                return value;
            }
        };
    }
    
    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>() {
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }
            
            public T next() {
                return enumeration.nextElement();
            }
        };
    }
    
    public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new Enumeration<T>() {
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }
            
            public T nextElement() {
                return iterator.next();
            }
        };
    }
    
    public static <T> PeekingIterator<T> peekingIterator(final Iterator<? extends T> iterator) {
        if (iterator instanceof PeekingImpl) {
            final PeekingImpl<T> peeking = (PeekingImpl<T>)(PeekingImpl)iterator;
            return peeking;
        }
        return new PeekingImpl<T>(iterator);
    }
    
    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(final PeekingIterator<T> iterator) {
        return Preconditions.checkNotNull(iterator);
    }
    
    static {
        EMPTY_ITERATOR = new UnmodifiableIterator<Object>() {
            public boolean hasNext() {
                return false;
            }
            
            public Object next() {
                throw new NoSuchElementException();
            }
        };
        EMPTY_MODIFIABLE_ITERATOR = new Iterator<Object>() {
            public boolean hasNext() {
                return false;
            }
            
            public Object next() {
                throw new NoSuchElementException();
            }
            
            public void remove() {
                throw new IllegalStateException();
            }
        };
    }
    
    private static class PeekingImpl<E> implements PeekingIterator<E>
    {
        private final Iterator<? extends E> iterator;
        private boolean hasPeeked;
        private E peekedElement;
        
        public PeekingImpl(final Iterator<? extends E> iterator) {
            this.iterator = Preconditions.checkNotNull(iterator);
        }
        
        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }
        
        public E next() {
            if (!this.hasPeeked) {
                return (E)this.iterator.next();
            }
            final E result = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return result;
        }
        
        public void remove() {
            Preconditions.checkState(!this.hasPeeked, (Object)"Can't remove after you've peeked at next");
            this.iterator.remove();
        }
        
        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = (E)this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }
}
