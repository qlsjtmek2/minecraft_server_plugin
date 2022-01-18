// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import java.util.AbstractSequentialList;
import java.io.Serializable;
import java.util.AbstractList;
import com.google.common.base.Objects;
import java.util.ListIterator;
import com.google.common.annotations.Beta;
import java.util.RandomAccess;
import com.google.common.base.Function;
import java.util.List;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.Iterator;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.Collections;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Lists
{
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }
    
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(final E... elements) {
        Preconditions.checkNotNull(elements);
        final int capacity = computeArrayListCapacity(elements.length);
        final ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }
    
    @VisibleForTesting
    static int computeArrayListCapacity(final int arraySize) {
        Preconditions.checkArgument(arraySize >= 0);
        return Ints.saturatedCast(5L + arraySize + arraySize / 10);
    }
    
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(final Iterable<? extends E> elements) {
        Preconditions.checkNotNull(elements);
        return (elements instanceof Collection) ? new ArrayList<E>(Collections2.cast(elements)) : newArrayList(elements.iterator());
    }
    
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayList(final Iterator<? extends E> elements) {
        Preconditions.checkNotNull(elements);
        final ArrayList<E> list = newArrayList();
        while (elements.hasNext()) {
            list.add((E)elements.next());
        }
        return list;
    }
    
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithCapacity(final int initialArraySize) {
        Preconditions.checkArgument(initialArraySize >= 0);
        return new ArrayList<E>(initialArraySize);
    }
    
    @GwtCompatible(serializable = true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(final int estimatedSize) {
        return new ArrayList<E>(computeArrayListCapacity(estimatedSize));
    }
    
    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }
    
    @GwtCompatible(serializable = true)
    public static <E> LinkedList<E> newLinkedList(final Iterable<? extends E> elements) {
        final LinkedList<E> list = newLinkedList();
        for (final E element : elements) {
            list.add(element);
        }
        return list;
    }
    
    public static <E> List<E> asList(@Nullable final E first, final E[] rest) {
        return new OnePlusArrayList<E>(first, rest);
    }
    
    public static <E> List<E> asList(@Nullable final E first, @Nullable final E second, final E[] rest) {
        return new TwoPlusArrayList<E>(first, second, rest);
    }
    
    public static <F, T> List<T> transform(final List<F> fromList, final Function<? super F, ? extends T> function) {
        return (List<T>)((fromList instanceof RandomAccess) ? new TransformingRandomAccessList<Object, Object>(fromList, function) : new TransformingSequentialList<Object, Object>(fromList, function));
    }
    
    public static <T> List<List<T>> partition(final List<T> list, final int size) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(size > 0);
        return (List<List<T>>)((list instanceof RandomAccess) ? new RandomAccessPartition<Object>(list, size) : new Partition(list, size));
    }
    
    @Beta
    public static ImmutableList<Character> charactersOf(final String string) {
        return new StringAsImmutableList(Preconditions.checkNotNull(string));
    }
    
    @Beta
    public static List<Character> charactersOf(final CharSequence sequence) {
        return new CharSequenceAsList(Preconditions.checkNotNull(sequence));
    }
    
    public static <T> List<T> reverse(final List<T> list) {
        if (list instanceof ReverseList) {
            return ((ReverseList)list).getForwardList();
        }
        if (list instanceof RandomAccess) {
            return new RandomAccessReverseList<T>(list);
        }
        return new ReverseList<T>(list);
    }
    
    static int hashCodeImpl(final List<?> list) {
        int hashCode = 1;
        for (final Object o : list) {
            hashCode = 31 * hashCode + ((o == null) ? 0 : o.hashCode());
        }
        return hashCode;
    }
    
    static boolean equalsImpl(final List<?> list, @Nullable final Object object) {
        if (object == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        final List<?> o = (List<?>)object;
        return list.size() == o.size() && Iterators.elementsEqual(list.iterator(), o.iterator());
    }
    
    static <E> boolean addAllImpl(final List<E> list, final int index, final Iterable<? extends E> elements) {
        boolean changed = false;
        final ListIterator<E> listIterator = list.listIterator(index);
        for (final E e : elements) {
            listIterator.add(e);
            changed = true;
        }
        return changed;
    }
    
    static int indexOfImpl(final List<?> list, @Nullable final Object element) {
        final ListIterator<?> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            if (Objects.equal(element, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }
    
    static int lastIndexOfImpl(final List<?> list, @Nullable final Object element) {
        final ListIterator<?> listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            if (Objects.equal(element, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }
    
    static <E> ListIterator<E> listIteratorImpl(final List<E> list, final int index) {
        return new AbstractListWrapper<E>(list).listIterator(index);
    }
    
    static <E> List<E> subListImpl(final List<E> list, final int fromIndex, final int toIndex) {
        List<E> wrapper;
        if (list instanceof RandomAccess) {
            wrapper = new RandomAccessListWrapper<E>(list) {
                private static final long serialVersionUID = 0L;
                
                public ListIterator<E> listIterator(final int index) {
                    return (ListIterator<E>)this.backingList.listIterator(index);
                }
            };
        }
        else {
            wrapper = new AbstractListWrapper<E>(list) {
                private static final long serialVersionUID = 0L;
                
                public ListIterator<E> listIterator(final int index) {
                    return (ListIterator<E>)this.backingList.listIterator(index);
                }
            };
        }
        return wrapper.subList(fromIndex, toIndex);
    }
    
    private static class OnePlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess
    {
        final E first;
        final E[] rest;
        private static final long serialVersionUID = 0L;
        
        OnePlusArrayList(@Nullable final E first, final E[] rest) {
            this.first = first;
            this.rest = Preconditions.checkNotNull(rest);
        }
        
        public int size() {
            return this.rest.length + 1;
        }
        
        public E get(final int index) {
            Preconditions.checkElementIndex(index, this.size());
            return (E)((index == 0) ? this.first : this.rest[index - 1]);
        }
    }
    
    private static class TwoPlusArrayList<E> extends AbstractList<E> implements Serializable, RandomAccess
    {
        final E first;
        final E second;
        final E[] rest;
        private static final long serialVersionUID = 0L;
        
        TwoPlusArrayList(@Nullable final E first, @Nullable final E second, final E[] rest) {
            this.first = first;
            this.second = second;
            this.rest = Preconditions.checkNotNull(rest);
        }
        
        public int size() {
            return this.rest.length + 2;
        }
        
        public E get(final int index) {
            switch (index) {
                case 0: {
                    return this.first;
                }
                case 1: {
                    return this.second;
                }
                default: {
                    Preconditions.checkElementIndex(index, this.size());
                    return (E)this.rest[index - 2];
                }
            }
        }
    }
    
    private static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable
    {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;
        private static final long serialVersionUID = 0L;
        
        TransformingSequentialList(final List<F> fromList, final Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(fromList);
            this.function = Preconditions.checkNotNull(function);
        }
        
        public void clear() {
            this.fromList.clear();
        }
        
        public int size() {
            return this.fromList.size();
        }
        
        public ListIterator<T> listIterator(final int index) {
            final ListIterator<F> delegate = this.fromList.listIterator(index);
            return new ListIterator<T>() {
                public void add(final T e) {
                    throw new UnsupportedOperationException();
                }
                
                public boolean hasNext() {
                    return delegate.hasNext();
                }
                
                public boolean hasPrevious() {
                    return delegate.hasPrevious();
                }
                
                public T next() {
                    return (T)TransformingSequentialList.this.function.apply(delegate.next());
                }
                
                public int nextIndex() {
                    return delegate.nextIndex();
                }
                
                public T previous() {
                    return (T)TransformingSequentialList.this.function.apply(delegate.previous());
                }
                
                public int previousIndex() {
                    return delegate.previousIndex();
                }
                
                public void remove() {
                    delegate.remove();
                }
                
                public void set(final T e) {
                    throw new UnsupportedOperationException("not supported");
                }
            };
        }
    }
    
    private static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable
    {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;
        private static final long serialVersionUID = 0L;
        
        TransformingRandomAccessList(final List<F> fromList, final Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(fromList);
            this.function = Preconditions.checkNotNull(function);
        }
        
        public void clear() {
            this.fromList.clear();
        }
        
        public T get(final int index) {
            return (T)this.function.apply((Object)this.fromList.get(index));
        }
        
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }
        
        public T remove(final int index) {
            return (T)this.function.apply((Object)this.fromList.remove(index));
        }
        
        public int size() {
            return this.fromList.size();
        }
    }
    
    private static class Partition<T> extends AbstractList<List<T>>
    {
        final List<T> list;
        final int size;
        
        Partition(final List<T> list, final int size) {
            this.list = list;
            this.size = size;
        }
        
        public List<T> get(final int index) {
            final int listSize = this.size();
            Preconditions.checkElementIndex(index, listSize);
            final int start = index * this.size;
            final int end = Math.min(start + this.size, this.list.size());
            return this.list.subList(start, end);
        }
        
        public int size() {
            int result = this.list.size() / this.size;
            if (result * this.size != this.list.size()) {
                ++result;
            }
            return result;
        }
        
        public boolean isEmpty() {
            return this.list.isEmpty();
        }
    }
    
    private static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess
    {
        RandomAccessPartition(final List<T> list, final int size) {
            super(list, size);
        }
    }
    
    private static final class StringAsImmutableList extends ImmutableList<Character>
    {
        private final String string;
        int hash;
        
        StringAsImmutableList(final String string) {
            this.hash = 0;
            this.string = string;
        }
        
        public boolean contains(@Nullable final Object object) {
            return this.indexOf(object) >= 0;
        }
        
        public int indexOf(@Nullable final Object object) {
            return (object instanceof Character) ? this.string.indexOf((char)object) : -1;
        }
        
        public int lastIndexOf(@Nullable final Object object) {
            return (object instanceof Character) ? this.string.lastIndexOf((char)object) : -1;
        }
        
        public UnmodifiableListIterator<Character> listIterator(final int index) {
            return new AbstractIndexedListIterator<Character>(this.size(), index) {
                protected Character get(final int index) {
                    return StringAsImmutableList.this.string.charAt(index);
                }
            };
        }
        
        public ImmutableList<Character> subList(final int fromIndex, final int toIndex) {
            return Lists.charactersOf(this.string.substring(fromIndex, toIndex));
        }
        
        boolean isPartialView() {
            return false;
        }
        
        public Character get(final int index) {
            return this.string.charAt(index);
        }
        
        public int size() {
            return this.string.length();
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (!(obj instanceof List)) {
                return false;
            }
            final List<?> list = (List<?>)obj;
            final int n = this.string.length();
            if (n != list.size()) {
                return false;
            }
            final Iterator<?> iterator = list.iterator();
            for (int i = 0; i < n; ++i) {
                final Object elem = iterator.next();
                if (!(elem instanceof Character) || (char)elem != this.string.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        
        public int hashCode() {
            int h = this.hash;
            if (h == 0) {
                h = 1;
                for (int i = 0; i < this.string.length(); ++i) {
                    h = h * 31 + this.string.charAt(i);
                }
                this.hash = h;
            }
            return h;
        }
    }
    
    private static final class CharSequenceAsList extends AbstractList<Character>
    {
        private final CharSequence sequence;
        
        CharSequenceAsList(final CharSequence sequence) {
            this.sequence = sequence;
        }
        
        public Character get(final int index) {
            return this.sequence.charAt(index);
        }
        
        public boolean contains(@Nullable final Object o) {
            return this.indexOf(o) >= 0;
        }
        
        public int indexOf(@Nullable final Object o) {
            if (o instanceof Character) {
                final char c = (char)o;
                for (int i = 0; i < this.sequence.length(); ++i) {
                    if (this.sequence.charAt(i) == c) {
                        return i;
                    }
                }
            }
            return -1;
        }
        
        public int lastIndexOf(@Nullable final Object o) {
            if (o instanceof Character) {
                final char c = (char)o;
                for (int i = this.sequence.length() - 1; i >= 0; --i) {
                    if (this.sequence.charAt(i) == c) {
                        return i;
                    }
                }
            }
            return -1;
        }
        
        public int size() {
            return this.sequence.length();
        }
        
        public List<Character> subList(final int fromIndex, final int toIndex) {
            return Lists.charactersOf(this.sequence.subSequence(fromIndex, toIndex));
        }
        
        public int hashCode() {
            int hash = 1;
            for (int i = 0; i < this.sequence.length(); ++i) {
                hash = hash * 31 + this.sequence.charAt(i);
            }
            return hash;
        }
        
        public boolean equals(@Nullable final Object o) {
            if (!(o instanceof List)) {
                return false;
            }
            final List<?> list = (List<?>)o;
            final int n = this.sequence.length();
            if (n != list.size()) {
                return false;
            }
            final Iterator<?> iterator = list.iterator();
            for (int i = 0; i < n; ++i) {
                final Object elem = iterator.next();
                if (!(elem instanceof Character) || (char)elem != this.sequence.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private static class ReverseList<T> extends AbstractList<T>
    {
        private final List<T> forwardList;
        
        ReverseList(final List<T> forwardList) {
            this.forwardList = Preconditions.checkNotNull(forwardList);
        }
        
        List<T> getForwardList() {
            return this.forwardList;
        }
        
        private int reverseIndex(final int index) {
            final int size = this.size();
            Preconditions.checkElementIndex(index, size);
            return size - 1 - index;
        }
        
        private int reversePosition(final int index) {
            final int size = this.size();
            Preconditions.checkPositionIndex(index, size);
            return size - index;
        }
        
        public void add(final int index, @Nullable final T element) {
            this.forwardList.add(this.reversePosition(index), element);
        }
        
        public void clear() {
            this.forwardList.clear();
        }
        
        public T remove(final int index) {
            return this.forwardList.remove(this.reverseIndex(index));
        }
        
        protected void removeRange(final int fromIndex, final int toIndex) {
            this.subList(fromIndex, toIndex).clear();
        }
        
        public T set(final int index, @Nullable final T element) {
            return this.forwardList.set(this.reverseIndex(index), element);
        }
        
        public T get(final int index) {
            return this.forwardList.get(this.reverseIndex(index));
        }
        
        public boolean isEmpty() {
            return this.forwardList.isEmpty();
        }
        
        public int size() {
            return this.forwardList.size();
        }
        
        public boolean contains(@Nullable final Object o) {
            return this.forwardList.contains(o);
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.forwardList.containsAll(c);
        }
        
        public List<T> subList(final int fromIndex, final int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
            return Lists.reverse(this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex)));
        }
        
        public int indexOf(@Nullable final Object o) {
            final int index = this.forwardList.lastIndexOf(o);
            return (index >= 0) ? this.reverseIndex(index) : -1;
        }
        
        public int lastIndexOf(@Nullable final Object o) {
            final int index = this.forwardList.indexOf(o);
            return (index >= 0) ? this.reverseIndex(index) : -1;
        }
        
        public Iterator<T> iterator() {
            return this.listIterator();
        }
        
        public ListIterator<T> listIterator(final int index) {
            final int start = this.reversePosition(index);
            final ListIterator<T> forwardIterator = this.forwardList.listIterator(start);
            return new ListIterator<T>() {
                boolean canRemove;
                boolean canSet;
                
                public void add(final T e) {
                    forwardIterator.add(e);
                    forwardIterator.previous();
                    final boolean b = false;
                    this.canRemove = b;
                    this.canSet = b;
                }
                
                public boolean hasNext() {
                    return forwardIterator.hasPrevious();
                }
                
                public boolean hasPrevious() {
                    return forwardIterator.hasNext();
                }
                
                public T next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final boolean b = true;
                    this.canRemove = b;
                    this.canSet = b;
                    return forwardIterator.previous();
                }
                
                public int nextIndex() {
                    return ReverseList.this.reversePosition(forwardIterator.nextIndex());
                }
                
                public T previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    final boolean b = true;
                    this.canRemove = b;
                    this.canSet = b;
                    return forwardIterator.next();
                }
                
                public int previousIndex() {
                    return this.nextIndex() - 1;
                }
                
                public void remove() {
                    Preconditions.checkState(this.canRemove);
                    forwardIterator.remove();
                    final boolean b = false;
                    this.canSet = b;
                    this.canRemove = b;
                }
                
                public void set(final T e) {
                    Preconditions.checkState(this.canSet);
                    forwardIterator.set(e);
                }
            };
        }
    }
    
    private static class RandomAccessReverseList<T> extends ReverseList<T> implements RandomAccess
    {
        RandomAccessReverseList(final List<T> forwardList) {
            super(forwardList);
        }
    }
    
    private static class AbstractListWrapper<E> extends AbstractList<E>
    {
        final List<E> backingList;
        
        AbstractListWrapper(final List<E> backingList) {
            this.backingList = Preconditions.checkNotNull(backingList);
        }
        
        public void add(final int index, final E element) {
            this.backingList.add(index, element);
        }
        
        public boolean addAll(final int index, final Collection<? extends E> c) {
            return this.backingList.addAll(index, c);
        }
        
        public E get(final int index) {
            return this.backingList.get(index);
        }
        
        public E remove(final int index) {
            return this.backingList.remove(index);
        }
        
        public E set(final int index, final E element) {
            return this.backingList.set(index, element);
        }
        
        public boolean contains(final Object o) {
            return this.backingList.contains(o);
        }
        
        public int size() {
            return this.backingList.size();
        }
    }
    
    private static class RandomAccessListWrapper<E> extends AbstractListWrapper<E> implements RandomAccess
    {
        RandomAccessListWrapper(final List<E> backingList) {
            super(backingList);
        }
    }
}
