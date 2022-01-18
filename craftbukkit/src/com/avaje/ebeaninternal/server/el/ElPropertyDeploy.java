// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface ElPropertyDeploy
{
    public static final String ROOT_ELPREFIX = "${}";
    
    boolean containsMany();
    
    boolean containsManySince(final String p0);
    
    String getElPrefix();
    
    String getElPlaceholder(final boolean p0);
    
    String getName();
    
    String getElName();
    
    String getDbColumn();
    
    BeanProperty getBeanProperty();
}
