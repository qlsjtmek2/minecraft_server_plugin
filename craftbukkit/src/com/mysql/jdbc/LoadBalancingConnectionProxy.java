// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Set;
import java.util.LinkedList;
import java.util.Collections;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.Map;
import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;

public class LoadBalancingConnectionProxy implements InvocationHandler, PingTarget
{
    private static Method getLocalTimeMethod;
    private long totalPhysicalConnections;
    private long activePhysicalConnections;
    private String hostToRemove;
    private long lastUsed;
    private long transactionCount;
    private ConnectionGroup connectionGroup;
    private String closedReason;
    public static final String BLACKLIST_TIMEOUT_PROPERTY_KEY = "loadBalanceBlacklistTimeout";
    protected MySQLConnection currentConn;
    protected List<String> hostList;
    protected Map<String, ConnectionImpl> liveConnections;
    private Map<ConnectionImpl, String> connectionsToHostsMap;
    private long[] responseTimes;
    private Map<String, Integer> hostsToListIndexMap;
    private boolean inTransaction;
    private long transactionStartTime;
    private Properties localProps;
    private boolean isClosed;
    private BalanceStrategy balancer;
    private int retriesAllDown;
    private static Map<String, Long> globalBlacklist;
    private int globalBlacklistTimeout;
    private long connectionGroupProxyID;
    private LoadBalanceExceptionChecker exceptionChecker;
    private Map<Class, Boolean> jdbcInterfacesForProxyCache;
    private MySQLConnection thisAsConnection;
    private int autoCommitSwapThreshold;
    private static Constructor JDBC_4_LB_CONNECTION_CTOR;
    private Map<Class, Class[]> allInterfacesToProxy;
    
