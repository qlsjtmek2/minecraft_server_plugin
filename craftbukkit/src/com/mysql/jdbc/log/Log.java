// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.log;

public interface Log
{
    boolean isDebugEnabled();
    
    boolean isErrorEnabled();
    
    boolean isFatalEnabled();
    
    boolean isInfoEnabled();
    
    boolean isTraceEnabled();
    
    boolean isWarnEnabled();
    
    void logDebug(final Object p0);
    
    void logDebug(final Object p0, final Throwable p1);
    
    void logError(final Object p0);
    
    void logError(final Object p0, final Throwable p1);
    
    void logFatal(final Object p0);
    
    void logFatal(final Object p0, final Throwable p1);
    
    void logInfo(final Object p0);
    
    void logInfo(final Object p0, final Throwable p1);
    
    void logTrace(final Object p0);
    
    void logTrace(final Object p0, final Throwable p1);
    
    void logWarn(final Object p0);
    
    void logWarn(final Object p0, final Throwable p1);
}
