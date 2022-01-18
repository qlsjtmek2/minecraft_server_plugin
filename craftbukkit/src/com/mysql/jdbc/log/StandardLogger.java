// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.log;

import com.mysql.jdbc.Util;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.util.Date;

public class StandardLogger implements Log
{
    private static final int FATAL = 0;
    private static final int ERROR = 1;
    private static final int WARN = 2;
    private static final int INFO = 3;
    private static final int DEBUG = 4;
    private static final int TRACE = 5;
    public static StringBuffer bufferedLog;
    private boolean logLocationInfo;
    
    public StandardLogger(final String name) {
        this(name, false);
    }
    
    public StandardLogger(final String name, final boolean logLocationInfo) {
        this.logLocationInfo = true;
        this.logLocationInfo = logLocationInfo;
    }
    
    public static void saveLogsToBuffer() {
        if (StandardLogger.bufferedLog == null) {
            StandardLogger.bufferedLog = new StringBuffer();
        }
    }
    
    public boolean isDebugEnabled() {
        return true;
    }
    
    public boolean isErrorEnabled() {
        return true;
    }
    
    public boolean isFatalEnabled() {
        return true;
    }
    
    public boolean isInfoEnabled() {
        return true;
    }
    
    public boolean isTraceEnabled() {
        return true;
    }
    
    public boolean isWarnEnabled() {
        return true;
    }
    
    public void logDebug(final Object message) {
        this.logInternal(4, message, null);
    }
    
    public void logDebug(final Object message, final Throwable exception) {
        this.logInternal(4, message, exception);
    }
    
    public void logError(final Object message) {
        this.logInternal(1, message, null);
    }
    
    public void logError(final Object message, final Throwable exception) {
        this.logInternal(1, message, exception);
    }
    
    public void logFatal(final Object message) {
        this.logInternal(0, message, null);
    }
    
    public void logFatal(final Object message, final Throwable exception) {
        this.logInternal(0, message, exception);
    }
    
    public void logInfo(final Object message) {
        this.logInternal(3, message, null);
    }
    
    public void logInfo(final Object message, final Throwable exception) {
        this.logInternal(3, message, exception);
    }
    
    public void logTrace(final Object message) {
        this.logInternal(5, message, null);
    }
    
    public void logTrace(final Object message, final Throwable exception) {
        this.logInternal(5, message, exception);
    }
    
    public void logWarn(final Object message) {
        this.logInternal(2, message, null);
    }
    
    public void logWarn(final Object message, final Throwable exception) {
        this.logInternal(2, message, exception);
    }
    
    private void logInternal(final int level, final Object msg, final Throwable exception) {
        final StringBuffer msgBuf = new StringBuffer();
        msgBuf.append(new Date().toString());
        msgBuf.append(" ");
        switch (level) {
            case 0: {
                msgBuf.append("FATAL: ");
                break;
            }
            case 1: {
                msgBuf.append("ERROR: ");
                break;
            }
            case 2: {
                msgBuf.append("WARN: ");
                break;
            }
            case 3: {
                msgBuf.append("INFO: ");
                break;
            }
            case 4: {
                msgBuf.append("DEBUG: ");
                break;
            }
            case 5: {
                msgBuf.append("TRACE: ");
                break;
            }
        }
        if (msg instanceof ProfilerEvent) {
            msgBuf.append(LogUtils.expandProfilerEventIfNecessary(msg));
        }
        else {
            if (this.logLocationInfo && level != 5) {
                final Throwable locationException = new Throwable();
                msgBuf.append(LogUtils.findCallingClassAndMethod(locationException));
                msgBuf.append(" ");
            }
            if (msg != null) {
                msgBuf.append(String.valueOf(msg));
            }
        }
        if (exception != null) {
            msgBuf.append("\n");
            msgBuf.append("\n");
            msgBuf.append("EXCEPTION STACK TRACE:");
            msgBuf.append("\n");
            msgBuf.append("\n");
            msgBuf.append(Util.stackTraceToString(exception));
        }
        final String messageAsString = msgBuf.toString();
        System.err.println(messageAsString);
        if (StandardLogger.bufferedLog != null) {
            StandardLogger.bufferedLog.append(messageAsString);
        }
    }
    
    static {
        StandardLogger.bufferedLog = null;
    }
}
