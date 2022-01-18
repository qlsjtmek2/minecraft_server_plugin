// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import com.avaje.ebean.bean.BeanCollection;

public interface BeanFinder<T>
{
    T find(final BeanQueryRequest<T> p0);
    
    BeanCollection<T> findMany(final BeanQueryRequest<T> p0);
}
