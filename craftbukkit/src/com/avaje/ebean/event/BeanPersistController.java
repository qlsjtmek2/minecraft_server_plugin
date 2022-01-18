// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

import java.util.Set;

public interface BeanPersistController
{
    int getExecutionOrder();
    
    boolean isRegisterFor(final Class<?> p0);
    
    boolean preInsert(final BeanPersistRequest<?> p0);
    
    boolean preUpdate(final BeanPersistRequest<?> p0);
    
    boolean preDelete(final BeanPersistRequest<?> p0);
    
    void postInsert(final BeanPersistRequest<?> p0);
    
    void postUpdate(final BeanPersistRequest<?> p0);
    
    void postDelete(final BeanPersistRequest<?> p0);
    
    void postLoad(final Object p0, final Set<String> p1);
}
