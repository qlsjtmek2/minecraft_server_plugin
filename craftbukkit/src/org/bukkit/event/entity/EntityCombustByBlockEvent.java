// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;

public class EntityCombustByBlockEvent extends EntityCombustEvent
{
    private final Block combuster;
    
    public EntityCombustByBlockEvent(final Block combuster, final Entity combustee, final int duration) {
        super(combustee, duration);
        this.combuster = combuster;
    }
    
    public Block getCombuster() {
        return this.combuster;
    }
}
