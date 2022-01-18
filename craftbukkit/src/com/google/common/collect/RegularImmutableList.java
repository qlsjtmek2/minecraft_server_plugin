// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.ListIterator;
import java.util.Iterator;
import java.util.List;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
class RegularImmutableList<E> extends ImmutableList<E>
{
    private final transient int offset;
    private final transient int size;
    private final transient Object[] array;
    
    RegularImmutableList(final Object[] array, final int offset, final int size) {
        this.offset = offset;
        this.size = size;
        this.array = array;
    }
    
    RegularImmutableList(final Object[] array) {
        this(array, 0, array.length);
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    boolean isPartialView() {
        return this.offset != 0 || this.size != this.array.length;
    }
    
    public boolean contains(@Nullable final Object target) {
        return this.indexOf(target) != -1;
    }
    
    public UnmodifiableIterator<E> iterator() {
        return Iterators.forArray(this.array, this.offset, this.size);
    }
    
    public Object[] toArray() {
        final Object[] newArray = new Object[this.size()];
        System.arraycopy(this.array, this.offset, newArray, 0, this.size);
        return newArray;
    }
    
    public <T> T[] toArray(T[] other) {
        if (other.length < this.size) {
            other = ObjectArrays.newArray(other, this.size);
        }
        else if (other.length > this.size) {
            other[this.size] = null;
        }
        System.arraycopy(this.array, this.offset, other, 0, this.size);
        return other;
    }
    
    public E get(final int index) {
        Preconditions.checkElementIndex(index, this.size);
        return (E)this.array[index + this.offset];
    }
    
    public int indexOf(@Nullable final Object target) {
        if (target != null) {
            for (int i = this.offset; i < this.offset + this.size; ++i) {
                if (this.array[i].equals(target)) {
                    return i - this.offset;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(@Nullable final Object target) {
        if (target != null) {
            for (int i = this.offset + this.size - 1; i >= this.offset; --i) {
                if (this.array[i].equals(target)) {
                    return i - this.offset;
                }
            }
        }
        return -1;
    }
    
    public ImmutableList<E> subList(final int fromIndex, final int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
        return (fromIndex == toIndex) ? ImmutableList.of() : new RegularImmutableList(this.array, this.offset + fromIndex, toIndex - fromIndex);
    }
    
    public UnmodifiableListIterator<E> listIterator(final int start) {
        return new AbstractIndexedListIterator<E>(this.size, start) {
            protected E get(final int index) {
                return (E)RegularImmutableList.this.array[index + RegularImmutableList.this.offset];
            }
        };
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        final List<?> that = (List<?>)object;
        if (this.size() != that.size()) {
            return false;
        }
        int index = this.offset;
        if (object instanceof RegularImmutableList) {
            final RegularImmutableList<?> other = (RegularImmutableList<?>)object;
            for (int i = other.offset; i < other.offset + other.size; ++i) {
                if (!this.array[index++].equals(other.array[i])) {
                    return false;
                }
            }
        }
        else {
            for (final Object element : that) {
                if (!this.array[index++].equals(element)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int hashCode() {
        int hashCode = 1;
        for (int i = this.offset; i < this.offset + this.size; ++i) {
            hashCode = 31 * hashCode + this.array[i].hashCode();
        }
        return hashCode;
    }
    
    public String toString() {
        final StringBuilder sb = Collections2.newStringBuilderForCollection(this.size()).append('[').append(this.array[this.offset]);
        for (int i = this.offset + 1; i < this.offset + this.size; ++i) {
            sb.append(", ").append(this.array[i]);
        }
        return sb.append(']').toString();
    }
}
