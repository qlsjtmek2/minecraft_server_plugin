// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.util.Properties;

public class SequentialBalanceStrategy implements BalanceStrategy
{
    private int currentHostIndex;
    
    public SequentialBalanceStrategy() {
        this.currentHostIndex = -1;
    }
    
    public void destroy() {
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
    }
    
    public ConnectionImpl pickConnection(final LoadBalancingConnectionProxy proxy, final List<String> configuredHosts, final Map<String, ConnectionImpl> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        final int numHosts = configuredHosts.size();
        SQLException ex = null;
        Map<String, Long> blackList = proxy.getGlobalBlacklist();
        final int attempts = 0;
        while (attempts < numRetries) {
            if (numHosts == 1) {
                this.currentHostIndex = 0;
            }
            else if (this.currentHostIndex == -1) {
                int i;
                int random;
                for (random = (i = (int)Math.floor(Math.random() * numHosts)); i < numHosts; ++i) {
                    if (!blackList.containsKey(configuredHosts.get(i))) {
                        this.currentHostIndex = i;
                        break;
                    }
                }
                if (this.currentHostIndex == -1) {
                    for (i = 0; i < random; ++i) {
                        if (!blackList.containsKey(configuredHosts.get(i))) {
                            this.currentHostIndex = i;
                            break;
                        }
                    }
                }
                if (this.currentHostIndex == -1) {
                    blackList = proxy.getGlobalBlacklist();
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException e) {}
                    continue;
                }
            }
            else {
                int j = this.currentHostIndex + 1;
                boolean foundGoodHost = false;
                while (j < numHosts) {
                    if (!blackList.containsKey(configuredHosts.get(j))) {
                        this.currentHostIndex = j;
                        foundGoodHost = true;
                        break;
                    }
                    ++j;
                }
                if (!foundGoodHost) {
                    for (j = 0; j < this.currentHostIndex; ++j) {
                        if (!blackList.containsKey(configuredHosts.get(j))) {
                            this.currentHostIndex = j;
                            foundGoodHost = true;
                            break;
                        }
                    }
                }
                if (!foundGoodHost) {
                    blackList = proxy.getGlobalBlacklist();
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException e2) {}
                    continue;
                }
            }
            final String hostPortSpec = configuredHosts.get(this.currentHostIndex);
            ConnectionImpl conn = liveConnections.get(hostPortSpec);
            if (conn == null) {
                try {
                    conn = proxy.createConnectionForHost(hostPortSpec);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (sqlEx instanceof CommunicationsException || "08S01".equals(sqlEx.getSQLState())) {
                        proxy.addToGlobalBlacklist(hostPortSpec);
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        continue;
                    }
                    throw sqlEx;
                }
            }
            return conn;
        }
        if (ex != null) {
            throw ex;
        }
        return null;
    }
}
