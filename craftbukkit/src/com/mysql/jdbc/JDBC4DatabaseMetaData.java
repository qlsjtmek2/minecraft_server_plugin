// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.RowIdLifetime;

public class JDBC4DatabaseMetaData extends DatabaseMetaData
{
    public JDBC4DatabaseMetaData(final MySQLConnection connToSet, final String databaseToSet) {
        super(connToSet, databaseToSet);
    }
    
    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return RowIdLifetime.ROWID_UNSUPPORTED;
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            return iface.cast(this);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.conn.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        final Field[] fields = { new Field("", "NAME", 12, 255), new Field("", "MAX_LEN", 4, 10), new Field("", "DEFAULT_VALUE", 12, 255), new Field("", "DESCRIPTION", 12, 255) };
        final ArrayList tuples = new ArrayList();
        return DatabaseMetaData.buildResultSet(fields, tuples, this.conn);
    }
    
    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return false;
    }
    
    @Override
    public ResultSet getFunctions(final String catalog, final String schemaPattern, final String functionNamePattern) throws SQLException {
        final Field[] fields = { new Field("", "FUNCTION_CAT", 1, 255), new Field("", "FUNCTION_SCHEM", 1, 255), new Field("", "FUNCTION_NAME", 1, 255), new Field("", "REMARKS", 1, 255), new Field("", "FUNCTION_TYPE", 5, 6), new Field("", "SPECIFIC_NAME", 1, 255) };
        return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, functionNamePattern, false, true);
    }
    
    @Override
    protected int getJDBC4FunctionNoTableConstant() {
        return 1;
    }
}
