// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLXML;
import java.sql.RowId;
import java.sql.NClob;
import java.io.Reader;
import java.sql.SQLException;

public class JDBC4ServerPreparedStatement extends ServerPreparedStatement
{
    public JDBC4ServerPreparedStatement(final MySQLConnection conn, final String sql, final String catalog, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        super(conn, sql, catalog, resultSetType, resultSetConcurrency);
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw SQLError.createSQLException("Can not call setNCharacterStream() when connection character set isn't UTF-8", this.getExceptionInterceptor());
        }
        this.checkClosed();
        if (reader == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = reader;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = length;
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob x) throws SQLException {
        this.setNClob(parameterIndex, x.getCharacterStream(), this.connection.getUseStreamLengthsInPrepStmts() ? x.length() : -1L);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw SQLError.createSQLException("Can not call setNClob() when connection character set isn't UTF-8", this.getExceptionInterceptor());
        }
        this.checkClosed();
        if (reader == null) {
            this.setNull(parameterIndex, 2011);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = reader;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = length;
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    @Override
    public void setNString(final int parameterIndex, final String x) throws SQLException {
        if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
            this.setString(parameterIndex, x);
            return;
        }
        throw SQLError.createSQLException("Can not call setNString() when connection character set isn't UTF-8", this.getExceptionInterceptor());
    }
    
    @Override
    public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x);
    }
    
    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject);
    }
}
