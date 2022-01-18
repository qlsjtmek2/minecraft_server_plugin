// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface GeneratedProperty
{
    Object getInsertValue(final BeanProperty p0, final Object p1);
    
    Object getUpdateValue(final BeanProperty p0, final Object p1);
    
    boolean includeInUpdate();
    
    boolean includeInInsert();
    
    boolean isDDLNotNullable();
}
