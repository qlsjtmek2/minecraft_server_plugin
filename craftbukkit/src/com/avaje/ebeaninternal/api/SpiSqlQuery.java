// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.sql.PreparedStatement;
import com.avaje.ebean.SqlQueryListener;
import com.avaje.ebean.SqlQuery;

public interface SpiSqlQuery extends SqlQuery
{
    BindParams getBindParams();
    
    String getQuery();
    
    SqlQueryListener getListener();
    
    int getFirstRow();
    
    int getMaxRows();
    
    int getBackgroundFetchAfter();
    
    String getMapKey();
    
    int getTimeout();
    
    int getBufferFetchSizeHint();
    
    boolean isFutureFetch();
    
    void setFutureFetch(final boolean p0);
    
    void setPreparedStatement(final PreparedStatement p0);
    
    boolean isCancelled();
}
