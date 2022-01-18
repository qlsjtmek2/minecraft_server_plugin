// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.SQLException;

public class JDBC4PreparedStatement extends PreparedStatement
{
    public JDBC4PreparedStatement(final MySQLConnection conn, final String catalog) throws SQLException {
        super(conn, catalog);
    }
    
    public JDBC4PreparedStatement(final MySQLConnection conn, final String sql, final String catalog) throws SQLException {
        super(conn, sql, catalog);
    }
    
    public JDBC4PreparedStatement(final MySQLConnection conn, final String sql, final String catalog, final ParseInfo cachedParseInfo) throws SQLException {
        super(conn, sql, catalog, cachedParseInfo);
    }
    
    @Override
    public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
        JDBC4PreparedStatementHelper.setNClob(this, parameterIndex, value);
    }
    
    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject);
    }
}
