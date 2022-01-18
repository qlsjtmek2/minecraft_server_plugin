// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Collection;

@GwtCompatible(emulated = true)
public abstract class ImmutableCollection<E> implements Collection<E>, Serializable
{
    static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION;
    private transient ImmutableList<E> asList;
    
    public abstract UnmodifiableIterator<E> iterator();
    
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }
    
    public <T> T[] toArray(final T[] other) {
        return ObjectArrays.toArrayImpl(this, other);
    }
    
    public boolean contains(@Nullable final Object object) {
        return object != null && Iterators.contains(this.iterator(), object);
    }
    
    public boolean containsAll(final Collection<?> targets) {
        return Collections2.containsAllImpl(this, targets);
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public String toString() {
        return Collections2.toStringImpl(this);
    }
    
    public final boolean add(final E e) {
        throw new UnsupportedOperationException();
    }
    
    public final boolean remove(final Object object) {
        throw new UnsupportedOperationException();
    }
    
    public final boolean addAll(final Collection<? extends E> newElements) {
        throw new UnsupportedOperationException();
    }
    
    public final boolean removeAll(final Collection<?> oldElements) {
        throw new UnsupportedOperationException();
    }
    
    public final boolean retainAll(final Collection<?> elementsToKeep) {
        throw new UnsupportedOperationException();
    }
    
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    public ImmutableList<E> asList() {
        final ImmutableList<E> list = this.asList;
        return (list == null) ? (this.asList = this.createAsList()) : list;
    }
    
    ImmutableList<E> createAsList() {
        switch (this.size()) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return ImmutableList.of(this.iterator().next());
            }
            default: {
                return new ImmutableAsList<E>(this.toArray(), this);
            }
        }
    }
    
    abstract boolean isPartialView();
    
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }
    
    static {
        EMPTY_IMMUTABLE_COLLECTION = new EmptyImmutableCollection();
    }
    
    private static class EmptyImmutableCollection extends ImmutableCollection<Object>
    {
        private static final Object[] EMPTY_ARRAY;
        
        public int size() {
            return 0;
        }
        
        public boolean isEmpty() {
            return true;
        }
        
        public boolean contains(@Nullable final Object object) {
            return false;
        }
        
        public UnmodifiableIterator<Object> iterator() {
            return Iterators.EMPTY_ITERATOR;
        }
        
        public Object[] toArray() {
            return EmptyImmutableCollection.EMPTY_ARRAY;
        }
        
        public <T> T[] toArray(final T[] array) {
            if (array.length > 0) {
                array[0] = null;
            }
            return array;
        }
        
        ImmutableList<Object> createAsList() {
            return ImmutableList.of();
        }
        
        boolean isPartialView() {
            return false;
        }
        
        static {
            EMPTY_ARRAY = new Object[0];
        }
    }
    
    private static class ArrayImmutableCollection<E> extends ImmutableCollection<E>
    {
        private final E[] elements;
        
        ArrayImmutableCollection(final E[] elements) {
            this.elements = elements;
        }
        
        public int size() {
            return this.elements.length;
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public UnmodifiableIterator<E> iterator() {
            return Iterators.forArray((E[])this.elements);
        }
        
        ImmutableList<E> createAsList() {
            return (ImmutableList<E>)((this.elements.length == 1) ? new SingletonImmutableList<Object>(this.elements[0]) : new RegularImmutableList<Object>(this.elements));
        }
        
        boolean isPartialView() {
            return false;
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
            return (this.elements.length == 0) ? ImmutableCollection.EMPTY_IMMUTABLE_COLLECTION : new ArrayImmutableCollection<Object>(Platform.clone(this.elements));
        }
    }
    
    public abstract static class Builder<E>
    {
        public abstract Builder<E> add(final E p0);
        
        public Builder<E> add(final E... elements) {
            for (final E element : elements) {
                this.add(element);
            }
            return this;
        }
        
        public Builder<E> addAll(final Iterable<? extends E> elements) {
            for (final E element : elements) {
                this.add(element);
            }
            return this;
        }
        
        public Builder<E> addAll(final Iterator<? extends E> elements) {
            while (elements.hasNext()) {
                this.add(elements.next());
            }
            return this;
        }
        
        public abstract ImmutableCollection<E> build();
    }
}
