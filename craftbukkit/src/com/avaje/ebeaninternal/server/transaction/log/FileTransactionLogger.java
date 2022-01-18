// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction.log;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.io.PrintStream;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;

public class FileTransactionLogger implements Runnable, TransactionLogWriter
{
    private static final Logger logger;
    private static final String atString = "        at ";
    private final String newLinePlaceholder = "\\r\\n";
    private final int maxStackTraceLines = 5;
    private final ConcurrentLinkedQueue<TransactionLogBuffer> logBufferQueue;
    private final Thread logWriterThread;
    private final String threadName;
    private final String filepath;
    private final String deliminator = ", ";
    private final String logFileName;
    private final String logFileSuffix;
    private volatile boolean shutdown;
    private volatile boolean shutdownComplete;
    private PrintStream out;
    private String currentPath;
    private int fileCounter;
    private long maxBytesPerFile;
    private long bytesWritten;
    
    public FileTransactionLogger(final String threadName, final String dir, final String logFileName, final int maxBytesPerFile) {
        this(threadName, dir, logFileName, "log", maxBytesPerFile);
    }
    
    public FileTransactionLogger(final String threadName, final String dir, final String logFileName, final String suffix, final int maxBytesPerFile) {
        this.logBufferQueue = new ConcurrentLinkedQueue<TransactionLogBuffer>();
        this.threadName = threadName;
        this.logFileName = logFileName;
        this.logFileSuffix = "." + suffix;
        this.maxBytesPerFile = maxBytesPerFile;
        try {
            this.filepath = this.makeDirIfRequired(dir);
            this.switchFile(LogTime.nextDay());
        }
        catch (Exception e) {
            System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            throw new RuntimeException(e);
        }
        (this.logWriterThread = new Thread(this, threadName)).setDaemon(true);
    }
    
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
    
    public void start() {
        this.logWriterThread.start();
    }
    
    public void shutdown() {
        this.shutdown = true;
        synchronized (this.logWriterThread) {
            try {
                this.logWriterThread.wait(20000L);
                FileTransactionLogger.logger.fine("Shutdown LogBufferWriter " + this.threadName + " shutdownComplete:" + this.shutdownComplete);
            }
            catch (InterruptedException e) {
                FileTransactionLogger.logger.fine("InterruptedException:" + e);
            }
        }
        if (!this.shutdownComplete) {
            final String m = "WARNING: Shutdown of LogBufferWriter " + this.threadName + " not completed.";
            System.err.println(m);
            FileTransactionLogger.logger.warning(m);
        }
    }
    
    public void run() {
        int missCount = 0;
        while (!this.shutdown || missCount < 10) {
            if (missCount > 50) {
                if (this.out != null) {
                    this.out.flush();
                }
                try {
                    Thread.sleep(20L);
                }
                catch (InterruptedException e) {
                    FileTransactionLogger.logger.log(Level.INFO, "Interrupted TxnLogBufferWriter", e);
                }
            }
            synchronized (this.logBufferQueue) {
                if (this.logBufferQueue.isEmpty()) {
                    ++missCount;
                }
                else {
                    final TransactionLogBuffer buffer = this.logBufferQueue.remove();
                    this.write(buffer);
                    missCount = 0;
                }
            }
        }
        this.close();
        this.shutdownComplete = true;
        synchronized (this.logWriterThread) {
            this.logWriterThread.notifyAll();
        }
    }
    
    public void log(final TransactionLogBuffer logBuffer) {
        this.logBufferQueue.add(logBuffer);
    }
    
    private void write(final TransactionLogBuffer logBuffer) {
        LogTime logTime = LogTime.get();
        if (logTime.isNextDay()) {
            logTime = LogTime.nextDay();
            this.switchFile(logTime);
        }
        if (this.bytesWritten > this.maxBytesPerFile) {
            ++this.fileCounter;
            this.switchFile(logTime);
        }
        final String txnId = logBuffer.getTransactionId();
        final List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
        for (int i = 0; i < messages.size(); ++i) {
            final TransactionLogBuffer.LogEntry msg = messages.get(i);
            this.printMessage(logTime, txnId, msg);
        }
    }
    
    private void printMessage(final LogTime logTime, final String txnId, final TransactionLogBuffer.LogEntry logEntry) {
        final String msg = logEntry.getMsg();
        final int len = msg.length();
        if (len == 0) {
            return;
        }
        this.bytesWritten += 16L;
        this.bytesWritten += len;
        if (txnId != null) {
            this.bytesWritten += 7L;
            this.bytesWritten += txnId.length();
            this.out.append("txn[");
            this.out.append(txnId);
            this.out.append("]");
            this.out.append(", ");
        }
        this.out.append(logTime.getTimestamp(logEntry.getTimestamp()));
        this.out.append(", ");
        if (msg != null) {
            this.out.append(msg).append(" ");
        }
        this.out.append("\n");
    }
    
    protected void printThrowable(final StringBuilder sb, final Throwable e, final boolean isCause) {
        if (e != null) {
            if (isCause) {
                sb.append("Caused by: ");
            }
            sb.append(e.getClass().getName());
            sb.append(":");
            sb.append(e.getMessage()).append("\\r\\n");
            final StackTraceElement[] ste = e.getStackTrace();
            int outputStackLines = ste.length;
            int notShownCount = 0;
            if (ste.length > 5) {
                outputStackLines = 5;
                notShownCount = ste.length - outputStackLines;
            }
            for (int i = 0; i < outputStackLines; ++i) {
                sb.append("        at ");
                sb.append(ste[i].toString()).append("\\r\\n");
            }
            if (notShownCount > 0) {
                sb.append("        ... ");
                sb.append(notShownCount);
                sb.append(" more").append("\\r\\n");
            }
            final Throwable cause = e.getCause();
            if (cause != null) {
                this.printThrowable(sb, cause, true);
            }
        }
    }
    
    private String newFileName(final LogTime logTime) {
        return this.filepath + File.separator + this.logFileName + logTime.getYMD() + "-" + this.fileCounter + this.logFileSuffix;
    }
    
    protected void switchFile(final LogTime logTime) {
        try {
            long currentFileLength = 0L;
            String newFilePath = null;
            do {
                newFilePath = this.newFileName(logTime);
                final File f = new File(newFilePath);
                if (!f.exists()) {
                    currentFileLength = 0L;
                }
                else if (f.length() < this.maxBytesPerFile * 0.8) {
                    currentFileLength = f.length();
                }
                else {
                    ++this.fileCounter;
                    newFilePath = null;
                }
            } while (newFilePath == null);
            if (!newFilePath.equals(this.currentPath)) {
                final PrintStream newOut = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, true)));
                this.close();
                this.bytesWritten = currentFileLength;
                this.currentPath = newFilePath;
                this.out = newOut;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            FileTransactionLogger.logger.log(Level.SEVERE, "Error switch log file", e);
        }
    }
    
    private void close() {
        if (this.out != null) {
            this.out.flush();
            this.out.close();
        }
    }
    
    protected String makeDirIfRequired(final String dir) {
        final File f = new File(dir);
        if (f.exists()) {
            if (!f.isDirectory()) {
                final String msg = "Transaction logs directory is a file? " + dir;
                throw new PersistenceException(msg);
            }
        }
        else if (!f.mkdirs()) {
            final String msg = "Failed to create transaction logs directory " + dir;
            FileTransactionLogger.logger.log(Level.SEVERE, msg);
        }
        return dir;
    }
    
    static {
        logger = Logger.getLogger(FileTransactionLogger.class.getName());
    }
}
