// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.event;

public interface BeanQueryAdapter
{
    boolean isRegisterFor(final Class<?> p0);
    
    int getExecutionOrder();
    
    void preQuery(final BeanQueryRequest<?> p0);
}
