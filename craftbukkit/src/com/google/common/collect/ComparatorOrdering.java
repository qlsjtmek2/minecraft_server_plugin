// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class ComparatorOrdering<T> extends Ordering<T> implements Serializable
{
    final Comparator<T> comparator;
    private static final long serialVersionUID = 0L;
    
    ComparatorOrdering(final Comparator<T> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }
    
    public int compare(final T a, final T b) {
        return this.comparator.compare(a, b);
    }
    
    public int binarySearch(final List<? extends T> sortedList, final T key) {
        return Collections.binarySearch(sortedList, key, this.comparator);
    }
    
    public <E extends T> List<E> sortedCopy(final Iterable<E> iterable) {
        final List<E> list = (List<E>)Lists.newArrayList((Iterable<?>)iterable);
        Collections.sort(list, this.comparator);
        return list;
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ComparatorOrdering) {
            final ComparatorOrdering<?> that = (ComparatorOrdering<?>)object;
            return this.comparator.equals(that.comparator);
        }
        return false;
    }
    
    public int hashCode() {
        return this.comparator.hashCode();
    }
    
    public String toString() {
        return this.comparator.toString();
    }
}
