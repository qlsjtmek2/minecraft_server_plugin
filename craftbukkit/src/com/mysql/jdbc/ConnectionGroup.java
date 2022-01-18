// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class ConnectionGroup
{
    private String groupName;
    private long connections;
    private long activeConnections;
    private HashMap<Long, LoadBalancingConnectionProxy> connectionProxies;
    private Set<String> hostList;
    private boolean isInitialized;
    private long closedProxyTotalPhysicalConnections;
    private long closedProxyTotalTransactions;
    private int activeHosts;
    private Set<String> closedHosts;
    
    ConnectionGroup(final String groupName) {
        this.connections = 0L;
        this.activeConnections = 0L;
        this.connectionProxies = new HashMap<Long, LoadBalancingConnectionProxy>();
        this.hostList = new HashSet<String>();
        this.isInitialized = false;
        this.closedProxyTotalPhysicalConnections = 0L;
        this.closedProxyTotalTransactions = 0L;
        this.activeHosts = 0;
        this.closedHosts = new HashSet<String>();
        this.groupName = groupName;
    }
    
    public long registerConnectionProxy(final LoadBalancingConnectionProxy proxy, final List<String> localHostList) {
        final long currentConnectionId;
        synchronized (this) {
            if (!this.isInitialized) {
                this.hostList.addAll(localHostList);
                this.isInitialized = true;
                this.activeHosts = localHostList.size();
            }
            final long connections = this.connections + 1L;
            this.connections = connections;
            currentConnectionId = connections;
            this.connectionProxies.put(new Long(currentConnectionId), proxy);
        }
        ++this.activeConnections;
        return currentConnectionId;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    
    public Collection<String> getInitialHosts() {
        return this.hostList;
    }
    
    public int getActiveHostCount() {
        return this.activeHosts;
    }
    
    public Collection<String> getClosedHosts() {
        return this.closedHosts;
    }
    
    public long getTotalLogicalConnectionCount() {
        return this.connections;
    }
    
    public long getActiveLogicalConnectionCount() {
        return this.activeConnections;
    }
    
    public long getActivePhysicalConnectionCount() {
        long connections = 0L;
        final Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
        synchronized (this.connectionProxies) {
            proxyMap.putAll(this.connectionProxies);
        }
        final Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
        while (i.hasNext()) {
            final LoadBalancingConnectionProxy proxy = i.next().getValue();
            connections += proxy.getActivePhysicalConnectionCount();
        }
        return connections;
    }
    
    public long getTotalPhysicalConnectionCount() {
        long allConnections = this.closedProxyTotalPhysicalConnections;
        final Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
        synchronized (this.connectionProxies) {
            proxyMap.putAll(this.connectionProxies);
        }
        final Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
        while (i.hasNext()) {
            final LoadBalancingConnectionProxy proxy = i.next().getValue();
            allConnections += proxy.getTotalPhysicalConnectionCount();
        }
        return allConnections;
    }
    
    public long getTotalTransactionCount() {
        long transactions = this.closedProxyTotalTransactions;
        final Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
        synchronized (this.connectionProxies) {
            proxyMap.putAll(this.connectionProxies);
        }
        final Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
        while (i.hasNext()) {
            final LoadBalancingConnectionProxy proxy = i.next().getValue();
            transactions += proxy.getTransactionCount();
        }
        return transactions;
    }
    
    public void closeConnectionProxy(final LoadBalancingConnectionProxy proxy) {
        --this.activeConnections;
        this.connectionProxies.remove(new Long(proxy.getConnectionGroupProxyID()));
        this.closedProxyTotalPhysicalConnections += proxy.getTotalPhysicalConnectionCount();
        this.closedProxyTotalTransactions += proxy.getTransactionCount();
    }
    
    public void removeHost(final String host) throws SQLException {
        this.removeHost(host, false);
    }
    
    public void removeHost(final String host, final boolean killExistingConnections) throws SQLException {
        this.removeHost(host, killExistingConnections, true);
    }
    
    public synchronized void removeHost(final String host, final boolean killExistingConnections, final boolean waitForGracefulFailover) throws SQLException {
        if (this.activeHosts == 1) {
            throw SQLError.createSQLException("Cannot remove host, only one configured host active.", null);
        }
        if (this.hostList.remove(host)) {
            --this.activeHosts;
            if (killExistingConnections) {
                final Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
                synchronized (this.connectionProxies) {
                    proxyMap.putAll(this.connectionProxies);
                }
                final Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
                while (i.hasNext()) {
                    final LoadBalancingConnectionProxy proxy = i.next().getValue();
                    if (waitForGracefulFailover) {
                        proxy.removeHostWhenNotInUse(host);
                    }
                    else {
                        proxy.removeHost(host);
                    }
                }
            }
            this.closedHosts.add(host);
            return;
        }
        throw SQLError.createSQLException("Host is not configured: " + host, null);
    }
    
    public void addHost(final String host) {
        this.addHost(host, false);
    }
    
    public void addHost(final String host, final boolean forExisting) {
        synchronized (this) {
            if (this.hostList.add(host)) {
                ++this.activeHosts;
            }
        }
        if (!forExisting) {
            return;
        }
        final Map<Long, LoadBalancingConnectionProxy> proxyMap = new HashMap<Long, LoadBalancingConnectionProxy>();
        synchronized (this.connectionProxies) {
            proxyMap.putAll(this.connectionProxies);
        }
        final Iterator<Map.Entry<Long, LoadBalancingConnectionProxy>> i = proxyMap.entrySet().iterator();
        while (i.hasNext()) {
            final LoadBalancingConnectionProxy proxy = i.next().getValue();
            proxy.addHost(host);
        }
    }
}