    LoadBalancingConnectionProxy(List<String> hosts, final Properties props) throws SQLException {
        this.totalPhysicalConnections = 0L;
        this.activePhysicalConnections = 0L;
        this.hostToRemove = null;
        this.lastUsed = 0L;
        this.transactionCount = 0L;
        this.connectionGroup = null;
        this.closedReason = null;
        this.inTransaction = false;
        this.transactionStartTime = 0L;
        this.isClosed = false;
        this.globalBlacklistTimeout = 0;
        this.connectionGroupProxyID = 0L;
        this.jdbcInterfacesForProxyCache = new HashMap<Class, Boolean>();
        this.thisAsConnection = null;
        this.autoCommitSwapThreshold = 0;
        this.allInterfacesToProxy = new HashMap<Class, Class[]>();
        final String group = props.getProperty("loadBalanceConnectionGroup", null);
        boolean enableJMX = false;
        final String enableJMXAsString = props.getProperty("loadBalanceEnableJMX", "false");
        try {
            enableJMX = Boolean.parseBoolean(enableJMXAsString);
        }
        catch (Exception e) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceEnableJMX", new Object[] { enableJMXAsString }), "S1009", null);
        }
        if (group != null) {
            this.connectionGroup = ConnectionGroupManager.getConnectionGroupInstance(group);
            if (enableJMX) {
                ConnectionGroupManager.registerJmx();
            }
            this.connectionGroupProxyID = this.connectionGroup.registerConnectionProxy(this, hosts);
            hosts = new ArrayList<String>(this.connectionGroup.getInitialHosts());
        }
        this.hostList = hosts;
        final int numHosts = this.hostList.size();
        this.liveConnections = new HashMap<String, ConnectionImpl>(numHosts);
        this.connectionsToHostsMap = new HashMap<ConnectionImpl, String>(numHosts);
        this.responseTimes = new long[numHosts];
        this.hostsToListIndexMap = new HashMap<String, Integer>(numHosts);
        (this.localProps = (Properties)props.clone()).remove("HOST");
        this.localProps.remove("PORT");
        for (int i = 0; i < numHosts; ++i) {
            this.hostsToListIndexMap.put(this.hostList.get(i), new Integer(i));
            this.localProps.remove("HOST." + (i + 1));
            this.localProps.remove("PORT." + (i + 1));
        }
        this.localProps.remove("NUM_HOSTS");
        this.localProps.setProperty("useLocalSessionState", "true");
        final String strategy = this.localProps.getProperty("loadBalanceStrategy", "random");
        final String lbExceptionChecker = this.localProps.getProperty("loadBalanceExceptionChecker", "com.mysql.jdbc.StandardLoadBalanceExceptionChecker");
        final String retriesAllDownAsString = this.localProps.getProperty("retriesAllDown", "120");
        try {
            this.retriesAllDown = Integer.parseInt(retriesAllDownAsString);
        }
        catch (NumberFormatException nfe) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForRetriesAllDown", new Object[] { retriesAllDownAsString }), "S1009", null);
        }
        final String blacklistTimeoutAsString = this.localProps.getProperty("loadBalanceBlacklistTimeout", "0");
        try {
            this.globalBlacklistTimeout = Integer.parseInt(blacklistTimeoutAsString);
        }
        catch (NumberFormatException nfe2) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceBlacklistTimeout", new Object[] { retriesAllDownAsString }), "S1009", null);
        }
        if ("random".equals(strategy)) {
            this.balancer = Util.loadExtensions(null, props, "com.mysql.jdbc.RandomBalanceStrategy", "InvalidLoadBalanceStrategy", null).get(0);
        }
        else if ("bestResponseTime".equals(strategy)) {
            this.balancer = Util.loadExtensions(null, props, "com.mysql.jdbc.BestResponseTimeBalanceStrategy", "InvalidLoadBalanceStrategy", null).get(0);
        }
        else {
            this.balancer = Util.loadExtensions(null, props, strategy, "InvalidLoadBalanceStrategy", null).get(0);
        }
        final String autoCommitSwapThresholdAsString = props.getProperty("loadBalanceAutoCommitStatementThreshold", "0");
        try {
            this.autoCommitSwapThreshold = Integer.parseInt(autoCommitSwapThresholdAsString);
        }
        catch (NumberFormatException nfe3) {
            throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceAutoCommitStatementThreshold", new Object[] { autoCommitSwapThresholdAsString }), "S1009", null);
        }
        final String autoCommitSwapRegex = props.getProperty("loadBalanceAutoCommitStatementRegex", "");
        if (!"".equals(autoCommitSwapRegex)) {
            try {
                "".matches(autoCommitSwapRegex);
            }
            catch (Exception e2) {
                throw SQLError.createSQLException(Messages.getString("LoadBalancingConnectionProxy.badValueForLoadBalanceAutoCommitStatementRegex", new Object[] { autoCommitSwapRegex }), "S1009", null);
            }
        }
        if (this.autoCommitSwapThreshold > 0) {
            final String statementInterceptors = this.localProps.getProperty("statementInterceptors");
            if (statementInterceptors == null) {
                this.localProps.setProperty("statementInterceptors", "com.mysql.jdbc.LoadBalancedAutoCommitInterceptor");
            }
            else if (statementInterceptors.length() > 0) {
                this.localProps.setProperty("statementInterceptors", statementInterceptors + ",com.mysql.jdbc.LoadBalancedAutoCommitInterceptor");
            }
            props.setProperty("statementInterceptors", this.localProps.getProperty("statementInterceptors"));
        }
        this.balancer.init(null, props);
        (this.exceptionChecker = Util.loadExtensions(null, props, lbExceptionChecker, "InvalidLoadBalanceExceptionChecker", null).get(0)).init(null, props);
        if (Util.isJdbc4() || LoadBalancingConnectionProxy.JDBC_4_LB_CONNECTION_CTOR != null) {
            this.thisAsConnection = (MySQLConnection)Util.handleNewInstance(LoadBalancingConnectionProxy.JDBC_4_LB_CONNECTION_CTOR, new Object[] { this }, null);
        }
        else {
            this.thisAsConnection = new LoadBalancedMySQLConnection(this);
        }
        this.pickNewConnection();
    }
    
    public synchronized ConnectionImpl createConnectionForHost(final String hostPortSpec) throws SQLException {
        final Properties connProps = (Properties)this.localProps.clone();
        final String[] hostPortPair = NonRegisteringDriver.parseHostPortPair(hostPortSpec);
        final String hostName = hostPortPair[0];
        String portNumber = hostPortPair[1];
        final String dbName = connProps.getProperty("DBNAME");
        if (hostName == null) {
            throw new SQLException("Could not find a hostname to start a connection to");
        }
        if (portNumber == null) {
            portNumber = "3306";
        }
        connProps.setProperty("HOST", hostName);
        connProps.setProperty("PORT", portNumber);
        connProps.setProperty("HOST.1", hostName);
        connProps.setProperty("PORT.1", portNumber);
        connProps.setProperty("NUM_HOSTS", "1");
        connProps.setProperty("roundRobinLoadBalance", "false");
        final ConnectionImpl conn = (ConnectionImpl)ConnectionImpl.getInstance(hostName, Integer.parseInt(portNumber), connProps, dbName, "jdbc:mysql://" + hostName + ":" + portNumber + "/");
        this.liveConnections.put(hostPortSpec, conn);
        this.connectionsToHostsMap.put(conn, hostPortSpec);
        ++this.activePhysicalConnections;
        ++this.totalPhysicalConnections;
        conn.setProxy(this.thisAsConnection);
        return conn;
    }
    
    void dealWithInvocationException(final InvocationTargetException e) throws SQLException, Throwable, InvocationTargetException {
        final Throwable t = e.getTargetException();
        if (t != null) {
            if (t instanceof SQLException && this.shouldExceptionTriggerFailover((SQLException)t)) {
                this.invalidateCurrentConnection();
                this.pickNewConnection();
            }
            throw t;
        }
        throw e;
    }
    
    synchronized void invalidateCurrentConnection() throws SQLException {
        try {
            if (!this.currentConn.isClosed()) {
                this.currentConn.close();
            }
        }
        finally {
            if (this.isGlobalBlacklistEnabled()) {
                this.addToGlobalBlacklist(this.connectionsToHostsMap.get(this.currentConn));
            }
            this.liveConnections.remove(this.connectionsToHostsMap.get(this.currentConn));
            final Object mappedHost = this.connectionsToHostsMap.remove(this.currentConn);
            if (mappedHost != null && this.hostsToListIndexMap.containsKey(mappedHost)) {
                final int hostIndex = this.hostsToListIndexMap.get(mappedHost);
                synchronized (this.responseTimes) {
                    this.responseTimes[hostIndex] = 0L;
                }
            }
        }
    }
    
    private void closeAllConnections() {
        synchronized (this) {
            final Iterator<ConnectionImpl> allConnections = this.liveConnections.values().iterator();
            while (allConnections.hasNext()) {
                try {
                    --this.activePhysicalConnections;
                    allConnections.next().close();
                }
                catch (SQLException e) {}
            }
            if (!this.isClosed) {
                this.balancer.destroy();
                if (this.connectionGroup != null) {
                    this.connectionGroup.closeConnectionProxy(this);
                }
            }
            this.liveConnections.clear();
            this.connectionsToHostsMap.clear();
        }
    }
    
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return this.invoke(proxy, method, args, true);
    }
    
    public Object invoke(final Object proxy, final Method method, final Object[] args, final boolean swapAtTransactionBoundary) throws Throwable {
        final String methodName = method.getName();
        if ("getLoadBalanceSafeProxy".equals(methodName)) {
            return this.currentConn;
        }
        if ("equals".equals(methodName) && args.length == 1) {
            if (args[0] instanceof Proxy) {
                return ((Proxy)args[0]).equals(this);
            }
            return this.equals(args[0]);
        }
        else {
            if ("hashCode".equals(methodName)) {
                return new Integer(this.hashCode());
            }
            if ("close".equals(methodName)) {
                this.closeAllConnections();
                this.isClosed = true;
                this.closedReason = "Connection explicitly closed.";
                return null;
            }
            if ("isClosed".equals(methodName)) {
                return this.isClosed;
            }
            if (this.isClosed) {
                String reason = "No operations allowed after connection closed.";
                if (this.closedReason != null) {
                    reason = reason + "  " + this.closedReason;
                }
                throw SQLError.createSQLException(reason, "08003", null);
            }
            if (!this.inTransaction) {
                this.inTransaction = true;
                this.transactionStartTime = getLocalTimeBestResolution();
                ++this.transactionCount;
            }
            Object result = null;
            try {
                this.lastUsed = System.currentTimeMillis();
                result = method.invoke(this.thisAsConnection, args);
                if (result != null) {
                    if (result instanceof Statement) {
                        ((Statement)result).setPingTarget(this);
                    }
                    result = this.proxyIfInterfaceIsJdbc(result, result.getClass());
                }
            }
            catch (InvocationTargetException e) {
                this.dealWithInvocationException(e);
            }
            finally {
                if (swapAtTransactionBoundary && ("commit".equals(methodName) || "rollback".equals(methodName))) {
                    this.inTransaction = false;
                    final String host = this.connectionsToHostsMap.get(this.currentConn);
                    if (host != null) {
                        synchronized (this.responseTimes) {
                            final int hostIndex = this.hostsToListIndexMap.get(host);
                            if (hostIndex < this.responseTimes.length) {
                                this.responseTimes[hostIndex] = getLocalTimeBestResolution() - this.transactionStartTime;
                            }
                        }
                    }
                    this.pickNewConnection();
                }
            }
            return result;
        }
    }
    
    protected synchronized void pickNewConnection() throws SQLException {
        if (this.currentConn == null) {
            this.currentConn = this.balancer.pickConnection(this, Collections.unmodifiableList((List<? extends String>)this.hostList), Collections.unmodifiableMap((Map<? extends String, ? extends ConnectionImpl>)this.liveConnections), this.responseTimes.clone(), this.retriesAllDown);
            return;
        }
        if (this.currentConn.isClosed()) {
            this.invalidateCurrentConnection();
        }
        final int pingTimeout = this.currentConn.getLoadBalancePingTimeout();
        final boolean pingBeforeReturn = this.currentConn.getLoadBalanceValidateConnectionOnSwapServer();
        int hostsTried = 0;
        final int hostsToTry = this.hostList.size();
        while (hostsTried <= hostsToTry) {
            try {
                final ConnectionImpl newConn = this.balancer.pickConnection(this, Collections.unmodifiableList((List<? extends String>)this.hostList), Collections.unmodifiableMap((Map<? extends String, ? extends ConnectionImpl>)this.liveConnections), this.responseTimes.clone(), this.retriesAllDown);
                if (this.currentConn != null) {
                    if (pingBeforeReturn) {
                        if (pingTimeout == 0) {
                            newConn.ping();
                        }
                        else {
                            newConn.pingInternal(true, pingTimeout);
                        }
                    }
                    this.syncSessionState(this.currentConn, newConn);
                }
                this.currentConn = newConn;
                return;
            }
            catch (SQLException e) {
                if (this.shouldExceptionTriggerFailover(e)) {
                    this.invalidateCurrentConnection();
                }
                ++hostsTried;
                continue;
            }
            break;
        }
        this.isClosed = true;
        this.closedReason = "Connection closed after inability to pick valid new connection during fail-over.";
    }
    
    Object proxyIfInterfaceIsJdbc(final Object toProxy, final Class clazz) {
        if (this.isInterfaceJdbc(clazz)) {
            final Class[] interfacesToProxy = this.getAllInterfacesToProxy(clazz);
            return Proxy.newProxyInstance(toProxy.getClass().getClassLoader(), interfacesToProxy, this.createConnectionProxy(toProxy));
        }
        return toProxy;
    }
    
    private Class[] getAllInterfacesToProxy(final Class clazz) {
        Class[] interfacesToProxy = this.allInterfacesToProxy.get(clazz);
        if (interfacesToProxy != null) {
            return interfacesToProxy;
        }
        final List<Class> interfaces = new LinkedList<Class>();
        for (Class superClass = clazz; !superClass.equals(Object.class); superClass = superClass.getSuperclass()) {
            final Class[] declared = superClass.getInterfaces();
            for (int i = 0; i < declared.length; ++i) {
                interfaces.add(declared[i]);
            }
        }
        interfacesToProxy = new Class[interfaces.size()];
        interfaces.toArray(interfacesToProxy);
        this.allInterfacesToProxy.put(clazz, interfacesToProxy);
        return interfacesToProxy;
    }
    
    private boolean isInterfaceJdbc(final Class clazz) {
        if (this.jdbcInterfacesForProxyCache.containsKey(clazz)) {
            return this.jdbcInterfacesForProxyCache.get(clazz);
        }
        final Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            final String packageName = interfaces[i].getPackage().getName();
            if ("java.sql".equals(packageName) || "javax.sql".equals(packageName) || "com.mysql.jdbc".equals(packageName)) {
                this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(true));
                return true;
            }
            if (this.isInterfaceJdbc(interfaces[i])) {
                this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(true));
                return true;
            }
        }
        this.jdbcInterfacesForProxyCache.put(clazz, new Boolean(false));
        return false;
    }
    
    protected ConnectionErrorFiringInvocationHandler createConnectionProxy(final Object toProxy) {
        return new ConnectionErrorFiringInvocationHandler(toProxy);
    }
    
    private static long getLocalTimeBestResolution() {
        if (LoadBalancingConnectionProxy.getLocalTimeMethod != null) {
            try {
                return (long)LoadBalancingConnectionProxy.getLocalTimeMethod.invoke(null, (Object[])null);
            }
            catch (IllegalArgumentException e) {}
            catch (IllegalAccessException e2) {}
            catch (InvocationTargetException ex) {}
        }
        return System.currentTimeMillis();
    }
    
    public synchronized void doPing() throws SQLException {
        SQLException se = null;
        boolean foundHost = false;
        final int pingTimeout = this.currentConn.getLoadBalancePingTimeout();
        synchronized (this) {
            for (final String host : this.hostList) {
                final ConnectionImpl conn = this.liveConnections.get(host);
                if (conn == null) {
                    continue;
                }
                try {
                    if (pingTimeout == 0) {
                        conn.ping();
                    }
                    else {
                        conn.pingInternal(true, pingTimeout);
                    }
                    foundHost = true;
                }
                catch (SQLException e) {
                    --this.activePhysicalConnections;
                    if (host.equals(this.connectionsToHostsMap.get(this.currentConn))) {
                        this.closeAllConnections();
                        this.isClosed = true;
                        this.closedReason = "Connection closed because ping of current connection failed.";
                        throw e;
                    }
                    if (e.getMessage().equals(Messages.getString("Connection.exceededConnectionLifetime"))) {
                        if (se == null) {
                            se = e;
                        }
                    }
                    else {
                        se = e;
                        if (this.isGlobalBlacklistEnabled()) {
                            this.addToGlobalBlacklist(host);
                        }
                    }
                    this.liveConnections.remove(this.connectionsToHostsMap.get(conn));
                }
            }
        }
        if (!foundHost) {
            this.closeAllConnections();
            this.isClosed = true;
            this.closedReason = "Connection closed due to inability to ping any active connections.";
            if (se != null) {
                throw se;
            }
            ((ConnectionImpl)this.currentConn).throwConnectionClosedException();
        }
    }
    
    public void addToGlobalBlacklist(final String host, final long timeout) {
        if (this.isGlobalBlacklistEnabled()) {
            synchronized (LoadBalancingConnectionProxy.globalBlacklist) {
                LoadBalancingConnectionProxy.globalBlacklist.put(host, new Long(timeout));
            }
        }
    }
    
    public void addToGlobalBlacklist(final String host) {
        this.addToGlobalBlacklist(host, System.currentTimeMillis() + this.globalBlacklistTimeout);
    }
    
    public boolean isGlobalBlacklistEnabled() {
        return this.globalBlacklistTimeout > 0;
    }
    
    public Map<String, Long> getGlobalBlacklist() {
        if (!this.isGlobalBlacklistEnabled()) {
            final String localHostToRemove = this.hostToRemove;
            if (this.hostToRemove != null) {
                final HashMap<String, Long> fakedBlacklist = new HashMap<String, Long>();
                fakedBlacklist.put(localHostToRemove, new Long(System.currentTimeMillis() + 5000L));
                return fakedBlacklist;
            }
            return new HashMap<String, Long>(1);
        }
        else {
            final Map<String, Long> blacklistClone = new HashMap<String, Long>(LoadBalancingConnectionProxy.globalBlacklist.size());
            synchronized (LoadBalancingConnectionProxy.globalBlacklist) {
                blacklistClone.putAll(LoadBalancingConnectionProxy.globalBlacklist);
            }
            final Set<String> keys = blacklistClone.keySet();
            keys.retainAll(this.hostList);
            if (keys.size() == this.hostList.size()) {
                return new HashMap<String, Long>(1);
            }
            final Iterator<String> i = keys.iterator();
            while (i.hasNext()) {
                final String host = i.next();
                final Long timeout = LoadBalancingConnectionProxy.globalBlacklist.get(host);
                if (timeout != null && timeout < System.currentTimeMillis()) {
                    synchronized (LoadBalancingConnectionProxy.globalBlacklist) {
                        LoadBalancingConnectionProxy.globalBlacklist.remove(host);
                    }
                    i.remove();
                }
            }
            return blacklistClone;
        }
    }
    
    public boolean shouldExceptionTriggerFailover(final SQLException ex) {
        return this.exceptionChecker.shouldExceptionTriggerFailover(ex);
    }
    
    public void removeHostWhenNotInUse(final String host) throws SQLException {
        final int timeBetweenChecks = 1000;
        final long timeBeforeHardFail = 15000L;
        this.addToGlobalBlacklist(host, timeBeforeHardFail + 1000L);
        final long cur = System.currentTimeMillis();
        while (System.currentTimeMillis() - timeBeforeHardFail < cur) {
            synchronized (this) {
                this.hostToRemove = host;
                if (!host.equals(this.currentConn.getHost())) {
                    this.removeHost(host);
                    return;
                }
            }
            try {
                Thread.sleep(timeBetweenChecks);
            }
            catch (InterruptedException e) {}
        }
        this.removeHost(host);
    }
    
    public void removeHost(final String host) throws SQLException {
        synchronized (this) {
            if (this.connectionGroup != null && this.connectionGroup.getInitialHosts().size() == 1 && this.connectionGroup.getInitialHosts().contains(host)) {
                throw SQLError.createSQLException("Cannot remove only configured host.", null);
            }
            this.hostToRemove = host;
            if (host.equals(this.currentConn.getHost())) {
                this.closeAllConnections();
            }
            else {
                this.connectionsToHostsMap.remove(this.liveConnections.remove(host));
                final Integer idx = this.hostsToListIndexMap.remove(host);
                final long[] newResponseTimes = new long[this.responseTimes.length - 1];
                int newIdx = 0;
                for (final String copyHost : this.hostList) {
                    if (idx != null && idx < this.responseTimes.length) {
                        newResponseTimes[newIdx] = this.responseTimes[idx];
                        this.hostsToListIndexMap.put(copyHost, new Integer(newIdx));
                    }
                    ++newIdx;
                }
                this.responseTimes = newResponseTimes;
            }
        }
    }
    
    public synchronized boolean addHost(final String host) {
        synchronized (this) {
            if (this.hostsToListIndexMap.containsKey(host)) {
                return false;
            }
            final long[] newResponseTimes = new long[this.responseTimes.length + 1];
            for (int i = 0; i < this.responseTimes.length; ++i) {
                newResponseTimes[i] = this.responseTimes[i];
            }
            this.responseTimes = newResponseTimes;
            this.hostList.add(host);
            this.hostsToListIndexMap.put(host, new Integer(this.responseTimes.length - 1));
        }
        return true;
    }
    
    public long getLastUsed() {
        return this.lastUsed;
    }
    
    public boolean inTransaction() {
        return this.inTransaction;
    }
    
    public long getTransactionCount() {
        return this.transactionCount;
    }
    
    public long getActivePhysicalConnectionCount() {
        return this.activePhysicalConnections;
    }
    
    public long getTotalPhysicalConnectionCount() {
        return this.totalPhysicalConnections;
    }
    
    public long getConnectionGroupProxyID() {
        return this.connectionGroupProxyID;
    }
    
    public String getCurrentActiveHost() {
        final MySQLConnection c = this.currentConn;
        if (c != null) {
            final Object o = this.connectionsToHostsMap.get(c);
            if (o != null) {
                return o.toString();
            }
        }
        return null;
    }
    
    public long getCurrentTransactionDuration() {
        long st = 0L;
        if (this.inTransaction && (st = this.transactionStartTime) > 0L) {
            return getLocalTimeBestResolution() - this.transactionStartTime;
        }
        return 0L;
    }
    
    protected void syncSessionState(final Connection initial, final Connection target) throws SQLException {
        if (initial == null || target == null) {
            return;
        }
        target.setAutoCommit(initial.getAutoCommit());
        target.setCatalog(initial.getCatalog());
        target.setTransactionIsolation(initial.getTransactionIsolation());
        target.setReadOnly(initial.isReadOnly());
    }
    
    static {
        try {
            LoadBalancingConnectionProxy.getLocalTimeMethod = System.class.getMethod("nanoTime", (Class<?>[])new Class[0]);
        }
        catch (SecurityException e) {}
        catch (NoSuchMethodException ex) {}
        LoadBalancingConnectionProxy.globalBlacklist = new HashMap<String, Long>();
        if (Util.isJdbc4()) {
            try {
                LoadBalancingConnectionProxy.JDBC_4_LB_CONNECTION_CTOR = Class.forName("com.mysql.jdbc.JDBC4LoadBalancedMySQLConnection").getConstructor(LoadBalancingConnectionProxy.class);
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
    }
    
    protected class ConnectionErrorFiringInvocationHandler implements InvocationHandler
    {
        Object invokeOn;
        
        public ConnectionErrorFiringInvocationHandler(final Object toInvokeOn) {
            this.invokeOn = null;
            this.invokeOn = toInvokeOn;
        }
        
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            Object result = null;
            try {
                result = method.invoke(this.invokeOn, args);
                if (result != null) {
                    result = LoadBalancingConnectionProxy.this.proxyIfInterfaceIsJdbc(result, result.getClass());
                }
            }
            catch (InvocationTargetException e) {
                LoadBalancingConnectionProxy.this.dealWithInvocationException(e);
            }
            return result;
        }
    }
}
