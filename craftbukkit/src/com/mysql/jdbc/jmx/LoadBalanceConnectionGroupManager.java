// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jmx;

import com.mysql.jdbc.ConnectionGroupManager;
import java.sql.SQLException;
import javax.management.MBeanServer;
import com.mysql.jdbc.ExceptionInterceptor;
import com.mysql.jdbc.SQLError;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class LoadBalanceConnectionGroupManager implements LoadBalanceConnectionGroupManagerMBean
{
    private boolean isJmxRegistered;
    
    public LoadBalanceConnectionGroupManager() {
        this.isJmxRegistered = false;
    }
    
    public synchronized void registerJmx() throws SQLException {
        if (this.isJmxRegistered) {
            return;
        }
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName name = new ObjectName("com.mysql.jdbc.jmx:type=LoadBalanceConnectionGroupManager");
            mbs.registerMBean(this, name);
            this.isJmxRegistered = true;
        }
        catch (Exception e) {
            throw SQLError.createSQLException("Uable to register load-balance management bean with JMX", null, e, null);
        }
    }
    
    public void addHost(final String group, final String host, final boolean forExisting) {
        try {
            ConnectionGroupManager.addHost(group, host, forExisting);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getActiveHostCount(final String group) {
        return ConnectionGroupManager.getActiveHostCount(group);
    }
    
    public long getActiveLogicalConnectionCount(final String group) {
        return ConnectionGroupManager.getActiveLogicalConnectionCount(group);
    }
    
    public long getActivePhysicalConnectionCount(final String group) {
        return ConnectionGroupManager.getActivePhysicalConnectionCount(group);
    }
    
    public int getTotalHostCount(final String group) {
        return ConnectionGroupManager.getTotalHostCount(group);
    }
    
    public long getTotalLogicalConnectionCount(final String group) {
        return ConnectionGroupManager.getTotalLogicalConnectionCount(group);
    }
    
    public long getTotalPhysicalConnectionCount(final String group) {
        return ConnectionGroupManager.getTotalPhysicalConnectionCount(group);
    }
    
    public long getTotalTransactionCount(final String group) {
        return ConnectionGroupManager.getTotalTransactionCount(group);
    }
    
    public void removeHost(final String group, final String host) throws SQLException {
        ConnectionGroupManager.removeHost(group, host);
    }
    
    public String getActiveHostsList(final String group) {
        return ConnectionGroupManager.getActiveHostLists(group);
    }
    
    public String getRegisteredConnectionGroups() {
        return ConnectionGroupManager.getRegisteredConnectionGroups();
    }
    
    public void stopNewConnectionsToHost(final String group, final String host) throws SQLException {
        ConnectionGroupManager.removeHost(group, host);
    }
}
