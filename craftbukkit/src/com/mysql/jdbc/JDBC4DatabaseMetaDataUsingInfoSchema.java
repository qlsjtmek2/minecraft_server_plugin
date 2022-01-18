// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.RowIdLifetime;
import java.sql.SQLException;

public class JDBC4DatabaseMetaDataUsingInfoSchema extends DatabaseMetaDataUsingInfoSchema
{
    public JDBC4DatabaseMetaDataUsingInfoSchema(final MySQLConnection connToSet, final String databaseToSet) throws SQLException {
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
    protected int getJDBC4FunctionNoTableConstant() {
        return 1;
    }
}
