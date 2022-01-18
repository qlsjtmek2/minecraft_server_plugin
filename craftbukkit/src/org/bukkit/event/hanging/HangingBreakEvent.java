// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.hanging;

import org.bukkit.entity.Hanging;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class HangingBreakEvent extends HangingEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final RemoveCause cause;
    
    public HangingBreakEvent(final Hanging hanging, final RemoveCause cause) {
        super(hanging);
        this.cause = cause;
    }
    
    public RemoveCause getCause() {
        return this.cause;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return HangingBreakEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return HangingBreakEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum RemoveCause
    {
        ENTITY, 
        EXPLOSION, 
        OBSTRUCTION, 
        PHYSICS, 
        DEFAULT;
    }
}
