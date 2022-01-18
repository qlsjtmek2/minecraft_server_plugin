// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.util.HashMap;
import javax.transaction.xa.XAException;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.Util;
import com.mysql.jdbc.ConnectionImpl;
import javax.transaction.xa.Xid;
import java.util.Map;
import java.lang.reflect.Constructor;
import javax.transaction.xa.XAResource;
import javax.sql.XAConnection;

public class SuspendableXAConnection extends MysqlPooledConnection implements XAConnection, XAResource
{
    private static final Constructor JDBC_4_XA_CONNECTION_WRAPPER_CTOR;
    private static final Map XIDS_TO_PHYSICAL_CONNECTIONS;
    private Xid currentXid;
    private XAConnection currentXAConnection;
    private XAResource currentXAResource;
    private ConnectionImpl underlyingConnection;
    
    protected static SuspendableXAConnection getInstance(final ConnectionImpl mysqlConnection) throws SQLException {
        if (!Util.isJdbc4()) {
            return new SuspendableXAConnection(mysqlConnection);
        }
        return (SuspendableXAConnection)Util.handleNewInstance(SuspendableXAConnection.JDBC_4_XA_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlConnection }, mysqlConnection.getExceptionInterceptor());
    }
    
    public SuspendableXAConnection(final ConnectionImpl connection) {
        super(connection);
        this.underlyingConnection = connection;
    }
    
    private static synchronized XAConnection findConnectionForXid(final ConnectionImpl connectionToWrap, final Xid xid) throws SQLException {
        XAConnection conn = SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.get(xid);
        if (conn == null) {
            conn = new MysqlXAConnection(connectionToWrap, connectionToWrap.getLogXaCommands());
            SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.put(xid, conn);
        }
        return conn;
    }
    
    private static synchronized void removeXAConnectionMapping(final Xid xid) {
        SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.remove(xid);
    }
    
    private synchronized void switchToXid(final Xid xid) throws XAException {
        if (xid == null) {
            throw new XAException();
        }
        try {
            if (!xid.equals(this.currentXid)) {
                final XAConnection toSwitchTo = findConnectionForXid(this.underlyingConnection, xid);
                this.currentXAConnection = toSwitchTo;
                this.currentXid = xid;
                this.currentXAResource = toSwitchTo.getXAResource();
            }
        }
        catch (SQLException sqlEx) {
            throw new XAException();
        }
    }
    
    public XAResource getXAResource() throws SQLException {
        return this;
    }
    
    public void commit(final Xid xid, final boolean arg1) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.commit(xid, arg1);
        removeXAConnectionMapping(xid);
    }
    
    public void end(final Xid xid, final int arg1) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.end(xid, arg1);
    }
    
    public void forget(final Xid xid) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.forget(xid);
        removeXAConnectionMapping(xid);
    }
    
    public int getTransactionTimeout() throws XAException {
        return 0;
    }
    
    public boolean isSameRM(final XAResource xaRes) throws XAException {
        return xaRes == this;
    }
    
    public int prepare(final Xid xid) throws XAException {
        this.switchToXid(xid);
        return this.currentXAResource.prepare(xid);
    }
    
    public Xid[] recover(final int flag) throws XAException {
        return MysqlXAConnection.recover(this.underlyingConnection, flag);
    }
    
    public void rollback(final Xid xid) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.rollback(xid);
        removeXAConnectionMapping(xid);
    }
    
    public boolean setTransactionTimeout(final int arg0) throws XAException {
        return false;
    }
    
    public void start(final Xid xid, final int arg1) throws XAException {
        this.switchToXid(xid);
        if (arg1 != 2097152) {
            this.currentXAResource.start(xid, arg1);
            return;
        }
        this.currentXAResource.start(xid, 134217728);
    }
    
    public synchronized java.sql.Connection getConnection() throws SQLException {
        if (this.currentXAConnection == null) {
            return this.getConnection(false, true);
        }
        return this.currentXAConnection.getConnection();
    }
    
    public void close() throws SQLException {
        if (this.currentXAConnection == null) {
            super.close();
        }
        else {
            removeXAConnectionMapping(this.currentXid);
            this.currentXAConnection.close();
        }
    }
    
    static {
        Label_0063: {
            if (Util.isJdbc4()) {
                try {
                    JDBC_4_XA_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4SuspendableXAConnection").getConstructor(ConnectionImpl.class);
                    break Label_0063;
                }
                catch (SecurityException e) {
                    throw new RuntimeException(e);
                }
                catch (NoSuchMethodException e2) {
                    throw new RuntimeException(e2);
                }
                catch (ClassNotFoundException e3) {
                    throw new RuntimeException(e3);
                }
            }
            JDBC_4_XA_CONNECTION_WRAPPER_CTOR = null;
        }
        XIDS_TO_PHYSICAL_CONNECTIONS = new HashMap();
    }
}
