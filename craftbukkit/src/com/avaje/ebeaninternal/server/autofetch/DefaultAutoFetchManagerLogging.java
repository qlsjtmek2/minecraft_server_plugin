// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import java.util.logging.Level;
import com.avaje.ebean.config.AutofetchConfig;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.transaction.log.SimpleLogger;
import java.util.logging.Logger;

public class DefaultAutoFetchManagerLogging
{
    private static final Logger logger;
    private final SimpleLogger fileLogger;
    private final DefaultAutoFetchManager manager;
    private final boolean useFileLogger;
    private final boolean traceUsageCollection;
    
    public DefaultAutoFetchManagerLogging(final ServerConfig serverConfig, final DefaultAutoFetchManager profileListener) {
        this.manager = profileListener;
        final AutofetchConfig autofetchConfig = serverConfig.getAutofetchConfig();
        this.traceUsageCollection = GlobalProperties.getBoolean("ebean.autofetch.traceUsageCollection", false);
        if (!(this.useFileLogger = autofetchConfig.isUseFileLogging())) {
            this.fileLogger = null;
        }
        else {
            final String baseDir = serverConfig.getLoggingDirectoryWithEval();
            this.fileLogger = new SimpleLogger(baseDir, "autofetch", true, "csv");
        }
        final int updateFreqInSecs = autofetchConfig.getProfileUpdateFrequency();
        BackgroundThread.add(updateFreqInSecs, new UpdateProfile());
    }
    
    public void logError(final Level level, final String msg, final Throwable e) {
        if (this.useFileLogger) {
            final String errMsg = (e == null) ? "" : e.getMessage();
            this.fileLogger.log("\"Error\",\"" + msg + " " + errMsg + "\",,,,");
        }
        DefaultAutoFetchManagerLogging.logger.log(level, msg, e);
    }
    
    public void logToJavaLogger(final String msg) {
        DefaultAutoFetchManagerLogging.logger.info(msg);
    }
    
    public void logSummary(final String summaryInfo) {
        final String msg = "\"Summary\",\"" + summaryInfo + "\",,,,";
        if (this.useFileLogger) {
            this.fileLogger.log(msg);
        }
        DefaultAutoFetchManagerLogging.logger.fine(msg);
    }
    
    public void logChanged(final TunedQueryInfo tunedFetch, final OrmQueryDetail newQueryDetail) {
        final String msg = tunedFetch.getLogOutput(newQueryDetail);
        if (this.useFileLogger) {
            this.fileLogger.log(msg);
        }
        else {
            DefaultAutoFetchManagerLogging.logger.fine(msg);
        }
    }
    
    public void logNew(final TunedQueryInfo tunedFetch) {
        final String msg = tunedFetch.getLogOutput(null);
        if (this.useFileLogger) {
            this.fileLogger.log(msg);
        }
        else {
            DefaultAutoFetchManagerLogging.logger.fine(msg);
        }
    }
    
    public boolean isTraceUsageCollection() {
        return this.traceUsageCollection;
    }
    
    static {
        logger = Logger.getLogger(DefaultAutoFetchManagerLogging.class.getName());
    }
    
    private final class UpdateProfile implements Runnable
    {
        public void run() {
            DefaultAutoFetchManagerLogging.this.manager.updateTunedQueryInfo();
        }
    }
}
