// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Iterator;
import java.sql.SQLException;
import java.util.List;

public class StandardLoadBalanceExceptionChecker implements LoadBalanceExceptionChecker
{
    private List sqlStateList;
    private List sqlExClassList;
    
    public boolean shouldExceptionTriggerFailover(final SQLException ex) {
        final String sqlState = ex.getSQLState();
        if (sqlState != null) {
            if (sqlState.startsWith("08")) {
                return true;
            }
            if (this.sqlStateList != null) {
                final Iterator i = this.sqlStateList.iterator();
                while (i.hasNext()) {
                    if (sqlState.startsWith(i.next().toString())) {
                        return true;
                    }
                }
            }
        }
        if (ex instanceof CommunicationsException) {
            return true;
        }
        if (this.sqlExClassList != null) {
            final Iterator i = this.sqlExClassList.iterator();
            while (i.hasNext()) {
                if (i.next().isInstance(ex)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void destroy() {
    }
    
    public void init(final Connection conn, final Properties props) throws SQLException {
        this.configureSQLStateList(props.getProperty("loadBalanceSQLStateFailover", null));
        this.configureSQLExceptionSubclassList(props.getProperty("loadBalanceSQLExceptionSubclassFailover", null));
    }
    
    private void configureSQLStateList(final String sqlStates) {
        if (sqlStates == null || "".equals(sqlStates)) {
            return;
        }
        final List states = StringUtils.split(sqlStates, ",", true);
        final List newStates = new ArrayList();
        final Iterator i = states.iterator();
        while (i.hasNext()) {
            final String state = i.next().toString();
            if (state.length() > 0) {
                newStates.add(state);
            }
        }
        if (newStates.size() > 0) {
            this.sqlStateList = newStates;
        }
    }
    
    private void configureSQLExceptionSubclassList(final String sqlExClasses) {
        if (sqlExClasses == null || "".equals(sqlExClasses)) {
            return;
        }
        final List classes = StringUtils.split(sqlExClasses, ",", true);
        final List newClasses = new ArrayList();
        final Iterator i = classes.iterator();
        while (i.hasNext()) {
            final String exClass = i.next().toString();
            try {
                final Class c = Class.forName(exClass);
                newClasses.add(c);
            }
            catch (Exception ex) {}
        }
        if (newClasses.size() > 0) {
            this.sqlExClassList = newClasses;
        }
    }
}
