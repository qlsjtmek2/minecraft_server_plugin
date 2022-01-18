// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.sql.ParameterMetaData;

public class MysqlParameterMetadata implements ParameterMetaData
{
    boolean returnSimpleMetadata;
    ResultSetMetaData metadata;
    int parameterCount;
    private ExceptionInterceptor exceptionInterceptor;
    
    MysqlParameterMetadata(final Field[] fieldInfo, final int parameterCount, final ExceptionInterceptor exceptionInterceptor) {
        this.returnSimpleMetadata = false;
        this.metadata = null;
        this.parameterCount = 0;
        this.metadata = new ResultSetMetaData(fieldInfo, false, exceptionInterceptor);
        this.parameterCount = parameterCount;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    MysqlParameterMetadata(final int count) {
        this.returnSimpleMetadata = false;
        this.metadata = null;
        this.parameterCount = 0;
        this.parameterCount = count;
        this.returnSimpleMetadata = true;
    }
    
    public int getParameterCount() throws SQLException {
        return this.parameterCount;
    }
    
    public int isNullable(final int arg0) throws SQLException {
        this.checkAvailable();
        return this.metadata.isNullable(arg0);
    }
    
    private void checkAvailable() throws SQLException {
        if (this.metadata == null || this.metadata.fields == null) {
            throw SQLError.createSQLException("Parameter metadata not available for the given statement", "S1C00", this.exceptionInterceptor);
        }
    }
    
    public boolean isSigned(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return false;
        }
        this.checkAvailable();
        return this.metadata.isSigned(arg0);
    }
    
    public int getPrecision(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return 0;
        }
        this.checkAvailable();
        return this.metadata.getPrecision(arg0);
    }
    
    public int getScale(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return 0;
        }
        this.checkAvailable();
        return this.metadata.getScale(arg0);
    }
    
    public int getParameterType(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return 12;
        }
        this.checkAvailable();
        return this.metadata.getColumnType(arg0);
    }
    
    public String getParameterTypeName(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return "VARCHAR";
        }
        this.checkAvailable();
        return this.metadata.getColumnTypeName(arg0);
    }
    
    public String getParameterClassName(final int arg0) throws SQLException {
        if (this.returnSimpleMetadata) {
            this.checkBounds(arg0);
            return "java.lang.String";
        }
        this.checkAvailable();
        return this.metadata.getColumnClassName(arg0);
    }
    
    public int getParameterMode(final int arg0) throws SQLException {
        return 1;
    }
    
    private void checkBounds(final int paramNumber) throws SQLException {
        if (paramNumber < 1) {
            throw SQLError.createSQLException("Parameter index of '" + paramNumber + "' is invalid.", "S1009", this.exceptionInterceptor);
        }
        if (paramNumber > this.parameterCount) {
            throw SQLError.createSQLException("Parameter index of '" + paramNumber + "' is greater than number of parameters, which is '" + this.parameterCount + "'.", "S1009", this.exceptionInterceptor);
        }
    }
    
    public boolean isWrapperFor(final Class iface) throws SQLException {
        return iface.isInstance(this);
    }
    
    public Object unwrap(final Class iface) throws SQLException {
        try {
            return Util.cast(iface, this);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
        }
    }
}
