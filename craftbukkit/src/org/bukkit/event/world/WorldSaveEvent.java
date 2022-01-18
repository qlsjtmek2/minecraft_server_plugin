// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.HandlerList;

public class WorldSaveEvent extends WorldEvent
{
    private static final HandlerList handlers;
    
    public WorldSaveEvent(final World world) {
        super(world);
    }
    
    public HandlerList getHandlers() {
        return WorldSaveEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return WorldSaveEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
