// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.painting;

import org.bukkit.entity.Painting;
import org.bukkit.event.HandlerList;
import org.bukkit.Warning;
import org.bukkit.event.Cancellable;

@Deprecated
@Warning(reason = "This event has been replaced by HangingBreakEvent")
public class PaintingBreakEvent extends PaintingEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final RemoveCause cause;
    
    public PaintingBreakEvent(final Painting painting, final RemoveCause cause) {
        super(painting);
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
        return PaintingBreakEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PaintingBreakEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum RemoveCause
    {
        ENTITY, 
        FIRE, 
        OBSTRUCTION, 
        WATER, 
        PHYSICS;
    }
}
