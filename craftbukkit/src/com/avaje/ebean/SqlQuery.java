// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;

public interface SqlQuery extends Serializable
{
    void cancel();
    
    List<SqlRow> findList();
    
    Set<SqlRow> findSet();
    
    Map<?, SqlRow> findMap();
    
    SqlRow findUnique();
    
    SqlFutureList findFutureList();
    
    SqlQuery setParameter(final String p0, final Object p1);
    
    SqlQuery setParameter(final int p0, final Object p1);
    
    SqlQuery setListener(final SqlQueryListener p0);
    
    SqlQuery setFirstRow(final int p0);
    
    SqlQuery setMaxRows(final int p0);
    
    SqlQuery setBackgroundFetchAfter(final int p0);
    
    SqlQuery setMapKey(final String p0);
    
    SqlQuery setTimeout(final int p0);
    
    SqlQuery setBufferFetchSizeHint(final int p0);
}
