// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.painting;

import org.bukkit.entity.Painting;
import org.bukkit.entity.Entity;
import org.bukkit.Warning;

@Deprecated
@Warning(reason = "This event has been replaced by HangingBreakByEntityEvent")
public class PaintingBreakByEntityEvent extends PaintingBreakEvent
{
    private final Entity remover;
    
    public PaintingBreakByEntityEvent(final Painting painting, final Entity remover) {
        super(painting, RemoveCause.ENTITY);
        this.remover = remover;
    }
    
    public Entity getRemover() {
        return this.remover;
    }
}
