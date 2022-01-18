// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.interceptors;

import java.sql.ResultSet;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import java.util.Properties;
import com.mysql.jdbc.Connection;
import java.util.HashMap;
import java.util.Map;
import com.mysql.jdbc.StatementInterceptor;

public class ServerStatusDiffInterceptor implements StatementInterceptor
{
    private Map preExecuteValues;
    private Map postExecuteValues;
    
    public ServerStatusDiffInterceptor() {
        this.preExecuteValues = new HashMap();
        this.postExecuteValues = new HashMap();
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
    }
    
    public ResultSetInternalMethods postProcess(final String sql, final Statement interceptedStatement, final ResultSetInternalMethods originalResultSet, final Connection connection) throws SQLException {
        if (connection.versionMeetsMinimum(5, 0, 2)) {
            this.populateMapWithSessionStatusValues(connection, this.postExecuteValues);
            connection.getLog().logInfo("Server status change for statement:\n" + Util.calculateDifferences(this.preExecuteValues, this.postExecuteValues));
        }
        return null;
    }
    
    private void populateMapWithSessionStatusValues(final Connection connection, final Map toPopulate) throws SQLException {
        java.sql.Statement stmt = null;
        ResultSet rs = null;
        try {
            toPopulate.clear();
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SHOW SESSION STATUS");
            Util.resultSetToMap(toPopulate, rs);
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    public ResultSetInternalMethods preProcess(final String sql, final Statement interceptedStatement, final Connection connection) throws SQLException {
        if (connection.versionMeetsMinimum(5, 0, 2)) {
            this.populateMapWithSessionStatusValues(connection, this.preExecuteValues);
        }
        return null;
    }
    
    public boolean executeTopLevelOnly() {
        return true;
    }
    
    public void destroy() {
    }
}
