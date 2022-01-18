// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.util.Collections;
import java.util.HashMap;
import com.mysql.jdbc.Constants;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.transaction.xa.Xid;
import javax.transaction.xa.XAException;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import com.mysql.jdbc.Util;
import java.lang.reflect.Constructor;
import com.mysql.jdbc.log.Log;
import java.util.Map;
import com.mysql.jdbc.ConnectionImpl;
import javax.transaction.xa.XAResource;
import javax.sql.XAConnection;

public class MysqlXAConnection extends MysqlPooledConnection implements XAConnection, XAResource
{
    private ConnectionImpl underlyingConnection;
    private static final Map MYSQL_ERROR_CODES_TO_XA_ERROR_CODES;
    private Log log;
    protected boolean logXaCommands;
    private static final Constructor JDBC_4_XA_CONNECTION_WRAPPER_CTOR;
    
    protected static MysqlXAConnection getInstance(final ConnectionImpl mysqlConnection, final boolean logXaCommands) throws SQLException {
        if (!Util.isJdbc4()) {
            return new MysqlXAConnection(mysqlConnection, logXaCommands);
        }
        return (MysqlXAConnection)Util.handleNewInstance(MysqlXAConnection.JDBC_4_XA_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlConnection, logXaCommands }, mysqlConnection.getExceptionInterceptor());
    }
    
    public MysqlXAConnection(final ConnectionImpl connection, final boolean logXaCommands) throws SQLException {
        super(connection);
        this.underlyingConnection = connection;
        this.log = connection.getLog();
        this.logXaCommands = logXaCommands;
    }
    
    public XAResource getXAResource() throws SQLException {
        return this;
    }
    
    public int getTransactionTimeout() throws XAException {
        return 0;
    }
    
    public boolean setTransactionTimeout(final int arg0) throws XAException {
        return false;
    }
    
    public boolean isSameRM(final XAResource xares) throws XAException {
        return xares instanceof MysqlXAConnection && this.underlyingConnection.isSameResource(((MysqlXAConnection)xares).underlyingConnection);
    }
    
    public Xid[] recover(final int flag) throws XAException {
        return recover(this.underlyingConnection, flag);
    }
    
    protected static Xid[] recover(final java.sql.Connection c, final int flag) throws XAException {
        final boolean startRscan = (flag & 0x1000000) > 0;
        final boolean endRscan = (flag & 0x800000) > 0;
        if (!startRscan && !endRscan && flag != 0) {
            throw new MysqlXAException(-5, "Invalid flag, must use TMNOFLAGS, or any combination of TMSTARTRSCAN and TMENDRSCAN", null);
        }
        if (!startRscan) {
            return new Xid[0];
        }
        ResultSet rs = null;
        Statement stmt = null;
        final List recoveredXidList = new ArrayList();
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("XA RECOVER");
            while (rs.next()) {
                final int formatId = rs.getInt(1);
                final int gtridLength = rs.getInt(2);
                final int bqualLength = rs.getInt(3);
                final byte[] gtridAndBqual = rs.getBytes(4);
                final byte[] gtrid = new byte[gtridLength];
                final byte[] bqual = new byte[bqualLength];
                if (gtridAndBqual.length != gtridLength + bqualLength) {
                    throw new MysqlXAException(105, "Error while recovering XIDs from RM. GTRID and BQUAL are wrong sizes", null);
                }
                System.arraycopy(gtridAndBqual, 0, gtrid, 0, gtridLength);
                System.arraycopy(gtridAndBqual, gtridLength, bqual, 0, bqualLength);
                recoveredXidList.add(new MysqlXid(gtrid, bqual, formatId));
            }
        }
        catch (SQLException sqlEx) {
            throw mapXAExceptionFromSQLException(sqlEx);
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException sqlEx2) {
                    throw mapXAExceptionFromSQLException(sqlEx2);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException sqlEx2) {
                    throw mapXAExceptionFromSQLException(sqlEx2);
                }
            }
        }
        final int numXids = recoveredXidList.size();
        final Xid[] asXids = new Xid[numXids];
        final Object[] asObjects = recoveredXidList.toArray();
        for (int i = 0; i < numXids; ++i) {
            asXids[i] = (Xid)asObjects[i];
        }
        return asXids;
    }
    
    public int prepare(final Xid xid) throws XAException {
        final StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA PREPARE ");
        commandBuf.append(xidToString(xid));
        this.dispatchCommand(commandBuf.toString());
        return 0;
    }
    
    public void forget(final Xid xid) throws XAException {
    }
    
    public void rollback(final Xid xid) throws XAException {
        final StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA ROLLBACK ");
        commandBuf.append(xidToString(xid));
        try {
            this.dispatchCommand(commandBuf.toString());
        }
        finally {
            this.underlyingConnection.setInGlobalTx(false);
        }
    }
    
    public void end(final Xid xid, final int flags) throws XAException {
        final StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA END ");
        commandBuf.append(xidToString(xid));
        switch (flags) {
            case 67108864: {
                break;
            }
            case 33554432: {
                commandBuf.append(" SUSPEND");
                break;
            }
            case 536870912: {
                break;
            }
            default: {
                throw new XAException(-5);
            }
        }
        this.dispatchCommand(commandBuf.toString());
    }
    
    public void start(final Xid xid, final int flags) throws XAException {
        final StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA START ");
        commandBuf.append(xidToString(xid));
        switch (flags) {
            case 2097152: {
                commandBuf.append(" JOIN");
                break;
            }
            case 134217728: {
                commandBuf.append(" RESUME");
                break;
            }
            case 0: {
                break;
            }
            default: {
                throw new XAException(-5);
            }
        }
        this.dispatchCommand(commandBuf.toString());
        this.underlyingConnection.setInGlobalTx(true);
    }
    
    public void commit(final Xid xid, final boolean onePhase) throws XAException {
        final StringBuffer commandBuf = new StringBuffer();
        commandBuf.append("XA COMMIT ");
        commandBuf.append(xidToString(xid));
        if (onePhase) {
            commandBuf.append(" ONE PHASE");
        }
        try {
            this.dispatchCommand(commandBuf.toString());
        }
        finally {
            this.underlyingConnection.setInGlobalTx(false);
        }
    }
    
    private ResultSet dispatchCommand(final String command) throws XAException {
        Statement stmt = null;
        try {
            if (this.logXaCommands) {
                this.log.logDebug("Executing XA statement: " + command);
            }
            stmt = this.underlyingConnection.createStatement();
            stmt.execute(command);
            final ResultSet rs = stmt.getResultSet();
            return rs;
        }
        catch (SQLException sqlEx) {
            throw mapXAExceptionFromSQLException(sqlEx);
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex) {}
            }
        }
    }
    
    protected static XAException mapXAExceptionFromSQLException(final SQLException sqlEx) {
        final Integer xaCode = MysqlXAConnection.MYSQL_ERROR_CODES_TO_XA_ERROR_CODES.get(Constants.integerValueOf(sqlEx.getErrorCode()));
        if (xaCode != null) {
            return new MysqlXAException(xaCode, sqlEx.getMessage(), null);
        }
        return new MysqlXAException(sqlEx.getMessage(), (String)null);
    }
    
    private static String xidToString(final Xid xid) {
        final byte[] gtrid = xid.getGlobalTransactionId();
        final byte[] btrid = xid.getBranchQualifier();
        int lengthAsString = 6;
        if (gtrid != null) {
            lengthAsString += 2 * gtrid.length;
        }
        if (btrid != null) {
            lengthAsString += 2 * btrid.length;
        }
        final String formatIdInHex = Integer.toHexString(xid.getFormatId());
        lengthAsString += formatIdInHex.length();
        lengthAsString += 3;
        final StringBuffer asString = new StringBuffer(lengthAsString);
        asString.append("0x");
        if (gtrid != null) {
            for (int i = 0; i < gtrid.length; ++i) {
                final String asHex = Integer.toHexString(gtrid[i] & 0xFF);
                if (asHex.length() == 1) {
                    asString.append("0");
                }
                asString.append(asHex);
            }
        }
        asString.append(",");
        if (btrid != null) {
            asString.append("0x");
            for (int i = 0; i < btrid.length; ++i) {
                final String asHex = Integer.toHexString(btrid[i] & 0xFF);
                if (asHex.length() == 1) {
                    asString.append("0");
                }
                asString.append(asHex);
            }
        }
        asString.append(",0x");
        asString.append(formatIdInHex);
        return asString.toString();
    }
    
    public synchronized java.sql.Connection getConnection() throws SQLException {
        final java.sql.Connection connToWrap = this.getConnection(false, true);
        return connToWrap;
    }
    
    static {
        final HashMap temp = new HashMap();
        temp.put(Constants.integerValueOf(1397), Constants.integerValueOf(-4));
        temp.put(Constants.integerValueOf(1398), Constants.integerValueOf(-5));
        temp.put(Constants.integerValueOf(1399), Constants.integerValueOf(-7));
        temp.put(Constants.integerValueOf(1400), Constants.integerValueOf(-9));
        temp.put(Constants.integerValueOf(1401), Constants.integerValueOf(-3));
        temp.put(Constants.integerValueOf(1402), Constants.integerValueOf(100));
        MYSQL_ERROR_CODES_TO_XA_ERROR_CODES = Collections.unmodifiableMap((Map<?, ?>)temp);
        if (Util.isJdbc4()) {
            try {
                JDBC_4_XA_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4MysqlXAConnection").getConstructor(ConnectionImpl.class, Boolean.TYPE);
                return;
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
}
