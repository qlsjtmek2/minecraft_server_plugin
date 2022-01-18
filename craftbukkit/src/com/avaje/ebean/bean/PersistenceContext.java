// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

public interface PersistenceContext
{
    void put(final Object p0, final Object p1);
    
    Object putIfAbsent(final Object p0, final Object p1);
    
    Object get(final Class<?> p0, final Object p1);
    
    void clear();
    
    void clear(final Class<?> p0);
    
    void clear(final Class<?> p0, final Object p1);
    
    int size(final Class<?> p0);
}
