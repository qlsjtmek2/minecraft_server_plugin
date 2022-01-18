// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

import java.util.Enumeration;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebean.config.GlobalProperties;
import java.util.logging.Level;
import com.avaje.ebean.common.BootupEbeanManager;
import java.util.Vector;
import java.util.logging.Logger;

public final class ShutdownManager
{
    private static final Logger logger;
    static final Vector<Runnable> runnables;
    static boolean stopping;
    static BootupEbeanManager serverFactory;
    static final ShutdownHook shutdownHook;
    static boolean whyShutdown;
    
    public static void registerServerFactory(final BootupEbeanManager factory) {
        ShutdownManager.serverFactory = factory;
    }
    
    public static void touch() {
    }
    
    public static boolean isStopping() {
        synchronized (ShutdownManager.runnables) {
            return ShutdownManager.stopping;
        }
    }
    
    private static void deregister() {
        synchronized (ShutdownManager.runnables) {
            try {
                Runtime.getRuntime().removeShutdownHook(ShutdownManager.shutdownHook);
            }
            catch (IllegalStateException ex) {
                if (!ex.getMessage().equals("Shutdown in progress")) {
                    throw ex;
                }
            }
        }
    }
    
    private static void register() {
        synchronized (ShutdownManager.runnables) {
            try {
                Runtime.getRuntime().addShutdownHook(ShutdownManager.shutdownHook);
            }
            catch (IllegalStateException ex) {
                if (!ex.getMessage().equals("Shutdown in progress")) {
                    throw ex;
                }
            }
        }
    }
    
    public static void shutdown() {
        synchronized (ShutdownManager.runnables) {
            if (ShutdownManager.stopping) {
                return;
            }
            if (ShutdownManager.whyShutdown) {
                try {
                    throw new RuntimeException("debug.shutdown.why=true ...");
                }
                catch (Throwable e) {
                    ShutdownManager.logger.log(Level.WARNING, "Stacktrace showing why shutdown was fired", e);
                }
            }
            ShutdownManager.stopping = true;
            deregister();
            BackgroundThread.shutdown();
            final String shutdownRunner = GlobalProperties.get("system.shutdown.runnable", null);
            if (shutdownRunner != null) {
                try {
                    final Runnable r = (Runnable)ClassUtil.newInstance(shutdownRunner);
                    r.run();
                }
                catch (Exception e2) {
                    ShutdownManager.logger.log(Level.SEVERE, null, e2);
                }
            }
            final Enumeration<Runnable> e3 = ShutdownManager.runnables.elements();
            while (e3.hasMoreElements()) {
                try {
                    final Runnable r2 = e3.nextElement();
                    r2.run();
                }
                catch (Exception ex) {
                    ShutdownManager.logger.log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
            try {
                if (ShutdownManager.serverFactory != null) {
                    ShutdownManager.serverFactory.shutdown();
                }
                ThreadPoolManager.shutdown();
                DataSourceGlobalManager.shutdown();
            }
            catch (Exception ex) {
                final String msg = "Shutdown Exception: " + ex.getMessage();
                System.err.println(msg);
                ex.printStackTrace();
                try {
                    ShutdownManager.logger.log(Level.SEVERE, null, ex);
                }
                catch (Exception exc) {
                    final String ms = "Error Logging error to the Log. It may be shutting down.";
                    System.err.println(ms);
                    exc.printStackTrace();
                }
            }
        }
    }
    
    public static void register(final Runnable runnable) {
        synchronized (ShutdownManager.runnables) {
            ShutdownManager.runnables.add(runnable);
        }
    }
    
    static {
        logger = Logger.getLogger(BackgroundThread.class.getName());
        runnables = new Vector<Runnable>();
        shutdownHook = new ShutdownHook();
        register();
        ShutdownManager.whyShutdown = GlobalProperties.getBoolean("debug.shutdown.why", false);
    }
}
