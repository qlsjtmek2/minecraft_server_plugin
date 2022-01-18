// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public interface RowData
{
    public static final int RESULT_SET_SIZE_UNKNOWN = -1;
    
    void addRow(final ResultSetRow p0) throws SQLException;
    
    void afterLast() throws SQLException;
    
    void beforeFirst() throws SQLException;
    
    void beforeLast() throws SQLException;
    
    void close() throws SQLException;
    
    ResultSetRow getAt(final int p0) throws SQLException;
    
    int getCurrentRowNumber() throws SQLException;
    
    ResultSetInternalMethods getOwner();
    
    boolean hasNext() throws SQLException;
    
    boolean isAfterLast() throws SQLException;
    
    boolean isBeforeFirst() throws SQLException;
    
    boolean isDynamic() throws SQLException;
    
    boolean isEmpty() throws SQLException;
    
    boolean isFirst() throws SQLException;
    
    boolean isLast() throws SQLException;
    
    void moveRowRelative(final int p0) throws SQLException;
    
    ResultSetRow next() throws SQLException;
    
    void removeRow(final int p0) throws SQLException;
    
    void setCurrentRow(final int p0) throws SQLException;
    
    void setOwner(final ResultSetImpl p0);
    
    int size() throws SQLException;
    
    boolean wasEmpty();
    
    void setMetadata(final Field[] p0);
}
