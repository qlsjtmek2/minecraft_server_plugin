// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.interceptors;

import java.sql.PreparedStatement;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.StatementInterceptor;

public class SessionAssociationInterceptor implements StatementInterceptor
{
    protected String currentSessionKey;
    protected static ThreadLocal sessionLocal;
    
    public static final void setSessionKey(final String key) {
        SessionAssociationInterceptor.sessionLocal.set(key);
    }
    
    public static final void resetSessionKey() {
        SessionAssociationInterceptor.sessionLocal.set(null);
    }
    
    public static final String getSessionKey() {
        return SessionAssociationInterceptor.sessionLocal.get();
    }
    
    public boolean executeTopLevelOnly() {
        return true;
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
    }
    
    public ResultSetInternalMethods postProcess(final String sql, final Statement interceptedStatement, final ResultSetInternalMethods originalResultSet, final Connection connection) throws SQLException {
        return null;
    }
    
    public ResultSetInternalMethods preProcess(final String sql, final Statement interceptedStatement, final Connection connection) throws SQLException {
        final String key = getSessionKey();
        if (key != null && !key.equals(this.currentSessionKey)) {
            final PreparedStatement pstmt = connection.clientPrepareStatement("SET @mysql_proxy_session=?");
            try {
                pstmt.setString(1, key);
                pstmt.execute();
            }
            finally {
                pstmt.close();
            }
            this.currentSessionKey = key;
        }
        return null;
    }
    
    public void destroy() {
    }
    
    static {
        SessionAssociationInterceptor.sessionLocal = new ThreadLocal();
    }
}
