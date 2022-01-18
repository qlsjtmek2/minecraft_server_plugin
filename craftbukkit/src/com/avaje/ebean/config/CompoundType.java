// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

public interface CompoundType<V>
{
    V create(final Object[] p0);
    
    CompoundTypeProperty<V, ?>[] getProperties();
}
