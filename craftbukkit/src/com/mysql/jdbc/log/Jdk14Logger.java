// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.log;

import com.mysql.jdbc.profiler.ProfilerEvent;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Jdk14Logger implements Log
{
    private static final Level DEBUG;
    private static final Level ERROR;
    private static final Level FATAL;
    private static final Level INFO;
    private static final Level TRACE;
    private static final Level WARN;
    protected Logger jdkLogger;
    
    public Jdk14Logger(final String name) {
        this.jdkLogger = null;
        this.jdkLogger = Logger.getLogger(name);
    }
    
    public boolean isDebugEnabled() {
        return this.jdkLogger.isLoggable(Level.FINE);
    }
    
    public boolean isErrorEnabled() {
        return this.jdkLogger.isLoggable(Level.SEVERE);
    }
    
    public boolean isFatalEnabled() {
        return this.jdkLogger.isLoggable(Level.SEVERE);
    }
    
    public boolean isInfoEnabled() {
        return this.jdkLogger.isLoggable(Level.INFO);
    }
    
    public boolean isTraceEnabled() {
        return this.jdkLogger.isLoggable(Level.FINEST);
    }
    
    public boolean isWarnEnabled() {
        return this.jdkLogger.isLoggable(Level.WARNING);
    }
    
    public void logDebug(final Object message) {
        this.logInternal(Jdk14Logger.DEBUG, message, null);
    }
    
    public void logDebug(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.DEBUG, message, exception);
    }
    
    public void logError(final Object message) {
        this.logInternal(Jdk14Logger.ERROR, message, null);
    }
    
    public void logError(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.ERROR, message, exception);
    }
    
    public void logFatal(final Object message) {
        this.logInternal(Jdk14Logger.FATAL, message, null);
    }
    
    public void logFatal(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.FATAL, message, exception);
    }
    
    public void logInfo(final Object message) {
        this.logInternal(Jdk14Logger.INFO, message, null);
    }
    
    public void logInfo(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.INFO, message, exception);
    }
    
    public void logTrace(final Object message) {
        this.logInternal(Jdk14Logger.TRACE, message, null);
    }
    
    public void logTrace(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.TRACE, message, exception);
    }
    
    public void logWarn(final Object message) {
        this.logInternal(Jdk14Logger.WARN, message, null);
    }
    
    public void logWarn(final Object message, final Throwable exception) {
        this.logInternal(Jdk14Logger.WARN, message, exception);
    }
    
    private static final int findCallerStackDepth(final StackTraceElement[] stackTrace) {
        for (int numFrames = stackTrace.length, i = 0; i < numFrames; ++i) {
            final String callerClassName = stackTrace[i].getClassName();
            if (!callerClassName.startsWith("com.mysql.jdbc") || callerClassName.startsWith("com.mysql.jdbc.compliance")) {
                return i;
            }
        }
        return 0;
    }
    
    private void logInternal(final Level level, final Object msg, final Throwable exception) {
        if (this.jdkLogger.isLoggable(level)) {
            String messageAsString = null;
            String callerMethodName = "N/A";
            String callerClassName = "N/A";
            int lineNumber = 0;
            String fileName = "N/A";
            if (msg instanceof ProfilerEvent) {
                messageAsString = LogUtils.expandProfilerEventIfNecessary(msg).toString();
            }
            else {
                final Throwable locationException = new Throwable();
                final StackTraceElement[] locations = locationException.getStackTrace();
                final int frameIdx = findCallerStackDepth(locations);
                if (frameIdx != 0) {
                    callerClassName = locations[frameIdx].getClassName();
                    callerMethodName = locations[frameIdx].getMethodName();
                    lineNumber = locations[frameIdx].getLineNumber();
                    fileName = locations[frameIdx].getFileName();
                }
                messageAsString = String.valueOf(msg);
            }
            if (exception == null) {
                this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString);
            }
            else {
                this.jdkLogger.logp(level, callerClassName, callerMethodName, messageAsString, exception);
            }
        }
    }
    
    static {
        DEBUG = Level.FINE;
        ERROR = Level.SEVERE;
        FATAL = Level.SEVERE;
        INFO = Level.INFO;
        TRACE = Level.FINEST;
        WARN = Level.WARNING;
    }
}
