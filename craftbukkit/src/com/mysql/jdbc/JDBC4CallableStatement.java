// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.Reader;
import java.sql.NClob;
import java.sql.SQLXML;
import java.sql.RowId;
import java.sql.SQLException;

public class JDBC4CallableStatement extends CallableStatement
{
    public JDBC4CallableStatement(final MySQLConnection conn, final CallableStatementParamInfo paramInfo) throws SQLException {
        super(conn, paramInfo);
    }
    
    public JDBC4CallableStatement(final MySQLConnection conn, final String sql, final String catalog, final boolean isFunctionCall) throws SQLException {
        super(conn, sql, catalog, isFunctionCall);
    }
    
    @Override
    public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        JDBC4PreparedStatementHelper.setRowId(this, parameterIndex, x);
    }
    
    @Override
    public void setRowId(final String parameterName, final RowId x) throws SQLException {
        JDBC4PreparedStatementHelper.setRowId(this, this.getNamedParamIndex(parameterName, false), x);
    }
    
    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        JDBC4PreparedStatementHelper.setSQLXML(this, parameterIndex, xmlObject);
    }
    
    @Override
    public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
        JDBC4PreparedStatementHelper.setSQLXML(this, this.getNamedParamIndex(parameterName, false), xmlObject);
    }
    
    @Override
    public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final SQLXML retValue = ((JDBC4ResultSet)rs).getSQLXML(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public SQLXML getSQLXML(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final SQLXML retValue = ((JDBC4ResultSet)rs).getSQLXML(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public RowId getRowId(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final RowId retValue = ((JDBC4ResultSet)rs).getRowId(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public RowId getRowId(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final RowId retValue = ((JDBC4ResultSet)rs).getRowId(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
        JDBC4PreparedStatementHelper.setNClob(this, parameterIndex, value);
    }
    
    @Override
    public void setNClob(final String parameterName, final NClob value) throws SQLException {
        JDBC4PreparedStatementHelper.setNClob(this, this.getNamedParamIndex(parameterName, false), value);
    }
    
    @Override
    public void setNClob(final String parameterName, final Reader reader) throws SQLException {
        this.setNClob(this.getNamedParamIndex(parameterName, false), reader);
    }
    
    @Override
    public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        this.setNClob(this.getNamedParamIndex(parameterName, false), reader, length);
    }
    
    @Override
    public void setNString(final String parameterName, final String value) throws SQLException {
        this.setNString(this.getNamedParamIndex(parameterName, false), value);
    }
    
    @Override
    public Reader getCharacterStream(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Reader retValue = rs.getCharacterStream(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public Reader getCharacterStream(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Reader retValue = rs.getCharacterStream(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Reader retValue = ((JDBC4ResultSet)rs).getNCharacterStream(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public Reader getNCharacterStream(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Reader retValue = ((JDBC4ResultSet)rs).getNCharacterStream(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public NClob getNClob(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final NClob retValue = ((JDBC4ResultSet)rs).getNClob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public NClob getNClob(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final NClob retValue = ((JDBC4ResultSet)rs).getNClob(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public String getNString(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final String retValue = ((JDBC4ResultSet)rs).getNString(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    @Override
    public String getNString(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final String retValue = ((JDBC4ResultSet)rs).getNString(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
}
