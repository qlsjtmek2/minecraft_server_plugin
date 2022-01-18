// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class WorldUnloadEvent extends WorldEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean isCancelled;
    
    public WorldUnloadEvent(final World world) {
        super(world);
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return WorldUnloadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return WorldUnloadEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
