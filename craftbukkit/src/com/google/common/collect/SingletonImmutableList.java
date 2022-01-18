// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableList<E> extends ImmutableList<E>
{
    final transient E element;
    
    SingletonImmutableList(final E element) {
        this.element = Preconditions.checkNotNull(element);
    }
    
    public E get(final int index) {
        Preconditions.checkElementIndex(index, 1);
        return this.element;
    }
    
    public int indexOf(@Nullable final Object object) {
        return this.element.equals(object) ? 0 : -1;
    }
    
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }
    
    public int lastIndexOf(@Nullable final Object object) {
        return this.element.equals(object) ? 0 : -1;
    }
    
    public UnmodifiableListIterator<E> listIterator(final int start) {
        Preconditions.checkPositionIndex(start, 1);
        return new UnmodifiableListIterator<E>() {
            boolean hasNext = start == 0;
            
            public boolean hasNext() {
                return this.hasNext;
            }
            
            public boolean hasPrevious() {
                return !this.hasNext;
            }
            
            public E next() {
                if (!this.hasNext) {
                    throw new NoSuchElementException();
                }
                this.hasNext = false;
                return SingletonImmutableList.this.element;
            }
            
            public int nextIndex() {
                return this.hasNext ? 0 : 1;
            }
            
            public E previous() {
                if (this.hasNext) {
                    throw new NoSuchElementException();
                }
                this.hasNext = true;
                return SingletonImmutableList.this.element;
            }
            
            public int previousIndex() {
                return this.hasNext ? -1 : 0;
            }
        };
    }
    
    public int size() {
        return 1;
    }
    
    public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, 1);
        return (fromIndex == toIndex) ? ImmutableList.of() : this;
    }
    
    public ImmutableList<E> reverse() {
        return this;
    }
    
    public boolean contains(@Nullable final Object object) {
        return this.element.equals(object);
    }
    
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof List) {
            final List<?> that = (List<?>)object;
            return that.size() == 1 && this.element.equals(that.get(0));
        }
        return false;
    }
    
    public int hashCode() {
        return 31 + this.element.hashCode();
    }
    
    public String toString() {
        final String elementToString = this.element.toString();
        return new StringBuilder(elementToString.length() + 2).append('[').append(elementToString).append(']').toString();
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public Object[] toArray() {
        return new Object[] { this.element };
    }
    
    public <T> T[] toArray(T[] array) {
        if (array.length == 0) {
            array = ObjectArrays.newArray(array, 1);
        }
        else if (array.length > 1) {
            array[1] = null;
        }
        final Object[] objectArray = array;
        objectArray[0] = this.element;
        return array;
    }
}
