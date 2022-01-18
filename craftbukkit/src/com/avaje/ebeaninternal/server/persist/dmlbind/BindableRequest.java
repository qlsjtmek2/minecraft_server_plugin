// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface BindableRequest
{
    void setIdValue(final Object p0);
    
    Object bind(final Object p0, final BeanProperty p1, final String p2, final boolean p3) throws SQLException;
    
    Object bind(final String p0, final Object p1, final int p2) throws SQLException;
    
    Object bindNoLog(final Object p0, final int p1, final String p2) throws SQLException;
    
    Object bindNoLog(final Object p0, final BeanProperty p1, final String p2, final boolean p3) throws SQLException;
    
    boolean isIncluded(final BeanProperty p0);
    
    boolean isIncludedWhere(final BeanProperty p0);
    
    void registerUpdateGenValue(final BeanProperty p0, final Object p1, final Object p2);
    
    void registerAdditionalProperty(final String p0);
    
    PersistRequestBean<?> getPersistRequest();
}
