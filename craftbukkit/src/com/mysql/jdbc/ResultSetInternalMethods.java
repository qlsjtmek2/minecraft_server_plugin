// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Statement;
import java.util.Map;
import java.sql.SQLException;
import java.sql.ResultSet;

public interface ResultSetInternalMethods extends ResultSet
{
    ResultSetInternalMethods copy() throws SQLException;
    
    boolean reallyResult();
    
    Object getObjectStoredProc(final int p0, final int p1) throws SQLException;
    
    Object getObjectStoredProc(final int p0, final Map p1, final int p2) throws SQLException;
    
    Object getObjectStoredProc(final String p0, final int p1) throws SQLException;
    
    Object getObjectStoredProc(final String p0, final Map p1, final int p2) throws SQLException;
    
    String getServerInfo();
    
    long getUpdateCount();
    
    long getUpdateID();
    
    void realClose(final boolean p0) throws SQLException;
    
    void setFirstCharOfQuery(final char p0);
    
    void setOwningStatement(final StatementImpl p0);
    
    char getFirstCharOfQuery();
    
    void clearNextResult();
    
    ResultSetInternalMethods getNextResultSet();
    
    void setStatementUsedForFetchingRows(final PreparedStatement p0);
    
    void setWrapperStatement(final Statement p0);
    
    void buildIndexMapping() throws SQLException;
    
    void initializeWithMetadata() throws SQLException;
    
    void redefineFieldsForDBMD(final Field[] p0);
    
    void populateCachedMetaData(final CachedResultSetMetaData p0) throws SQLException;
    
    void initializeFromCachedMetaData(final CachedResultSetMetaData p0);
    
    int getBytesSize() throws SQLException;
}
