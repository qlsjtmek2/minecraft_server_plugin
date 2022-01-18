// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.ListIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class TransformedImmutableList<D, E> extends ImmutableList<E>
{
    private final transient ImmutableList<D> backingList;
    
    TransformedImmutableList(final ImmutableList<D> backingList) {
        this.backingList = Preconditions.checkNotNull(backingList);
    }
    
    abstract E transform(final D p0);
    
    public int indexOf(@Nullable final Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.get(i).equals(object)) {
                return i;
            }
        }
        return -1;
    }
    
    public int lastIndexOf(@Nullable final Object object) {
        if (object == null) {
            return -1;
        }
        for (int i = this.size() - 1; i >= 0; --i) {
            if (this.get(i).equals(object)) {
                return i;
            }
        }
        return -1;
    }
    
    public E get(final int index) {
        return this.transform(this.backingList.get(index));
    }
    
    public UnmodifiableListIterator<E> listIterator(final int index) {
        return new AbstractIndexedListIterator<E>(this.size(), index) {
            protected E get(final int index) {
                return TransformedImmutableList.this.get(index);
            }
        };
    }
    
    public int size() {
        return this.backingList.size();
    }
    
    public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
        return new TransformedView(this.backingList.subList(fromIndex, toIndex));
    }
    
    public boolean equals(@Nullable final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            final List<?> list = (List<?>)obj;
            return this.size() == list.size() && Iterators.elementsEqual(this.iterator(), list.iterator());
        }
        return false;
    }
    
    public int hashCode() {
        int hashCode = 1;
        for (final E e : this) {
            hashCode = 31 * hashCode + ((e == null) ? 0 : e.hashCode());
        }
        return hashCode;
    }
    
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }
    
    public <T> T[] toArray(final T[] array) {
        return ObjectArrays.toArrayImpl(this, array);
    }
    
    boolean isPartialView() {
        return true;
    }
    
    private class TransformedView extends TransformedImmutableList<D, E>
    {
        TransformedView(final ImmutableList<D> backingList) {
            super(backingList);
        }
        
        E transform(final D d) {
            return TransformedImmutableList.this.transform(d);
        }
    }
}
