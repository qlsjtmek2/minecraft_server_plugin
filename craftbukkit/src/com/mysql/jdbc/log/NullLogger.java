// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.log;

public class NullLogger implements Log
{
    public NullLogger(final String instanceName) {
    }
    
    public boolean isDebugEnabled() {
        return false;
    }
    
    public boolean isErrorEnabled() {
        return false;
    }
    
    public boolean isFatalEnabled() {
        return false;
    }
    
    public boolean isInfoEnabled() {
        return false;
    }
    
    public boolean isTraceEnabled() {
        return false;
    }
    
    public boolean isWarnEnabled() {
        return false;
    }
    
    public void logDebug(final Object msg) {
    }
    
    public void logDebug(final Object msg, final Throwable thrown) {
    }
    
    public void logError(final Object msg) {
    }
    
    public void logError(final Object msg, final Throwable thrown) {
    }
    
    public void logFatal(final Object msg) {
    }
    
    public void logFatal(final Object msg, final Throwable thrown) {
    }
    
    public void logInfo(final Object msg) {
    }
    
    public void logInfo(final Object msg, final Throwable thrown) {
    }
    
    public void logTrace(final Object msg) {
    }
    
    public void logTrace(final Object msg, final Throwable thrown) {
    }
    
    public void logWarn(final Object msg) {
    }
    
    public void logWarn(final Object msg, final Throwable thrown) {
    }
}
