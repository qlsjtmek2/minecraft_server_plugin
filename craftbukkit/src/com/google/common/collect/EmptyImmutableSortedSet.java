// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
class EmptyImmutableSortedSet<E> extends ImmutableSortedSet<E>
{
    private static final Object[] EMPTY_ARRAY;
    
    EmptyImmutableSortedSet(final Comparator<? super E> comparator) {
        super(comparator);
    }
    
    public int size() {
        return 0;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    public boolean contains(final Object target) {
        return false;
    }
    
    public UnmodifiableIterator<E> iterator() {
        return Iterators.emptyIterator();
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public Object[] toArray() {
        return EmptyImmutableSortedSet.EMPTY_ARRAY;
    }
    
    public <T> T[] toArray(final T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }
    
    public boolean containsAll(final Collection<?> targets) {
        return targets.isEmpty();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Set) {
            final Set<?> that = (Set<?>)object;
            return that.isEmpty();
        }
        return false;
    }
    
    public int hashCode() {
        return 0;
    }
    
    public String toString() {
        return "[]";
    }
    
    public E first() {
        throw new NoSuchElementException();
    }
    
    public E last() {
        throw new NoSuchElementException();
    }
    
    ImmutableSortedSet<E> headSetImpl(final E toElement, final boolean inclusive) {
        return this;
    }
    
    ImmutableSortedSet<E> subSetImpl(final E fromElement, final boolean fromInclusive, final E toElement, final boolean toInclusive) {
        return this;
    }
    
    ImmutableSortedSet<E> tailSetImpl(final E fromElement, final boolean inclusive) {
        return this;
    }
    
    int indexOf(@Nullable final Object target) {
        return -1;
    }
    
    static {
        EMPTY_ARRAY = new Object[0];
    }
}
