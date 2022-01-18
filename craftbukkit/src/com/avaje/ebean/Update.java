// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public interface Update<T>
{
    String getName();
    
    Update<T> setNotifyCache(final boolean p0);
    
    Update<T> setTimeout(final int p0);
    
    int execute();
    
    Update<T> set(final int p0, final Object p1);
    
    Update<T> setParameter(final int p0, final Object p1);
    
    Update<T> setNull(final int p0, final int p1);
    
    Update<T> setNullParameter(final int p0, final int p1);
    
    Update<T> set(final String p0, final Object p1);
    
    Update<T> setParameter(final String p0, final Object p1);
    
    Update<T> setNull(final String p0, final int p1);
    
    Update<T> setNullParameter(final String p0, final int p1);
    
    String getGeneratedSql();
}
