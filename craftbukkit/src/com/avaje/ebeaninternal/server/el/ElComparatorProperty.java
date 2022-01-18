// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public final class ElComparatorProperty<T> implements Comparator<T>, ElComparator<T>
{
    private final ElPropertyValue elGetValue;
    private final int nullOrder;
    private final int asc;
    
    public ElComparatorProperty(final ElPropertyValue elGetValue, final boolean ascending, final boolean nullsHigh) {
        this.elGetValue = elGetValue;
        this.asc = (ascending ? 1 : -1);
        this.nullOrder = this.asc * (nullsHigh ? 1 : -1);
    }
    
    public int compare(final T o1, final T o2) {
        final Object val1 = this.elGetValue.elGetValue(o1);
        final Object val2 = this.elGetValue.elGetValue(o2);
        return this.compareValues(val1, val2);
    }
    
    public int compareValue(final Object value, final T o2) {
        final Object val2 = this.elGetValue.elGetValue(o2);
        return this.compareValues(value, val2);
    }
    
    public int compareValues(final Object val1, final Object val2) {
        if (val1 == null) {
            return (val2 == null) ? 0 : this.nullOrder;
        }
        if (val2 == null) {
            return -1 * this.nullOrder;
        }
        final Comparable c = (Comparable)val1;
        return this.asc * c.compareTo(val2);
    }
}
