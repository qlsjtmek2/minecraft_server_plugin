// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jmx;

import java.sql.SQLException;

public interface LoadBalanceConnectionGroupManagerMBean
{
    int getActiveHostCount(final String p0);
    
    int getTotalHostCount(final String p0);
    
    long getTotalLogicalConnectionCount(final String p0);
    
    long getActiveLogicalConnectionCount(final String p0);
    
    long getActivePhysicalConnectionCount(final String p0);
    
    long getTotalPhysicalConnectionCount(final String p0);
    
    long getTotalTransactionCount(final String p0);
    
    void removeHost(final String p0, final String p1) throws SQLException;
    
    void stopNewConnectionsToHost(final String p0, final String p1) throws SQLException;
    
    void addHost(final String p0, final String p1, final boolean p2);
    
    String getActiveHostsList(final String p0);
    
    String getRegisteredConnectionGroups();
}
