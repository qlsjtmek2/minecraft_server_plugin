// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.transaction.log.FileTransactionLoggerWrapper;
import com.avaje.ebeaninternal.server.transaction.log.JuliTransactionLogger;
import com.avaje.ebean.config.ServerConfig;

public class TransactionLogManager
{
    private final TransactionLogWriter logWriter;
    
    public TransactionLogManager(final ServerConfig serverConfig) {
        if (serverConfig.isLoggingToJavaLogger()) {
            this.logWriter = new JuliTransactionLogger();
        }
        else {
            this.logWriter = new FileTransactionLoggerWrapper(serverConfig);
        }
    }
    
    public void shutdown() {
        this.logWriter.shutdown();
    }
    
    public void log(final TransactionLogBuffer logBuffer) {
        this.logWriter.log(logBuffer);
    }
}
