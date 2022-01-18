// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.util.Properties;

public class BestResponseTimeBalanceStrategy implements BalanceStrategy
{
    public void destroy() {
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
    }
    
    public ConnectionImpl pickConnection(final LoadBalancingConnectionProxy proxy, final List<String> configuredHosts, final Map<String, ConnectionImpl> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        Map<String, Long> blackList = proxy.getGlobalBlacklist();
        SQLException ex = null;
        int attempts = 0;
        while (attempts < numRetries) {
            long minResponseTime = Long.MAX_VALUE;
            int bestHostIndex = 0;
            if (blackList.size() == configuredHosts.size()) {
                blackList = proxy.getGlobalBlacklist();
            }
            for (int i = 0; i < responseTimes.length; ++i) {
                final long candidateResponseTime = responseTimes[i];
                if (candidateResponseTime < minResponseTime && !blackList.containsKey(configuredHosts.get(i))) {
                    if (candidateResponseTime == 0L) {
                        bestHostIndex = i;
                        break;
                    }
                    bestHostIndex = i;
                    minResponseTime = candidateResponseTime;
                }
            }
            final String bestHost = configuredHosts.get(bestHostIndex);
            ConnectionImpl conn = liveConnections.get(bestHost);
            if (conn == null) {
                try {
                    conn = proxy.createConnectionForHost(bestHost);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (proxy.shouldExceptionTriggerFailover(sqlEx)) {
                        proxy.addToGlobalBlacklist(bestHost);
                        blackList.put(bestHost, null);
                        if (blackList.size() != configuredHosts.size()) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        blackList = proxy.getGlobalBlacklist();
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
