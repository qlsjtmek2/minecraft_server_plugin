// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import org.bukkit.event.EventException;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;

public class TimedRegisteredListener extends RegisteredListener
{
    private int count;
    private long totalTime;
    public long curTickTotal;
    public long violations;
    private Event event;
    private boolean multiple;
    
    public TimedRegisteredListener(final Listener pluginListener, final EventExecutor eventExecutor, final EventPriority eventPriority, final Plugin registeredPlugin, final boolean listenCancelled) {
        super(pluginListener, eventExecutor, eventPriority, registeredPlugin, listenCancelled);
        this.curTickTotal = 0L;
        this.violations = 0L;
        this.multiple = false;
    }
    
    public void callEvent(final Event event) throws EventException {
        if (!Bukkit.getServer().getPluginManager().useTimings()) {
            super.callEvent(event);
            return;
        }
        if (event.isAsynchronous()) {
            super.callEvent(event);
            return;
        }
        ++this.count;
        if (this.event == null) {
            this.event = event;
        }
        else if (!this.event.getClass().equals(event.getClass())) {
            this.multiple = true;
        }
        final long start = System.nanoTime();
        super.callEvent(event);
        final long diff = System.nanoTime() - start;
        this.curTickTotal += diff;
        this.totalTime += diff;
    }
    
    public void reset() {
        this.count = 0;
        this.totalTime = 0L;
        this.curTickTotal = 0L;
        this.violations = 0L;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public long getTotalTime() {
        return this.totalTime;
    }
    
    public Event getEvent() {
        return this.event;
    }
    
    public boolean hasMultiple() {
        return this.multiple;
    }
}
