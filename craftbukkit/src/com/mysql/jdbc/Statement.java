// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.ResultSet;
import java.io.InputStream;
import java.sql.SQLException;

public interface Statement extends java.sql.Statement
{
    void enableStreamingResults() throws SQLException;
    
    void disableStreamingResults() throws SQLException;
    
    void setLocalInfileInputStream(final InputStream p0);
    
    InputStream getLocalInfileInputStream();
    
    void setPingTarget(final PingTarget p0);
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void removeOpenResultSet(final ResultSet p0);
    
    int getOpenResultSetCount();
    
    void setHoldResultsOpenOverClose(final boolean p0);
}
