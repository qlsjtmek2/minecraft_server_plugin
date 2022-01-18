// 
// Decompiled by Procyon v0.5.30
// 

package org.spigotmc;

import java.util.HashSet;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.TimedRegisteredListener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.event.HandlerList;
import java.util.Iterator;
import org.bukkit.World;
import org.bukkit.Bukkit;
import java.io.PrintStream;
import java.util.Collection;

public class CustomTimingsHandler
{
    private static final Collection<CustomTimingsHandler> ALL_HANDLERS;
    private static CustomTimingsHandler[] BAKED_HANDLERS;
    private final String name;
    private final CustomTimingsHandler parent;
    private long count;
    private long start;
    private long timingDepth;
    private long totalTime;
    private long curTickTotal;
    private long violations;
    
    public CustomTimingsHandler(final String name) {
        this(name, null);
    }
    
    public CustomTimingsHandler(final String name, final CustomTimingsHandler parent) {
        this.count = 0L;
        this.start = 0L;
        this.timingDepth = 0L;
        this.totalTime = 0L;
        this.curTickTotal = 0L;
        this.violations = 0L;
        this.name = name;
        this.parent = parent;
        CustomTimingsHandler.ALL_HANDLERS.add(this);
        CustomTimingsHandler.BAKED_HANDLERS = CustomTimingsHandler.ALL_HANDLERS.toArray(new CustomTimingsHandler[CustomTimingsHandler.ALL_HANDLERS.size()]);
    }
    
    public static void printTimings(final PrintStream printStream) {
        printStream.println("Minecraft");
        for (final CustomTimingsHandler timings : CustomTimingsHandler.BAKED_HANDLERS) {
            final long time = timings.totalTime;
            final long count = timings.count;
            if (count != 0L) {
                final long avg = time / count;
                printStream.println("    " + timings.name + " Time: " + time + " Count: " + count + " Avg: " + avg + " Violations: " + timings.violations);
            }
        }
        printStream.println("# Version " + Bukkit.getVersion());
        int entities = 0;
        int livingEntities = 0;
        for (final World world : Bukkit.getWorlds()) {
            entities += world.getEntities().size();
            livingEntities += world.getLivingEntities().size();
        }
        printStream.println("# Entities " + entities);
        printStream.println("# LivingEntities " + livingEntities);
    }
    
    public static void reload() {
        if (Bukkit.getPluginManager().useTimings()) {
            for (final CustomTimingsHandler timings : CustomTimingsHandler.BAKED_HANDLERS) {
                timings.reset();
            }
        }
    }
    
    public static void tick() {
        if (Bukkit.getPluginManager().useTimings()) {
            for (final CustomTimingsHandler timings : CustomTimingsHandler.BAKED_HANDLERS) {
                if (timings.curTickTotal > 50000000L) {
                    final CustomTimingsHandler customTimingsHandler = timings;
                    customTimingsHandler.violations += (long)Math.ceil(timings.curTickTotal / 50000000L);
                }
                timings.curTickTotal = 0L;
            }
            for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                for (final RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                    if (listener instanceof TimedRegisteredListener) {
                        final TimedRegisteredListener timings2 = (TimedRegisteredListener)listener;
                        if (timings2.curTickTotal > 50000000L) {
                            final TimedRegisteredListener timedRegisteredListener = timings2;
                            timedRegisteredListener.violations += (long)Math.ceil(timings2.curTickTotal / 50000000L);
                        }
                        timings2.curTickTotal = 0L;
                    }
                }
            }
        }
    }
    
    public void startTiming() {
        if (Bukkit.getPluginManager().useTimings()) {
            final long timingDepth = this.timingDepth + 1L;
            this.timingDepth = timingDepth;
            if (timingDepth == 1L) {
                this.start = System.nanoTime();
                if (this.parent != null) {
                    final CustomTimingsHandler parent = this.parent;
                    final long timingDepth2 = parent.timingDepth + 1L;
                    parent.timingDepth = timingDepth2;
                    if (timingDepth2 == 1L) {
                        this.parent.start = this.start;
                    }
                }
            }
        }
    }
    
    public void stopTiming() {
        if (Bukkit.getPluginManager().useTimings()) {
            final long timingDepth = this.timingDepth - 1L;
            this.timingDepth = timingDepth;
            if (timingDepth != 0L || this.start == 0L) {
                return;
            }
            final long diff = System.nanoTime() - this.start;
            this.totalTime += diff;
            this.curTickTotal += diff;
            ++this.count;
            this.start = 0L;
            if (this.parent != null) {
                this.parent.stopTiming();
            }
        }
    }
    
    public void reset() {
        this.count = 0L;
        this.violations = 0L;
        this.curTickTotal = 0L;
        this.totalTime = 0L;
    }
    
    static {
        ALL_HANDLERS = new HashSet<CustomTimingsHandler>();
    }
}
