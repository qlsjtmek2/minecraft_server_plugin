// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.bean;

public interface BeanCollectionLoader
{
    String getName();
    
    void loadMany(final BeanCollection<?> p0, final boolean p1);
}
