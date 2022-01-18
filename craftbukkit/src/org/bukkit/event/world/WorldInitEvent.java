// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class WorldInitEvent extends WorldEvent
{
    private static final HandlerList handlers;
    
    public WorldInitEvent(final World world) {
        super(world);
    }
    
    public HandlerList getHandlers() {
        return WorldInitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return WorldInitEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
