// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction.log;

import java.util.logging.Level;
import javax.persistence.PersistenceException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.logging.Logger;

public class SimpleLogger
{
    private static final Logger logger;
    private static final String atString = "        at ";
    private PrintStream out;
    private boolean doAppend;
    private boolean open;
    private String currentPath;
    private final String filepath;
    private final boolean useFileSwitching;
    private final int maxStackTraceLines = 5;
    private final String deliminator;
    private final Object fileMonitor;
    private final String logFileName;
    private final String logFileSuffix;
    private final String newLineChar = "\\r\\n";
    private final boolean csv;
    
    public SimpleLogger(final String dir, final String logFileName, final boolean useFileSwitching, final String suffix) {
        this.doAppend = true;
        this.open = true;
        this.fileMonitor = new Object();
        this.logFileName = logFileName;
        this.useFileSwitching = useFileSwitching;
        this.logFileSuffix = "." + suffix;
        this.csv = "csv".equalsIgnoreCase(suffix);
        this.deliminator = (this.csv ? "," : ", ");
        try {
            this.filepath = this.makeDirIfRequired(dir);
            this.switchFile(LogTime.nextDay());
        }
        catch (Exception e) {
            System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public SimpleLogger(final String dir, final String logFileName, final boolean useFileSwitching) {
        this(dir, logFileName, useFileSwitching, "log");
    }
    
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
    
    public void close() {
        if (this.open) {
            this.out.flush();
            this.out.close();
            this.open = false;
        }
    }
    
    public void log(final String msg) {
        this.log(null, msg, null);
    }
    
    public void log(final String msg, final Throwable e) {
        this.log(null, msg, e);
    }
    
    public void log(final String transId, final String msg, final Throwable e) {
        LogTime logTime = LogTime.get();
        if (logTime.isNextDay()) {
            logTime = LogTime.nextDay();
            try {
                this.switchFile(logTime);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        int roughSize = 40;
        if (msg != null) {
            roughSize += msg.length();
        }
        if (e != null) {
            roughSize += 200;
        }
        final StringBuilder line = new StringBuilder(roughSize);
        if (transId != null) {
            line.append("trans[").append(transId).append("]").append(this.deliminator);
        }
        if (this.csv) {
            line.append("\"'");
        }
        line.append(logTime.getNow());
        if (this.csv) {
            line.append("'\"");
        }
        line.append(this.deliminator);
        if (msg != null) {
            line.append(msg).append(" ");
        }
        this.printThrowable(line, e, false);
        final String lineString = line.toString();
        synchronized (this.fileMonitor) {
            this.out.println(lineString);
            this.out.flush();
        }
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
    
    protected void switchFile(final LogTime logTime) throws Exception {
        String newFilePath = this.filepath + File.separator + this.logFileName;
        if (this.useFileSwitching) {
            newFilePath = newFilePath + logTime.getYMD() + this.logFileSuffix;
        }
        else {
            newFilePath += this.logFileSuffix;
        }
        synchronized (this.fileMonitor) {
            if (!newFilePath.equals(this.currentPath)) {
                this.currentPath = newFilePath;
                this.out = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, this.doAppend)));
            }
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
            SimpleLogger.logger.log(Level.SEVERE, msg);
        }
        return dir;
    }
    
    static {
        logger = Logger.getLogger(SimpleLogger.class.getName());
    }
}
