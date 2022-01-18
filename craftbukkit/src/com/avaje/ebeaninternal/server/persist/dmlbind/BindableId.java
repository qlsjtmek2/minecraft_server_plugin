// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public interface BindableId extends Bindable
{
    boolean isConcatenated();
    
    String getIdentityColumn();
    
    boolean deriveConcatenatedId(final PersistRequestBean<?> p0);
}
