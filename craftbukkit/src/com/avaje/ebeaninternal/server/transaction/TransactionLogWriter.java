// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

public interface TransactionLogWriter
{
    void log(final TransactionLogBuffer p0);
    
    void shutdown();
}
