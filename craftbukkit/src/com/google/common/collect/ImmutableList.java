// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.ListIterator;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Collection;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;
import java.util.RandomAccess;
import java.util.List;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess
{
    public static <E> ImmutableList<E> of() {
        return (ImmutableList<E>)EmptyImmutableList.INSTANCE;
    }
    
    public static <E> ImmutableList<E> of(final E element) {
        return new SingletonImmutableList<E>(element);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2) {
        return construct(e1, e2);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3) {
        return construct(e1, e2, e3);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4) {
        return construct(e1, e2, e3, e4);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5) {
        return construct(e1, e2, e3, e4, e5);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6) {
        return construct(e1, e2, e3, e4, e5, e6);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7) {
        return construct(e1, e2, e3, e4, e5, e6, e7);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7, final E e8) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7, final E e8, final E e9) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7, final E e8, final E e9, final E e10) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7, final E e8, final E e9, final E e10, final E e11) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
    }
    
    public static <E> ImmutableList<E> of(final E e1, final E e2, final E e3, final E e4, final E e5, final E e6, final E e7, final E e8, final E e9, final E e10, final E e11, final E e12, final E... others) {
        final Object[] array = new Object[12 + others.length];
        array[0] = e1;
        array[1] = e2;
        array[2] = e3;
        array[3] = e4;
        array[4] = e5;
        array[5] = e6;
        array[6] = e7;
        array[7] = e8;
        array[8] = e9;
        array[9] = e10;
        array[10] = e11;
        array[11] = e12;
        System.arraycopy(others, 0, array, 12, others.length);
        return construct(array);
    }
    
    @Deprecated
    public static <E> ImmutableList<E> of(final E[] elements) {
        return (ImmutableList<E>)copyOf((Object[])elements);
    }
    
    public static <E> ImmutableList<E> copyOf(final Iterable<? extends E> elements) {
        Preconditions.checkNotNull(elements);
        return (elements instanceof Collection) ? copyOf((Collection<? extends E>)Collections2.cast((Iterable<? extends E>)elements)) : copyOf(elements.iterator());
    }
    
    public static <E> ImmutableList<E> copyOf(final Collection<? extends E> elements) {
        if (elements instanceof ImmutableCollection) {
            final ImmutableList<E> list = ((ImmutableCollection)elements).asList();
            return (ImmutableList<E>)(list.isPartialView() ? copyFromCollection((Collection<?>)list) : list);
        }
        return (ImmutableList<E>)copyFromCollection((Collection<?>)elements);
    }
    
    public static <E> ImmutableList<E> copyOf(final Iterator<? extends E> elements) {
        return copyFromCollection((Collection<? extends E>)Lists.newArrayList((Iterator<?>)elements));
    }
    
    public static <E> ImmutableList<E> copyOf(final E[] elements) {
        switch (elements.length) {
            case 0: {
                return of();
            }
            case 1: {
                return new SingletonImmutableList<E>(elements[0]);
            }
            default: {
                return (ImmutableList<E>)construct((Object[])elements.clone());
            }
        }
    }
    
    private static <E> ImmutableList<E> copyFromCollection(final Collection<? extends E> collection) {
        final Object[] elements = collection.toArray();
        switch (elements.length) {
            case 0: {
                return of();
            }
            case 1: {
                final ImmutableList<E> list = new SingletonImmutableList<E>((E)elements[0]);
                return list;
            }
            default: {
                return construct(elements);
            }
        }
    }
    
    private static <E> ImmutableList<E> construct(final Object... elements) {
        for (int i = 0; i < elements.length; ++i) {
            checkElementNotNull(elements[i], i);
        }
        return new RegularImmutableList<E>(elements);
    }
    
    private static Object checkElementNotNull(final Object element, final int index) {
        if (element == null) {
            throw new NullPointerException("at index " + index);
        }
        return element;
    }
    
    public UnmodifiableIterator<E> iterator() {
        return this.listIterator();
    }
    
    public UnmodifiableListIterator<E> listIterator() {
        return this.listIterator(0);
    }
    
    public abstract UnmodifiableListIterator<E> listIterator(final int p0);
    
    public abstract int indexOf(@Nullable final Object p0);
    
    public abstract int lastIndexOf(@Nullable final Object p0);
    
    public abstract ImmutableList<E> subList(final int p0, final int p1);
    
    public final boolean addAll(final int index, final Collection<? extends E> newElements) {
        throw new UnsupportedOperationException();
    }
    
    public final E set(final int index, final E element) {
        throw new UnsupportedOperationException();
    }
    
    public final void add(final int index, final E element) {
        throw new UnsupportedOperationException();
    }
    
    public final E remove(final int index) {
        throw new UnsupportedOperationException();
    }
    
    public ImmutableList<E> asList() {
        return this;
    }
    
    public ImmutableList<E> reverse() {
        return new ReverseImmutableList<E>(this);
    }
    
    public boolean equals(final Object obj) {
        return Lists.equalsImpl(this, obj);
    }
    
    public int hashCode() {
        return Lists.hashCodeImpl(this);
    }
    
    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }
    
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }
    
    public static <E> Builder<E> builder() {
        return new Builder<E>();
    }
    
    private static class ReverseImmutableList<E> extends ImmutableList<E>
    {
        private final transient ImmutableList<E> forwardList;
        private final transient int size;
        
        ReverseImmutableList(final ImmutableList<E> backingList) {
            this.forwardList = backingList;
            this.size = backingList.size();
        }
        
        private int reverseIndex(final int index) {
            return this.size - 1 - index;
        }
        
        private int reversePosition(final int index) {
            return this.size - index;
        }
        
        public ImmutableList<E> reverse() {
            return this.forwardList;
        }
        
        public boolean contains(@Nullable final Object object) {
            return this.forwardList.contains(object);
        }
        
        public boolean containsAll(final Collection<?> targets) {
            return this.forwardList.containsAll(targets);
        }
        
        public int indexOf(@Nullable final Object object) {
            final int index = this.forwardList.lastIndexOf(object);
            return (index >= 0) ? this.reverseIndex(index) : -1;
        }
        
        public int lastIndexOf(@Nullable final Object object) {
            final int index = this.forwardList.indexOf(object);
            return (index >= 0) ? this.reverseIndex(index) : -1;
        }
        
        public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
            return this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex)).reverse();
        }
        
        public E get(final int index) {
            Preconditions.checkElementIndex(index, this.size);
            return this.forwardList.get(this.reverseIndex(index));
        }
        
        public UnmodifiableListIterator<E> listIterator(final int index) {
            Preconditions.checkPositionIndex(index, this.size);
            final UnmodifiableListIterator<E> forward = this.forwardList.listIterator(this.reversePosition(index));
            return new UnmodifiableListIterator<E>() {
                public boolean hasNext() {
                    return forward.hasPrevious();
                }
                
                public boolean hasPrevious() {
                    return forward.hasNext();
                }
                
                public E next() {
                    return (E)forward.previous();
                }
                
                public int nextIndex() {
                    return ReverseImmutableList.this.reverseIndex(forward.previousIndex());
                }
                
                public E previous() {
                    return (E)forward.next();
                }
                
                public int previousIndex() {
                    return ReverseImmutableList.this.reverseIndex(forward.nextIndex());
                }
            };
        }
        
        public int size() {
            return this.size;
        }
        
        public boolean isEmpty() {
            return this.forwardList.isEmpty();
        }
        
        boolean isPartialView() {
            return this.forwardList.isPartialView();
        }
    }
    
    private static class SerializedForm implements Serializable
    {
        final Object[] elements;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Object[] elements) {
            this.elements = elements;
        }
        
        Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }
    
    public static final class Builder<E> extends ImmutableCollection.Builder<E>
    {
        private final ArrayList<E> contents;
        
        public Builder() {
            this.contents = Lists.newArrayList();
        }
        
        public Builder<E> add(final E element) {
            this.contents.add(Preconditions.checkNotNull(element));
            return this;
        }
        
        public Builder<E> addAll(final Iterable<? extends E> elements) {
            if (elements instanceof Collection) {
                final Collection<?> collection = (Collection<?>)(Collection)elements;
                this.contents.ensureCapacity(this.contents.size() + collection.size());
            }
            super.addAll(elements);
            return this;
        }
        
        public Builder<E> add(final E... elements) {
            this.contents.ensureCapacity(this.contents.size() + elements.length);
            super.add(elements);
            return this;
        }
        
        public Builder<E> addAll(final Iterator<? extends E> elements) {
            super.addAll(elements);
            return this;
        }
        
        public ImmutableList<E> build() {
            return ImmutableList.copyOf((Collection<? extends E>)this.contents);
        }
    }
}
