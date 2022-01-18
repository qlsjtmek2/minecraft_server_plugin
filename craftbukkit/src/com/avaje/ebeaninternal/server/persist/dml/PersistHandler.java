// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;

public interface PersistHandler
{
    String getBindLog();
    
    void bind() throws SQLException;
    
    void addBatch() throws SQLException;
    
    void execute() throws SQLException;
    
    void close() throws SQLException;
}
