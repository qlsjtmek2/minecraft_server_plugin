// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Iterator;
import java.util.Map;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.List;

public class FailoverConnectionProxy extends LoadBalancingConnectionProxy
{
    boolean failedOver;
    boolean hasTriedMaster;
    private long masterFailTimeMillis;
    boolean preferSlaveDuringFailover;
    private String primaryHostPortSpec;
    private long queriesBeforeRetryMaster;
    long queriesIssuedFailedOver;
    private int secondsBeforeRetryMaster;
    
    FailoverConnectionProxy(final List<String> hosts, final Properties props) throws SQLException {
        super(hosts, props);
        final ConnectionPropertiesImpl connectionProps = new ConnectionPropertiesImpl();
        connectionProps.initializeProperties(props);
        this.queriesBeforeRetryMaster = connectionProps.getQueriesBeforeRetryMaster();
        this.secondsBeforeRetryMaster = connectionProps.getSecondsBeforeRetryMaster();
        this.preferSlaveDuringFailover = false;
    }
    
    protected ConnectionErrorFiringInvocationHandler createConnectionProxy(final Object toProxy) {
        return new FailoverInvocationHandler(toProxy);
    }
    
    void dealWithInvocationException(final InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
        final Throwable t = e.getTargetException();
        if (t != null) {
            if (this.failedOver) {
                this.createPrimaryConnection();
                if (this.currentConn != null) {
                    throw t;
                }
            }
            this.failOver();
            throw t;
        }
        throw e;
    }
    
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final String methodName = method.getName();
        if ("setPreferSlaveDuringFailover".equals(methodName)) {
            this.preferSlaveDuringFailover = (boolean)args[0];
        }
        else if ("clearHasTriedMaster".equals(methodName)) {
            this.hasTriedMaster = false;
        }
        else {
            if ("hasTriedMaster".equals(methodName)) {
                return this.hasTriedMaster;
            }
            if ("isMasterConnection".equals(methodName)) {
                return !this.failedOver;
            }
            if ("isSlaveConnection".equals(methodName)) {
                return this.failedOver;
            }
            if ("setReadOnly".equals(methodName)) {
                if (this.failedOver) {
                    return null;
                }
            }
            else if ("setAutoCommit".equals(methodName) && this.failedOver && this.shouldFallBack() && Boolean.TRUE.equals(args[0]) && this.failedOver) {
                this.createPrimaryConnection();
                return super.invoke(proxy, method, args, this.failedOver);
            }
        }
        return super.invoke(proxy, method, args, this.failedOver);
    }
    
    private void createPrimaryConnection() throws SQLException {
        try {
            this.currentConn = this.createConnectionForHost(this.primaryHostPortSpec);
            this.failedOver = false;
            this.hasTriedMaster = true;
            this.queriesIssuedFailedOver = 0L;
        }
        catch (SQLException sqlEx) {
            this.failedOver = true;
            if (this.currentConn != null) {
                this.currentConn.getLog().logWarn("Connection to primary host failed", sqlEx);
            }
        }
    }
    
    synchronized void invalidateCurrentConnection() throws SQLException {
        if (!this.failedOver) {
            this.failedOver = true;
            this.queriesIssuedFailedOver = 0L;
            this.masterFailTimeMillis = System.currentTimeMillis();
        }
        super.invalidateCurrentConnection();
    }
    
    protected synchronized void pickNewConnection() throws SQLException {
        if (this.primaryHostPortSpec == null) {
            this.primaryHostPortSpec = this.hostList.remove(0);
        }
        if (this.currentConn == null || (this.failedOver && this.shouldFallBack())) {
            this.createPrimaryConnection();
            if (this.currentConn != null) {
                return;
            }
        }
        this.failOver();
    }
    
    private void failOver() throws SQLException {
        if (this.failedOver) {
            for (final Map.Entry<String, ConnectionImpl> entry : this.liveConnections.entrySet()) {
                entry.getValue().close();
            }
            this.liveConnections.clear();
        }
        super.pickNewConnection();
        if (this.currentConn.getFailOverReadOnly()) {
            this.currentConn.setReadOnly(true);
        }
        else {
            this.currentConn.setReadOnly(false);
        }
        this.failedOver = true;
    }
    
    private boolean shouldFallBack() {
        final long secondsSinceFailedOver = (System.currentTimeMillis() - this.masterFailTimeMillis) / 1000L;
        if (secondsSinceFailedOver >= this.secondsBeforeRetryMaster) {
            this.masterFailTimeMillis = System.currentTimeMillis();
            return true;
        }
        return this.queriesBeforeRetryMaster != 0L && this.queriesIssuedFailedOver >= this.queriesBeforeRetryMaster;
    }
    
    class FailoverInvocationHandler extends ConnectionErrorFiringInvocationHandler
    {
        public FailoverInvocationHandler(final Object toInvokeOn) {
            super(toInvokeOn);
        }
        
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final String methodName = method.getName();
            if (FailoverConnectionProxy.this.failedOver && methodName.indexOf("execute") != -1) {
                final FailoverConnectionProxy this$0 = FailoverConnectionProxy.this;
                ++this$0.queriesIssuedFailedOver;
            }
            return super.invoke(proxy, method, args);
        }
    }
}
