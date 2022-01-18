// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.sql.NClob;
import java.sql.Clob;
import java.sql.Blob;
import com.mysql.jdbc.SQLError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.sql.SQLClientInfoException;
import java.util.Properties;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLXML;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class JDBC4ConnectionWrapper extends ConnectionWrapper
{
    public JDBC4ConnectionWrapper(final MysqlPooledConnection mysqlPooledConnection, final Connection mysqlConnection, final boolean forXa) throws SQLException {
        super(mysqlPooledConnection, mysqlConnection, forXa);
    }
    
    @Override
    public void close() throws SQLException {
        try {
            super.close();
        }
        finally {
            this.unwrappedInterfaces = null;
        }
    }
    
    @Override
    public SQLXML createSQLXML() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createSQLXML();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createArrayOf(typeName, elements);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createStruct(typeName, attributes);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public Properties getClientInfo() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getClientInfo();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public String getClientInfo(final String name) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getClientInfo(name);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public synchronized boolean isValid(final int timeout) throws SQLException {
        try {
            return this.mc.isValid(timeout);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }
    
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        try {
            this.checkClosed();
            this.mc.setClientInfo(properties);
        }
        catch (SQLException sqlException) {
            try {
                this.checkAndFireConnectionError(sqlException);
            }
            catch (SQLException sqlEx2) {
                final SQLClientInfoException clientEx = new SQLClientInfoException();
                clientEx.initCause(sqlEx2);
                throw clientEx;
            }
        }
    }
    
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        try {
            this.checkClosed();
            this.mc.setClientInfo(name, value);
        }
        catch (SQLException sqlException) {
            try {
                this.checkAndFireConnectionError(sqlException);
            }
            catch (SQLException sqlEx2) {
                final SQLClientInfoException clientEx = new SQLClientInfoException();
                clientEx.initCause(sqlEx2);
                throw clientEx;
            }
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        this.checkClosed();
        final boolean isInstance = iface.isInstance(this);
        return isInstance || iface.getName().equals("com.mysql.jdbc.Connection") || iface.getName().equals("com.mysql.jdbc.ConnectionProperties");
    }
    
    @Override
    public synchronized <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            if ("java.sql.Connection".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName())) {
                return iface.cast(this);
            }
            if (this.unwrappedInterfaces == null) {
                this.unwrappedInterfaces = new HashMap();
            }
            Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
            if (cachedUnwrapped == null) {
                cachedUnwrapped = Proxy.newProxyInstance(this.mc.getClass().getClassLoader(), new Class[] { iface }, new ConnectionErrorFiringInvocationHandler(this, this.mc));
                this.unwrappedInterfaces.put(iface, cachedUnwrapped);
            }
            return iface.cast(cachedUnwrapped);
        }
        catch (ClassCastException cce) {
            throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.exceptionInterceptor);
        }
    }
    
    @Override
    public Blob createBlob() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createBlob();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public Clob createClob() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createClob();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    @Override
    public NClob createNClob() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.createNClob();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
}
