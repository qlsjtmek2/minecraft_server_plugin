// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction.log;

import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;

public class FileTransactionLoggerWrapper implements TransactionLogWriter
{
    private static final Logger logger;
    private final String serverName;
    private final String dir;
    private final int maxFileSize;
    private volatile FileTransactionLogger logWriter;
    
    public FileTransactionLoggerWrapper(final ServerConfig serverConfig) {
        final String evalDir = serverConfig.getLoggingDirectoryWithEval();
        this.dir = ((evalDir != null) ? evalDir : "logs");
        this.maxFileSize = GlobalProperties.getInt("ebean.logging.maxFileSize", 104857600);
        this.serverName = serverConfig.getName();
    }
    
    private FileTransactionLogger initialiseLogger() {
        synchronized (this) {
            final FileTransactionLogger writer = this.logWriter;
            if (writer != null) {
                return writer;
            }
            final String middleName = GlobalProperties.get("ebean.logging.filename", "_txn_");
            final String logPrefix = this.serverName + middleName;
            final String threadName = "Ebean-" + this.serverName + "-TxnLogWriter";
            final FileTransactionLogger newLogWriter = new FileTransactionLogger(threadName, this.dir, logPrefix, this.maxFileSize);
            (this.logWriter = newLogWriter).start();
            FileTransactionLoggerWrapper.logger.info("Transaction logs in: " + this.dir);
            return newLogWriter;
        }
    }
    
    public void log(final TransactionLogBuffer logBuffer) {
        FileTransactionLogger writer = this.logWriter;
        if (writer == null) {
            writer = this.initialiseLogger();
        }
        writer.log(logBuffer);
    }
    
    public void shutdown() {
        if (this.logWriter != null) {
            this.logWriter.shutdown();
        }
    }
    
    static {
        logger = Logger.getLogger(FileTransactionLoggerWrapper.class.getName());
    }
}
