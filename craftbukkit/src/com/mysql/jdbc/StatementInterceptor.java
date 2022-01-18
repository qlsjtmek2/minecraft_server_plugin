// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public interface StatementInterceptor extends Extension
{
    void init(final Connection p0, final Properties p1) throws SQLException;
    
    ResultSetInternalMethods preProcess(final String p0, final Statement p1, final Connection p2) throws SQLException;
    
    ResultSetInternalMethods postProcess(final String p0, final Statement p1, final ResultSetInternalMethods p2, final Connection p3) throws SQLException;
    
    boolean executeTopLevelOnly();
    
    void destroy();
}
