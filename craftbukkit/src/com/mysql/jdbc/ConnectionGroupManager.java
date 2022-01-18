// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.sql.SQLException;
import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;
import java.util.HashMap;

public class ConnectionGroupManager
{
    private static HashMap GROUP_MAP;
    private static LoadBalanceConnectionGroupManager mbean;
    private static boolean hasRegisteredJmx;
    
    public static synchronized ConnectionGroup getConnectionGroupInstance(final String groupName) {
        if (ConnectionGroupManager.GROUP_MAP.containsKey(groupName)) {
            return ConnectionGroupManager.GROUP_MAP.get(groupName);
        }
        final ConnectionGroup group = new ConnectionGroup(groupName);
        ConnectionGroupManager.GROUP_MAP.put(groupName, group);
        return group;
    }
    
    public static void registerJmx() throws SQLException {
        if (ConnectionGroupManager.hasRegisteredJmx) {
            return;
        }
        ConnectionGroupManager.mbean.registerJmx();
        ConnectionGroupManager.hasRegisteredJmx = true;
    }
    
    public static ConnectionGroup getConnectionGroup(final String groupName) {
        return ConnectionGroupManager.GROUP_MAP.get(groupName);
    }
    
    private static Collection getGroupsMatching(final String group) {
        if (group == null || group.equals("")) {
            final Set s = new HashSet();
            s.addAll(ConnectionGroupManager.GROUP_MAP.values());
            return s;
        }
        final Set s = new HashSet();
        final Object o = ConnectionGroupManager.GROUP_MAP.get(group);
        if (o != null) {
            s.add(o);
        }
        return s;
    }
    
    public static void addHost(final String group, final String host, final boolean forExisting) {
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            i.next().addHost(host, forExisting);
        }
    }
    
    public static int getActiveHostCount(final String group) {
        final Set active = new HashSet();
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            active.addAll(i.next().getInitialHosts());
        }
        return active.size();
    }
    
    public static long getActiveLogicalConnectionCount(final String group) {
        int count = 0;
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            count += (int)i.next().getActiveLogicalConnectionCount();
        }
        return count;
    }
    
    public static long getActivePhysicalConnectionCount(final String group) {
        int count = 0;
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            count += (int)i.next().getActivePhysicalConnectionCount();
        }
        return count;
    }
    
    public static int getTotalHostCount(final String group) {
        final Collection s = getGroupsMatching(group);
        final Set hosts = new HashSet();
        for (final ConnectionGroup cg : s) {
            hosts.addAll(cg.getInitialHosts());
            hosts.addAll(cg.getClosedHosts());
        }
        return hosts.size();
    }
    
    public static long getTotalLogicalConnectionCount(final String group) {
        long count = 0L;
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            count += i.next().getTotalLogicalConnectionCount();
        }
        return count;
    }
    
    public static long getTotalPhysicalConnectionCount(final String group) {
        long count = 0L;
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            count += i.next().getTotalPhysicalConnectionCount();
        }
        return count;
    }
    
    public static long getTotalTransactionCount(final String group) {
        long count = 0L;
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            count += i.next().getTotalTransactionCount();
        }
        return count;
    }
    
    public static void removeHost(final String group, final String host) throws SQLException {
        removeHost(group, host, false);
    }
    
    public static void removeHost(final String group, final String host, final boolean removeExisting) throws SQLException {
        final Collection s = getGroupsMatching(group);
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            i.next().removeHost(host, removeExisting);
        }
    }
    
    public static String getActiveHostLists(final String group) {
        final Collection s = getGroupsMatching(group);
        final Map hosts = new HashMap();
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            final Collection l = i.next().getInitialHosts();
            final Iterator j = l.iterator();
            while (j.hasNext()) {
                final String host = j.next().toString();
                Object o = hosts.get(host);
                if (o == null) {
                    o = new Integer(1);
                }
                else {
                    o = new Integer((int)o + 1);
                }
                hosts.put(host, o);
            }
        }
        final StringBuffer sb = new StringBuffer();
        String sep = "";
        final Iterator k = hosts.keySet().iterator();
        while (k.hasNext()) {
            final String host = k.next().toString();
            sb.append(sep);
            sb.append(host);
            sb.append('(');
            sb.append(hosts.get(host));
            sb.append(')');
            sep = ",";
        }
        return sb.toString();
    }
    
    public static String getRegisteredConnectionGroups() {
        final Collection s = getGroupsMatching(null);
        final StringBuffer sb = new StringBuffer();
        String sep = "";
        final Iterator i = s.iterator();
        while (i.hasNext()) {
            final String group = i.next().getGroupName();
            sb.append(sep);
            sb.append(group);
            sep = ",";
        }
        return sb.toString();
    }
    
    static {
        ConnectionGroupManager.GROUP_MAP = new HashMap();
        ConnectionGroupManager.mbean = new LoadBalanceConnectionGroupManager();
        ConnectionGroupManager.hasRegisteredJmx = false;
    }
}
