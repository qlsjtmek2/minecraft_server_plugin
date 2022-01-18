// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.sql.SQLException;
import java.sql.CallableStatement;

public interface CallableSql
{
    String getLabel();
    
    CallableSql setLabel(final String p0);
    
    int getTimeout();
    
    String getSql();
    
    CallableSql setTimeout(final int p0);
    
    CallableSql setSql(final String p0);
    
    CallableSql bind(final int p0, final Object p1);
    
    CallableSql setParameter(final int p0, final Object p1);
    
    CallableSql registerOut(final int p0, final int p1);
    
    Object getObject(final int p0);
    
    boolean executeOverride(final CallableStatement p0) throws SQLException;
    
    CallableSql addModification(final String p0, final boolean p1, final boolean p2, final boolean p3);
}
