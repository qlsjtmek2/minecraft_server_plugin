// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import java.util.Set;

public interface BeanPersistListener<T>
{
    boolean inserted(final T p0);
    
    boolean updated(final T p0, final Set<String> p1);
    
    boolean deleted(final T p0);
    
    void remoteInsert(final Object p0);
    
    void remoteUpdate(final Object p0);
    
    void remoteDelete(final Object p0);
}
