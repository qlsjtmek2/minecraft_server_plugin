// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import com.avaje.ebeaninternal.server.lib.cron.CronManager;
import java.sql.Connection;
import java.util.logging.Level;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.sql.SQLException;
import com.avaje.ebean.config.DataSourceConfig;
import java.util.Properties;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DataSourcePool implements DataSource
{
    private static final Logger logger;
    private final String name;
    private final DataSourceNotify notify;
    private final DataSourcePoolListener poolListener;
    private final Properties connectionProps;
    private final String databaseUrl;
    private final String databaseDriver;
    private final String heartbeatsql;
    private final int transactionIsolation;
    private final boolean autoCommit;
    private boolean captureStackTrace;
    private int maxStackTraceSize;
    private boolean dataSourceDownAlertSent;
    private long lastTrimTime;
    private boolean dataSourceUp;
    private boolean inWarningMode;
    private int minConnections;
    private int maxConnections;
    private int warningSize;
    private int waitTimeoutMillis;
    private int pstmtCacheSize;
    private int maxInactiveTimeSecs;
    private final PooledConnectionQueue queue;
    private long leakTimeMinutes;
    
    public DataSourcePool(final DataSourceNotify notify, final String name, final DataSourceConfig params) {
        this.dataSourceUp = true;
        this.notify = notify;
        this.name = name;
        this.poolListener = this.createPoolListener(params.getPoolListener());
        this.autoCommit = false;
        this.transactionIsolation = params.getIsolationLevel();
        this.maxInactiveTimeSecs = params.getMaxInactiveTimeSecs();
        this.leakTimeMinutes = params.getLeakTimeMinutes();
        this.captureStackTrace = params.isCaptureStackTrace();
        this.maxStackTraceSize = params.getMaxStackTraceSize();
        this.databaseDriver = params.getDriver();
        this.databaseUrl = params.getUrl();
        this.pstmtCacheSize = params.getPstmtCacheSize();
        this.minConnections = params.getMinConnections();
        this.maxConnections = params.getMaxConnections();
        this.waitTimeoutMillis = params.getWaitTimeoutMillis();
        this.heartbeatsql = params.getHeartbeatSql();
        this.queue = new PooledConnectionQueue(this);
        final String un = params.getUsername();
        final String pw = params.getPassword();
        if (un == null) {
            throw new RuntimeException("DataSource user is null?");
        }
        if (pw == null) {
            throw new RuntimeException("DataSource password is null?");
        }
        (this.connectionProps = new Properties()).setProperty("user", un);
        this.connectionProps.setProperty("password", pw);
        try {
            this.initialise();
        }
        catch (SQLException ex) {
            throw new DataSourceException(ex);
        }
    }
    
    private DataSourcePoolListener createPoolListener(final String cn) {
        if (cn == null) {
            return null;
        }
        try {
            return (DataSourcePoolListener)ClassUtil.newInstance(cn, this.getClass());
        }
        catch (Exception e) {
            throw new DataSourceException(e);
        }
    }
    
    private void initialise() throws SQLException {
        try {
            ClassUtil.forName(this.databaseDriver, this.getClass());
        }
        catch (Throwable e) {
            throw new PersistenceException("Problem loading Database Driver [" + this.databaseDriver + "]: " + e.getMessage(), e);
        }
        final String transIsolation = TransactionIsolation.getLevelDescription(this.transactionIsolation);
        final StringBuffer sb = new StringBuffer();
        sb.append("DataSourcePool [").append(this.name);
        sb.append("] autoCommit[").append(this.autoCommit);
        sb.append("] transIsolation[").append(transIsolation);
        sb.append("] min[").append(this.minConnections);
        sb.append("] max[").append(this.maxConnections).append("]");
        DataSourcePool.logger.info(sb.toString());
        this.queue.ensureMinimumConnections();
    }
    
    public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
        return false;
    }
    
    public <T> T unwrap(final Class<T> arg0) throws SQLException {
        throw new SQLException("Not Implemented");
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getMaxStackTraceSize() {
        return this.maxStackTraceSize;
    }
    
    public boolean isDataSourceUp() {
        return this.dataSourceUp;
    }
    
    protected void notifyWarning(final String msg) {
        if (!this.inWarningMode) {
            this.inWarningMode = true;
            DataSourcePool.logger.warning(msg);
            if (this.notify != null) {
                final String subject = "DataSourcePool [" + this.name + "] warning";
                this.notify.notifyWarning(subject, msg);
            }
        }
    }
    
    private void notifyDataSourceIsDown(final SQLException ex) {
        if (this.isExpectedToBeDownNow()) {
            if (this.dataSourceUp) {
                final String msg = "DataSourcePool [" + this.name + "] is down but in downtime!";
                DataSourcePool.logger.log(Level.WARNING, msg, ex);
            }
        }
        else if (!this.dataSourceDownAlertSent) {
            final String msg = "FATAL: DataSourcePool [" + this.name + "] is down!!!";
            DataSourcePool.logger.log(Level.SEVERE, msg, ex);
            if (this.notify != null) {
                this.notify.notifyDataSourceDown(this.name);
            }
            this.dataSourceDownAlertSent = true;
        }
        if (this.dataSourceUp) {
            this.reset();
        }
        this.dataSourceUp = false;
    }
    
    private void notifyDataSourceIsUp() {
        if (this.dataSourceDownAlertSent) {
            final String msg = "RESOLVED FATAL: DataSourcePool [" + this.name + "] is back up!";
            DataSourcePool.logger.log(Level.SEVERE, msg);
            if (this.notify != null) {
                this.notify.notifyDataSourceUp(this.name);
            }
            this.dataSourceDownAlertSent = false;
        }
        else if (!this.dataSourceUp) {
            DataSourcePool.logger.log(Level.WARNING, "DataSourcePool [" + this.name + "] is back up!");
        }
        if (!this.dataSourceUp) {
            this.dataSourceUp = true;
            this.reset();
        }
    }
    
    protected void checkDataSource() {
        Connection conn = null;
        try {
            conn = this.getConnection();
            this.testConnection(conn);
            this.notifyDataSourceIsUp();
            if (System.currentTimeMillis() > this.lastTrimTime + this.maxInactiveTimeSecs * 1000) {
                this.queue.trim(this.maxInactiveTimeSecs);
                this.lastTrimTime = System.currentTimeMillis();
            }
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(ex);
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                DataSourcePool.logger.log(Level.WARNING, "Can't close connection in checkDataSource!");
            }
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex2) {
                DataSourcePool.logger.log(Level.WARNING, "Can't close connection in checkDataSource!");
            }
        }
    }
    
    private boolean isExpectedToBeDownNow() {
        return CronManager.isDowntime();
    }
    
    public Connection createUnpooledConnection() throws SQLException {
        try {
            final Connection conn = DriverManager.getConnection(this.databaseUrl, this.connectionProps);
            conn.setAutoCommit(this.autoCommit);
            conn.setTransactionIsolation(this.transactionIsolation);
            return conn;
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(null);
            throw ex;
        }
    }
    
    public void setMaxSize(final int max) {
        this.queue.setMaxSize(max);
        this.maxConnections = max;
    }
    
    public int getMaxSize() {
        return this.maxConnections;
    }
    
    public void setMinSize(final int min) {
        this.queue.setMinSize(min);
        this.minConnections = min;
    }
    
    public int getMinSize() {
        return this.minConnections;
    }
    
    public void setWarningSize(final int warningSize) {
        this.queue.setWarningSize(warningSize);
        this.warningSize = warningSize;
    }
    
    public int getWarningSize() {
        return this.warningSize;
    }
    
    public int getWaitTimeoutMillis() {
        return this.waitTimeoutMillis;
    }
    
    public void setMaxInactiveTimeSecs(final int maxInactiveTimeSecs) {
        this.maxInactiveTimeSecs = maxInactiveTimeSecs;
    }
    
    public int getMaxInactiveTimeSecs() {
        return this.maxInactiveTimeSecs;
    }
    
    private void testConnection(final Connection conn) throws SQLException {
        if (this.heartbeatsql == null) {
            return;
        }
        Statement stmt = null;
        ResultSet rset = null;
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(this.heartbeatsql);
            conn.commit();
        }
        finally {
            try {
                if (rset != null) {
                    rset.close();
                }
            }
            catch (SQLException e) {
                DataSourcePool.logger.log(Level.SEVERE, null, e);
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException e) {
                DataSourcePool.logger.log(Level.SEVERE, null, e);
            }
        }
    }
    
    protected boolean validateConnection(final PooledConnection conn) {
        try {
            if (this.heartbeatsql == null) {
                DataSourcePool.logger.info("Can not test connection as heartbeatsql is not set");
                return false;
            }
            this.testConnection(conn);
            return true;
        }
        catch (Exception e) {
            final String desc = "heartbeatsql test failed on connection[" + conn.getName() + "]";
            DataSourcePool.logger.warning(desc);
            return false;
        }
    }
    
    protected void returnConnection(final PooledConnection pooledConnection) {
        if (this.poolListener != null) {
            this.poolListener.onBeforeReturnConnection(pooledConnection);
        }
        this.queue.returnPooledConnection(pooledConnection);
    }
    
    public String getBusyConnectionInformation() {
        return this.queue.getBusyConnectionInformation();
    }
    
    public void dumpBusyConnectionInformation() {
        this.queue.dumpBusyConnectionInformation();
    }
    
    public void closeBusyConnections(final long leakTimeMinutes) {
        this.queue.closeBusyConnections(leakTimeMinutes);
    }
    
    protected PooledConnection createConnectionForQueue(final int connId) throws SQLException {
        try {
            final Connection c = this.createUnpooledConnection();
            final PooledConnection pc = new PooledConnection(this, connId, c);
            pc.resetForUse();
            if (!this.dataSourceUp) {
                this.notifyDataSourceIsUp();
            }
            return pc;
        }
        catch (SQLException ex) {
            this.notifyDataSourceIsDown(ex);
            throw ex;
        }
    }
    
    public void reset() {
        this.queue.reset(this.leakTimeMinutes);
        this.inWarningMode = false;
    }
    
    public Connection getConnection() throws SQLException {
        return this.getPooledConnection();
    }
    
    public PooledConnection getPooledConnection() throws SQLException {
        final PooledConnection c = this.queue.getPooledConnection();
        if (this.captureStackTrace) {
            c.setStackTrace(Thread.currentThread().getStackTrace());
        }
        if (this.poolListener != null) {
            this.poolListener.onAfterBorrowConnection(c);
        }
        return c;
    }
    
    public void testAlert() {
        final String subject = "Test DataSourcePool [" + this.name + "]";
        final String msg = "Just testing if alert message is sent successfully.";
        if (this.notify != null) {
            this.notify.notifyWarning(subject, msg);
        }
    }
    
    public void shutdown() {
        this.queue.shutdown();
    }
    
    public boolean getAutoCommit() {
        return this.autoCommit;
    }
    
    public int getTransactionIsolation() {
        return this.transactionIsolation;
    }
    
    public boolean isCaptureStackTrace() {
        return this.captureStackTrace;
    }
    
    public void setCaptureStackTrace(final boolean captureStackTrace) {
        this.captureStackTrace = captureStackTrace;
    }
    
    public Connection getConnection(final String username, final String password) throws SQLException {
        throw new SQLException("Method not supported");
    }
    
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Method not supported");
    }
    
    public void setLoginTimeout(final int seconds) throws SQLException {
        throw new SQLException("Method not supported");
    }
    
    public PrintWriter getLogWriter() {
        return null;
    }
    
    public void setLogWriter(final PrintWriter writer) throws SQLException {
        throw new SQLException("Method not supported");
    }
    
    public void setLeakTimeMinutes(final long leakTimeMinutes) {
        this.leakTimeMinutes = leakTimeMinutes;
    }
    
    public long getLeakTimeMinutes() {
        return this.leakTimeMinutes;
    }
    
    public int getPstmtCacheSize() {
        return this.pstmtCacheSize;
    }
    
    public void setPstmtCacheSize(final int pstmtCacheSize) {
        this.pstmtCacheSize = pstmtCacheSize;
    }
    
    public Status getStatus(final boolean reset) {
        return this.queue.getStatus(reset);
    }
    
    static {
        logger = Logger.getLogger(DataSourcePool.class.getName());
    }
    
    public static class Status
    {
        private final String name;
        private final int minSize;
        private final int maxSize;
        private final int free;
        private final int busy;
        private final int waiting;
        private final int highWaterMark;
        private final int waitCount;
        private final int hitCount;
        
        protected Status(final String name, final int minSize, final int maxSize, final int free, final int busy, final int waiting, final int highWaterMark, final int waitCount, final int hitCount) {
            this.name = name;
            this.minSize = minSize;
            this.maxSize = maxSize;
            this.free = free;
            this.busy = busy;
            this.waiting = waiting;
            this.highWaterMark = highWaterMark;
            this.waitCount = waitCount;
            this.hitCount = hitCount;
        }
        
        public String toString() {
            return "min:" + this.minSize + " max:" + this.maxSize + " free:" + this.free + " busy:" + this.busy + " waiting:" + this.waiting + " highWaterMark:" + this.highWaterMark + " waitCount:" + this.waitCount + " hitCount:" + this.hitCount;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getMinSize() {
            return this.minSize;
        }
        
        public int getMaxSize() {
            return this.maxSize;
        }
        
        public int getFree() {
            return this.free;
        }
        
        public int getBusy() {
            return this.busy;
        }
        
        public int getWaiting() {
            return this.waiting;
        }
        
        public int getHighWaterMark() {
            return this.highWaterMark;
        }
        
        public int getWaitCount() {
            return this.waitCount;
        }
        
        public int getHitCount() {
            return this.hitCount;
        }
    }
}
