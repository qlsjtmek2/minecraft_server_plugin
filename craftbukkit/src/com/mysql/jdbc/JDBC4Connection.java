// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.NClob;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.SQLClientInfoException;
import java.sql.Connection;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLXML;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC4Connection extends ConnectionImpl
{
    private JDBC4ClientInfoProvider infoProvider;
    
    public JDBC4Connection(final String hostToConnectTo, final int portToConnectTo, final Properties info, final String databaseToConnectTo, final String url) throws SQLException {
        super(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);
    }
    
    @Override
    public SQLXML createSQLXML() throws SQLException {
        return new JDBC4MysqlSQLXML(this.getExceptionInterceptor());
    }
    
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    @Override
    public Properties getClientInfo() throws SQLException {
        return this.getClientInfoProviderImpl().getClientInfo(this);
    }
    
    @Override
    public String getClientInfo(final String name) throws SQLException {
        return this.getClientInfoProviderImpl().getClientInfo(this, name);
    }
    
    @Override
    public synchronized boolean isValid(final int timeout) throws SQLException {
        if (this.isClosed()) {
            return false;
        }
        try {
            synchronized (this.getMutex()) {
                try {
                    this.pingInternal(false, timeout * 1000);
                }
                catch (Throwable t) {
                    try {
                        this.abortInternal();
                    }
                    catch (Throwable t3) {}
                    return false;
                }
            }
        }
        catch (Throwable t2) {
            return false;
        }
        return true;
    }
    
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        try {
            this.getClientInfoProviderImpl().setClientInfo(this, properties);
        }
        catch (SQLClientInfoException ciEx) {
            throw ciEx;
        }
        catch (SQLException sqlEx) {
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
    
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        try {
            this.getClientInfoProviderImpl().setClientInfo(this, name, value);
        }
        catch (SQLClientInfoException ciEx) {
            throw ciEx;
        }
        catch (SQLException sqlEx) {
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        this.checkClosed();
        return iface.isInstance(this);
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            return iface.cast(this);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob createBlob() {
        return new com.mysql.jdbc.Blob(this.getExceptionInterceptor());
    }
    
    @Override
    public Clob createClob() {
        return new com.mysql.jdbc.Clob(this.getExceptionInterceptor());
    }
    
    @Override
    public NClob createNClob() {
        return new JDBC4NClob(this.getExceptionInterceptor());
    }
    
    protected synchronized JDBC4ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
        if (this.infoProvider == null) {
            try {
                try {
                    this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance(this.getClientInfoProvider(), new Class[0], new Object[0], this.getExceptionInterceptor());
                }
                catch (SQLException sqlEx) {
                    if (sqlEx.getCause() instanceof ClassCastException) {
                        this.infoProvider = (JDBC4ClientInfoProvider)Util.getInstance("com.mysql.jdbc." + this.getClientInfoProvider(), new Class[0], new Object[0], this.getExceptionInterceptor());
                    }
                }
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("JDBC4Connection.ClientInfoNotImplemented", new Object[] { this.getClientInfoProvider() }), "S1009", this.getExceptionInterceptor());
            }
            this.infoProvider.initialize(this, this.props);
        }
        return this.infoProvider;
    }
}
