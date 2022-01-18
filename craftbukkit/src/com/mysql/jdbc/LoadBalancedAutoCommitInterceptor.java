// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public class LoadBalancedAutoCommitInterceptor implements StatementInterceptorV2
{
    private int matchingAfterStatementCount;
    private int matchingAfterStatementThreshold;
    private String matchingAfterStatementRegex;
    private ConnectionImpl conn;
    private LoadBalancingConnectionProxy proxy;
    
    public LoadBalancedAutoCommitInterceptor() {
        this.matchingAfterStatementCount = 0;
        this.matchingAfterStatementThreshold = 0;
        this.proxy = null;
    }
    
    public void destroy() {
    }
    
    public boolean executeTopLevelOnly() {
        return false;
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
        this.conn = (ConnectionImpl)conn;
        final String autoCommitSwapThresholdAsString = props.getProperty("loadBalanceAutoCommitStatementThreshold", "0");
        try {
            this.matchingAfterStatementThreshold = Integer.parseInt(autoCommitSwapThresholdAsString);
        }
        catch (NumberFormatException ex) {}
        final String autoCommitSwapRegex = props.getProperty("loadBalanceAutoCommitStatementRegex", "");
        if ("".equals(autoCommitSwapRegex)) {
            return;
        }
        this.matchingAfterStatementRegex = autoCommitSwapRegex;
    }
    
    public ResultSetInternalMethods postProcess(final String sql, final Statement interceptedStatement, final ResultSetInternalMethods originalResultSet, final Connection connection, final int warningCount, final boolean noIndexUsed, final boolean noGoodIndexUsed, final SQLException statementException) throws SQLException {
        if (!this.conn.getAutoCommit()) {
            this.matchingAfterStatementCount = 0;
        }
        else {
            if (this.proxy == null && this.conn.isProxySet()) {
                MySQLConnection lcl_proxy;
                for (lcl_proxy = this.conn.getLoadBalanceSafeProxy(); lcl_proxy != null && !(lcl_proxy instanceof LoadBalancedMySQLConnection); lcl_proxy = lcl_proxy.getLoadBalanceSafeProxy()) {}
                if (lcl_proxy != null) {
                    this.proxy = ((LoadBalancedMySQLConnection)lcl_proxy).getProxy();
                }
            }
            if (this.proxy != null && (this.matchingAfterStatementRegex == null || sql.matches(this.matchingAfterStatementRegex))) {
                ++this.matchingAfterStatementCount;
            }
            if (this.matchingAfterStatementCount >= this.matchingAfterStatementThreshold) {
                this.matchingAfterStatementCount = 0;
                try {
                    if (this.proxy != null) {
                        this.proxy.pickNewConnection();
                    }
                }
                catch (SQLException ex) {}
            }
        }
        return originalResultSet;
    }
    
    public ResultSetInternalMethods preProcess(final String sql, final Statement interceptedStatement, final Connection connection) throws SQLException {
        return null;
    }
}
