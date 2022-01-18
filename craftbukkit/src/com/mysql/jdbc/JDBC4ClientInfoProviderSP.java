// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Enumeration;
import java.sql.SQLClientInfoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class JDBC4ClientInfoProviderSP implements JDBC4ClientInfoProvider
{
    PreparedStatement setClientInfoSp;
    PreparedStatement getClientInfoSp;
    PreparedStatement getClientInfoBulkSp;
    
    @Override
    public synchronized void initialize(final Connection conn, final Properties configurationProps) throws SQLException {
        final String identifierQuote = conn.getMetaData().getIdentifierQuoteString();
        final String setClientInfoSpName = configurationProps.getProperty("clientInfoSetSPName", "setClientInfo");
        final String getClientInfoSpName = configurationProps.getProperty("clientInfoGetSPName", "getClientInfo");
        final String getClientInfoBulkSpName = configurationProps.getProperty("clientInfoGetBulkSPName", "getClientInfoBulk");
        final String clientInfoCatalog = configurationProps.getProperty("clientInfoCatalog", "");
        final String catalog = "".equals(clientInfoCatalog) ? conn.getCatalog() : clientInfoCatalog;
        this.setClientInfoSp = ((com.mysql.jdbc.Connection)conn).clientPrepareStatement("CALL " + identifierQuote + catalog + identifierQuote + "." + identifierQuote + setClientInfoSpName + identifierQuote + "(?, ?)");
        this.getClientInfoSp = ((com.mysql.jdbc.Connection)conn).clientPrepareStatement("CALL" + identifierQuote + catalog + identifierQuote + "." + identifierQuote + getClientInfoSpName + identifierQuote + "(?)");
        this.getClientInfoBulkSp = ((com.mysql.jdbc.Connection)conn).clientPrepareStatement("CALL " + identifierQuote + catalog + identifierQuote + "." + identifierQuote + getClientInfoBulkSpName + identifierQuote + "()");
    }
    
    @Override
    public synchronized void destroy() throws SQLException {
        if (this.setClientInfoSp != null) {
            this.setClientInfoSp.close();
            this.setClientInfoSp = null;
        }
        if (this.getClientInfoSp != null) {
            this.getClientInfoSp.close();
            this.getClientInfoSp = null;
        }
        if (this.getClientInfoBulkSp != null) {
            this.getClientInfoBulkSp.close();
            this.getClientInfoBulkSp = null;
        }
    }
    
    @Override
    public synchronized Properties getClientInfo(final Connection conn) throws SQLException {
        ResultSet rs = null;
        final Properties props = new Properties();
        try {
            this.getClientInfoBulkSp.execute();
            rs = this.getClientInfoBulkSp.getResultSet();
            while (rs.next()) {
                props.setProperty(rs.getString(1), rs.getString(2));
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
        }
        return props;
    }
    
    @Override
    public synchronized String getClientInfo(final Connection conn, final String name) throws SQLException {
        ResultSet rs = null;
        String clientInfo = null;
        try {
            this.getClientInfoSp.setString(1, name);
            this.getClientInfoSp.execute();
            rs = this.getClientInfoSp.getResultSet();
            if (rs.next()) {
                clientInfo = rs.getString(1);
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
        }
        return clientInfo;
    }
    
    @Override
    public synchronized void setClientInfo(final Connection conn, final Properties properties) throws SQLClientInfoException {
        try {
            final Enumeration propNames = properties.propertyNames();
            while (propNames.hasMoreElements()) {
                final String name = propNames.nextElement();
                final String value = properties.getProperty(name);
                this.setClientInfo(conn, name, value);
            }
        }
        catch (SQLException sqlEx) {
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
    
    @Override
    public synchronized void setClientInfo(final Connection conn, final String name, final String value) throws SQLClientInfoException {
        try {
            this.setClientInfoSp.setString(1, name);
            this.setClientInfoSp.setString(2, value);
            this.setClientInfoSp.execute();
        }
        catch (SQLException sqlEx) {
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
}
