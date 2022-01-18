// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction.log;

import java.util.logging.Level;
import java.util.List;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;

public class JuliTransactionLogger implements TransactionLogWriter
{
    private static Logger logger;
    
    public void log(final TransactionLogBuffer logBuffer) {
        final String txnId = logBuffer.getTransactionId();
        final List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
        for (int i = 0; i < messages.size(); ++i) {
            final TransactionLogBuffer.LogEntry logEntry = messages.get(i);
            this.log(txnId, logEntry);
        }
    }
    
    public void shutdown() {
    }
    
    private void log(final String txnId, final TransactionLogBuffer.LogEntry entry) {
        String message = entry.getMsg();
        if (txnId != null && message != null && !message.startsWith("Trans[")) {
            message = "Trans[" + txnId + "] " + message;
        }
        JuliTransactionLogger.logger.log(Level.INFO, message);
    }
    
    static {
        JuliTransactionLogger.logger = Logger.getLogger(JuliTransactionLogger.class.getName());
    }
}
