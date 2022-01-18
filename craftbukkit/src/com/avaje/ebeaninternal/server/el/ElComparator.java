// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public interface ElComparator<T> extends Comparator<T>
{
    int compare(final T p0, final T p1);
    
    int compareValue(final Object p0, final T p1);
}
