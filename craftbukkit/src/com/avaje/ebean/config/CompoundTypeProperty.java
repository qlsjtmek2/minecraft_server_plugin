// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface CompoundTypeProperty<V, P>
{
    String getName();
    
    P getValue(final V p0);
    
    int getDbType();
}
