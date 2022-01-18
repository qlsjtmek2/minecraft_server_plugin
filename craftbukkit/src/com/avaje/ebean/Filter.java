// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.List;
import java.util.Set;

public interface Filter<T>
{
    Filter<T> sort(final String p0);
    
    Filter<T> maxRows(final int p0);
    
    Filter<T> eq(final String p0, final Object p1);
    
    Filter<T> ne(final String p0, final Object p1);
    
    Filter<T> ieq(final String p0, final String p1);
    
    Filter<T> between(final String p0, final Object p1, final Object p2);
    
    Filter<T> gt(final String p0, final Object p1);
    
    Filter<T> ge(final String p0, final Object p1);
    
    Filter<T> lt(final String p0, final Object p1);
    
    Filter<T> le(final String p0, final Object p1);
    
    Filter<T> isNull(final String p0);
    
    Filter<T> isNotNull(final String p0);
    
    Filter<T> startsWith(final String p0, final String p1);
    
    Filter<T> istartsWith(final String p0, final String p1);
    
    Filter<T> endsWith(final String p0, final String p1);
    
    Filter<T> iendsWith(final String p0, final String p1);
    
    Filter<T> contains(final String p0, final String p1);
    
    Filter<T> icontains(final String p0, final String p1);
    
    Filter<T> in(final String p0, final Set<?> p1);
    
    List<T> filter(final List<T> p0);
}
