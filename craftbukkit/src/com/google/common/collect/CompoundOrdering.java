// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(serializable = true)
final class CompoundOrdering<T> extends Ordering<T> implements Serializable
{
    final ImmutableList<Comparator<? super T>> comparators;
    private static final long serialVersionUID = 0L;
    
    CompoundOrdering(final Comparator<? super T> primary, final Comparator<? super T> secondary) {
        this.comparators = ImmutableList.of(primary, secondary);
    }
    
    CompoundOrdering(final Iterable<? extends Comparator<? super T>> comparators) {
        this.comparators = ImmutableList.copyOf(comparators);
    }
    
    CompoundOrdering(final List<? extends Comparator<? super T>> comparators, final Comparator<? super T> lastComparator) {
        this.comparators = new ImmutableList.Builder<Comparator<? super T>>().addAll(comparators).add(lastComparator).build();
    }
    
    public int compare(final T left, final T right) {
        for (final Comparator<? super T> comparator : this.comparators) {
            final int result = comparator.compare((Object)left, (Object)right);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof CompoundOrdering) {
            final CompoundOrdering<?> that = (CompoundOrdering<?>)object;
            return this.comparators.equals(that.comparators);
        }
        return false;
    }
    
    public int hashCode() {
        return this.comparators.hashCode();
    }
    
    public String toString() {
        return "Ordering.compound(" + this.comparators + ")";
    }
}
