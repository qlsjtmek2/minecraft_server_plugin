// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.common.annotations.VisibleForTesting;

public final class UncaughtExceptionHandlers
{
    public static Thread.UncaughtExceptionHandler systemExit() {
        return new Exiter(Runtime.getRuntime());
    }
    
    @VisibleForTesting
    static final class Exiter implements Thread.UncaughtExceptionHandler
    {
        private static final Logger logger;
        private final Runtime runtime;
        
        Exiter(final Runtime runtime) {
            this.runtime = runtime;
        }
        
        public void uncaughtException(final Thread t, final Throwable e) {
            Exiter.logger.log(Level.SEVERE, String.format("Caught an exception in %s.  Shutting down.", t), e);
            this.runtime.exit(1);
        }
        
        static {
            logger = Logger.getLogger(Exiter.class.getName());
        }
    }
}
