// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class SpawnChangeEvent extends WorldEvent
{
    private static final HandlerList handlers;
    private final Location previousLocation;
    
    public SpawnChangeEvent(final World world, final Location previousLocation) {
        super(world);
        this.previousLocation = previousLocation;
    }
    
    public Location getPreviousLocation() {
        return this.previousLocation;
    }
    
    public HandlerList getHandlers() {
        return SpawnChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return SpawnChangeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
