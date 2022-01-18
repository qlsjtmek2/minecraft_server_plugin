// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc;

import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.util.logging.Logger;
import org.bukkit.craftbukkit.v1_5_R3.Spigot;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public class WatchdogThread extends Thread
{
    private static WatchdogThread instance;
    private final long timeoutTime;
    private final boolean restart;
    private volatile long lastTick;
    private volatile boolean stopping;
    
    private WatchdogThread(final long timeoutTime, final boolean restart) {
        super("Spigot Watchdog Thread");
        this.timeoutTime = timeoutTime;
        this.restart = restart;
    }
    
    public static void doStart(final int timeoutTime, final boolean restart) {
        if (WatchdogThread.instance == null) {
            (WatchdogThread.instance = new WatchdogThread(timeoutTime * 1000L, restart)).start();
        }
    }
    
    public static void tick() {
        WatchdogThread.instance.lastTick = System.currentTimeMillis();
    }
    
    public static void doStop() {
        if (WatchdogThread.instance != null) {
            WatchdogThread.instance.stopping = true;
        }
    }
    
    public void run() {
        while (!this.stopping) {
            if (this.lastTick != 0L && System.currentTimeMillis() > this.lastTick + this.timeoutTime) {
                final Logger log = Bukkit.getServer().getLogger();
                log.log(Level.SEVERE, "The server has stopped responding!");
                log.log(Level.SEVERE, "Please report this to http://www.spigotmc.org/");
                log.log(Level.SEVERE, "Be sure to include ALL relevant console errors and Minecraft crash reports");
                log.log(Level.SEVERE, "Spigot version: " + Bukkit.getServer().getVersion());
                log.log(Level.SEVERE, "Current Thread State:");
                final ThreadInfo[] arr$;
                final ThreadInfo[] threads = arr$ = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
                for (final ThreadInfo thread : arr$) {
                    if (thread.getThreadState() != State.WAITING) {
                        log.log(Level.SEVERE, "------------------------------");
                        log.log(Level.SEVERE, "Current Thread: " + thread.getThreadName());
                        log.log(Level.SEVERE, "\tPID: " + thread.getThreadId() + " | Suspended: " + thread.isSuspended() + " | Native: " + thread.isInNative() + " | State: " + thread.getThreadState());
                        if (thread.getLockedMonitors().length != 0) {
                            log.log(Level.SEVERE, "\tThread is waiting on monitor(s):");
                            for (final MonitorInfo monitor : thread.getLockedMonitors()) {
                                log.log(Level.SEVERE, "\t\tLocked on:" + monitor.getLockedStackFrame());
                            }
                        }
                        log.log(Level.SEVERE, "\tStack:");
                        final StackTraceElement[] stack = thread.getStackTrace();
                        for (int line = 0; line < stack.length; ++line) {
                            log.log(Level.SEVERE, "\t\t" + stack[line].toString());
                        }
                    }
                }
                log.log(Level.SEVERE, "------------------------------");
                if (this.restart) {
                    Spigot.restart();
                    break;
                }
                break;
            }
            else {
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException ex) {
                    this.interrupt();
                }
            }
        }
    }
}
