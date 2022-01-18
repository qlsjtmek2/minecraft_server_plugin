// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.SQLException;
import java.sql.PreparedStatement;

public interface PstmtBatch
{
    void setBatchSize(final PreparedStatement p0, final int p1);
    
    void addBatch(final PreparedStatement p0) throws SQLException;
    
    int executeBatch(final PreparedStatement p0, final int p1, final String p2, final boolean p3) throws SQLException;
}
