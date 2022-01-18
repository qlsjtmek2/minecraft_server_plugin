// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.Collection;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class EmptyImmutableList extends ImmutableList<Object>
{
    static final EmptyImmutableList INSTANCE;
    static final UnmodifiableListIterator<Object> ITERATOR;
    private static final Object[] EMPTY_ARRAY;
    private static final long serialVersionUID = 0L;
    
    public int size() {
        return 0;
    }
    
    public boolean isEmpty() {
        return true;
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public boolean contains(final Object target) {
        return false;
    }
    
    public UnmodifiableIterator<Object> iterator() {
        return Iterators.emptyIterator();
    }
    
    public Object[] toArray() {
        return EmptyImmutableList.EMPTY_ARRAY;
    }
    
    public <T> T[] toArray(final T[] a) {
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }
    
    public Object get(final int index) {
        Preconditions.checkElementIndex(index, 0);
        throw new AssertionError((Object)"unreachable");
    }
    
    public int indexOf(@Nullable final Object target) {
        return -1;
    }
    
    public int lastIndexOf(@Nullable final Object target) {
        return -1;
    }
    
    public ImmutableList<Object> subList(final int fromIndex, final int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, 0);
        return this;
    }
    
    public ImmutableList<Object> reverse() {
        return this;
    }
    
    public UnmodifiableListIterator<Object> listIterator() {
        return EmptyImmutableList.ITERATOR;
    }
    
    public UnmodifiableListIterator<Object> listIterator(final int start) {
        Preconditions.checkPositionIndex(start, 0);
        return EmptyImmutableList.ITERATOR;
    }
    
    public boolean containsAll(final Collection<?> targets) {
        return targets.isEmpty();
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof List) {
            final List<?> that = (List<?>)object;
            return that.isEmpty();
        }
        return false;
    }
    
    public int hashCode() {
        return 1;
    }
    
    public String toString() {
        return "[]";
    }
    
    Object readResolve() {
        return EmptyImmutableList.INSTANCE;
    }
    
    static {
        INSTANCE = new EmptyImmutableList();
        ITERATOR = new UnmodifiableListIterator<Object>() {
            public boolean hasNext() {
                return false;
            }
            
            public boolean hasPrevious() {
                return false;
            }
            
            public Object next() {
                throw new NoSuchElementException();
            }
            
            public int nextIndex() {
                return 0;
            }
            
            public Object previous() {
                throw new NoSuchElementException();
            }
            
            public int previousIndex() {
                return -1;
            }
        };
        EMPTY_ARRAY = new Object[0];
    }
}
