// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.HashMap;
import java.sql.SQLException;
import com.mysql.jdbc.profiler.ProfilerEventHandler;
import com.mysql.jdbc.log.Log;
import java.util.Map;

public class ProfilerEventHandlerFactory
{
    private static final Map CONNECTIONS_TO_SINKS;
    private Connection ownerConnection;
    private Log log;
    
    public static synchronized ProfilerEventHandler getInstance(final MySQLConnection conn) throws SQLException {
        ProfilerEventHandler handler = ProfilerEventHandlerFactory.CONNECTIONS_TO_SINKS.get(conn);
        if (handler == null) {
            handler = (ProfilerEventHandler)Util.getInstance(conn.getProfilerEventHandler(), new Class[0], new Object[0], conn.getExceptionInterceptor());
            conn.initializeExtension(handler);
            ProfilerEventHandlerFactory.CONNECTIONS_TO_SINKS.put(conn, handler);
        }
        return handler;
    }
    
    public static synchronized void removeInstance(final Connection conn) {
        final ProfilerEventHandler handler = ProfilerEventHandlerFactory.CONNECTIONS_TO_SINKS.remove(conn);
        if (handler != null) {
            handler.destroy();
        }
    }
    
    private ProfilerEventHandlerFactory(final Connection conn) {
        this.ownerConnection = null;
        this.log = null;
        this.ownerConnection = conn;
        try {
            this.log = this.ownerConnection.getLog();
        }
        catch (SQLException sqlEx) {
            throw new RuntimeException("Unable to get logger from connection");
        }
    }
    
    static {
        CONNECTIONS_TO_SINKS = new HashMap();
    }
}
