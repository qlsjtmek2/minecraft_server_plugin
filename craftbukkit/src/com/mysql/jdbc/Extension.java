// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public interface Extension
{
    void init(final Connection p0, final Properties p1) throws SQLException;
    
    void destroy();
}
