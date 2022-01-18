// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.Set;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;

public interface SpiUpdatePlan
{
    boolean isEmptySetClause();
    
    void bindSet(final DmlHandler p0, final Object p1) throws SQLException;
    
    long getTimeCreated();
    
    Long getTimeLastUsed();
    
    Integer getKey();
    
    ConcurrencyMode getMode();
    
    String getSql();
    
    Bindable getSet();
    
    Set<String> getProperties();
}
