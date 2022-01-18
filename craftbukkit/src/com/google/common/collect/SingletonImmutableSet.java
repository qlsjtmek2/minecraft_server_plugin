// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableSet<E> extends ImmutableSet<E>
{
    final transient E element;
    private transient Integer cachedHashCode;
    
    SingletonImmutableSet(final E element) {
        this.element = Preconditions.checkNotNull(element);
    }
    
    SingletonImmutableSet(final E element, final int hashCode) {
        this.element = element;
        this.cachedHashCode = hashCode;
    }
    
    public int size() {
        return 1;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public boolean contains(final Object target) {
        return this.element.equals(target);
    }
    
    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
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
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Set) {
            final Set<?> that = (Set<?>)object;
            return that.size() == 1 && this.element.equals(that.iterator().next());
        }
        return false;
    }
    
    public final int hashCode() {
        final Integer code = this.cachedHashCode;
        if (code == null) {
            final Integer value = this.element.hashCode();
            this.cachedHashCode = value;
            return value;
        }
        return code;
    }
    
    boolean isHashCodeFast() {
        return false;
    }
    
    public String toString() {
        final String elementToString = this.element.toString();
        return new StringBuilder(elementToString.length() + 2).append('[').append(elementToString).append(']').toString();
    }
}
