// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.hanging;

import org.bukkit.entity.Hanging;
import org.bukkit.entity.Entity;

public class HangingBreakByEntityEvent extends HangingBreakEvent
{
    private final Entity remover;
    
    public HangingBreakByEntityEvent(final Hanging hanging, final Entity remover) {
        super(hanging, RemoveCause.ENTITY);
        this.remover = remover;
    }
    
    public Entity getRemover() {
        return this.remover;
    }
}
