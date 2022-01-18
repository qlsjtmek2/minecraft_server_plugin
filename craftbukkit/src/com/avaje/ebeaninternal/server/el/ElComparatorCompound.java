// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public final class ElComparatorCompound<T> implements Comparator<T>, ElComparator<T>
{
    private final ElComparator<T>[] array;
    
    public ElComparatorCompound(final ElComparator<T>[] array) {
        this.array = array;
    }
    
    public int compare(final T o1, final T o2) {
        for (int i = 0; i < this.array.length; ++i) {
            final int ret = this.array[i].compare(o1, o2);
            if (ret != 0) {
                return ret;
            }
        }
        return 0;
    }
    
    public int compareValue(final Object value, final T o2) {
        for (int i = 0; i < this.array.length; ++i) {
            final int ret = this.array[i].compareValue(value, o2);
            if (ret != 0) {
                return ret;
            }
        }
        return 0;
    }
}
