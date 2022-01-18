// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Savepoint;
import java.sql.SQLException;

public interface ConnectionLifecycleInterceptor extends Extension
{
    void close() throws SQLException;
    
    boolean commit() throws SQLException;
    
    boolean rollback() throws SQLException;
    
    boolean rollback(final Savepoint p0) throws SQLException;
    
    boolean setAutoCommit(final boolean p0) throws SQLException;
    
    boolean setCatalog(final String p0) throws SQLException;
    
    boolean transactionBegun() throws SQLException;
    
    boolean transactionCompleted() throws SQLException;
}
