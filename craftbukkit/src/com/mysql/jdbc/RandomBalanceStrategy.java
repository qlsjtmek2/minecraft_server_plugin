// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.sql.SQLException;
import java.util.Properties;

public class RandomBalanceStrategy implements BalanceStrategy
{
    public void destroy() {
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
    }
    
    public ConnectionImpl pickConnection(final LoadBalancingConnectionProxy proxy, final List<String> configuredHosts, final Map<String, ConnectionImpl> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        final int numHosts = configuredHosts.size();
        SQLException ex = null;
        final List<String> whiteList = new ArrayList<String>(numHosts);
        whiteList.addAll(configuredHosts);
        Map<String, Long> blackList = proxy.getGlobalBlacklist();
        whiteList.removeAll(blackList.keySet());
        Map<String, Integer> whiteListMap = this.getArrayIndexMap(whiteList);
        int attempts = 0;
        while (attempts < numRetries) {
            final int random = (int)Math.floor(Math.random() * whiteList.size());
            if (whiteList.size() == 0) {
                throw SQLError.createSQLException("No hosts configured", null);
            }
            final String hostPortSpec = whiteList.get(random);
            ConnectionImpl conn = liveConnections.get(hostPortSpec);
            if (conn == null) {
                try {
                    conn = proxy.createConnectionForHost(hostPortSpec);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (proxy.shouldExceptionTriggerFailover(sqlEx)) {
                        final Integer whiteListIndex = whiteListMap.get(hostPortSpec);
                        if (whiteListIndex != null) {
                            whiteList.remove((int)whiteListIndex);
                            whiteListMap = this.getArrayIndexMap(whiteList);
                        }
                        proxy.addToGlobalBlacklist(hostPortSpec);
                        if (whiteList.size() != 0) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        whiteListMap = new HashMap<String, Integer>(numHosts);
                        whiteList.addAll(configuredHosts);
                        blackList = proxy.getGlobalBlacklist();
                        whiteList.removeAll(blackList.keySet());
                        whiteListMap = this.getArrayIndexMap(whiteList);
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
    
    private Map<String, Integer> getArrayIndexMap(final List<String> l) {
        final Map<String, Integer> m = new HashMap<String, Integer>(l.size());
        for (int i = 0; i < l.size(); ++i) {
            m.put(l.get(i), new Integer(i));
        }
        return m;
    }
}
