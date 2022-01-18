// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.ResultSetMetaData;
import java.util.Map;

public class CachedResultSetMetaData
{
    Map columnNameToIndex;
    Field[] fields;
    Map fullColumnNameToIndex;
    ResultSetMetaData metadata;
    
    public CachedResultSetMetaData() {
        this.columnNameToIndex = null;
        this.fullColumnNameToIndex = null;
    }
    
    public Map getColumnNameToIndex() {
        return this.columnNameToIndex;
    }
    
    public Field[] getFields() {
        return this.fields;
    }
    
    public Map getFullColumnNameToIndex() {
        return this.fullColumnNameToIndex;
    }
    
    public ResultSetMetaData getMetadata() {
        return this.metadata;
    }
}
